package eu.ohim.sp.external.classification.outside;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.design.ProductIndication;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/09/13
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class LocarnoClassificationClientBeanTest {

    @InjectMocks
    LocarnoClassificationClientBean locarnoClassificationClientService;

    @Mock
    ConfigurationService configurationService;

    static Endpoint endpoint = null;
    
    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/locarnoClassification/services",
                new LocarnoClassificationManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }

    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("locarnoClassification");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/locarnoClassification/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(configurationService.getObject(eq("service.adapters.list"), eq("general"), eq(Adapters.class)))
                .thenReturn(adapters);

        locarnoClassificationClientService.init();

        verify(configurationService, times(1))
                .getObject(eq("service.adapters.list"), eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testSearchProductIndication() {
        List<ProductIndication> productIndications = locarnoClassificationClientService.searchProductIndication("1",
                "1", "1", "en");

        assertEquals(productIndications.size(), 1);
        assertEquals(productIndications.get(0).getLanguageCode(), "en");
    }

    @Test(expected = SPException.class)
    public void testSearchProductIndicationError() {
        locarnoClassificationClientService.searchProductIndication("", "", "", "");

    }

    @Test
    public void testGetFullClassification() {
        List<ProductIndication> productIndications = locarnoClassificationClientService.getFullClassification("en");

        assertEquals(productIndications.size(), 1);
        assertEquals(productIndications.get(0).getLanguageCode(), "en");
    }

    @Test(expected = SPException.class)
    public void testGetFullClassificationError() {
        locarnoClassificationClientService.getFullClassification("");

    }

}
