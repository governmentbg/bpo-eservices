package eu.ohim.sp.external.outside.representative;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.external.person.outside.RepresentativeClientBean;

public class RepresentativeClientBeanTest {


    @InjectMocks
    RepresentativeClientBean representativeClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/representative/services", new RepresentativeManagementWS());

        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void init() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("representative");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/representative/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        representativeClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetRepresentative() {
        assertEquals("0001", representativeClientService.getRepresentative("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetRepresentativeNull() {
        assertNull(representativeClientService.getRepresentative("tmefiling", "EM", null));
    }

    @Test
    public void testRepresentativeAutocomplete() {
        String module = "tmefiling";
        String office = "EM";

        assertEquals("testRepresentativeAutocomplete : " + module + office, representativeClientService.getRepresentativeAutocomplete(module,office,"test", 5));

    }

    @Test
    public void testRepresentativeAutocompleteError() {
        String module = "";
        String office = "EM";

        assertNull(representativeClientService.getRepresentativeAutocomplete(module,office,"test", 5));

    }

    @Test
    public void testGetRepresentativeAutocomplete() {
        assertNotNull(representativeClientService.getRepresentativeAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testSearchRepresentative() {
        List<Representative> representativeList = representativeClientService.searchRepresentative("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, representativeList.size());
        assertEquals("nationality", representativeList.get(0).getNationality());
    }

    @Test
    public void testMatchRepresentative() {
        Representative representative = new Representative();
        representative.setNationality("UK");
        List<Representative> representativeList = representativeClientService.matchRepresentative("tmefilig", "office", representative, 5);

        assertEquals(5, representativeList.size());
        assertEquals(representative.getNationality(), representativeList.get(0).getNationality());
    }


    @Test
    public void testSaveRepresentative() {
        assertNotNull(representativeClientService.saveRepresentative("tmefiling", "EM", "user", new Representative()));
    }

}
