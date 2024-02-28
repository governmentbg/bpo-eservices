package eu.ohim.sp.external.application.outside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 12/11/13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class AdapterDisabledTest {


    @InjectMocks
    ApplicationClientBean applicationClientBean;

    @Mock
    ConfigurationService configurationService;

    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("application");
        adapter.setEnabled(false);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/classification/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(configurationService.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        applicationClientBean.init();

        verify(configurationService, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }



    @Test
    public void testLoadApplication() {
        assertNull(applicationClientBean.loadApplication("EM", "user", "provisionalID"));
    }


    @Test
    public void testSaveApplication() {
        assertNull(applicationClientBean.saveApplication("EM", "user", "provisionalID"));
    }

    @Test
    public void testGetApplicationNumber() {
        assertNull(applicationClientBean.getApplicationNumber("", "ES", "004016648"));
    }

}
