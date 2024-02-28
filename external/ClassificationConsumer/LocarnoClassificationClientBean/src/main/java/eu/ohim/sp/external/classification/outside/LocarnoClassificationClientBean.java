package eu.ohim.sp.external.classification.outside;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;

import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.external.classification.LocarnoClassificationClientOutside;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.external.classification.outside.ws.client.LocarnoClassificationWSClient;
import eu.ohim.sp.external.classification.outside.ws.client.LocarnoClassificationWSClientService;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.LocarnoClassificationFaultException;

/**
 * This class is the router that connects to the JBoss ESB adapter for
 * classification. It is called from the fsp core service. It connect to the esb
 * using the jboss-remoting library. In this case the url to connect is passed
 * by system properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @LocarnoClassificationClientOutside
public class LocarnoClassificationClientBean extends AbstractWSClient implements LocarnoClassificationService {

    /** The Constant ADAPTER_NAME. */
    protected static final String ADAPTER_NAME = "locarnoClassification";

    private static final Logger LOGGER = Logger.getLogger(LocarnoClassificationClientBean.class);

    /**
     * The system configuration service.
     */
    @EJB(lookup = "java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual client to the web service
     */
    private LocarnoClassificationWSClient webServiceRef;

    /**
     * Utility class that transforms external to core domain and vice versa
     */
    private DozerBeanMapper dozerBeanMapper;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        // Mapper init
        dozerBeanMapper = new DozerBeanMapper();

        // Initialization
        super.init(ADAPTER_NAME);
        if (getAdapterEnabled()) {
            webServiceRef = new LocarnoClassificationWSClientService(getWsdlLocation())
                    .getManageLocarnoClassificationPort();
        }
    }

    @Override
    public List<ProductIndication> getFullClassification(String lang) {
        List<ProductIndication> productCoreList = new ArrayList<ProductIndication>();
        try {
            List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList = webServiceRef
                    .getFullClassification(lang);
            for (eu.ohim.sp.external.domain.design.ProductIndication productExtIndication : productExtList) {
                productCoreList.add(dozerBeanMapper.map(productExtIndication, ProductIndication.class));
            }
        } catch (LocarnoClassificationFaultException e) {
            LOGGER.error(e);
            throw new SPException(e);
        }

        return productCoreList;
    }

    @Override
	public List<ProductIndication> getProductIndications(String clazz, String subclass, String lang) {
        List<ProductIndication> productCoreList = new ArrayList<ProductIndication>();
        try {
			List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList =
					webServiceRef.getProductIndications(clazz, subclass, lang);
			for(eu.ohim.sp.external.domain.design.ProductIndication productExtIndication : productExtList){
                productCoreList.add(dozerBeanMapper.map(productExtIndication, ProductIndication.class));
            }
        } catch (LocarnoClassificationFaultException e) {
            LOGGER.error(e);
            throw new SPException(e);
        }

        return productCoreList;
    }

    @Override
	public List<ProductIndication> searchProductIndication(String indicationProduct, String clazz, String subclass, String lang) {
		List<ProductIndication> productCoreList = new ArrayList<ProductIndication>();
		try {
			List<eu.ohim.sp.external.domain.design.ProductIndication> productExtList =
					webServiceRef.searchProductIndication(indicationProduct, clazz, subclass, lang);
            for(eu.ohim.sp.external.domain.design.ProductIndication productExtIndication : productExtList){
				productCoreList.add(dozerBeanMapper.map(productExtIndication, ProductIndication.class));
			}
		} catch (LocarnoClassificationFaultException e) {
            LOGGER.error(e);
            throw new SPException(e);
		}

		return productCoreList;
	}

	@Override
	public ErrorList validateProductIndication(String module, ProductIndication productIndication, RulesInformation rulesInformation) {
        // throw new NotImplementedException();
        return new ErrorList();
    }

    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    @Override
    public String getLocarnoAutocomplete(String language, String text, Integer numberOfResults) {
        throw new NotImplementedException();
    }

    @Override
    public ClassificationHeading getLocarnoClasses(String language, String version) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public List<LocarnoSubClassHeading> getLocarnoSubclasses(Integer locarnoClass, String language, String version) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public List<ClassDescription> validateClassDescriptions(List<ClassDescription> classDescriptions) {
        throw new NotImplementedException();
    }

    @Override
    public TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput, Integer selectedClass, Integer selectedSubclass, Object... arguments) {
        throw new NotImplementedException();
    }

    @Override
    public List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass, String subclass) {
        throw new NotImplementedException();
    }
}
