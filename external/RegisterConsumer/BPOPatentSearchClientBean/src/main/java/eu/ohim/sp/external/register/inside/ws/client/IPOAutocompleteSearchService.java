
package eu.ohim.sp.external.register.inside.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "IPOAutocompleteSearchService", targetNamespace = "http://bg.org/bpo/register/search/", wsdlLocation = "/wsdl//bpo-ipo-autocomplete.wsdl")
public class IPOAutocompleteSearchService
    extends Service
{

    private final static URL IPOAUTOCOMPLETESEARCHSERVICE_WSDL_LOCATION;
    private final static WebServiceException IPOAUTOCOMPLETESEARCHSERVICE_EXCEPTION;
    private final static QName IPOAUTOCOMPLETESEARCHSERVICE_QNAME = new QName("http://bg.org/bpo/register/search/", "IPOAutocompleteSearchService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("/wsdl//bpo-ipo-autocomplete.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        IPOAUTOCOMPLETESEARCHSERVICE_WSDL_LOCATION = url;
        IPOAUTOCOMPLETESEARCHSERVICE_EXCEPTION = e;
    }

    public IPOAutocompleteSearchService() {
        super(__getWsdlLocation(), IPOAUTOCOMPLETESEARCHSERVICE_QNAME);
    }

    public IPOAutocompleteSearchService(WebServiceFeature... features) {
        super(__getWsdlLocation(), IPOAUTOCOMPLETESEARCHSERVICE_QNAME, features);
    }

    public IPOAutocompleteSearchService(URL wsdlLocation) {
        super(wsdlLocation, IPOAUTOCOMPLETESEARCHSERVICE_QNAME);
    }

    public IPOAutocompleteSearchService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, IPOAUTOCOMPLETESEARCHSERVICE_QNAME, features);
    }

    public IPOAutocompleteSearchService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IPOAutocompleteSearchService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IPOAutocompleteSearch
     */
    @WebEndpoint(name = "IPOAutocompleteSearchServiceDefaultPort")
    public IPOAutocompleteSearch getIPOAutocompleteSearchServiceDefaultPort() {
        return super.getPort(new QName("http://bg.org/bpo/register/search/", "IPOAutocompleteSearchServiceDefaultPort"), IPOAutocompleteSearch.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IPOAutocompleteSearch
     */
    @WebEndpoint(name = "IPOAutocompleteSearchServiceDefaultPort")
    public IPOAutocompleteSearch getIPOAutocompleteSearchServiceDefaultPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://bg.org/bpo/register/search/", "IPOAutocompleteSearchServiceDefaultPort"), IPOAutocompleteSearch.class, features);
    }

    private static URL __getWsdlLocation() {
        if (IPOAUTOCOMPLETESEARCHSERVICE_EXCEPTION!= null) {
            throw IPOAUTOCOMPLETESEARCHSERVICE_EXCEPTION;
        }
        return IPOAUTOCOMPLETESEARCHSERVICE_WSDL_LOCATION;
    }

}