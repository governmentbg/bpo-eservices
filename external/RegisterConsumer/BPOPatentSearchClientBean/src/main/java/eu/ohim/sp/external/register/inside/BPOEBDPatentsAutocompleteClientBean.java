package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.GetEBDAutocompleteType;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.register.BPOEBDPatentsAutocompleteService;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import eu.ohim.sp.external.register.inside.ws.client.BPOEBDPatentsAutocomplete;
import eu.ohim.sp.external.register.utils.BPOSearchUtils;
import eu.ohim.sp.external.utils.AbstractWSClient;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 20.04.2022
 * Time: 12:51
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOPatentSearchClientInside
public class BPOEBDPatentsAutocompleteClientBean extends AbstractWSClient implements BPOEBDPatentsAutocompleteService {

    public static final String ADAPTER_NAME = "ebdpatent_autocomplete_in";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(BPOEBDPatentsAutocompleteClientBean.class);

    /**
     * The system configuration service.
     */
    @EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual clients to the web services
     */
    private BPOEBDPatentsAutocomplete webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        if (getAdapterEnabled()) {
            webServiceRef = new eu.ohim.sp.external.register.inside.ws.client.BPOEBDPatentsAutocompleteService(getWsdlLocation()).getBPOEBDPatentsAutocompleteServiceDefaultPort();
            BPOSearchUtils.setAutocompleteTimeout(webServiceRef);
        }
    }

    @Override
    public String ebdPatentsAutocomplete(String word, int rows) {
        GetEBDAutocompleteType request = new GetEBDAutocompleteType();
        request.setRows(BigInteger.valueOf(rows));
        request.setWord(word);
        return webServiceRef.getAutocomplete(request);
    }

    @Override
    protected String getErrorCode() {
        return null;
    }

    @Override
    protected ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
