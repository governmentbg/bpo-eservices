package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.payment.Fee;

@Component
public class AddressFactory implements UIFactory<AddressForm, Address>{

	@Override
	public AddressForm convertTo(Address address) {
		if (address == null) {
			return new AddressForm();
		}

		AddressForm form = new AddressForm();
		form.setCity(address.getCity());
		form.setCountry(address.getCountry());
		form.setStateprovince(address.getState());
		form.setPostalCode(address.getPostCode());
        form.setHouseNumber(address.getStreetNumber());
		form.setStreet(address.getStreet());

		return form;
	}

	@Override
	public Address convertFrom(AddressForm form) {
		if (form == null) {
			return new Address();
		}

		if (form.getCity() == null && form.getCountry() == null 
				&& form.getHouseNumber() == null && form.getPostalCode() == null 
				&& form.getStateprovince() == null && form.getStreet() == null) {
			//return null;
			return new Address();
		}
		Address core = new Address();
		core.setCity(form.getCity());
		core.setCountry(form.getCountry());
		core.setState(form.getStateprovince());
		core.setPostCode(form.getPostalCode());
        core.setStreet(form.getStreet());
        core.setStreetNumber(form.getHouseNumber());

		return core;
	}

}
