package openai.requests;

import openai.OpenAIContentType;
import openai.interfaces.AbstractOpenAIRequestStrategy;
import openai.messages.OpenAIMessageRole;
import openai.query.OpenAIQuery;
import openai.OpenAIVisionPayload;
import openai.Endpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ytamrakar
 * Request class for OpenAI requests that involve computer vision tasks
 * which in turn involves uploading the image along with the prompt.
 * 
 * Example request: Please identify the apartment number in this image. + [uploaded image]
 */
@Endpoint("/v1/chat/completions")
public class OpenAIVisionRequest extends AbstractOpenAIRequestStrategy<OpenAIVisionPayload> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(OpenAIVisionRequest.class);

	@Override
	protected Object preparePayload(OpenAIQuery query, OpenAIVisionPayload payload) {
		// can also use StringBuilder instead of maps to prepare payload. see ChainOfThought.java
		Validate.notNull(payload);
		// Encode the image to Base64
		List<Map<String, Object>> imageContentList = new ArrayList<>();
		for (byte[] pageByte : payload.getPageBytes()) {
			Map<String, Object> imageContent = new HashMap<>();
			try {
				String base64Image = encodeImage(pageByte);
				
				String contentType = OpenAIContentType.IMAGE_URL.getContentType();

				imageContent.put("type", contentType);
				Map<String, String> imageUrl = new HashMap<>();
				imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
				imageContent.put(contentType, imageUrl);
				imageContentList.add(imageContent);
			} catch (IOException ex) {
				logger.error("Error encoding document to base64", ex);
			}
		}

		// Prepare the request payload
		Map<String, Object> textContent = new HashMap<>();
		textContent.put("type", OpenAIContentType.TEXT.getContentType());
		//textContent.put("text", "Return a JSON document (labels in parenthesis) with the plaintiff name (plaintiff), defendant name (defendant), case number (case_number), attorney last name (attorney_last), matter or file number (matter), hearing date (hearing_date), hearing time (hearing_time), a 1-2 word court county (court_county), 1-2 word court district or circuit (court_district), 1 word court type (court_type), court state (court_state), pleading title (pleading), and for the person the document is being served upon get: full name (served_fullname), first name (served_firstname), last name (served_lastname), business name (served_business), street address 1 (served_street1), street address 2 (served_street2), city (served_city), state abbreviation (served_state), zip code (served_zip) and purpose of judgment (Judgment is for)");
		textContent.put("text", query.getPrompt());

		List<Map<String, Object>> contentList = new ArrayList<>();
		contentList.add(textContent);
		contentList.addAll(imageContentList);

		Map<String, Object> userMessage = new HashMap<>();
		userMessage.put("role", OpenAIMessageRole.USER.getRole());
		userMessage.put("content", contentList);

		List<Map<String, Object>> messages = new ArrayList<>();
		messages.add(userMessage);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("model", query.getOpenAIModel().getModel());
		requestBody.put("messages", messages);
		requestBody.put("max_tokens", query.getMaxTokens());

		ObjectMapper objectMapper = new ObjectMapper();
		String payloadJson = "";
		try {
			payloadJson = objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException ex) {
			logger.error("Error converting Vision API Request to json");
		}
		return payloadJson;
	}

	private String encodeImage(byte[] fileBytes) throws IOException {
		return Base64.getEncoder().encodeToString(fileBytes);
	}
}
