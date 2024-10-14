package openai.requests;

import openai.messages.SystemMessage;
import openai.OpenAIContentType;
import openai.messages.OpenAIMessage;
import openai.query.OpenAIQuery;
import openai.interfaces.AbstractOpenAIRequestStrategy;
import openai.Endpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ytamrakar
 * Class encapsulating general chat requests to OpenAI (without the need to upload images, etc)
 * Example request: "Please tell me why a potato can be the king of all vegetables"
 */
@Endpoint("/v1/chat/completions")
public class OpenAIGeneralChatRequest extends AbstractOpenAIRequestStrategy<List <SystemMessage>> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Log logger = LogFactory.getLog(OpenAIGeneralChatRequest.class);

	/***
	 * 
	 * @param query : OpenAI Query: contains fields such as user prompt, max_token, top_p, temperature
	 * @param payload: Optional to be used for more advanced use cases, such as when needing to send
	 * multiple system messages to the OpenAI model. 
	 * 
	 * Note: Assistant messages are not included here. That is a further advanced use case
	 * If using system + user + assistant messages together for multi-shot learning
	 * then you may consider utilizing the ChainOfThought.java class
	 * 
	 * Example of system instructions from OpenAI docs
	 * https://platform.openai.com/docs/guides/chat-completions/getting-started
	 * 
	 *	"messages": [
      {
        "role": "system",
        "content": "You are a helpful assistant."
      },
      {
        "role": "user",
        "content": "Who won the world series in 2020?"
      },
      ...
    ]
	 * @return 
	 */
	@Override
	protected Object preparePayload(OpenAIQuery query, List <SystemMessage> payload) {
		Validate.notNull(query, "Query to OpenAI cannot be null");
		// Prepare the request payload. Can also use StringBuilder instead of maps to prepare payload. see ChainOfThought.java
		Map<String, Object> requestBody = new HashMap<>();
		List<Map<String, Object>> messages = new ArrayList<>();
		List<Map<String, Object>> contentList = new ArrayList<>();
		
		if (payload != null) {
			List<Map<String, Object>> systemInstructionList = new ArrayList<>();
			Map<String, Object> instructionContent = new HashMap<>();
			for (OpenAIMessage message: payload) {
				Validate.notNull(message.getContentType(), "Content Type is not null");
				
				instructionContent.put("type", message.getContentType().getContentType());
				instructionContent.put("text", message.getContent());
				
				systemInstructionList.add(instructionContent);
				
				Map <String, Object> instructionMap = new HashMap<>();
				
				instructionMap.put("role", message.getRole().getRole());
				instructionMap.put("content", systemInstructionList);
				
				messages.add(instructionMap);
			}
		}
		
		Map<String, Object> promptContent = new HashMap<>();
		promptContent.put("type", OpenAIContentType.TEXT.getContentType());
		promptContent.put("text", query.getPrompt());

		contentList.add(promptContent);

		Map<String, Object> userMessage = new HashMap<>();
		userMessage.put("role", "user");
		userMessage.put("content", contentList);

		messages.add(userMessage);

		
		requestBody.put("model", query.getOpenAIModel().getModel());
		requestBody.put("messages", messages);
		requestBody.put("max_tokens", query.getMaxTokens());
		requestBody.put("temperature", query.getTemperature());
		requestBody.put("top_p", query.getTopP());

		ObjectMapper objectMapper = new ObjectMapper();
		String payloadJson = "";
		try {
			payloadJson = objectMapper.writeValueAsString(requestBody);
		} catch (JsonProcessingException ex) {
			logger.error("Error converting Vision API Request to json");
		}
		return payloadJson;
	}
}
