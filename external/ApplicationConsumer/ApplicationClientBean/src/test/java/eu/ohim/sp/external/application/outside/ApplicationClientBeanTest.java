package eu.ohim.sp.external.application.outside;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.adapter.xsd.Adapters;
import eu.ohim.sp.core.domain.application.DraftApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.jms.*;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.Date;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 19/09/13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
@WebService
public class ApplicationClientBeanTest {

    @InjectMocks
    ApplicationClientBean applicationClientService;

    @Mock
    private ConfigurationService configurationService;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Session session;

    @Mock
    private Connection connection;

    @Mock
    private Queue queue;

    @Mock
    private MapMessage message;

    @Mock
    private MessageProducer messageProducer;

    static Endpoint endpoint = null;

    @BeforeClass
    public static void setupEnpoint() {
        try {
            endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/application/services", new ApplicationManagementWS());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertTrue(endpoint.isPublished());
    }
    
    @Before
    public void setup() {
        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        String local_address = "localhost";

        adapter.setName("application");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://" + local_address + ":8380/fsp/ws/application/services?WSDL");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(configurationService.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        applicationClientService.init();

        verify(configurationService, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testLoadApplication() {
        try {
            assertEquals(new String(applicationClientService.loadApplication("EM", "user", "provisionalID"), "UTF-8"), "EM");
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void testNotifyApplication() throws JMSException {
        DraftApplication draftApplication = new DraftApplication();
        draftApplication.setOffice("EM");
        draftApplication.setDtCreated(new Date());
        draftApplication.setProvisionalId("000001");

        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createSession(eq(false), eq(Session.AUTO_ACKNOWLEDGE))).thenReturn(session);
        when(session.createQueue(eq("DefaultQueue"))).thenReturn(queue);
        when(session.createMapMessage()).thenReturn(message);

        when(session.createProducer(queue)).thenReturn(messageProducer);


        applicationClientService.notifyApplicationFiling("EM", "eservice", draftApplication);


        verify(messageProducer, times(1)).send(message);

    }


    @Test(expected = SPException.class)
    public void testLoadApplicationError() {
        assertNull(applicationClientService.loadApplication("", "", "provisionalID"));
    }

    @Test
    public void testSaveApplicationError() {
        assertNull(applicationClientService.saveApplication("", "", "provisionalID"));
    }

    @Test
    public void testSaveApplication() {
        assertEquals(applicationClientService.saveApplication("EM", "user", "provisionalID").getErrorCode(), "success");
    }


    @Test
    public void testCheckExistingApplication() {
        assertEquals(applicationClientService.checkExistingApplication("TM_RENEWAL", "formName", "004016648", "004016648"), Boolean.TRUE);
    }


    @Test
    public void testGetApplicationNumber() {
        assertEquals(applicationClientService.getApplicationNumber("eServices", "DESIGN_APPLICATION", "004016648").getNumber(), "TEST004016648");
    }

    @Test
    public void testGetApplicationNumberError() {
        //Probably Exception should be expected instead
        assertNull(applicationClientService.getApplicationNumber("error", "ES", "004016648"));
    }

}
