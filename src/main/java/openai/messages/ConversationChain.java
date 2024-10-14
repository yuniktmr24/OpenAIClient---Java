package openai.messages;

import openai.OpenAIContentType;
import openai.interfaces.MultiShotPrompting;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ytamrakar
 * Encapsulates list of OpenAIMessages which form a conversation
 */
public class ConversationChain implements MultiShotPrompting <OpenAIMessage>, Serializable {
	private static final long serialVersionUID = 1L;
	private List<OpenAIMessage> messages;
	
	public ConversationChain() {
		this.messages = new ArrayList<>();
	}

	@Override
	public List<OpenAIMessage> getMessages() {
		return messages;
	}

	@Override
	public void addMessage(OpenAIMessage message) {
		this.messages.add(message);
	}

	public void addMessage(OpenAIMessageRole openAIMessageRole, OpenAIContentType openAIContentType,  Object message) {
		this.messages.add(new OpenAIMessage(openAIMessageRole, openAIContentType, message));
	}
	
	public void clear() {
		this.messages.clear();
	}
}
