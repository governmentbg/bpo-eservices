package eu.ohim.sp.external.resource.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.resource.ResourceService;
import eu.ohim.sp.external.resource.ResourceClientOutside;
import eu.ohim.sp.external.resource.outside.ws.client.ResourceClientWS;
import eu.ohim.sp.external.resource.outside.ws.client.ResourceClientWSService;
import eu.ohim.sp.external.utils.AbstractWSClient;
import eu.ohim.sp.external.ws.exception.ResourceFaultException;
import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;

@Dependent @ResourceClientOutside
public class ResourceClientBean extends AbstractWSClient implements ResourceService {

    private static final String ADAPTER_NAME = "resource";

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ResourceClientBean.class);

    /**
     * The system configuration service.
     */
	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

    /**
     * Actual client to the web service
     */
    private ResourceClientWS webServiceRef;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        super.init(ADAPTER_NAME);
        if (getAdapterEnabled()) {
            webServiceRef = new ResourceClientWSService(getWsdlLocation()).getManageResourcePort();
        }
    }

    @Override
    public String getMessage(String messageKey) {
        String message = null;
        if (getAdapterEnabled()) {
            try {
                message = webServiceRef.getMessage(messageKey);
            } catch (ResourceFaultException exc) {
                LOGGER.error(" getMessage ERROR WS SOAP: " + exc.getMessage());
            }
        }
        return message;
    }

    /*
     * (non-Javadoc)
     * @see eu.ohim.sp.external.adaptors.AbstractWSClient#getErrorCode()
     */
    @Override
    protected String getErrorCode() {
        return "error.generic";
    }

    /*
     * (non-Javadoc)
     * @see
     * eu.ohim.sp.external.adaptors.AbstractWSClient#getConfigurationService
     * ()
     */
    @Override
    public ConfigurationService getConfigurationService() {
        return configurationService;
    }
}
