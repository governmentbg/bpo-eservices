package eu.ohim.sp.external.inside.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ohim.sp.external.injectors.AddressInjector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.external.person.inside.AddressClientBean;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AddressInjector.class)
public class AddressClientBeanTest {


    @InjectMocks
    AddressClientBean addressClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    @Before
    public void init() {
        PowerMockito.mockStatic(AddressInjector.class);
        MockitoAnnotations.initMocks(this);
        addressClientService.init();
    }

    @Test
    public void testGetAddressAutocomplete() {
        Address address = new Address();
        address.setCity("Alicante");
        address.setStreet("Avenida de Europa");
        address.setPostalName("Vaggos");

        Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = new ArrayList();
        eu.ohim.sp.external.domain.contact.Address a = new eu.ohim.sp.external.domain.contact.Address(
                null, "Vaggos", "Avenida de Europa", "1", "Alicante", null, null, null, null
        );
        addressesExt.add(a);

        BDDMockito.given(AddressInjector.getAddressAutocomplete(any(), any(), any(), any()))
                .willReturn((List<eu.ohim.sp.external.domain.contact.Address>) addressesExt);


        Collection<Address> addresses = addressClientService.getAddressAutocomplete("EM", "aveni", address, 1);

        for (Address address1 : addresses) {
            assertEquals(address1.getCity(), address.getCity());
            assertEquals(address1.getStreet(), address.getStreet());
            assertEquals(address1.getPostalName(), address.getPostalName());
        }
    }

    @Test
    public void testGetAddressAutocompleteError() {
        Address address = new Address();
        address.setCity("Alicante");
        address.setStreet("Avenida de Europa");
        address.setPostalName("Vaggos");

        Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = new ArrayList();
        BDDMockito.given(AddressInjector.getAddressAutocomplete(any(), any(), any(), any()))
                .willReturn((List<eu.ohim.sp.external.domain.contact.Address>) addressesExt);

        Collection<Address> addresses = addressClientService.getAddressAutocomplete("", "aveni", address, 1);

        assertTrue(addresses.isEmpty());
    }


    @Test
    public void testMatchAddress() {
        Address address = new Address();
        address.setCity("Alicante");
        address.setStreet("Avenida de Europa");
        address.setPostalName("Vaggos");

        Collection<eu.ohim.sp.external.domain.contact.Address> addressesExt = new ArrayList();
        eu.ohim.sp.external.domain.contact.Address a = new eu.ohim.sp.external.domain.contact.Address(
                null, "Vaggos", "Avenida de Europa", "1", "Alicante", null, null, null, null
        );
        addressesExt.add(a);
        BDDMockito.given(AddressInjector.getAddressAutocomplete(any(), any(), any(), any()))
                .willReturn((List<eu.ohim.sp.external.domain.contact.Address>) addressesExt);

        Collection<Address> addresses = addressClientService.matchAddress("EM", address, 1);
        for (Address address1 : addresses) {
            assertEquals(address1.getCity(), address.getCity());
            assertEquals(address1.getStreet(), address.getStreet());
            assertEquals(address1.getPostalName(), address.getPostalName());
        }
    }
}
