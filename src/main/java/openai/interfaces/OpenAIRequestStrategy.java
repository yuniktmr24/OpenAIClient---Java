package openai.interfaces;

import openai.query.OpenAIQuery;

/**
 *
 * @author ytamrakar
 * @param <T>: type of payload. Different requests to different endpoints or even different
 * use cases for the same endpoint can have different 
 * types of payloads. 
 */
public interface OpenAIRequestStrategy<T> {
    void prepareQueryPayload(OpenAIQuery query, T payload);
    String getEndpoint();
    Object getPayload();
}
