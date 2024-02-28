package eu.ohim.sp.external.domain.claim;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.core.domain.person.*;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.external.domain.common.CharacterSet;
import eu.ohim.sp.external.domain.person.*;
import eu.ohim.sp.external.domain.person.Applicant;
import eu.ohim.sp.external.domain.person.Gender;
import eu.ohim.sp.external.domain.person.PersonIdentifier;
import eu.ohim.sp.external.domain.person.PersonKind;
import eu.ohim.sp.external.domain.person.PersonRoleRelationship;
import eu.ohim.sp.external.domain.person.Representative;
import eu.ohim.sp.external.domain.person.TransliteratedPersonName;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;

public class PersonConverterTest {

    @Test
    public void testDummyApplicant() {
        eu.ohim.sp.external.domain.person.Applicant applicant
                = new eu.ohim.sp.external.domain.person.Applicant(BigInteger.ONE, new ArrayList<PersonIdentifier>(), PersonKind.LEGAL_ENTITY,
                new TransliteratedPersonName(CharacterSet.CYRILLIC, "title", "firstName", "middleName", "lastName", "secondLastName", "organizationName",
                        CharacterSet.GREEK, "transliteratedFirstName", "transliteratedMiddleName", "transliteratedLastName", "transliteratedSecondLastName", "transliteratedOrganizationName"),
                "personNumber", new Date(), Gender.FEMALE, "nationality", "domicileLocality", "domicileCountry", "legalForm", "incorporationCountry",
                "incorporationState", false, new ArrayList<eu.ohim.sp.external.domain.contact.Address>(), new ArrayList<eu.ohim.sp.external.domain.contact.Phone>(),
                new ArrayList<eu.ohim.sp.external.domain.contact.Email>(), new ArrayList<String>(), "preferredCorrespondenceKind", new ArrayList<eu.ohim.sp.external.domain.contact.Address>(),
                new ArrayList<eu.ohim.sp.external.domain.contact.Phone>(), new ArrayList<eu.ohim.sp.external.domain.contact.Email>(), new ArrayList<PersonRoleRelationship>(),
                BigInteger.ONE, OperationCodeType.INSERT);

        DozerBeanMapper mapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.person.Applicant dest =
                mapper.map(applicant, eu.ohim.sp.core.domain.person.Applicant.class);

        assertEquals(applicant.getLegalForm(), dest.getLegalForm());
        assertEquals(applicant.getNationality(), dest.getNationality());
        assertEquals(applicant.getDomicileCountry(), dest.getDomicileCountry());
        assertEquals(applicant.getDomicileLocality(), dest.getDomicileLocality());

        assertEquals(applicant.getIncorporationState(), dest.getIncorporationState());

        assertEquals(applicant.getName().getFirstName(), dest.getName().getFirstName());
        assertEquals(applicant.getName().getMiddleName(), dest.getName().getMiddleName());
        assertEquals(applicant.getName().getLastName(), dest.getName().getLastName());
        assertEquals(applicant.getName().getSecondLastName(), dest.getName().getSecondLastName());
        assertEquals(applicant.getName().getOrganizationName(), dest.getName().getOrganizationName());
        assertEquals(applicant.getName().getCharacterSet().value().toLowerCase(), dest.getName().getCharacterSet().toString().toLowerCase());
        //issue with dozer and TransliteratedPersonName
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliteratedFirstName(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliteratedFirstName());
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliteratedLastName(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliteratedLastName());
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliteratedMiddleName(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliteratedMiddleName());
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliteratedOrganizationName(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliteratedOrganizationName());
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliteratedSecondLastName(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliteratedSecondLastName());
        //assertEquals(((TransliteratedPersonName) applicant.getName()).getTransliterationCharacterSet().value().toLowerCase(), ((eu.ohim.sp.core.domain.person.TransliteratedPersonName) dest.getName()).getTransliterationCharacterSet().toString().toLowerCase());

    }

    @Test
	public void testApplicant() {
		eu.ohim.sp.core.domain.person.Applicant applicant = new eu.ohim.sp.core.domain.person.Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("EL");
		applicant.setDomicileCountry("GR");
		
		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry("GR");
		applicant.setIncorporationState("Attica");
        applicant.setCorrespondenceEmails(new ArrayList<Email>());
		
		applicant.setCorrespondenceAddresses(new ArrayList<Address>());
		applicant.getCorrespondenceAddresses().add(new Address());
		
		applicant.setEmails(new ArrayList<Email>());
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
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
		applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");		
		
		applicant.setAddresses(new ArrayList<Address>());
		
		applicant.getAddresses().add(new Address());
		applicant.getAddresses().get(0).setCity("London Address");
		applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		applicant.getAddresses().get(0).setState("Attica Address");
		applicant.getAddresses().get(0).setCountry("GR");
		applicant.getAddresses().get(0).setStreetNumber("36 B");
		
		applicant.setName(new PersonName());
		
		applicant.getName().setFirstName("Christos");
		applicant.getName().setMiddleName("Paparas");
		applicant.getName().setLastName("Karalis");

		applicant.getName().setSecondLastName("secondLastName");
		applicant.getName().setOrganizationName("organizationName");

		DozerBeanMapper mapper = new DozerBeanMapper();
		
		eu.ohim.sp.external.domain.person.Applicant dest =
			    mapper.map(applicant, eu.ohim.sp.external.domain.person.Applicant.class);

		assertEquals(applicant.getLegalForm(), dest.getLegalForm());
		assertEquals(applicant.getNationality(), dest.getNationality());
		assertEquals(applicant.getDomicileCountry(), dest.getDomicileCountry());
		assertEquals(applicant.getDomicileLocality(), dest.getDomicileLocality());
		
		assertEquals(applicant.getIncorporationState(), dest.getIncorporationState());
		assertEquals(applicant.getAddresses().get(0).getCity(), dest.getAddresses().get(0).getCity());
		assertEquals(applicant.getAddresses().get(0).getCountry(), dest.getAddresses().get(0).getCountry());
		assertEquals(applicant.getAddresses().get(0).getState(), dest.getAddresses().get(0).getState());
		assertEquals(applicant.getAddresses().get(0).getStreet(), dest.getAddresses().get(0).getStreet());
		assertEquals(applicant.getAddresses().get(0).getStreetNumber(), dest.getAddresses().get(0).getStreetNumber());
		assertEquals(applicant.getAddresses().get(0).getPostCode(), dest.getAddresses().get(0).getPostCode());
		
	
		assertEquals(applicant.getEmails().get(0).getEmailAddress(), dest.getEmails().get(0).getEmailAddress());
		assertEquals(applicant.getEmails().get(1).getEmailAddress(), dest.getEmails().get(1).getEmailAddress());

		assertEquals(applicant.getPhones().get(0).getNumber(), dest.getPhones().get(0).getNumber());
		assertEquals(applicant.getPhones().get(0).getPhoneKind().value(), dest.getPhones().get(0).getPhoneKind().value());

		assertEquals(applicant.getName().getFirstName(), dest.getName().getFirstName());
		assertEquals(applicant.getName().getMiddleName(), dest.getName().getMiddleName());
		assertEquals(applicant.getName().getLastName(), dest.getName().getLastName());
		assertEquals(applicant.getName().getSecondLastName(), dest.getName().getSecondLastName());
		assertEquals(applicant.getName().getOrganizationName(), dest.getName().getOrganizationName());

		assertEquals(applicant.getCorrespondenceAddresses().get(0).getCity(), dest.getCorrespondenceAddresses().get(0).getCity());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getCountry(), dest.getCorrespondenceAddresses().get(0).getCountry());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getStreet(), dest.getCorrespondenceAddresses().get(0).getStreet());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getStreetNumber(), dest.getCorrespondenceAddresses().get(0).getStreetNumber());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getState(), dest.getCorrespondenceAddresses().get(0).getState());
		assertEquals(applicant.getCorrespondenceAddresses().get(0).getPostalName(), dest.getCorrespondenceAddresses().get(0).getPostalName());
	}

	@Test
	public void testRepresentative() {
		eu.ohim.sp.core.domain.person.Representative representative = new eu.ohim.sp.core.domain.person.Representative();
		representative.setLegalForm("screw");
		representative.setNationality("EL");
		representative.setDomicileCountry("GR");
		
		//representative.setRepresentativeKind(RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS);
		representative.setEconomicConnectionIndicator(true);
		representative.setEconomicConnectionNature("nature");
	
		representative.setDomicileLocality("test");
		representative.setIncorporationCountry("GR");
		representative.setIncorporationState("Attica");
		
		representative.setCorrespondenceAddresses(new ArrayList<Address>());
		representative.getCorrespondenceAddresses().add(new Address());
		
		representative.setEmails(new ArrayList<Email>());
		representative.getEmails().add(new Email());
		representative.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
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
		representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");		
		
		representative.setAddresses(new ArrayList<Address>());
		
		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("London Address");
		representative.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
		representative.getAddresses().get(0).setState("Attica Address");
		representative.getAddresses().get(0).setCountry("GR");
		representative.getAddresses().get(0).setStreetNumber("36 B");
		
		representative.setName(new PersonName());
		
		representative.getName().setFirstName("Christos");
		representative.getName().setMiddleName("Paparas");
		representative.getName().setLastName("Karalis");
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		eu.ohim.sp.external.domain.person.Representative dest =
			    mapper.map(representative, eu.ohim.sp.external.domain.person.Representative.class);
		
		assertEquals(representative.getLegalForm(), dest.getLegalForm());
		assertEquals(representative.getIncorporationCountry(), dest.getIncorporationCountry());
		assertEquals(representative.getNationality(), dest.getNationality());
		assertEquals(representative.getDomicileCountry(), dest.getDomicileCountry());
		assertEquals(representative.getDomicileLocality(), dest.getDomicileLocality());
		
		//assertEquals(representative.getRepresentativeKind().value(), dest.getRepresentativeKind().value());
		assertEquals(representative.getEconomicConnectionNature(), dest.getEconomicConnectionNature());
		assertEquals(representative.isEconomicConnectionIndicator(), dest.isEconomicConnectionIndicator());
		
		
		assertEquals(representative.getIncorporationState(), dest.getIncorporationState());
		assertEquals(representative.getAddresses().get(0).getCity(), dest.getAddresses().get(0).getCity());
		assertEquals(representative.getAddresses().get(0).getCountry(), dest.getAddresses().get(0).getCountry());
		assertEquals(representative.getAddresses().get(0).getState(), dest.getAddresses().get(0).getState());
		assertEquals(representative.getAddresses().get(0).getStreet(), dest.getAddresses().get(0).getStreet());
		assertEquals(representative.getAddresses().get(0).getStreetNumber(), dest.getAddresses().get(0).getStreetNumber());
	
		assertEquals(representative.getEmails().get(0).getEmailAddress(), dest.getEmails().get(0).getEmailAddress());
		assertEquals(representative.getEmails().get(1).getEmailAddress(), dest.getEmails().get(1).getEmailAddress());
		assertEquals(representative.getPhones().get(0).getNumber(), dest.getPhones().get(0).getNumber());
		assertEquals(representative.getPhones().get(0).getPhoneKind().value(), dest.getPhones().get(0).getPhoneKind().value());

		assertEquals(representative.getName().getFirstName(), dest.getName().getFirstName());
		assertEquals(representative.getName().getMiddleName(), dest.getName().getMiddleName());
		assertEquals(representative.getName().getLastName(), dest.getName().getLastName());

		assertEquals(representative.getCorrespondenceAddresses().get(0).getCity(), dest.getCorrespondenceAddresses().get(0).getCity());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getCountry(), dest.getCorrespondenceAddresses().get(0).getCountry());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getStreet(), dest.getCorrespondenceAddresses().get(0).getStreet());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getStreetNumber(), dest.getCorrespondenceAddresses().get(0).getStreetNumber());
		assertEquals(representative.getCorrespondenceAddresses().get(0).getState(), dest.getCorrespondenceAddresses().get(0).getState());

	}
}
