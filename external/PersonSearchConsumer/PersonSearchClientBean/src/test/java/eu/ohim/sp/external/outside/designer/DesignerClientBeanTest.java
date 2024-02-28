package eu.ohim.sp.external.outside.designer;

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
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.external.person.outside.DesignerClientBean;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class DesignerClientBeanTest {


    @InjectMocks
    DesignerClientBean designerClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/designer/services", new DesignerManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void init() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("designer");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/designer/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        designerClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetDesigner() {
        assertEquals("0001", designerClientService.getDesigner("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetDesignerNull() {
        assertNull(designerClientService.getDesigner("tmefiling", "EM", null));
    }

    @Test
    public void testDesignerAutocomplete() {
        String module = "tmefiling";
        String office = "EM";

        assertEquals("testDesignerAutocomplete : " + module + office, designerClientService.getDesignerAutocomplete(module, office, "test", 5));

    }

    @Test
    public void testSearchDesigner() {
        List<Designer> designerList = designerClientService.searchDesigner("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, designerList.size());
        assertEquals("nationality", designerList.get(0).getNationality());
    }

    @Test
    public void testMatchDesigner() {
        Designer designer = new Designer();
        designer.setNationality("UK");
        List<Designer> designerList = designerClientService.matchDesigner("tmefilig", "office", designer, 5);

        assertEquals(5, designerList.size());
        assertEquals(designer.getNationality(), designerList.get(0).getNationality());
    }

    @Test
    public void testGetDesignerAutocomplete() {
        assertNotNull(designerClientService.getDesignerAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testGetDesignerAutocompleteError() {
        assertNull(designerClientService.getDesignerAutocomplete("", "office", "Geor", 5));
    }

    @Test
    public void testSaveDesigner() {
        assertNotNull(designerClientService.saveDesigner("tmefiling", "EM", "user", new Designer()));
    }
}
