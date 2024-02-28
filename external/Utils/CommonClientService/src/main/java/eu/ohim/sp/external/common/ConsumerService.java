package eu.ohim.sp.external.common;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public interface ConsumerService {

    public ConsumerResponse doRequest(ConsumerRequest request);

    public <T extends Object> T doTransform(ConsumerResponseTransformer transformer, ConsumerResponse response);

    /**DS Class Integration changes start*/
    public ConsumerResponse doPostRequest(ConsumerRequest consumerRequest, Object request) throws Exception;
    ConsumerResponse doPostRequestWithCookieSkip(ConsumerRequest consumerRequest, Object request) throws Exception;
    /**DS Class Integration changes end*/
}