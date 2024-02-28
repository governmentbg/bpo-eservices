package eu.ohim.sp.external.inside.applicant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.external.domain.common.Result;
import eu.ohim.sp.external.injectors.PersonInjector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.external.person.inside.ApplicantClientBean;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Mock
    PersonInjector personInjector;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        applicantClientService.init();
        ReflectionTestUtils.setField(applicantClientService, "personInjector", personInjector);
    }

    @Test
    public void testGetApplicant() {
        eu.ohim.sp.external.domain.person.Applicant a = new eu.ohim.sp.external.domain.person.Applicant();
        a.setPersonNumber("0001");
        when(personInjector.getApplicant(any(), any(), any())).thenReturn(a);
        assertEquals("0001", applicantClientService.getApplicant("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetApplicantNull() {
        when(personInjector.getApplicant(any(), any(), any())).thenReturn(null);
        assertNull(applicantClientService.getApplicant("tmefiling", "EM", null));
    }

    @Test
    public void testApplicantAutocomplete() {
        String module = "tmefiling";
        String office = "EM";
        when(personInjector.getApplicantAutocomplete(any(), any(), any(), any())).thenReturn(
                "testApplicantAutocomplete : tmefilingEM"
        );
        assertEquals("testApplicantAutocomplete : " + module + office, applicantClientService.getApplicantAutocomplete(module, office, "test", 5));

    }

    @Test
    public void testApplicantAutocompleteError() {
        String module = "";
        String office = "EM";
        when(personInjector.getApplicantAutocomplete(any(), any(), any(), any())).thenReturn(null);
        assertNull(applicantClientService.getApplicantAutocomplete(module, office, "test", 5));
    }

    @Test
    public void testSearchApplicant() {
        eu.ohim.sp.external.domain.person.Applicant a = new eu.ohim.sp.external.domain.person.Applicant();
        a.setPersonNumber("0001");
        a.setNationality("nationality");
        List<eu.ohim.sp.external.domain.person.Applicant> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.searchApplicant(any(), any(), any(), any(), any(), any())).thenReturn(list);

        List<Applicant> applicantList = applicantClientService.searchApplicant("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, applicantList.size());
        assertEquals("nationality", applicantList.get(0).getNationality());
    }

    @Test
    public void testMatchApplicant() {
        eu.ohim.sp.external.domain.person.Applicant a = new eu.ohim.sp.external.domain.person.Applicant();
        a.setPersonNumber("0001");
        a.setNationality("UK");
        List<eu.ohim.sp.external.domain.person.Applicant> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.matchApplicant(any(), any(), any(), any())).thenReturn(list);

        Applicant applicant = new Applicant();
        applicant.setNationality("UK");
        List<Applicant> applicantList = applicantClientService.matchApplicant("tmefilig", "office", applicant, 5);
        assertEquals(5, applicantList.size());
        assertEquals(applicant.getNationality(), applicantList.get(0).getNationality());
    }

    @Test
    public void testGetApplicantAutocomplete() {
        when(personInjector.getApplicantAutocomplete(any(), any(), any(), any())).thenReturn(
                "testApplicantAutocomplete : tmefilingEM"
        );
        assertNotNull(applicantClientService.getApplicantAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testSaveApplicant() {
        eu.ohim.sp.external.domain.person.Applicant a = new eu.ohim.sp.external.domain.person.Applicant();
        a.setPersonNumber("0001");
        a.setNationality("nationality");
        when(personInjector.saveApplicant(any(), any(), any(), any())).thenReturn(new Result());
        assertNotNull(applicantClientService.saveApplicant("tmefiling", "EM", "user", new Applicant()));
    }
}
