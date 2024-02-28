package eu.ohim.sp.external.outside.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.external.person.outside.AddressClientBean;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class AddressClientBeanTest {


    @InjectMocks
    AddressClientBean addressClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/address/services", new AddressManagementWS());

        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void init() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("address");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/address/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        addressClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetAddressAutocomplete() {
        Address address = new Address();
        address.setCity("Alicante");
        address.setStreet("Avenida de Europa");
        address.setPostalName("Vaggos");
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
        Collection<Address> addresses = addressClientService.getAddressAutocomplete("", "aveni", address, 1);

        assertTrue(addresses.isEmpty());
    }


    @Test
    public void testMatchAddress() {
        Address address = new Address();
        address.setCity("Alicante");
        address.setStreet("Avenida de Europa");
        address.setPostalName("Vaggos");
        Collection<Address> addresses = addressClientService.matchAddress("EM", address, 1);

        for (Address address1 : addresses) {
            assertEquals(address1.getCity(), address.getCity());
            assertEquals(address1.getStreet(), address.getStreet());
            assertEquals(address1.getPostalName(), address.getPostalName());
        }
    }
}
