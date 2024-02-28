
package eu.ohim.sp.external.user.outside.ws.client;

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
@WebServiceClient(name = UserWSClientService.SERVICE_NAME, targetNamespace = UserWSClientService.SERVICE_NAMESPACE, wsdlLocation = "wsdl/UserSearchService.wsdl")
public class UserWSClientService extends Service {

    private static final Logger LOGGER = Logger.getLogger(UserWSClientService.class.getName());

    protected static final String SERVICE_NAMESPACE = "http://ohim.eu/sp/services/user-search/v3";
    protected static final String SERVICE_NAME = "UserSearchService";
    protected static final String PORT_NAME = "UserSearchServicePort";

    public UserWSClientService(URL url) {
        super(url, new QName(SERVICE_NAMESPACE, SERVICE_NAME));
        LOGGER.debug("Connecting to : " + url);
    }
    
    /**
     * 
     * @return
     *     returns UserManagementWS
     */
    @WebEndpoint(name = PORT_NAME)
    public UserWSClient getManageUserPort() {
        return super.getPort(new QName(SERVICE_NAMESPACE, PORT_NAME), UserWSClient.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserManagementWS
     */
    @WebEndpoint(name = PORT_NAME)
    public UserWSClient getManageUserPort(WebServiceFeature... features) {
        return super.getPort(new QName(SERVICE_NAMESPACE, PORT_NAME), UserWSClient.class, features);
    }

}