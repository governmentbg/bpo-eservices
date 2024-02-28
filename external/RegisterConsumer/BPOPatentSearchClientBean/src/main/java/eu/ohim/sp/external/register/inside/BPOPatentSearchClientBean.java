package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.PatentType;
import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.BPOPatentSearchService;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import eu.ohim.sp.external.register.inside.ws.client.BPOPatentsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOPatentsSearchService;
import eu.ohim.sp.external.register.utils.BPOSearchUtils;
import eu.ohim.sp.external.utils.AbstractWSClient;
import org.apache.commons.lang.NotImplementedException;
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
public class BPOPatentSearchClientBean extends AbstractWSClient implements BPOPatentSearchService {

    public static final String ADAPTER_NAME = "patent_in";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(BPOPatentSearchClientBean.class);

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
    private BPOPatentsSearch webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper(Arrays.asList("ext-global-dozerMapping.xml", "ext-patentType-dozerMapping.xml"));
        if (getAdapterEnabled()) {
            webServiceRef = new BPOPatentsSearchService(getWsdlLocation()).getBPOPatentsSearchServiceDefaultPort();
            BPOSearchUtils.setSearchTimeout(webServiceRef);
        }
    }

    @Override
    public Patent getPatentByAppNumber(String appNum) {
        Patent corePatent = null;
        try {
            List<PatentType> result = webServiceRef.getPatentByAppNum(appNum);
            if (result == null || result.size() == 0 || result.size() != 1) {
                return null;
            }
            corePatent = new Patent();
            mapper.map(result.get(0), corePatent);

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

    @Override
    public ErrorList validatePatent(String module, Patent patent, RulesInformation rulesInformation) {
        throw new NotImplementedException();
    }
}
