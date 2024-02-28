package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public class StringConsumerResponse implements ConsumerResponse<String> {

    private final String response;

    public StringConsumerResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}