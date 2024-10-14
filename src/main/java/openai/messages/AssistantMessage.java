package openai.messages;

import openai.OpenAIContentType;
import java.io.Serializable;

/**
 *
 * @author ytamrakar
 * Assistant messages store previous assistant responses, but can also be written by you to give examples of desired behavior 
 * that is few-shot learning
 */
public class AssistantMessage extends OpenAIMessage<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AssistantMessage(String content) {
		super(OpenAIMessageRole.ASSISTANT, OpenAIContentType.TEXT, content);
	}
	
}
