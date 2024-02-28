package eu.ohim.sp.external.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by marcoantonioalberoalbero on 30/8/16.
 */
public class AbstractConsumerService implements ConsumerService {

    private final ConcurrentMap<String, ConsumerResponseTransformer> transformerCache = new ConcurrentHashMap<String, ConsumerResponseTransformer>();

    public ConsumerResponse doRequest(final ConsumerRequest request) {
        return request.doRequest();
    }

    public <T> T doTransform(final ConsumerResponseTransformer transformer, final ConsumerResponse response) {
        return (T) transformer.transform(response.getResponse());
    }

    protected boolean hasTransformer(final String transformerName) {
        return transformerCache.containsKey(transformerName);
    }

    protected ConsumerResponseTransformer getTransformer(final String transformerName){
        return transformerCache.get(transformerName);
    }

    protected void addTransformer(final String transformerName, final ConsumerResponseTransformer transformer) {
        transformerCache.putIfAbsent(transformerName, transformer);
    }

    /**DS Class Integration changes start*/
    @Override
    public ConsumerResponse doPostRequest(ConsumerRequest consumerRequest, Object request) throws Exception {
        return consumerRequest.doPostRequest(request);
    }

    @Override
    public ConsumerResponse doPostRequestWithCookieSkip(ConsumerRequest consumerRequest, Object request) throws Exception {
        return consumerRequest.doPostRequestWithCookieSkip(request);
    }

    public ConsumerResponse doRequestWithCookieSkip(final ConsumerRequest request) {
        return request.doRequestWithCookieSkip();
    }
    /**DS Class Integration changes end*/

}