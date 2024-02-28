package eu.ohim.sp.ptefiling.ui.adapter;

import eu.ohim.sp.common.ui.adapter.AddressFactory;
import eu.ohim.sp.common.ui.adapter.patent.InventorFactory;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Inventor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * Created by Raya
 * 11.04.2019
 */
public class InventorFactoryTest {

    @Mock
    private AddressFactory addressFactory;

    @InjectMocks
    private InventorFactory inventorFactory;

    private InventorForm form;
    private Inventor core;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        form = PTFactoriesTestSource.createTestInventorForm();
        core = PTFactoriesTestSource.createTestInventor();
    }


    @Test
    public void test_convertTo(){


        Address addr = PTFactoriesTestSource.createTestAddress();
        Mockito.when(addressFactory.convertFrom(form.getAddress())).thenReturn(addr);

        Inventor result = inventorFactory.convertTo(form);

        assertEquals(form.getId(), result.getIdentifiers().get(0).getValue());

        assertEquals(form.getFirstName(), result.getName().getFirstName());
        assertEquals(form.getMiddleName(), result.getName().getMiddleName());
        assertEquals(form.getSurname(), result.getName().getLastName());

        assertEquals(addr.getCountry(), result.getAddresses().get(0).getCountry());
        assertEquals(addr.getCity(), result.getAddresses().get(0).getCity());
        assertEquals(addr.getPostCode(), result.getAddresses().get(0).getPostCode());
        assertEquals(addr.getStreet(), result.getAddresses().get(0).getStreet());

        assertEquals(form.getEmail(), result.getEmails().get(0).getEmailAddress());
        assertEquals(form.getPhone(), result.getPhones().get(0).getNumber());
        assertEquals(PhoneKind.MOBILE_PHONE, result.getPhones().get(0).getPhoneKind());

    }

    @Test
    public void test_convertFrom(){
        AddressForm addr = PTFactoriesTestSource.createTestAddressForm();

        Mockito.when(addressFactory.convertTo(core.getAddresses().get(0))).thenReturn(addr);

        InventorForm result = inventorFactory.convertFrom(core);

        assertEquals(core.getName().getFirstName(), result.getFirstName());
        assertEquals(core.getName().getMiddleName(), result.getMiddleName());
        assertEquals(core.getName().getLastName(), result.getSurname());

        assertEquals(addr.getCountry(), result.getAddress().getCountry());
        assertEquals(addr.getCity(), result.getAddress().getCity());
        assertEquals(addr.getPostalCode(), result.getAddress().getPostalCode());
        assertEquals(addr.getStreet(), result.getAddress().getStreet());

        assertEquals(core.getEmails().get(0).getEmailAddress(), result.getEmail());
        assertEquals(core.getPhones().get(0).getNumber(), result.getPhone());
    }


}
