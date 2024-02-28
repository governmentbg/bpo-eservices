package eu.ohim.sp.common.ui.adapter.design;

import eu.ohim.sp.common.ui.adapter.AddressFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactDetailsFactory  implements UIFactory<ContactDetails,CorrespondenceAddressForm > {

	@Autowired
	private AddressFactory addressFactory;
	@Override
	public ContactDetails convertTo(CorrespondenceAddressForm form) {
        if(form == null)
        {
            return new ContactDetails();
        }
		ContactDetails contactDetails= new ContactDetails();
		
		if (form.getAddressForm() != null) {
			List<Address> addresses=new ArrayList<Address>();
			Address address=addressFactory.convertFrom(form.getAddressForm());
			address.setPostalName(form.getCorrespondenceName());
            address.setOtherIndicator(form.isSubmittedJurisdictionIndicator());
			addresses.add(address);
			contactDetails.setAddress(addresses);
		}
		
		List<String> emailList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(form.getCorrespondenceEmail())) {
			emailList.add(form.getCorrespondenceEmail());
		}
		contactDetails.setEmail(emailList);
		
		List<Phone> faxes = new ArrayList<Phone>();
		if (StringUtils.isNotEmpty(form.getCorrespondenceFax())) {
			Phone fax=new Phone();
			fax.setNumber(form.getCorrespondenceFax());
			fax.setPhoneKind(PhoneKind.FAX);
			faxes.add(fax);
		}
		contactDetails.setFax(faxes);
		
		List<Phone> phones = new ArrayList<Phone>();
		if(StringUtils.isNotEmpty(form.getCorrespondencePhone())) {
			Phone phone=new Phone();
			phone.setNumber(form.getCorrespondencePhone());
			phone.setPhoneKind(PhoneKind.MOBILE_PHONE);
			phones.add(phone);
		}
		contactDetails.setPhone(phones);

		contactDetails.setUrl(new ArrayList<String>());

		contactDetails.setElectronicCorrespondence(form.getElectronicCorrespondence());
		
		return contactDetails;
	}

	@Override
	public CorrespondenceAddressForm convertFrom(ContactDetails core) {
		CorrespondenceAddressForm toReturn=new CorrespondenceAddressForm();
		if(core.getAddress()!=null && core.getAddress().size()>0){
			toReturn.setAddressForm(addressFactory.convertTo(core.getAddress().get(0)));
			toReturn.setCorrespondenceName(core.getAddress().get(0).getPostalName());
            toReturn.setSubmittedJurisdictionIndicator(core.getAddress().get(0).isOtherIndicator());
		}
		if(core.getEmail()!=null && core.getEmail().size()>0){
			toReturn.setCorrespondenceEmail(core.getEmail().get(0));
		}
		if(core.getFax()!=null && core.getFax().size()>0){
			toReturn.setCorrespondenceFax(core.getFax().get(0).getNumber());
		}
		if(core.getPhone()!=null && core.getPhone().size()>0){
			toReturn.setCorrespondencePhone(core.getPhone().get(0).getNumber());
		}

		toReturn.setElectronicCorrespondence(core.getElectronicCorrespondence());
		
		return toReturn;
	}

}
