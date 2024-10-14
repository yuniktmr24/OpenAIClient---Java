package openai.interfaces;

import openai.messages.ConversationChain;
import openai.messages.OpenAIMessage;
import openai.query.OpenAIQuery;
import openai.query.OpenAIQueryFactory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;

/**
 * Utility class for fine tuning models via few shot learning, chain of thought prompting
 * 
 * /***
	 * usage Example
	 *"messages": [
      {
        "role": "system",
        "content": "You are a helpful assistant."
      },
      {
        "role": "user",
        "content": "Who won the world series in 2020?"
      },
      {
        "role": "assistant",
        "content": "The Los Angeles Dodgers won the World Series in 2020."
      },
      {
        "role": "user",
        "content": "Where was it played?"
      }
    ]
	 * @param args 
	 *  *
	* https://platform.openai.com/docs/guides/prompt-engineering/strategy-give-models-time-to-think
*/

public class ChainOfThought extends AbstractOpenAIRequestStrategy<MultiShotPrompting> implements Serializable {
    private static final long serialVersionUID = 1L;
    private OpenAIQuery querySettings;

    public ChainOfThought() {
		this(OpenAIQueryFactory.createOpenAIQuery());
		setEndpoint("/v1/chat/completions");
    }

    public ChainOfThought(OpenAIQuery query) {
        this.querySettings = query;
    }

	@Override
	public String getEndpoint() {
		return super.getEndpoint();
	}

	@Override
	public void setEndpoint(String endpoint) {
		super.setEndpoint(endpoint);
	}
	

	/***
	 * 
	 * @return json payload
	 */
	public String getJsonPayload() {
		return this.toString();
	}

	@SuppressWarnings("unchecked")
    public String toString(MultiShotPrompting messages) {
		Validate.notNull(messages);
		Validate.notNull(messages.getMessages(), "Conversation chain cannot be null");
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("  \"model\": \"").append(querySettings.getOpenAIModel().name().toLowerCase().replace('_', '-')).append("\",\n");

		if (messages instanceof ConversationChain) {
			sb.append("  \"messages\": [\n");
			sb.append(((List <OpenAIMessage>) messages.getMessages())
					.stream()
					.map(this::messageToString)
					.collect(Collectors.joining(",\n")));
			sb.append("\n  ],\n");
		}
		sb.append("  \"max_tokens\": ").append(querySettings.getMaxTokens()).append(",\n");
		sb.append("  \"temperature\": ").append(querySettings.getTemperature()).append(",\n");
		sb.append("  \"top_p\": ").append(querySettings.getTopP()).append("\n");
		sb.append("}");
		return sb.toString();
    }

    private String messageToString(OpenAIMessage message) {
		Validate.notNull(message, "Message cannot be null");
		StringBuilder sb = new StringBuilder();
		sb.append("    {\n");
		sb.append("      \"role\": \"").append(message.getRole().name().toLowerCase()).append("\",\n");
		sb.append("      \"content\": [\n");
		sb.append("        {\n");
		sb.append("          \"type\": \"").append(message.getContentType().name().toLowerCase()).append("\",\n");
		sb.append("          \"text\": \"").append(((String)message.getContent()).replace("\"", "\\\"")).append("\"\n");
		sb.append("        }\n");
		sb.append("      ]\n");
		sb.append("    }");
		return sb.toString();
    }

	@Override
	protected Object preparePayload(OpenAIQuery query, MultiShotPrompting payload) {
		this.querySettings = query;
		return toString(payload);
	}
}