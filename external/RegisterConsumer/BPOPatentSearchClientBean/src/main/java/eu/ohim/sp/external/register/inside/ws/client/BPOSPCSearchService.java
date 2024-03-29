
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
@WebServiceClient(name = "BPOSPCSearchService", targetNamespace = "http://bg.org/bpo/register/search/", wsdlLocation = "/wsdl/bpo-spc-search.wsdl")
public class BPOSPCSearchService
    extends Service
{

    private final static URL BPOSPCSEARCHSERVICE_WSDL_LOCATION;
    private final static WebServiceException BPOSPCSEARCHSERVICE_EXCEPTION;
    private final static QName BPOSPCSEARCHSERVICE_QNAME = new QName("http://bg.org/bpo/register/search/", "BPOSPCSearchService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("/wsdl/bpo-spc-search.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BPOSPCSEARCHSERVICE_WSDL_LOCATION = url;
        BPOSPCSEARCHSERVICE_EXCEPTION = e;
    }

    public BPOSPCSearchService() {
        super(__getWsdlLocation(), BPOSPCSEARCHSERVICE_QNAME);
    }

    public BPOSPCSearchService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BPOSPCSEARCHSERVICE_QNAME, features);
    }

    public BPOSPCSearchService(URL wsdlLocation) {
        super(wsdlLocation, BPOSPCSEARCHSERVICE_QNAME);
    }

    public BPOSPCSearchService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BPOSPCSEARCHSERVICE_QNAME, features);
    }

    public BPOSPCSearchService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BPOSPCSearchService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BPOSPCSearch
     */
    @WebEndpoint(name = "BPOSPCSearchServiceDefaultPort")
    public BPOSPCSearch getBPOSPCSearchServiceDefaultPort() {
        return super.getPort(new QName("http://bg.org/bpo/register/search/", "BPOSPCSearchServiceDefaultPort"), BPOSPCSearch.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BPOSPCSearch
     */
    @WebEndpoint(name = "BPOSPCSearchServiceDefaultPort")
    public BPOSPCSearch getBPOSPCSearchServiceDefaultPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://bg.org/bpo/register/search/", "BPOSPCSearchServiceDefaultPort"), BPOSPCSearch.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BPOSPCSEARCHSERVICE_EXCEPTION!= null) {
            throw BPOSPCSEARCHSERVICE_EXCEPTION;
        }
        return BPOSPCSEARCHSERVICE_WSDL_LOCATION;
    }

}
