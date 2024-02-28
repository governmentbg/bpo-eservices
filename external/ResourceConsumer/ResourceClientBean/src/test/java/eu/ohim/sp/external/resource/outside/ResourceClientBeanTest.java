package eu.ohim.sp.external.resource.outside;

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

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public class ResourceClientBeanTest {

    @InjectMocks
    ResourceClientBean resourceClient;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/resource/services", new ResourceManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void init() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("resource");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/resource/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        resourceClient.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetMessage() {
        assertEquals("resource : " + "tmefiling", resourceClient.getMessage("tmefiling"));
    }

    @Test
    public void testGetMessageError() {
        assertNull(resourceClient.getMessage(""));
    }

}
