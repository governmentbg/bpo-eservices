package eu.ohim.sp.external.register.outside.ws.client;

import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.URL;

/**
 * The type Design client wS service.
 */
@WebServiceClient(name = DesignSearchClientWSService.SERVICE_NAME, targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, wsdlLocation = "wsdl/DesignSearchService.wsdl")
public class DesignSearchClientWSService extends Service {

    private static final Logger LOGGER = Logger.getLogger(DesignSearchClientWSService.class.getName());

    protected static final String SERVICE_NAMESPACE = "http://ohim.eu/sp/services/design-search/v3";
    protected static final String SERVICE_NAME = "DesignSearchService";
    protected static final String PORT_NAME = "DesignSearchServicePort";

    /**
	 * Instantiates a new Design client wS service.
	 *
	 * @param url the url
	 */
	public DesignSearchClientWSService(URL url) {
        super(url, new QName(SERVICE_NAMESPACE, "DesignSearchService"));
        LOGGER.debug("Connecting to : " + url);
    }

	/**
	 * Gets manage design port.
	 *
	 * @return the manage design port
	 */
	@WebEndpoint(name = PORT_NAME)
    public DesignSearchClientWS getManageDesignPort() {
        return super.getPort(new QName(SERVICE_NAMESPACE, PORT_NAME), DesignSearchClientWS.class);
    }

	/**
	 * Gets manage design port.
	 *
	 * @param features the features
	 * @return the manage design port
	 */
	@WebEndpoint(name = PORT_NAME)
    public DesignSearchClientWS getManageDesignPort(WebServiceFeature... features) {
        return super.getPort(new QName(SERVICE_NAMESPACE, PORT_NAME),
                DesignSearchClientWS.class, features);
    }

}
