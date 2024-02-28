package eu.ohim.sp.common.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.adapter.AddressFactory;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.core.domain.contact.Address;

@Service(value="AddressServiceUI")
public class AddressService implements eu.ohim.sp.common.ui.service.interfaces.AddressServiceInterface {
	
	@Autowired
    private eu.ohim.sp.core.person.AddressService addressService;
	
	@Autowired
	protected AddressFactory addressFactory;
	
    public List<AddressForm> addressAutoComplete(String module, String text){    	
    	List<AddressForm> result = new ArrayList<AddressForm>();    	
    	int numberOfRows = 10;
    	Collection<Address> coreAdresses = addressService.getAddressAutocomplete(module, text, new Address(), numberOfRows);
    	
    	for(Address address: coreAdresses){
    		result.add(addressFactory.convertTo(address));
    	}
    	return result;
    }

}
