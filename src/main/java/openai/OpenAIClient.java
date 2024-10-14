package openai;

import openai.interfaces.AbstractOpenAIRequestStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author ytamrakar
 */
public class OpenAIClient {

	private static final Log logger = LogFactory.getLog(OpenAIClient.class);
	private static final OpenAIClient INSTANCE = new OpenAIClient();
	private String host;
	private String apiKey;
	private static final ObjectMapper objectMapper = new ObjectMapper(); 

	private OpenAIClient() {
	}

	public static OpenAIClient getInstance() {
		return INSTANCE;
	}

	public synchronized void init(String host, String apiKey) {
		if (this.host == null && this.apiKey == null) {
			this.host = "https://" + host;
			this.apiKey = apiKey;
		} else {
			logger.warn("Client is already configured");
			//throw new IllegalStateException();
		}
	}

	public String executePostQuery(AbstractOpenAIRequestStrategy requestStrategy) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost(this.host + requestStrategy.getEndpoint());
		addHeaders(request);

		if (requestStrategy.getPayload() instanceof String) {
			try {
				request.setEntity(new StringEntity((String) requestStrategy.getPayload()));
			} catch (UnsupportedEncodingException ex) {
				logger.error("Error encoding string payload ", ex);
			}
		}

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			if (response.getEntity() != null) {
				JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
				return jsonNode != null ? jsonNode.toString() : "";
			} else {
				logger.warn("OpenAI Error: Response entity is null.");
				return "";
			}
		}
	}
	
	public String getModelsList() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(this.host + "/v1/models");
		addHeaders(request);


		try (CloseableHttpResponse response = httpClient.execute(request)) {
			if (response.getEntity() != null) {
				JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
				return jsonNode != null ? jsonNode.toString() : "";
			} else {
				logger.warn("OpenAI Error: Response entity is null.");
				return "";
			}
		}
	}


	private void addHeaders(HttpRequestBase request) {
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Authorization", "Bearer " + apiKey);
	}
}
