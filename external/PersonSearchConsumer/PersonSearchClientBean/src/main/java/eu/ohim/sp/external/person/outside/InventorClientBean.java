package eu.ohim.sp.external.person.outside;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.external.person.InventorClient;
import eu.ohim.sp.external.person.PersonSearchClientOutside;
import eu.ohim.sp.external.person.outside.ws.client.inventor.InventorClientWS;
import eu.ohim.sp.external.person.outside.ws.client.inventor.InventorClientWSService;
import eu.ohim.sp.external.utils.AbstractWSClient;

import eu.ohim.sp.external.ws.exception.InventorFaultException;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is the router that connects to the JBoss ESB adapter for person
 * search/get. It is called from the fsp core service. Can be connecting to
 * tmview or esearch depends on the environment It connect to the esb using the
 * jboss-remoting library. In this case the url to connect is passed by system
 * properties. It contains all the dependencies to the esb.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Dependent @PersonSearchClientOutside
public class InventorClientBean extends AbstractWSClient implements InventorClient {
    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(InventorClientBean.class);

    /** The Constant APPLICANT_ADAPTER_NAME. */
    public static final String ADAPTER_NAME = "inventor";
    public static final String ADAPTER_ENABLED = "adapter enabled";

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
     * Actual client to the web service
     */
    private InventorClientWS webServiceRef;


    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        mapper = new DozerBeanMapper();
        if (getAdapterEnabled()) {
            webServiceRef = new InventorClientWSService(getWsdlLocation()).getManageInventorPort();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInventorAutocomplete(String module, String office, String text, int numberOfRows) {
        String results = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            results = null;
            try {
                results = webServiceRef.getInventorAutocomplete(module, office, text, numberOfRows);
            } catch (InventorFaultException exc) {
                LOGGER.error(" getInventorAutocomplete ERROR WS SOAP: " + exc.getMessage(), exc);
            }


        }

        return results;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Inventor getInventor(String module, String office, String inventorId) {
        Inventor result = null;
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Inventor externalResult = null;
            try {
                externalResult = webServiceRef.getInventor(module, office, inventorId);
            } catch (InventorFaultException exc) {
                LOGGER.error(" getInventor ERROR WS SOAP: " + exc.getMessage(), exc);
            }

            if (externalResult != null) {
                result = mapper.map(externalResult, Inventor.class);
            }

        }

        return result;
    }

    @Override
    public List<Inventor> matchInventor(String module, String office, Inventor inventor, int numberOfResults) {
        List<Inventor> result = new ArrayList<Inventor>();
        if (getAdapterEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(ADAPTER_ENABLED);
            }
            eu.ohim.sp.external.domain.person.Inventor extInventor
                = mapper.map(inventor, eu.ohim.sp.external.domain.person.Inventor.class);

            if (extInventor != null) {
                List<eu.ohim.sp.external.domain.person.Inventor> externalResult = null;
                try {
                    externalResult = webServiceRef.matchInventor(module, office, extInventor, numberOfResults);
                } catch (InventorFaultException exc) {
                    LOGGER.error(" matchInventor ERROR WS SOAP: " + exc.getMessage(), exc);
                }

                if (externalResult != null) {
                    result = new ArrayList<Inventor>();
                    for (eu.ohim.sp.external.domain.person.Inventor matchedInventor : externalResult) {
                        result.add(mapper.map(matchedInventor, Inventor.class));
                    }
                }
            } else {
                LOGGER.error("searchInventor external to core: Error converting input inventor to external domain");
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
