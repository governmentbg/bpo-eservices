/*
 *  FspDomain:: RepresentativeConverterTest 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.filing.domain.tm.AddressBook;
import eu.ohim.sp.filing.domain.tm.FormattedAddress;
import eu.ohim.sp.filing.domain.tm.FormattedName;
import eu.ohim.sp.filing.domain.tm.FormattedNameAddress;
import eu.ohim.sp.filing.domain.tm.FreeFormatNameDetailsType;
import eu.ohim.sp.filing.domain.tm.FreeFormatNameType;
import eu.ohim.sp.filing.domain.tm.ISOCountryCode;
import eu.ohim.sp.filing.domain.tm.Text;

public class RepresentativeConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		Representative representative = new Representative();
		representative.setLegalForm("screw");
		representative.setNationality("EL");
		representative.setDomicileCountry("GR");
		
		representative.setDomicileLocality("test");
		representative.setIncorporationCountry("GR");
		representative.setIncorporationState("Attica");
		
		representative.setCorrespondenceAddresses(new ArrayList<Address>());
		representative.getCorrespondenceAddresses().add(new Address());
		
		representative.setEmails(new ArrayList<Email>());
		representative.getEmails().add(new Email());
		representative.getEmails().get(0).setEmailAddress("test@oami.gr");
		representative.getEmails().add(new Email());
		representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		representative.setPhones(new ArrayList<Phone>());
		representative.getPhones().add(new Phone());
		representative.getPhones().get(0).setNumber("2133542352");
		representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);
		
		representative.getCorrespondenceAddresses().get(0).setCity("London");
		representative.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).setState("Attica");
		representative.getCorrespondenceAddresses().get(0).setCountry("GR");
		representative.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");
		
		representative.setAddresses(new ArrayList<Address>());
		
		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("London Address");
		representative.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		representative.getAddresses().get(0).setState("Attica Address");
		representative.getAddresses().get(0).setCountry("GR");
		representative.getAddresses().get(0).setStreetNumber("36 B");
		
		representative.setUrls(new ArrayList<String>());
		representative.getUrls().add("some url");
		representative.getUrls().add("some url 2");
		
		representative.setName(new PersonName());
		
		representative.getName().setFirstName("Vladimiros");
		representative.getName().setMiddleName("V");
		representative.getName().setLastName("Papadopoulos");
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.tm.Representative dest = 
			    dozerBeanMapper.map(representative, eu.ohim.sp.filing.domain.tm.Representative.class);
		
		assertEquals(representative.getLegalForm(), dest.getLegalForm());
		assertEquals(representative.getIncorporationCountry(), dest.getIncorporationCountry().value());
		assertEquals(representative.getNationality(), dest.getNationality());
		assertEquals(representative.getDomicileCountry(), dest.getDomicileCountry().value());
		assertEquals(representative.getDomicileLocality(), dest.getDomicileLocality());
		
		assertEquals(representative.getIncorporationState(), dest.getIncorporationState());
		assertEquals(representative.getAddresses().get(0).getCity(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(representative.getAddresses().get(0).getCountry(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(representative.getAddresses().get(0).getState(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(representative.getAddresses().get(0).getStreet(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(representative.getAddresses().get(0).getStreetNumber(), dest.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
	
		assertEquals(representative.getEmails().get(0).getEmailAddress(), dest.getAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(representative.getEmails().get(1).getEmailAddress(), dest.getAddressBook().getContactInformationDetails().getEmail().get(1));
		assertEquals(representative.getPhones().get(0).getNumber(), dest.getAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		assertEquals(representative.getPhones().get(0).getPhoneKind().value(), dest.getAddressBook().getContactInformationDetails().getPhone().get(0).getPhoneKind().value());

		assertEquals(representative.getName().getFirstName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName());
		assertEquals(representative.getName().getMiddleName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName());
		assertEquals(representative.getName().getLastName(), dest.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName());

		assertEquals(representative.getCorrespondenceAddresses().get(0).getCity(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getCountry(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getStreet(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getStreetNumber(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getState(), dest.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getState());

		assertEquals(representative.getUrls().get(0), dest.getAddressBook().getContactInformationDetails().getURL().get(0));
		assertEquals(representative.getUrls().get(1), dest.getAddressBook().getContactInformationDetails().getURL().get(1));
		
	}
	
	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.Representative representative = new eu.ohim.sp.filing.domain.tm.Representative();
		representative.setLegalForm("screw");
		representative.setNationality("EL");
		representative.setAddressBook(new AddressBook());
		representative.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
		representative.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

		representative.setDomicileCountry(ISOCountryCode.US);
		representative.setComment(new Text());
		representative.getComment().setValue("comment");
		
		representative.setDomicileLocality("test");
		representative.setIncorporationCountry(ISOCountryCode.GR);
		representative.setIncorporationState("Attica");
	
		representative.setCorrespondenceAddresses(new ArrayList<AddressBook>());
		representative.getCorrespondenceAddresses().add(new AddressBook());
		representative.getCorrespondenceAddresses().get(0).setFormattedNameAddress(new FormattedNameAddress());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.tm.Address());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
		
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("Limnoupoli");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("street");

		representative.getCorrespondenceAddresses().get(0).setContactInformationDetails(new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());
		representative.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whatever@oami.gr---------");
		representative.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("whoever@oami.gr");
		
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica");
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.IS);
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36");
		
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setName(new eu.ohim.sp.filing.domain.tm.Name());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().setFreeFormatName(new FreeFormatNameType());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().setFreeFormatNameDetails(new FreeFormatNameDetailsType());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().add(new Text());
		representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).setValue("Mr Christos Papas");		
		
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("London Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("Agias Lauras 36 Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("Attica Address");
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
		representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("36 B");
		
		representative.getAddressBook().getFormattedNameAddress().setName(new eu.ohim.sp.filing.domain.tm.Name());
		representative.getAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("Panagiotis");
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("P");
		representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("Papadakis");
		
		representative.getAddressBook().setContactInformationDetails(new eu.ohim.sp.filing.domain.tm.ContactInformationDetails());
		
		representative.getAddressBook().getContactInformationDetails().getEmail().add("taska.sta.tramou.mou@oami.gr");
		representative.getAddressBook().getContactInformationDetails().getEmail().add("tesr.kapelo@oami.gr");
		representative.getAddressBook().getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.tm.Phone("4536546", eu.ohim.sp.filing.domain.tm.PhoneKind.UNDEFINED));
	
		representative.getAddressBook().getContactInformationDetails().getURL().add("some url");
		representative.getAddressBook().getContactInformationDetails().getURL().add("some url 2");
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Representative dest = 
			    dozerBeanMapper.map(representative, Representative.class);
		
		
		assertEquals(representative.getLegalForm(), dest.getLegalForm());
		assertEquals(representative.getNationality(), dest.getNationality());
		assertEquals(dest.getDomicileCountry(), representative.getDomicileCountry().value());
		assertEquals(representative.getDomicileLocality(), dest.getDomicileLocality());
		
		assertEquals(representative.getIncorporationState(), dest.getIncorporationState());
		assertEquals(dest.getAddresses().get(0).getCity(), representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(dest.getAddresses().get(0).getCountry(), representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(dest.getAddresses().get(0).getState(), representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
		assertEquals(dest.getAddresses().get(0).getStreet(), representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(dest.getAddresses().get(0).getStreetNumber(), representative.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
	
		assertEquals(dest.getEmails().get(0).getEmailAddress(), representative.getAddressBook().getContactInformationDetails().getEmail().get(0));
		assertEquals(dest.getEmails().get(1).getEmailAddress(), representative.getAddressBook().getContactInformationDetails().getEmail().get(1));
		assertEquals(dest.getPhones().get(0).getNumber(), representative.getAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
		assertEquals(dest.getPhones().get(0).getPhoneKind().value(), representative.getAddressBook().getContactInformationDetails().getPhone().get(0).getPhoneKind().value());
		
		assertEquals(dest.getName().getFirstName(), representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName());
		assertEquals(dest.getName().getMiddleName(), representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName());
		assertEquals(dest.getName().getLastName(), representative.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName());

		assertEquals(dest.getCorrespondenceAddresses().get(0).getCity(), representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getCountry(), representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getStreet(), representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getStreetNumber(), representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
		assertEquals(dest.getCorrespondenceAddresses().get(0).getState(), representative.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getState());

		assertEquals(dest.getUrls().get(0), representative.getAddressBook().getContactInformationDetails().getURL().get(0));
		assertEquals(dest.getUrls().get(1), representative.getAddressBook().getContactInformationDetails().getURL().get(1));
		
	}


}
