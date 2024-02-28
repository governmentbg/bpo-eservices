package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.filing.domain.ds.AddressBook;
import eu.ohim.sp.filing.domain.ds.ContactInformationDetails;
import eu.ohim.sp.filing.domain.ds.FormattedAddress;
import eu.ohim.sp.filing.domain.ds.FormattedNameAddress;
import eu.ohim.sp.filing.domain.ds.Phone;

public class PersonCorrespondenceToAddressBookListConverterTest {

	@Test
	public void testConvertPersonRole() {
		PersonCorrespondenceToAddressBookListConverter converter = new PersonCorrespondenceToAddressBookListConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);

		PersonRole personRole = new PersonRole();
		personRole.setCorrespondenceAddresses(new ArrayList<Address>());
		personRole.getCorrespondenceAddresses().add(new Address());
		personRole.getCorrespondenceAddresses().get(0).setCity("another city");
		personRole.getCorrespondenceAddresses().get(0)
				.setStreet("another street");
		personRole.getCorrespondenceAddresses().get(0)
				.setState("another state");
		personRole.getCorrespondenceAddresses().get(0).setCountry("IT");
		personRole.getCorrespondenceAddresses().get(0)
				.setStreetNumber("another number");
		personRole.setCorrespondenceEmails(new ArrayList<Email>());
		personRole.getCorrespondenceEmails().add(new Email());
		personRole.getCorrespondenceEmails().get(0)
				.setEmailAddress("some@some.com");

		List<AddressBook> addressList = (List<AddressBook>) converter.convert(
				personRole, personRole, PersonRole.class, PersonRole.class);
		assertEquals(addressList.size(), 1);
		assertEquals(addressList.get(0).getContactInformationDetails()
				.getEmail().size(), 1);

	}

	@Test
	public void testConvertList() {
		PersonCorrespondenceToAddressBookListConverter converter = new PersonCorrespondenceToAddressBookListConverter();
		DozerBeanMapper dozerBeanMapper = CommonSetup.getMapper();
		converter.setMapper(dozerBeanMapper);

		PersonRole personRole = new PersonRole();
		personRole.setCorrespondenceAddresses(new ArrayList<Address>());
		personRole.getCorrespondenceAddresses().add(new Address());
		personRole.getCorrespondenceAddresses().get(0).setCity("another city");
		personRole.getCorrespondenceAddresses().get(0)
				.setStreet("another street");
		personRole.getCorrespondenceAddresses().get(0)
				.setState("another state");
		personRole.getCorrespondenceAddresses().get(0).setCountry("IT");
		personRole.getCorrespondenceAddresses().get(0)
				.setStreetNumber("another number");
		personRole.setCorrespondenceEmails(new ArrayList<Email>());
		personRole.getCorrespondenceEmails().add(new Email());
		personRole.getCorrespondenceEmails().get(0)
				.setEmailAddress("some@some.com");
		
		List<AddressBook> addressBookList = new ArrayList<AddressBook>();
		AddressBook addressBook = new AddressBook();
		addressBook.setFormattedNameAddress(new FormattedNameAddress());
		addressBook.getFormattedNameAddress().setAddress(
				new eu.ohim.sp.filing.domain.ds.Address());
		addressBook.getFormattedNameAddress().getAddress()
				.setFormattedAddress(new FormattedAddress());
		addressBook
				.setContactInformationDetails(new ContactInformationDetails());
		addressBook.getContactInformationDetails().setEmail(
				new ArrayList<String>());
		addressBook.getContactInformationDetails().getEmail()
				.add("some@some.com");
		addressBook.getContactInformationDetails().setPhone(
				new ArrayList<Phone>());
		addressBook.getContactInformationDetails().getPhone().add(new Phone());
		addressBook.getContactInformationDetails().getPhone().get(0)
				.setPhoneKind(eu.ohim.sp.filing.domain.ds.PhoneKind.FIXED);
		addressBook.getContactInformationDetails().getPhone().get(0)
				.setValue("12313213");
		addressBook.getContactInformationDetails().setFax(new ArrayList<String>());
		addressBook.getContactInformationDetails().getFax().add("55454545454");
		addressBookList.add(addressBook);
		
		PersonRole expected = (PersonRole) converter.convert(personRole, addressBookList, null, null);
		assertEquals(expected.getCorrespondenceEmails().size(), 1);
		assertEquals(expected.getCorrespondencePhones().size(), 2);
		assertEquals(expected.getCorrespondenceAddresses().size(), 1);
	}

	@Test
	public void testConvertNull() {
		PersonCorrespondenceToAddressBookListConverter converter = new PersonCorrespondenceToAddressBookListConverter();
		assertEquals(converter.convert(null, null, null, null), null);
	}

}
