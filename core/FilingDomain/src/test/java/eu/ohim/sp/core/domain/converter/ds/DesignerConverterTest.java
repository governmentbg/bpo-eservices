package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.filing.domain.ds.*;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author ionitdi
 */
public class DesignerConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception
    {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP()
    {
        /// Arrange
        Designer core = new Designer();

        core.setLegalForm("some form");
        core.setNationality("FR");
        core.setDomicileCountry("ES");

        core.setDomicileLocality("some domicile");
        core.setIncorporationCountry("GB");
        core.setIncorporationState("some state");

        core.setCorrespondenceAddresses(new ArrayList<Address>());
        core.getCorrespondenceAddresses().add(new Address());

        core.setEmails(new ArrayList<Email>());
        core.getEmails().add(new Email());
        core.getEmails().get(0).setEmailAddress("some@mail.adr");
        core.getEmails().add(new Email());
        core.getEmails().get(1).setEmailAddress("another@mail.adr");

        core.setPhones(new ArrayList<Phone>());
        core.getPhones().add(new Phone());
        core.getPhones().get(0).setNumber("123123123");
        core.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        core.getCorrespondenceAddresses().get(0).setCity("some city");
        core.getCorrespondenceAddresses().get(0).setStreet("some street");
        core.getCorrespondenceAddresses().get(0).setState("some state");
        core.getCorrespondenceAddresses().get(0).setCountry("BG");
        core.getCorrespondenceAddresses().get(0).setStreetNumber("some number");
        core.getCorrespondenceAddresses().get(0).setPostalName("some postal name");

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

        /// Act
        eu.ohim.sp.filing.domain.ds.Designer ext = dozerBeanMapper.map(core, eu.ohim.sp.filing.domain.ds.Designer.class, "fullDesigner");


        /// Assert


        assertEquals("another city", ext.getAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
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
    }

    @Test
    public void testConvertToCore()
    {
        /// Arrange
        eu.ohim.sp.filing.domain.ds.Designer ext = new eu.ohim.sp.filing.domain.ds.Designer();
        ext.setNationality("ES");
        ext.setAddressBook(new AddressBook());
        ext.getAddressBook().setFormattedNameAddress(new FormattedNameAddress());
        ext.getAddressBook().getFormattedNameAddress().setAddress(new eu.ohim.sp.filing.domain.ds.Address());
        ext.getAddressBook().getFormattedNameAddress().getAddress().setFormattedAddress(new FormattedAddress());

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

        /// Act
        Designer core = dozerBeanMapper.map(ext, Designer.class, "fullDesigner");

        /// Assert
        assertEquals("ES", core.getNationality());

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

        assertEquals("some url", core.getUrls().get(0));

    }
    
    @Test(expected=MappingException.class)
    public void testConvertExceptionExpected() {
    	DesignerConverter converter = new DesignerConverter();
    	converter.convert(String.class, null);
    }
    
    @Test
    public void testConvertNullExpected() {
    	DesignerConverter converter = new DesignerConverter();
    	List<String> list = new ArrayList<String>();
    	assertEquals(converter.convert(String.class, list), null);
    }
    
    @Test
    public void testConvertNullExpected2() {
    	DesignerConverter converter = new DesignerConverter();
    	List<String> list = new ArrayList<String>();
    	list.add("some value");
    	assertEquals(converter.convert(String.class, list), null);
    }
}
