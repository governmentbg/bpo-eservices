package eu.ohim.sp.core.domain.converter.ds;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.filing.domain.ds.AddressBook;
import eu.ohim.sp.filing.domain.ds.ContactInformationDetails;
import eu.ohim.sp.filing.domain.ds.Phone;

/**
 * @author serrajo
 */
public class PersonCorrespondenceToAddressBookListConverter implements CustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, @SuppressWarnings("unused") Class<?> destinationClass, @SuppressWarnings("unused") Class<?> sourceClass) {
    	
    	if (sourceFieldValue instanceof PersonRole) {

    		PersonRole personRole = (PersonRole) sourceFieldValue;
    		List<eu.ohim.sp.filing.domain.ds.AddressBook> addressBooks = new ArrayList<AddressBook>();
    		
    		int size = personRole.getCorrespondenceAddresses() != null ? personRole.getCorrespondenceAddresses().size() : 0;
    		for (int i = 0; i < size; i++) {
    			Address address = personRole.getCorrespondenceAddresses().get(i);
    			eu.ohim.sp.filing.domain.ds.AddressBook addressBook = mapper.map(address, eu.ohim.sp.filing.domain.ds.AddressBook.class, "fullAddress");
    			
    			ContactInformationDetails contactInformationDetails = new ContactInformationDetails();
    			contactInformationDetails.setEmail(new ArrayList<String>());
    			contactInformationDetails.setPhone(new ArrayList<Phone>());
    			
    			// Address 1 - Email 1 or empty
    			// Address 2 - Email 2 or empty
    			// Address 3 - Email 3 or empty
    			// ...
    			if (personRole.getCorrespondenceEmails() != null && personRole.getCorrespondenceEmails().size() >= (i+1)) {
    				contactInformationDetails.getEmail().add(mapper.map(personRole.getCorrespondenceEmails().get(i), String.class));
    			}

    			// Address 1 - Phone 1 or empty
    			// Address 1 - Fax 1 or empty
    			// Address 2 - Phone 2 or empty
    			// Address 2 - Fax 2 or empty
    			// ...
    			if (personRole.getCorrespondencePhones() != null && personRole.getCorrespondencePhones().size() >= ((i*2)+2)) {
    				contactInformationDetails.getPhone().add(mapper.map(personRole.getCorrespondencePhones().get(i*2), Phone.class));
    				contactInformationDetails.getFax().add(mapper.map(personRole.getCorrespondencePhones().get((i*2)+1), String.class));
    			}
    			
    			addressBook.setContactInformationDetails(contactInformationDetails);
    			addressBooks.add(addressBook);
    		}
    		
    		return addressBooks;
    	
    	} else if (sourceFieldValue instanceof List) {
    		
    		@SuppressWarnings("unchecked")
			List<AddressBook> addressBooks = (List<AddressBook>) sourceFieldValue;
    		List<Address> correspondenceAddresses = new ArrayList<Address>();
    		List<eu.ohim.sp.core.domain.contact.Phone> correspondencePhones = new ArrayList<eu.ohim.sp.core.domain.contact.Phone>();
    		List<Email> correspondenceEmails = new ArrayList<Email>();
    		
    		for (AddressBook addressBook : addressBooks) {
    			Address address = mapper.map(addressBook, Address.class, "fullAddress");
    			correspondenceAddresses.add(address);
    			
    			int phonesSize = addressBook.getContactInformationDetails().getPhone().size();
    			int faxSize = addressBook.getContactInformationDetails().getFax().size();
    			int maxSize = NumberUtils.max(phonesSize, faxSize, 0);
    			
    			for (int i = 0; i < maxSize; i++) {
    				
    				if (i < phonesSize) {
    					correspondencePhones.add(mapper.map(addressBook.getContactInformationDetails().getPhone().get(i), eu.ohim.sp.core.domain.contact.Phone.class));
    				} else {
    					eu.ohim.sp.core.domain.contact.Phone phoneAux = new eu.ohim.sp.core.domain.contact.Phone();
    					phoneAux.setPhoneKind(PhoneKind.MOBILE_PHONE);
    					phoneAux.setNumber("");
    					correspondencePhones.add(phoneAux);
    				}
    				
    				if (i < faxSize) {
    					eu.ohim.sp.core.domain.contact.Phone fax = mapper.map(addressBook.getContactInformationDetails().getFax().get(i), eu.ohim.sp.core.domain.contact.Phone.class);
       					fax.setPhoneKind(PhoneKind.FAX);
       					correspondencePhones.add(fax);
    				} else {
    					eu.ohim.sp.core.domain.contact.Phone faxAux = new eu.ohim.sp.core.domain.contact.Phone();
    					faxAux.setPhoneKind(PhoneKind.FAX);
    					faxAux.setNumber("");
       					correspondencePhones.add(faxAux);
    				}
    					
    			}
    			
    			for (String extEmail : addressBook.getContactInformationDetails().getEmail()) {
    				correspondenceEmails.add(mapper.map(extEmail, Email.class));
    			}
    		}
    		
    		PersonRole personRole = (PersonRole) existingDestinationFieldValue;
    		personRole.setCorrespondenceAddresses(correspondenceAddresses);
    		personRole.setCorrespondencePhones(correspondencePhones);
    		personRole.setCorrespondenceEmails(correspondenceEmails);
    		return existingDestinationFieldValue;
    		
    	} else {
    		
    		return null;
    		
    	}
    		
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

}
