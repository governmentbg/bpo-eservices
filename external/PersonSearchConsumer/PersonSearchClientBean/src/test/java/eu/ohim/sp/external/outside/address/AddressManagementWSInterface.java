package eu.ohim.sp.external.outside.address;

import eu.ohim.sp.external.domain.contact.Address;
import eu.ohim.sp.external.ws.exception.AddressFaultException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface AddressManagementWSInterface {


    /**
     *
     * @param module
     * @param address
     * @param numberOfResults
     * @return
     *     returns java.util.List<eu.ohim.sp.external.services.applicant.Applicant>
     */
    @WebMethod
    List<Address> matchAddress(String module, Address address, int numberOfResults) throws AddressFaultException;

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
    List<Address> getAddressAutocomplete(String module, String text, Address address, int numberOfRows) throws AddressFaultException;
}
