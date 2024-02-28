package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.common.CharacterSet;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.filing.domain.ds.AddressBook;
import eu.ohim.sp.filing.domain.ds.ContactInformationDetails;
import eu.ohim.sp.filing.domain.ds.CorrespondenceAddress;
import eu.ohim.sp.filing.domain.ds.FormattedAddress;
import eu.ohim.sp.filing.domain.ds.FormattedNameAddress;

/**
 * @author ionitdi
 */
public class ContactDetailsConverterTest
{
    DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() {
        dozerBeanMapper = CommonSetup.getMapper();
    }

    @Test
    public void testConvertToFSP() {
        ContactDetails core = new ContactDetails();
        core.setAddress(new ArrayList<Address>());
        
        Address a = new Address();
        a.setCharacterSet(CharacterSet.CYRILLIC);
        a.setCity("some city");
        a.setCountry("ES");
        a.setKind("some address kind");
        a.setPostalName("some postal name");
        a.setPostCode("some postcode");
        a.setState("some state");
        a.setStreet("some street");
        a.setStreetNumber("some number");
        a.setId(123);
        core.getAddress().add(a);

        core.setEmail(new ArrayList<String>());
        core.getEmail().add("s@me.email");
        
        core.setPhone(new ArrayList<Phone>());
        core.getPhone().add(new Phone());
        core.getPhone().get(0).setPhoneKind(PhoneKind.MOBILE_PHONE);
        core.getPhone().get(0).setNumber("phone number");
        core.getPhone().get(0).setInternationalPrefix("0034");
        
        core.setFax(new ArrayList<Phone>());
        core.getFax().add(new Phone());
        core.getFax().get(0).setPhoneKind(PhoneKind.FAX);
        core.getFax().get(0).setNumber("fax number");
        core.getFax().get(0).setInternationalPrefix("0034");

        core.setUrl(new ArrayList<String>());
        core.getUrl().add("some url");
        
        /// ACT
        CorrespondenceAddress ext = dozerBeanMapper.map(core, CorrespondenceAddress.class);

        /// ASSERT
        assertEquals("some city", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCity());
        assertEquals("ES", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getCountry().value());
        // TODO: address kind
        assertEquals("some postcode", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getPostcode());
        assertEquals("some postal name", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getName().getFreeFormatName().getFreeFormatNameDetails().getFreeFormatNameLine().get(0).getValue());
        assertEquals("some state", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getState());
        assertEquals("some street", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreet());
        assertEquals("some number", ext.getCorrespondenceAddressBook().getFormattedNameAddress().getAddress().getFormattedAddress().getStreetNumber());

        assertEquals("s@me.email", ext.getCorrespondenceAddressBook().getContactInformationDetails().getEmail().get(0));
        assertEquals("phone number", ext.getCorrespondenceAddressBook().getContactInformationDetails().getPhone().get(0).getValue());
        // TODO: fax mapping
        assertEquals("fax number", ext.getCorrespondenceAddressBook().getContactInformationDetails().getFax().get(0));
        assertEquals("some url", ext.getCorrespondenceAddressBook().getContactInformationDetails().getURL().get(0));
    }

    @Test
    public void testConvertToCore() {
        CorrespondenceAddress ext = new CorrespondenceAddress();
        AddressBook addressBook = new AddressBook();
        FormattedNameAddress formattedNameAddress = new FormattedNameAddress();
        eu.ohim.sp.filing.domain.ds.Address address = new eu.ohim.sp.filing.domain.ds.Address();
        FormattedAddress formattedAddress = new FormattedAddress();
        formattedAddress.setCity("some city");
        address.setFormattedAddress(formattedAddress);
        formattedNameAddress.setAddress(address);
        addressBook.setFormattedNameAddress(formattedNameAddress);
        
        ContactInformationDetails contactInformationDetails = new ContactInformationDetails();
        contactInformationDetails.setEmail(new ArrayList<String>());
        contactInformationDetails.getEmail().add("s@me.email");
        contactInformationDetails.setPhone(new ArrayList<eu.ohim.sp.filing.domain.ds.Phone>());
        contactInformationDetails.getPhone().add(new eu.ohim.sp.filing.domain.ds.Phone());
        contactInformationDetails.getPhone().get(0).setValue("phone number");
        contactInformationDetails.setFax(new ArrayList<String>());
        contactInformationDetails.getFax().add("fax number");
        contactInformationDetails.setURL(new ArrayList<String>());
        contactInformationDetails.getURL().add("some url");
        
        addressBook.setContactInformationDetails(contactInformationDetails);
        
        ext.setCorrespondenceAddressBook(addressBook);
        
        ContactDetails core = dozerBeanMapper.map(ext, ContactDetails.class);
        
        assertEquals("some city", core.getAddress().get(0).getCity());
        assertEquals("s@me.email", core.getEmail().get(0));
        assertEquals("phone number", core.getPhone().get(0).getNumber());
        assertEquals("fax number", core.getFax().get(0).getNumber());
        assertEquals("some url", core.getUrl().get(0));
    }
}
