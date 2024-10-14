package openai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import openai.messages.SystemMessage;
import openai.query.OpenAIQuery;
import openai.query.OpenAIQueryFactory;
import openai.requests.OpenAIGeneralChatRequest;
import openai.responses.OpenAIResponseParser;

public class OpenAIDriver {

    public static void main(String[] args) {
        OpenAIClient client = init();

        OpenAIGeneralChatRequest request = new OpenAIGeneralChatRequest();

        OpenAIQuery query = OpenAIQueryFactory.createOpenAIQuery();

        //this is used to provide GPT some context as to what the prompt
        //could be about
        List<SystemMessage> msg = new ArrayList<>();
        msg.add(new SystemMessage("The poem should have three words"));

        //actual prompt
        query.setPrompt("Write a poem about potatoes");

        request.prepareQueryPayload(query, msg);
        try {
            String response = client.executePostQuery(request);
            String parsedResponse = OpenAIResponseParser.parseAndReturnJson(response);
            System.out.println(parsedResponse);
        } catch (IOException e) {
            System.err.println("Error while executing post query: " + e.getMessage());
        }
    }

    public static OpenAIClient init() {
        OpenAIClient client = OpenAIClient.getInstance();
        String host = "api.openai.com";
        String apiKey = "MY_API_KEY"; // Replace with your OpenAI API key

        client.init(host, apiKey);
        return client;
    }
}
