package openai.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ytamrakar
 */
public class OpenAIResponseParser<T> {
	private static final Log logger = LogFactory.getLog(OpenAIResponseParser.class);

	private static final String DEFAULT_NODE_TO_BE_EXTRACTED = "/choices/0/message/content";
	private static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	;

    public T parseJson(String jsonString, Class<T> clazz) {
		return parse(jsonString, clazz, DEFAULT_NODE_TO_BE_EXTRACTED);
	}

	public static <T> T parse(String jsonString, Class<T> clazz) {
		return parse(jsonString, clazz, DEFAULT_NODE_TO_BE_EXTRACTED);
	}

	public static <T> T parse(String jsonString, Class<T> clazz, String pathToTargetNode) {
		try {
			String contentJson = parseAndReturnJson(jsonString, pathToTargetNode);
			return objectMapper.readValue(contentJson, clazz);
		} catch (JsonProcessingException e) {
			logger.error("Error processing response json from OpenAI :" + jsonString);
			logger.error("Error processing response json from OpenAI ", e);
			return null;
		}
	}
	
	public static <T> String parseAndReturnJson(String jsonString) {
		return parseAndReturnJson(jsonString, DEFAULT_NODE_TO_BE_EXTRACTED);
	}

	public static <T> String parseAndReturnJson(String jsonString, String pathToTargetNode) {
		try {
			JsonNode rootNode = objectMapper.readTree(jsonString);
			if (rootNode != null) {
				if (pathToTargetNode != null && !pathToTargetNode.isEmpty()) {
					JsonNode contentNode = rootNode.at(pathToTargetNode);
					if (contentNode != null && contentNode.isTextual()) {
						String contentJson = contentNode.asText();
						contentJson = cleanJson(contentJson);
						return cleanJson(contentJson);
					} else {
						throw new IllegalArgumentException("Invalid JSON structure: content node is not a string" + jsonString);
					}
				}
				return rootNode.toString();
			}
		} catch (JsonProcessingException | IllegalArgumentException e) {
			logger.error("Error processing response json ", e);
		}
		return null;
	}

	private static String cleanJson(String contentJson) {
		// Remove backticks and other unwanted characters
		contentJson = contentJson.replace("```json", "").replace("```", "").trim();
		return contentJson;
	}
}
