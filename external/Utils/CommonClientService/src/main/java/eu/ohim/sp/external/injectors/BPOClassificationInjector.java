package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.common.AbstractConsumerService;
import eu.ohim.sp.external.common.RestJsonConsumerRequest;
import eu.ohim.sp.external.common.StringConsumerResponse;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.05.2022
 * Time: 14:31
 */
public class BPOClassificationInjector extends AbstractConsumerService implements Serializable {

    private final static String GET_PLANT_LATINCLASSIFICATION_AUTOCOMPLETE = System.getProperty("url.plant.get.latinclassification.autocomplete");

    private final static String URL_GET_PLANT_LATINCLASSIFICATION_AUTOCOMPLETE = "urlPlantLatinClassificationAutocomplete";

    private static final String UTF_ENCODING = "UTF-8";

    private String _getUrl(final String selector, final Object... arguments) {
        StringBuilder url = new StringBuilder();
        try {
            switch (selector) {
                case URL_GET_PLANT_LATINCLASSIFICATION_AUTOCOMPLETE:
                    url.append(GET_PLANT_LATINCLASSIFICATION_AUTOCOMPLETE)
                            .append("?word=").append(URLEncoder.encode((String) arguments[0], UTF_ENCODING))
                            .append("&rows=").append(arguments[1]);
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            throw new SPException(e);
        }
        return url.toString();
    }

    public String getPlantLatinClassificationAutocomplete(String word, int rows){
        String url = _getUrl(URL_GET_PLANT_LATINCLASSIFICATION_AUTOCOMPLETE, word, rows);
        String result = ((StringConsumerResponse)new RestJsonConsumerRequest(url).doRequest()).getResponse();
        return result;
    }
}
