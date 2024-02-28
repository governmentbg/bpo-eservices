package eu.ohim.sp.dsefiling.ui.service.mock;

import java.util.Collection;

import eu.ohim.sp.core.person.AddressService;
import eu.ohim.sp.core.domain.contact.Address;

public class MockedAddressService implements AddressService {

	@Override
	public Collection<Address> getAddressAutocomplete(String module,
			String text, Address address, int numberOfRows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Address> matchAddress(String module, Address address,
			int numberOfResults) {
		// TODO Auto-generated method stub
		return null;
	}

}
