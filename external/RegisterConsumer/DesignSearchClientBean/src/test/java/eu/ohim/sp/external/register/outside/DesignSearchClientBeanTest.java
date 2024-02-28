package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.ws.Endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class DesignSearchClientBeanTest {

    @InjectMocks
    DesignSearchClientBean designClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/design/services", new DesignManagementWS());

        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("design");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/design/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);
        
        designClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetDesign() {
        eu.ohim.sp.core.domain.design.Design design = designClientService.getDesign("EM", "0001");

        assertEquals(design.getRegistrationNumber(), "0001");
    }

    @Test
    public void testGetDesignError() {
        eu.ohim.sp.core.domain.design.Design design = designClientService.getDesign("", "0001");

        assertNull(design);
    }

    @Test
    public void testGetDesignAutocomplete() {
        String office = "EM";
        String text = "test";

        String response = designClientService.getDesignAutocomplete(office, text, 5);

        assertEquals(response, "getDesignAutocomplete"+office+text);
    }

    @Test(expected = SPException.class)
    public void testGetDesignAutocompleteError() {
        String office = "";
        String text = "test";

        String response = designClientService.getDesignAutocomplete(office, text, 5);

    }

    @Test
    public void testGetDesignApplcation() {
        eu.ohim.sp.core.domain.design.DesignApplication designApplication = designClientService.getDesignApplication("EM", "0001", null,true);

        assertEquals(designApplication.getReceivingOffice(), "EM");
        assertEquals(designApplication.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetDesignApplcationError() {
        eu.ohim.sp.core.domain.design.DesignApplication designApplication = designClientService.getDesignApplication("", "0001", null,true);


        assertNull(designApplication);
    }

}
