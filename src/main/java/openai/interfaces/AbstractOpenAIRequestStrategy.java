package openai.interfaces;


import openai.query.OpenAIQuery;
import openai.Endpoint;
import java.io.Serializable;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author ytamrakar
 */
public abstract class AbstractOpenAIRequestStrategy<T> implements OpenAIRequestStrategy<T>, Serializable {
	private static final long serialVersionUID = 1L;
    private boolean isPayloadPrepared = false;
    protected Object payload;
    private String endpoint;

    public AbstractOpenAIRequestStrategy() {
        // Ensure the correct class is used to get the annotation
        Class<?> clazz = this.getClass();
        Endpoint endpointAnnotation = clazz.getAnnotation(Endpoint.class);
        if (endpointAnnotation != null) {
            this.endpoint = endpointAnnotation.value();
        } 
    }

    @Override
    public final void prepareQueryPayload(OpenAIQuery query, T payload) {
		Validate.notNull(query, "Query cannot be null");
        this.payload = preparePayload(query, payload);
        isPayloadPrepared = true;
    }

    protected abstract Object preparePayload(OpenAIQuery query, T payload);

    @Override
    public final Object getPayload() {
        if (!isPayloadPrepared) {
            throw new IllegalStateException("Payload not prepared. Call prepareQueryPayload first.");
        }
        return payload;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }
	
	void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}