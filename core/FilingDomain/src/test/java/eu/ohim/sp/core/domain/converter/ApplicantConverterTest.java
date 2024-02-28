/*
 *  FspDomain:: ApplicantConverterTest 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.filing.domain.tm.*;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ApplicantConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		Applicant applicant = new Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("EL");
		applicant.setDomicileCountry("GR");
        applicant.setOwner(false);

		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry("GR");
		applicant.setIncorporationState("Attica");
		
		applicant.setCorrespondenceAddresses(new ArrayList<Address>());
		applicant.getCorrespondenceAddresses().add(new Address());
		
		applicant.setEmails(new ArrayList<Email>());
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(0).setEmailAddress("test@oami.gr");
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		applicant.setPhones(new ArrayList<Phone>());
		applicant.getPhones().add(new Phone());
		applicant.getPhones().get(0).setNumber("2133542352");
		applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);
		
		applicant.getCorrespondenceAddresses().get(0).setCity("London");
		applicant.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
		applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");
		applicant.setCorrespondenceEmails(new ArrayList<Email>());
		applicant.getCorrespondenceEmails().add(new Email());
		applicant.getCorrespondenceEmails().get(0).setEmailAddress("testcor@oami.gr");
		
		applicant.setAddresses(new ArrayList<Address>());
		
		applicant.getAddresses().add(new Address());
		applicant.getAddresses().get(0).setCity("London Address");
		applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		applicant.getAddresses().get(0).setState("Attica Address");
		applicant.getAddresses().get(0).setCountry("GR");
		applicant.getAddresses().get(0).setStreetNumber("36 B");
		
		applicant.setUrls(new ArrayList<String>());
		applicant.getUrls().add("some url");
		
		applicant.setName(new PersonName());
		
		applicant.getName().setFirstName("Nikos");
		applicant.getName().setMiddleName("G");
		applicant.getName().setLastName("Papadopoulos");

		applicant.getName().setSecondLastName("secondLastName");
		applicant.getName().setOrganizationName("organizationName");

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
        
		eu.ohim.sp.filing.domain.tm.Applicant dest =
				dozerBeanMapper.map(applicant, eu.ohim.sp.filing.domain.tm.Applicant.class);

		assertEquals(applicant.getLegalForm(), dest.getLegalForm());
		assertEquals(applicant.getNationality(), dest.getNationality());
		assertEquals(applicant.getDomicileCountry(), dest.getDomicileCountry().value());
		assertEquals(applicant.getDomicileLocality(), dest.getDomicileLocality());
        assertEquals(applicant.getOwner(), dest.isApplicantIsOwner());

        assertEquals(applicant.getIncorporationState(), dest.getIncorporationState());
		
		assertEquals(applicant.getAddresses().get(0).getCity(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(applicant.getAddresses().get(0).getCountry(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(applicant.getAddresses().get(0).getState(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(applicant.getAddresses().get(0).getStreet(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(applicant.getAddresses().get(0).getStreetNumber(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
	
		assertEquals(applicant.getEmails().get(0).getEmailAddress(), dest.getAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(applicant.getEmails().get(1).getEmailAddress(), dest.getAddressBook().getContactInformationDetails().getEmail().get(1));

		assertEquals(applicant.getPhones().get(0).getNumber(), dest.getAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		assertEquals(applicant.getPhones().get(0).getPhoneKind().value(), dest.getAddressBook().getContactInformationDetails().getPhone().get(0).getPhoneKind().value());

		assertEquals(applicant.getName().getFirstName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName());
		assertEquals(applicant.getName().getMiddleName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName());
		assertEquals(applicant.getName().getLastName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName());
		assertEquals(applicant.getName().getSecondLastName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getSecondLastName());
		assertEquals(applicant.getName().getOrganizationName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getOrganizationName());

		assertEquals(applicant.getCorrespondenceAddresses().get(0).getCity(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getCountry(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getStreet(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getStreetNumber(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getState(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getPostalName(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
		assertEquals(applicant.getCorrespondenceEmails().get(0).getEmailAddress(), dest.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().get(0));
		
		
		assertEquals(applicant.getUrls().get(0), dest.getAddressBook().getContactInformationDetails().getURL().get(0));
	}
	
	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.Applicant applicant = new eu.ohim.sp.filing.domain.tm.Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("EL");
        applicant.setApplicantIsOwner(false);

		applicant.setAddressBook(new AddressBook());
		applicant.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
		applicant.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

		applicant.setDomicileCountry(ISOCountryCode.US);
		applicant.setComment(new Text());
		applicant.getComment().setValue("comment");
		
		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry(ISOCountryCode.GR);
		applicant.setIncorporationState("Attica");
		
		applicant.getCorrespondenceAddresses().add(new AddressBook());
		
		applicant.getCorrespondenceAddresses().get(0).setFormattedNameAddress(new FormattedNameAddress());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
		
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("Limnoupoli");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("street");

		applicant.getCorrespondenceAddresses().get(0).setContactInformationDetails(new ContactInformationDetails());
		applicant.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whatever@oami.gr---------");
		applicant.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whoever@oami.gr");


		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.IS);
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36");
		
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setName(new Name());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().setFreeFormatName(new FreeFormatNameType());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().add(new Text());
		applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).setValue("Mr Christos Papas");
		
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36 Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica Address");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36 B");
		applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setPostcode("00313");
		
		applicant.getAddressBook().getFormattedNameAddress().setName(new Name());
		applicant.getAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("George");
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("M");
		applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("Papadopoulos");
		
		applicant.getAddressBook().setContactInformationDetails(new ContactInformationDetails());
		
		applicant.getAddressBook().getContactInformationDetails().getEmail().add("whatever@oami.gr");
		applicant.getAddressBook().getContactInformationDetails().getEmail().add("whoever@oami.gr");
		applicant.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("3423423", eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));
		
		applicant.getAddressBook().getContactInformationDetails().getURL().add("some url");
		applicant.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("3423423", eu.ohim.sp.filing.domain.tm.PhoneKind.OTHER));

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Applicant dest = 
				dozerBeanMapper.map(applicant, Applicant.class);
		
		assertEquals(applicant.getLegalForm(), dest.getLegalForm());
		assertEquals(applicant.getNationality(), dest.getNationality());
		assertEquals(dest.getDomicileCountry(), applicant.getDomicileCountry().value());
		assertEquals(applicant.getDomicileLocality(), dest.getDomicileLocality());
        assertEquals(applicant.isApplicantIsOwner(), dest.getOwner());


        assertEquals(applicant.getIncorporationState(), dest.getIncorporationState());
		assertEquals(dest.getAddresses().get(0).getCity(), applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(dest.getAddresses().get(0).getCountry(), applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(dest.getAddresses().get(0).getState(), applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(dest.getAddresses().get(0).getStreet(), applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(dest.getAddresses().get(0).getStreetNumber(), applicant.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
	
		assertEquals(dest.getEmails().get(0).getEmailAddress(), applicant.getAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(dest.getEmails().get(1).getEmailAddress(), applicant.getAddressBook().getContactInformationDetails().getEmail().get(1));
		
		assertEquals(dest.getPhones().get(0).getNumber(), applicant.getAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		assertEquals(dest.getPhones().get(0).getPhoneKind().value(), applicant.getAddressBook().getContactInformationDetails().getPhone().get(0).getPhoneKind().value());

		assertEquals(dest.getName().getFirstName(), applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName());
		assertEquals(dest.getName().getMiddleName(), applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName());
		assertEquals(dest.getName().getLastName(), applicant.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName());

		assertEquals(dest.getCorrespondenceAddresses().get(0).getCity(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getCountry(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getStreet(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getStreetNumber(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getState(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getPostalName(), applicant.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
		assertEquals(dest.getCorrespondenceEmails().get(0).getEmailAddress(), applicant.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().get(0));
		
		
		assertEquals(dest.getUrls().get(0), applicant.getAddressBook().getContactInformationDetails().getURL().get(0));
		
	}
}
