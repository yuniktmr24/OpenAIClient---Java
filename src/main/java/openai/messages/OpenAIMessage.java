package openai.messages;

import openai.OpenAIContentType;
import java.io.Serializable;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author ytamrakar
 * 
 * This is the fundamental constituency of a "message" object sent to OpenAI
 * 
 * Further specialized messages such as a message including an input image for information extraction
 * can have additional fields, such objects can be codified as an extension of this base class while
 * inheriting the fundamental properties from here.
 */
public class OpenAIMessage <T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final OpenAIMessageRole role;
	private final OpenAIContentType contentType;
	private final T content;
	
	public OpenAIMessage(OpenAIMessageRole role, OpenAIContentType contentType, T content) {
		Validate.notNull(role, "Role cannot be null");
		Validate.notNull(content, "ContentType cannot be null");
		this.role = role;
		this.contentType = contentType;
		this.content = content;
	}

	public OpenAIMessageRole getRole() {
		return role;
	}

	public OpenAIContentType getContentType() {
		return contentType;
	}

	public T getContent() {
		return content;
	}
	
}
