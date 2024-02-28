package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.GetUtilityModelByAppNumType;
import bg.egov.regix.patentdepartment.PatentDetailsType;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.register.BPOUMSearchService;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import eu.ohim.sp.external.register.inside.ws.client.BPOUMsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOUMsSearchService;
import eu.ohim.sp.external.register.utils.BPOSearchUtils;
import eu.ohim.sp.external.utils.AbstractWSClient;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raya
 * 17.06.2019
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent
@BPOPatentSearchClientInside
public class BPOUMSearchClientBean extends AbstractWSClient implements BPOUMSearchService {

    public static final String ADAPTER_NAME = "utilitymodel_in";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(BPOUMSearchClientBean.class);

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
    private BPOUMsSearch webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper(Arrays.asList("ext-global-dozerMapping.xml", "ext-patentType-dozerMapping.xml"));
        if (getAdapterEnabled()) {
            webServiceRef = new BPOUMsSearchService(getWsdlLocation()).getBPOUMsSearchServiceDefaultPort();
            BPOSearchUtils.setSearchTimeout(webServiceRef);
        }
    }

    @Override
    public Patent getUtilityModelByAppNumber(String appNum) {
        Patent corePatent = null;
        try {
            GetUtilityModelByAppNumType appNumType = new GetUtilityModelByAppNumType(appNum);
            PatentDetailsType result = webServiceRef.getUMByAppNum(appNumType);
            if (result == null || result.getPatent() == null || result.getPatent().size() == 0 || result.getPatent().size() != 1) {
                return null;
            }
            corePatent = new Patent();
            mapper.map(result.getPatent().get(0), corePatent);
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
