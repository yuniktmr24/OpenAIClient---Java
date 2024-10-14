package openai.query;

import openai.OpenAIModel;
import java.io.Serializable;

/**
 *
 * @author ytamrakar
 *
 * Encapsulating object for a query to an OpenAI model
 *
 * @param model - OpenAI model that gets queried
 * @param prompt - Prompt to the model for inference
 * @param
 */
public class OpenAIQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	//default model for inference
	private OpenAIModel openAIModel;
	private String prompt;

	/**
	 * *
	 * As a general rule, try not to alter both top_p and temperature value at
	 * once. Typically, temp and top_p values do not need alteration unless to
	 * fine tune the randomness of the response. Still, if needed, tune only one
	 * param at a time for best results.
	 */
	/**
	 * *
	 * OpenAI Defaults top_p value = 1, temperature value = 0.7
	 */
	/**
	 * *
	 * Nucleus sampling, The model considers the results of the tokens with
	 * top_p probability mass. So 0.1 means only the tokens comprising the top
	 * 10% probability mass are considered.
	 */
	private final int topP;

	/**
	 * * Temperature Higher values like 0.8 will make the output more random,
	 * while lower values like 0.2 will make it more focused and deterministic.
	 */
	private final double temperature;
	private final int maxTokens;

	OpenAIQuery(OpenAIModel openAIModel, String prompt, int topP, double temperature, int maxTokens) {
		this.openAIModel = openAIModel; 
		this.prompt = prompt;
		this.topP = topP;
		this.temperature = temperature;
		this.maxTokens = maxTokens;
	}

	public int getTopP() {
		return topP;
	}

	public double getTemperature() {
		return temperature;
	}

	public OpenAIModel getOpenAIModel() {
		return openAIModel;
	}

	public void setOpenAIModel(OpenAIModel openAIModel) {
		this.openAIModel = openAIModel;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public int getMaxTokens() {
		return maxTokens;
	}

}
