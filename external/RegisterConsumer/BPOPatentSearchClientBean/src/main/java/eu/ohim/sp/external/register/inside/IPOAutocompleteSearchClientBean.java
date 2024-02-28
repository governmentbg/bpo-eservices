package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.GetAutocompleteType;
import bg.egov.regix.patentdepartment.IpoType;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.register.IPOAutocompleteSearchService;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import eu.ohim.sp.external.register.inside.ws.client.IPOAutocompleteSearch;
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
 * Date: 13.04.2022
 * Time: 14:36
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOPatentSearchClientInside
public class IPOAutocompleteSearchClientBean extends AbstractWSClient implements IPOAutocompleteSearchService {

    public static final String ADAPTER_NAME = "ipoautocomplete_in";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(IPOAutocompleteSearchClientBean.class);

    /**
     * The system configuration service.
     */
    @EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual clients to the web services
     */
    private IPOAutocompleteSearch webServiceRef;

    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        if (getAdapterEnabled()) {
            webServiceRef = new eu.ohim.sp.external.register.inside.ws.client.IPOAutocompleteSearchService(getWsdlLocation()).getIPOAutocompleteSearchServiceDefaultPort();
            BPOSearchUtils.setAutocompleteTimeout(webServiceRef);
        }
    }

    @Override
    public String ipoAutocomplete(String word, int rows, String appType) {
        IpoType ipoType;
        if(appType.startsWith("sv-")){
            ipoType = IpoType.PLANT;
        } else if (appType.startsWith("pt-")){
            ipoType = IpoType.PATENT;
        } else if (appType.startsWith("um-")){
            ipoType = IpoType.UTILITY_MODEL;
        } else if (appType.startsWith("ep-")){
            ipoType = IpoType.EPO_PATENT;
        } else if (appType.startsWith("spc-")){
            ipoType = IpoType.SPC;
        } else if (appType.startsWith("gi-")){
            ipoType = IpoType.GEO_INDICATION;
        } else if (appType.startsWith("tm-")){
            ipoType = IpoType.MARK;
        } else if (appType.startsWith("ds-")){
            ipoType = IpoType.DESIGN;
        } else {
            throw new SPException("Bad application type");
        }
        GetAutocompleteType request = new GetAutocompleteType();
        request.setIpoType(ipoType);
        request.setWord(word);
        request.setRows(BigInteger.valueOf(rows));
        return webServiceRef.getAutocomplete(request);
    }

    @Override
    protected String getErrorCode() {
        return "";
    }

    @Override
    protected ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
