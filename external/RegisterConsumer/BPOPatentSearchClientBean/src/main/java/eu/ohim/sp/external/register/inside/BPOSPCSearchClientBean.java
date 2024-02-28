package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.GetSPCByPatentAppNumType;
import bg.egov.regix.patentdepartment.SPCDetailsType;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.register.BPOSPCSearchService;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import eu.ohim.sp.external.register.inside.ws.client.BPOSPCSearch;
import eu.ohim.sp.external.register.utils.BPOSearchUtils;
import eu.ohim.sp.external.utils.AbstractWSClient;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.04.2022
 * Time: 13:41
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOPatentSearchClientInside
public class BPOSPCSearchClientBean extends AbstractWSClient implements BPOSPCSearchService {

    public static final String ADAPTER_NAME = "spc_in";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(BPOSPCSearchClientBean.class);

    /**
     * The system configuration service.
     */
    @EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper mapper;

    /**
     * Actual clients to the web services
     */
    private BPOSPCSearch webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper(Arrays.asList("ext-global-dozerMapping.xml", "ext-spcType-dozerMapping.xml"));
        if (getAdapterEnabled()) {
            webServiceRef = new eu.ohim.sp.external.register.inside.ws.client.BPOSPCSearchService(getWsdlLocation()).getBPOSPCSearchServiceDefaultPort();
            BPOSearchUtils.setSearchTimeout(webServiceRef);
        }
    }

    @Override
    public Patent getSPCByAppNumber(String appNum) {
        Patent corePatent = null;
        try {
            GetSPCByPatentAppNumType appNumType = new GetSPCByPatentAppNumType(appNum);
            SPCDetailsType result = webServiceRef.getSPCByAppNum(appNumType);
            if (result == null || result.getSPC() == null || result.getSPC().size() == 0 || result.getSPC().size() != 1) {
                return null;
            }
            corePatent = new Patent();
            mapper.map(result.getSPC().get(0), corePatent);
        } catch (Exception e){
            LOGGER.error(e);
        }
        return corePatent;
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
