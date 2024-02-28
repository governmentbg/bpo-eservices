package bg.duosoft.taxcalculator.config;

import bg.duosoft.taxcalculator.rest.BasicAuthInterceptor;
import bg.duosoft.taxcalculator.rest.HttpComponentsClientHttpRequestFactoryBasicAuth;
import bg.duosoft.taxcalculator.service.PaymentsService;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class PaymentsRestTemplateConfig {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate paymentsRestTemplate() {
        String username = env.getProperty("payment.rest.service.username");
        String password = env.getProperty("payment.rest.service.password");
        String restServiceURL = env.getProperty("payment.rest.service.url");

        try {
            //The getLiabilityUrl looks like http://192.168.0.44:8080/payments/rest/getstatus/{referenceNumber}/{liabilityCode}, and there is as problem converting the URL to URI because of the braces
            URI url = new URI(restServiceURL + PaymentsService.SELECT_LIABILITY_URL.replace("{", "").replace("}", ""));
            HttpHost host = new HttpHost(url.getHost());
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
            List<ClientHttpRequestInterceptor> interceptors = Arrays.asList(new BasicAuthInterceptor(username, password));
            restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(restTemplate.getRequestFactory(), interceptors));
            return restTemplate;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public String paymentsRestServiceURL() {
        return env.getProperty("payment.rest.service.url");
    }
}
