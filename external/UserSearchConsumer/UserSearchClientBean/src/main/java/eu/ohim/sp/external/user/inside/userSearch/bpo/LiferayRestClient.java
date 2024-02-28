package eu.ohim.sp.external.user.inside.userSearch.bpo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects.BaseResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriBuilder;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Stream;

public class LiferayRestClient<T extends BaseResult> {
    private static Logger logger = Logger.getLogger(LiferayRestClient.class);
    private static final String REMOTE_USER = System.getProperty("liferay.remote.user.name");
    private static final String REMOTE_PASSWORD = System.getProperty("liferay.remote.user.password");

    private Class<T> cls;
    private String url;
    private Client client;
    public LiferayRestClient(Class<T> cls, String url, String... params) {
        this.cls = cls;
        if (params == null || params.length == 0) {
            this.url = UriBuilder.fromPath(url).build().toString();
        } else {
            this.url = UriBuilder.fromPath(url).build(Stream.of(params).map(f -> urlEncode(f)).toArray()).toString();
        }
        client = Client.create();
        this.client.setConnectTimeout(5000);
        this.client.setReadTimeout(10000);
        client.addFilter(new HTTPBasicAuthFilter(REMOTE_USER, REMOTE_PASSWORD));
        client.addFilter(new LoggingFilter(new PrintStream(new LogOutputStream(logger, Level.DEBUG))));

    }
    public T callService() {
        ClientResponse resp = client.resource(url).get(ClientResponse.class);
        if (isResponseValid(resp)) {
            T result = resp.getEntity(cls);
            if (!StringUtils.isEmpty(result.getException())) {
                throw new RuntimeException("Exception: " + result.getException() + "\nMessage:" + result.getMessage());
            }
            return result;
        }
        throw new RuntimeException("Unknown error when processing request for url: " + url);
    }

    private static String urlEncode(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isResponseValid(final ClientResponse response){
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return true;
    }
}
