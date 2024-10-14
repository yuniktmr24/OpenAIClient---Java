package openai.query;

import openai.OpenAIModel;

/**
 *
 * @author ytamrakar
 * Class that creates an OpenAIQueryObject with non-null values
 */

public class OpenAIQueryFactory {
	private static final OpenAIModel DEFAULT_MODEL = OpenAIModel.GPT_4o_mini;
	private static final int DEFAULT_TOP_P = 1;
	private static final double DEFAULT_TEMPERATURE = 0.7;
	private static final int DEFAULT_MAX_TOKENS = 1000; 
	private static final String ORDER_ENTRY_FURTHER_SYSTEM_INSTRUCTIONS = "\n Here are further instructions for your reference: "
			+ "Please return only the requested JSON. "
			+ "If the values do not exist, please return an empty string. "
			+ "If there are multiple values for a field, please return a single, comma separated multi-valued string instead of a list. "
			+ "Also, if the text 'Hearing' is not present in the form, please set the values of all the hearing fields (hearing_* only) to empty string.";
	
	private static final String ORDER_ENTRY_AUTOMATION_PROMPT = "Return a JSON document (labels in parenthesis) with "
			+ "the plaintiff name (plaintiff), "
			+ "defendant name (defendant), "
			+ "case number (case_number), "
			+ "a 1-2 word court county (court_county), "
			+ "1-2 word court district or circuit (court_district), "
			+ "1 word court type (court_type), "
			+ "court state abbreviation (court_state), "
			+ "court name (court_name), "
			+ "suit amount (suit_amount), "
			+ "attorney last name (attorney_last), "
			+ "matter or file number (matter), "
			+ "hearing date (hearing_date), "
			+ "hearing time (hearing_time), "
			+ "hearing location (hearing_location), "
			+ "filing date (filing_date), "
			+ "pleading title (pleading), "
			+ "and for the person the document is being served upon get: "
				+ "full name (served_fullname), "
				+ "first name (served_firstname), "
				+ "last name (served_lastname), "
				+ "phone (served_phone), "
				+ "date of birth (served_date_of_birth), "
				+ "SSN (served_ssn), "
				+ "special Handling requirements (special_handling), "
				+ "Service Notes (service_notes), business name(served_business), "
				+ "street address 1 (served_street1), "
				+ "street address 2 (served_street2), "
				+ "building (served_building), "
				+ "city (served_city), "
				+ "state abbreviation (served_state), "
				+ "zip code (served_zip)";
			
    public static OpenAIQuery createOpenAIQuery(OpenAIModel openAIModel, String prompt, Integer topP, Double temperature, Integer maxTokens) {
        if (openAIModel == null) {
            return new OpenAIQuery(DEFAULT_MODEL, prompt, topP, temperature, maxTokens);
        }
        return new OpenAIQuery(
            openAIModel,
            prompt,
            topP != null ? topP : DEFAULT_TOP_P,
            temperature != null ? temperature : DEFAULT_TEMPERATURE,
            maxTokens != null ? maxTokens :  DEFAULT_MAX_TOKENS
        );
    }
	
	public static OpenAIQuery createOpenAIQuery() {
		return new OpenAIQuery(DEFAULT_MODEL, "", DEFAULT_TOP_P, DEFAULT_TEMPERATURE, DEFAULT_MAX_TOKENS);
	}
	
	public static OpenAIQuery createOpenAIQuery(OpenAIModel openAIModel, String prompt) {
		return new OpenAIQuery(openAIModel, prompt, DEFAULT_TOP_P, DEFAULT_TEMPERATURE, DEFAULT_MAX_TOKENS);
	}
	
	public static OpenAIQuery createOpenAIOrderEntryAutomationQuery(OpenAIModel openAIModel) {
		return new OpenAIQuery(openAIModel, 
				ORDER_ENTRY_AUTOMATION_PROMPT + ORDER_ENTRY_FURTHER_SYSTEM_INSTRUCTIONS, 
				DEFAULT_TOP_P, 
				DEFAULT_TEMPERATURE, 
				DEFAULT_MAX_TOKENS);
	}
}
