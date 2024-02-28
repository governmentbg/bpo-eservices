package eu.ohim.sp.external.outside.address;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.contact.Address;
import eu.ohim.sp.external.services.contact.AddressFault;
import eu.ohim.sp.external.ws.exception.AddressFaultException;
import eu.ohim.sp.external.person.outside.ws.client.person.PersonWSClient;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@WebService(serviceName = "AddressService", targetNamespace = "http://ohim.eu/sp/services/address/v3", portName = "AddressServicePort", wsdlLocation = "wsdl/AddressService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class AddressManagementWS implements AddressManagementWSInterface {


    /**
     *
     * @param module
     * @param address
     * @param numberOfResults
     * @return
     *     returns java.util.List<eu.ohim.sp.external.services.applicant.Applicant>
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "address")
    public List<Address> matchAddress(
            @WebParam(name = PersonWSClient.MODULE, targetNamespace = "")
            String module,
            @WebParam(name = "address", targetNamespace = "")
            Address address,
            @WebParam(name = "numberOfResults", targetNamespace = "")
            int numberOfResults) throws AddressFaultException {
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(address);
        return addresses;
    }

    /**
     *
     * @param module
     * @param text
     * @param address
     * @param numberOfRows
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "", name = "address")
    public List<Address> getAddressAutocomplete(
            @WebParam(name = PersonWSClient.MODULE, targetNamespace = "")
            String module,
            @WebParam(name = "text", targetNamespace = "")
            String text,
            @WebParam(name = "address", targetNamespace = "")
            Address address,
            @WebParam(name = "numberOfRows", targetNamespace = "")
            int numberOfRows) throws AddressFaultException {
        if (StringUtils.isBlank(module)) {
            try {
                StringUtils.defaultIfBlank(module, null).equals("error");
            } catch (NullPointerException e) {
                AddressFault addressFault = new AddressFault();
                addressFault.setFault(new Fault());
                addressFault.getFault().setCode("error.code");
                addressFault.getFault().setMessage("system error");
                throw new AddressFaultException("system error", addressFault, e);
            }
        }

        List<Address> addresses = new ArrayList<Address>();
        addresses.add(address);
        return addresses;
    }

}
