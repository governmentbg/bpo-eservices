package eu.ohim.sp.external.classification.outside.ws.client;

import org.apache.log4j.Logger;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = NiceClassificationWSClientService.SERVICE_NAME, targetNamespace = NiceClassificationWSClientService.SERVICE_NAMESPACE, wsdlLocation = "wsdl/NiceClassificationService.wsdl")
public class NiceClassificationWSClientService extends Service {

    private static final Logger LOGGER = Logger.getLogger(NiceClassificationWSClientService.class);

    protected static final String SERVICE_NAMESPACE = "http://ohim.eu/sp/services/nice-classification/v3";
    protected static final String SERVICE_NAME = "NiceClassificationService";
    protected static final String PORT_NAME = "NiceClassificationServicePort";

    public NiceClassificationWSClientService(URL url) {
        super(url,  new QName(SERVICE_NAMESPACE, SERVICE_NAME));
        LOGGER.debug("Connecting to : " + url);
    }
    /**
     * 
     * @return
     *         returns ManageClassification
     */
    @WebEndpoint(name = PORT_NAME)
    public NiceClassificationWSClient getManageClassificationPort() {
        return super.getPort(new QName(SERVICE_NAMESPACE,
                PORT_NAME), NiceClassificationWSClient.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in
     *            the <code>features</code> parameter will have their default values.
     * @return
     *         returns ManageClassification
     */
    @WebEndpoint(name = PORT_NAME)
    public NiceClassificationWSClient getManageClassificationPort(WebServiceFeature... features) {
        return super.getPort(new QName(SERVICE_NAMESPACE,
                PORT_NAME), NiceClassificationWSClient.class, features);
    }

}
