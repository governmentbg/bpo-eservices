package eu.ohim.sp.external.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import javax.ws.rs.core.Cookie;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public class RestJsonConsumerRequest implements ConsumerRequest {

    private static final String ACCEPT_MIME_TYPE = "application/json";
    private final String url;
    private final Client client;
    private final WebResource webResource;

    public RestJsonConsumerRequest(final String url) {
        this.url = url;
        this.client= Client.create();
        // Very important to set timeouts. Otherwise server may hang if client never returns
        this.client.setConnectTimeout(5000);
        this.client.setReadTimeout(10000);
        this.webResource = client.resource(url);
    }

    public RestJsonConsumerRequest(final String url, final String user, final String password) {
        this(url);
        this.client.addFilter(new HTTPBasicAuthFilter(user, password));
    }

    public ConsumerResponse doRequest() {
        final ClientResponse response = getClientResponse();
        if (isResponseValid(response)) return getConsumerResponse(response);
        throw new RuntimeException("Unknown error when processing request for url: " + url);
    }

    private ClientResponse getClientResponse() {
        return webResource.accept(ACCEPT_MIME_TYPE).get(ClientResponse.class);
    }

    private boolean isResponseValid(final ClientResponse response){
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return true;
    }

    private StringConsumerResponse getConsumerResponse(final ClientResponse response) {
        final String output = response.getEntity(String.class);
        return new StringConsumerResponse(output);
    }

    /**DS Class Integration changes start*/
    @Override
    public ConsumerResponse doPostRequest(Object request) throws Exception {
        ClientResponse response = webResource.type(ACCEPT_MIME_TYPE).post(ClientResponse.class, (new com.fasterxml.jackson.databind.ObjectMapper()).writeValueAsString(request));
        ConsumerResponse res = getConsumerResponse(response);
        return res;
    }

    public ConsumerResponse doRequestWithCookieSkip() {
        final ClientResponse response = getClientResponseWithCookieSkip();
        return getConsumerResponse(response);
    }

    @Override
    public ConsumerResponse doPostRequestWithCookieSkip(Object request) throws Exception {
        ClientResponse response = postClientResponseWithCookieSkip(request);
        ConsumerResponse res = getConsumerResponse(response);
        return res;
    }

    private ClientResponse getClientResponseWithCookieSkip(){
        return webResource.accept(ACCEPT_MIME_TYPE).cookie(new Cookie(COOKIECONSENT_STATUS, DISMISS)).get(ClientResponse.class);
    }

    private ClientResponse postClientResponseWithCookieSkip(Object request) throws Exception {
        return webResource.type(ACCEPT_MIME_TYPE).cookie(new Cookie(COOKIECONSENT_STATUS, DISMISS)).post(ClientResponse.class, (new com.fasterxml.jackson.databind.ObjectMapper()).writeValueAsString(request));
    }

    /**DS Class Integration changes end*/

}