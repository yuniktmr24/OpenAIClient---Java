package openai.messages;

/**
 *
 * @author ytamrakar
 * 
 * Each message object sent to OpenAI has a role 
 * (either system, user, or assistant) and content.
 * 
 * The system message is optional and can be used to set the behavior of the assistant
	The user messages provide requests or comments for the assistant to respond to
	Assistant messages store previous assistant responses, but can also be written by you to give examples of desired behavior
 */
public enum OpenAIMessageRole {
	SYSTEM("system"), 
	USER("user"), 
	ASSISTANT("assistant");
	
	private String role;

	private OpenAIMessageRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
