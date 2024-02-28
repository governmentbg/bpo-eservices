package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.external.person.PersonSearchClientInside;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by marcoantonioalberoalbero on 22/8/16.
 */
@Stateless
public class AddressServiceBean implements AddressServiceLocal, AddressServiceRemote {

    @Inject @PersonSearchClientInside
    AddressService addressService;

    @Override
    public Collection<Address> getAddressAutocomplete(String module, String text, Address address, int numberOfRows) {
        return addressService.getAddressAutocomplete(module, text, address, numberOfRows);
    }

    @Override
    public Collection<Address> matchAddress(String module, Address address, int numberOfResults) {
        return addressService.matchAddress(module, address, numberOfResults);
    }
}
