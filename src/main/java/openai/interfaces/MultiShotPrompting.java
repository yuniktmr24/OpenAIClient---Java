package openai.interfaces;

import java.util.List;

/**
 *
 * @author ytamrakar
 * 
 * Interface that encapsulates multi shot prompting tuning for the OpenAI models
 * Multi shot prompting is where numerous examples of input - inferences are supplied to the
 * model with the expectation of guiding its behavior
 * @param <T> : Message object flavor. OpenAIMessage is an example of such a message object
 */
public interface MultiShotPrompting <T>{
	void addMessage(T msg);
	List <T> getMessages();
}
