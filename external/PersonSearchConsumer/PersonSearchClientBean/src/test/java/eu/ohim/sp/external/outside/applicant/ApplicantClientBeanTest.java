package eu.ohim.sp.external.outside.applicant;

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
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.external.person.outside.ApplicantClientBean;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 06/09/13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class ApplicantClientBeanTest {


    @InjectMocks
    ApplicantClientBean applicantClientService;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    static Endpoint endpoint = null;
    
    @BeforeClass
    public static void setupEnpoint() {
    	endpoint = Endpoint.publish("http://localhost:8380/fsp/ws/applicant/services", new ApplicantManagementWS());
    	
    	assertTrue(endpoint.isPublished());
    }

    @Before
    public void init() {

        Adapters adapters = new Adapters();
        Adapters.Adapter adapter = new Adapters.Adapter();

        adapter.setName("applicant");
        adapter.setEnabled(true);
        adapter.setWsdlLocation("http://localhost:8380/fsp/ws/applicant/services?wsdl");
        adapters.getAdapter().add(adapter);

        MockitoAnnotations.initMocks(this);
        when(systemConfigurationServiceInterface.getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class))).thenReturn(adapters);

        applicantClientService.init();

        verify(systemConfigurationServiceInterface, times(1)).getObject(eq("service.adapters.list"),
                eq("general"), eq(Adapters.class));
    }

    @AfterClass
    public static void shutdown() {
        endpoint.stop();
    }

    @Test
    public void testGetApplicant() {
        assertEquals("0001", applicantClientService.getApplicant("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetApplicantNull() {
        assertNull(applicantClientService.getApplicant("tmefiling", "EM", null));
    }

    @Test
    public void testApplicantAutocomplete() {
        String module = "tmefiling";
        String office = "EM";

        assertEquals("testApplicantAutocomplete : " + module + office, applicantClientService.getApplicantAutocomplete(module, office, "test", 5));

    }

    @Test
    public void testApplicantAutocompleteError() {
        String module = "";
        String office = "EM";

        assertNull(applicantClientService.getApplicantAutocomplete(module, office, "test", 5));
    }

    @Test
    public void testSearchApplicant() {
        List<Applicant> applicantList = applicantClientService.searchApplicant("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, applicantList.size());
        assertEquals("nationality", applicantList.get(0).getNationality());
    }

    @Test
    public void testMatchApplicant() {
        Applicant applicant = new Applicant();
        applicant.setNationality("UK");
        List<Applicant> applicantList = applicantClientService.matchApplicant("tmefilig", "office", applicant, 5);

        assertEquals(5, applicantList.size());
        assertEquals(applicant.getNationality(), applicantList.get(0).getNationality());
    }

    @Test
    public void testGetApplicantAutocomplete() {
        assertNotNull(applicantClientService.getApplicantAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testSaveApplicant() {
        assertNotNull(applicantClientService.saveApplicant("tmefiling", "EM", "user", new Applicant()));
    }
}
