package openai;

/**
 *
 * @author ytamrakar
 */
public enum OpenAIContentType {
	TEXT("text"), 
	IMAGE_URL("image_url");
	
	private final String contentType;

	private OpenAIContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
}
