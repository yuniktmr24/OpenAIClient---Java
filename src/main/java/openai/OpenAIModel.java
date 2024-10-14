package openai;

/**
 *
 * @author ytamrakar
 */
/* OpenAI models that can be queried by the OpenAI client. These models are continuously being
 * iterated upon, so at a given time, this list may not be exhaustive with current state of tech 
 * at OpenAI 
*/
public enum OpenAIModel {
    GPT_4o("gpt-4o"),
    GPT_4o_mini("gpt-4o-mini"),
    GPT_4_turbo("gpt-4-turbo");
    
    private final String model;
    
    private OpenAIModel(String model) {
	    this.model = model;
    }
    
    public String getModel() {
	    return this.model;
    }
}
