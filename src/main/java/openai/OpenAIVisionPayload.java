package openai;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ytamrakar
 * The Vision API expects the document to be converted into base64 and sent to the endpoint
 * If the document has multiple pages, we will need to convert each page to base64
 * and send each base64 representation as a separate "image_url" resource. See the docs for more info
 * https://platform.openai.com/docs/guides/vision
 * 
 * Please make sure the image is ideally below 20 MB in size and is of one the following formats: 
 * ['png', 'jpeg', 'gif', 'webp'].
 */
public class OpenAIVisionPayload implements Serializable {
	private static final long serialVersionUID = 1L;
    private List<byte[]> pageBytes;

    public OpenAIVisionPayload(List<byte[]> pageBytes) {
        this.pageBytes = pageBytes;
    }

    public List<byte[]> getPageBytes() {
        return pageBytes;
    }

    public void setPageBytes(List<byte[]> pageBytes) {
        this.pageBytes = pageBytes;
    }
}
