package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.filing.domain.ds.*;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class ApplicantConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        /// Arrange
        Applicant core = new Applicant();

        core.setLegalForm("some form");
        core.setNationality("FR");
        core.setDomicileCountry("ES");

        core.setDomicileLocality("some domicile");
        core.setIncorporationCountry("GB");
        core.setIncorporationState("some state");

        core.setEmails(new ArrayList<Email>());
        core.getEmails().add(new Email());
        core.getEmails().get(0).setEmailAddress("some@mail.adr");
        core.getEmails().add(new Email());
        core.getEmails().get(1).setEmailAddress("another@mail.adr");

        core.setPhones(new ArrayList<Phone>());
        core.getPhones().add(new Phone());
        core.getPhones().get(0).setNumber("123123123");
        core.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        core.setAddresses(new ArrayList<Address>());

        core.getAddresses().add(new Address());
        core.getAddresses().get(0).setCity("another city");
        core.getAddresses().get(0).setStreet("another street");
        core.getAddresses().get(0).setState("another state");
        core.getAddresses().get(0).setCountry("IT");
        core.getAddresses().get(0).setStreetNumber("another number");

        core.setUrls(new ArrayList<String>());
        core.getUrls().add("some url");

        core.setName(new PersonName());

        core.getName().setFirstName("some name");
        core.getName().setMiddleName("some middle");
        core.getName().setLastName("some last");

        core.getName().setSecondLastName("some second last");
        core.getName().setOrganizationName("some org name");

        
        core.setCorrespondenceAddresses(new ArrayList<Address>());
        core.getCorrespondenceAddresses().add(new Address());
        core.getCorrespondenceAddresses().get(0).setCity("some city");
        core.getCorrespondenceAddresses().get(0).setStreet("some street");
        core.getCorrespondenceAddresses().get(0).setState("some state");
        core.getCorrespondenceAddresses().get(0).setCountry("BG");
        core.getCorrespondenceAddresses().get(0).setStreetNumber("some number");
        core.getCorrespondenceAddresses().get(0).setPostalName("some postal name");
        core.getCorrespondenceAddresses().get(0).setOtherIndicator(true);
        
        core.setCorrespondenceEmails(new ArrayList<Email>());
        core.getCorrespondenceEmails().add(new Email());
        core.getCorrespondenceEmails().get(0).setEmailAddress("s@ome.email");
        
        core.setCorrespondencePhones(new ArrayList<Phone>());
        core.getCorrespondencePhones().add(new Phone());
        core.getCorrespondencePhones().get(0).setPhoneKind(PhoneKind.MOBILE_PHONE);
        core.getCorrespondencePhones().get(0).setNumber("corres phone number");
        core.getCorrespondencePhones().add(new Phone());
        core.getCorrespondencePhones().get(1).setPhoneKind(PhoneKind.FAX);
        core.getCorrespondencePhones().get(1).setNumber("corres fax number");
        
        core.setPrivacyWaiver(true);

        core.setIdentifiers(new ArrayList<PersonIdentifier>());
        PersonIdentifier afimi = new PersonIdentifier();
        afimi.setIdentifierKindCode("VAT Number");
        afimi.setValue("afimi value");

        PersonIdentifier doy = new PersonIdentifier();
        doy.setIdentifierKindCode("Tax office");
        doy.setValue("doy value");

        core.getIdentifiers().add(afimi);
        core.getIdentifiers().add(doy);
        core.setKind(PersonKind.LEGAL_ENTITY);

        /// Act
        eu.ohim.sp.filing.domain.ds.Applicant ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Applicant.class);

        /// Assert
        assertEquals("some form", ext.getLegalForm());
        assertEquals("FR", ext.getNationality());
        assertEquals("ES", ext.getDomicileCountry().value());
        assertEquals("some domicile", ext.getDomicileLocality());

        assertEquals("some state", ext.getIncorporationState());

        assertEquals(NameKind.LEGAL_ENTITY.value(), ext.getAddressBook().getFormattedNameAddress().getName().getNameKind().value());
        assertEquals("another city",
                     ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
        assertEquals("IT", ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
        assertEquals("another state", ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
        assertEquals("another street", ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
        assertEquals("another number",
                     ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());

        assertEquals("some@mail.adr", ext.getAddressBook().getContactInformationDetails().getEmail().get(0));
        assertEquals("another@mail.adr", ext.getAddressBook().getContactInformationDetails().getEmail().get(1));

        assertEquals("123123123", ext.getAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
        assertEquals(PhoneKind.FIXED.toString(),
                     ext.getAddressBook().getContactInformationDetails().getPhone().get(0).getPhoneKind().value());

        assertEquals("some name", ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getFirstName());
        assertEquals("some middle", ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getMiddleName());
        assertEquals("some last", ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getLastName());
        assertEquals("some second last",
                     ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getSecondLastName());
        assertEquals("some org name",
                     ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().getOrganizationName());
        
        assertEquals("some url", ext.getAddressBook().getContactInformationDetails().getURL().get(0));
        
        
        assertEquals("some city",
                     ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
        assertEquals("BG", ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
        assertEquals("some street",
                     ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
        assertEquals("some number", ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());
        assertEquals("some state",
                     ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().getState());
        assertEquals("some postal name", ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(
                0).getValue());


        assertEquals("s@ome.email", ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().get(0));
        assertEquals("corres phone number", ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getPhone().get(0).getValue());
        assertEquals(eu.ohim.sp.filing.domain.ds.PhoneKind.MOBILE_PHONE, ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getPhone().get(0).getPhoneKind());
        assertEquals(true, ext.getCorrespondenceAddresses().get(0).isOtherIndicator());
        
        assertEquals("corres fax number", ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getFax().get(0));
        
        assertEquals(Boolean.TRUE, ext.getAddressBook().isDataPrivacyWaiver());

        assertEquals("afimi value", ext.getIdentifiers().get(0).getValue());
        assertEquals("VAT Number", ext.getIdentifiers().get(0).getIdentifierKindCode());

        assertEquals("doy value", ext.getIdentifiers().get(1).getValue());
        assertEquals("Tax office", ext.getIdentifiers().get(1).getIdentifierKindCode());

    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.Applicant ext = new eu.ohim.sp.filing.domain.ds.Applicant();
        ext.setLegalForm("some form");
        ext.setNationality("ES");
        ext.setAddressBook(new AddressBook());
        ext.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
        ext.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.ds.Address());
        ext.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());
        
        ext.setDomicileCountry(ISOCountryCode.US);
        ext.setComment(new Text());
        ext.getComment().setValue("some comment");

        ext.setDomicileLocality("some domicile");
        ext.setIncorporationCountry(ISOCountryCode.GR);
        ext.setIncorporationState("some state");

        ext.getCorrespondenceAddresses().add(new AddressBook());

        ext.getCorrespondenceAddresses().get(0).setFormattedNameAddress(new FormattedNameAddress());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.ds.Address());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("some city");
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.FR);
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("some street");

        ext.getCorrespondenceAddresses().get(0).setContactInformationDetails(new ContactInformationDetails());
        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("s@me.mail");
        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getEmail().add("an@ther.mail");


        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCity("some city");
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("some street");
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setState("some state");
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(
                ISOCountryCode.IT);
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber(
                "some number");

        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().setName(new Name());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().setFreeFormatName(new FreeFormatNameType());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().setFreeFormatNameDetails(
                new FreeFormatNameDetailsType());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().add(
                new Text());
        ext.getCorrespondenceAddresses().get(0).getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(
                0).setValue("some postal name");

        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().setPhone(new ArrayList<eu.ohim.sp.filing.domain.ds.Phone>());
        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getPhone().add(new eu.ohim.sp.filing.domain.ds.Phone("some corres phone number", eu.ohim.sp.filing.domain.ds.PhoneKind.MOBILE_PHONE));
        
        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().setFax(new ArrayList<String>());
        ext.getCorrespondenceAddresses().get(0).getContactInformationDetails().getFax().add("some corres fax number");
        
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCity("another city");
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreet("another street");
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setState("another state");
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setCountry(ISOCountryCode.GB);
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setStreetNumber("another number");
        ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().setPostcode("012012");

        ext.getAddressBook().getFormattedNameAddress().setName(new Name());
        ext.getAddressBook().getFormattedNameAddress().getName().setFormattedName(new FormattedName());
        ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setFirstName("some first name");
        ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setMiddleName("some middle name");
        ext.getAddressBook().getFormattedNameAddress().getName().getFormattedName().setLastName("some last name");

        ext.getAddressBook().setContactInformationDetails(new ContactInformationDetails());

        ext.getAddressBook().getContactInformationDetails().getEmail().add("first@e.mail");
        ext.getAddressBook().getContactInformationDetails().getEmail().add("sec@nd.mail");
        ext.getAddressBook().getContactInformationDetails().getPhone().add(
                new eu.ohim.sp.filing.domain.ds.Phone("123456", eu.ohim.sp.filing.domain.ds.PhoneKind.OTHER));

        ext.getAddressBook().getContactInformationDetails().getURL().add("some url");
        ext.getAddressBook().getContactInformationDetails().getPhone().add(
                new eu.ohim.sp.filing.domain.ds.Phone("654321", eu.ohim.sp.filing.domain.ds.PhoneKind.OTHER));

        ext.getAddressBook().setDataPrivacyWaiver(Boolean.TRUE);

        ext.setIdentifiers(new ArrayList<Identifier>());
        ext.getIdentifiers().add(new Identifier("afimi value", "Afimi"));
        ext.getIdentifiers().add(new Identifier("doy value", "DOY"));


        /// Act
        Applicant core = dozerBeanMapper.map(ext, Applicant.class);

        /// Assert
        assertEquals("some form", core.getLegalForm());
        assertEquals("ES", core.getNationality());
        assertEquals("US", core.getDomicileCountry());
        assertEquals("some domicile", core.getDomicileLocality());

        assertEquals("some state", core.getIncorporationState());
        assertEquals("another city", core.getAddresses().get(0).getCity());
        assertEquals("GB", core.getAddresses().get(0).getCountry());
        assertEquals("another state", core.getAddresses().get(0).getState());
        assertEquals("another street", core.getAddresses().get(0).getStreet());
        assertEquals("another number", core.getAddresses().get(0).getStreetNumber());
        
        assertEquals("first@e.mail", core.getEmails().get(0).getEmailAddress());
        assertEquals("sec@nd.mail", core.getEmails().get(1).getEmailAddress());

        assertEquals("123456", core.getPhones().get(0).getNumber());
        assertEquals("Other", core.getPhones().get(0).getPhoneKind().value());

        assertEquals("some first name", core.getName().getFirstName());
        assertEquals("some middle name", core.getName().getMiddleName());
        assertEquals("some last name", core.getName().getLastName());

        assertEquals("some city", core.getCorrespondenceAddresses().get(0).getCity());
        assertEquals("IT", core.getCorrespondenceAddresses().get(0).getCountry());
        assertEquals("some street", core.getCorrespondenceAddresses().get(0).getStreet());
        assertEquals("some number", core.getCorrespondenceAddresses().get(0).getStreetNumber());
        assertEquals("some state", core.getCorrespondenceAddresses().get(0).getState());
        assertEquals("some postal name", core.getCorrespondenceAddresses().get(0).getPostalName());

        assertEquals("some url", core.getUrls().get(0));

        assertEquals("s@me.mail", core.getCorrespondenceEmails().get(0).getEmailAddress());
        assertEquals("some corres phone number", core.getCorrespondencePhones().get(0).getNumber());
        assertEquals(PhoneKind.MOBILE_PHONE, core.getCorrespondencePhones().get(0).getPhoneKind());
        
        assertEquals("some corres fax number", core.getCorrespondencePhones().get(1).getNumber());
        
        assertEquals(Boolean.TRUE, core.isPrivacyWaiver());

        assertEquals("afimi value", core.getIdentifiers().get(0).getValue());
        assertEquals("doy value", core.getIdentifiers().get(1).getValue());
    }
}
