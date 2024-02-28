package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public interface ConsumerRequest {

    String COOKIECONSENT_STATUS = "cookieconsent_status";
    String DISMISS = "dismiss";

    public ConsumerResponse doRequest();

    /**DS Class Integration changes start*/
    ConsumerResponse doPostRequest(Object request) throws Exception;
    ConsumerResponse doPostRequestWithCookieSkip(Object request) throws Exception;
    ConsumerResponse doRequestWithCookieSkip();
    /**DS Class Integration changes end*/
}