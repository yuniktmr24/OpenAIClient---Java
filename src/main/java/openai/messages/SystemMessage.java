package openai.messages;

import openai.OpenAIContentType;
import java.io.Serializable;

/**
 *
 * @author ytamrakar
 * The system message is optional and can be used to set the behavior of the assistant
 */
public class SystemMessage extends OpenAIMessage <String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public SystemMessage(String content) {
		super(OpenAIMessageRole.SYSTEM, OpenAIContentType.TEXT, content);
	}
}
