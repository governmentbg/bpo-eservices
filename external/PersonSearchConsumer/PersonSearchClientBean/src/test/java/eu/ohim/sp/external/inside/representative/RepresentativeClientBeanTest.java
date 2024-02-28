package eu.ohim.sp.external.inside.representative;

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
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.external.person.inside.RepresentativeClientBean;
import org.springframework.test.util.ReflectionTestUtils;

public class RepresentativeClientBeanTest {


    @InjectMocks
    RepresentativeClientBean representativeClientService;

    @Mock
    PersonInjector personInjector;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        representativeClientService.init();
        ReflectionTestUtils.setField(representativeClientService, "personInjector", personInjector);
    }

    @Test
    public void testGetRepresentative() {
        eu.ohim.sp.external.domain.person.Representative a = new eu.ohim.sp.external.domain.person.Representative();
        a.setPersonNumber("0001");
        when(personInjector.getRepresentative(any(), any(), any())).thenReturn(a);
        assertEquals("0001", representativeClientService.getRepresentative("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetRepresentativeNull() {
        when(personInjector.getRepresentative(any(), any(), any())).thenReturn(null);
        assertNull(representativeClientService.getRepresentative("tmefiling", "EM", null));
    }

    @Test
    public void testRepresentativeAutocomplete() {
        String module = "tmefiling";
        String office = "EM";

        when(personInjector.getRepresentativeAutocomplete(any(), any(), any(), any())).thenReturn(
                "testRepresentativeAutocomplete : tmefilingEM"
        );

        assertEquals("testRepresentativeAutocomplete : " + module + office, representativeClientService.getRepresentativeAutocomplete(module,office,"test", 5));

    }

    @Test
    public void testRepresentativeAutocompleteError() {
        String module = "";
        String office = "EM";

        when(personInjector.getRepresentativeAutocomplete(any(), any(), any(), any())).thenReturn(null);

        assertNull(representativeClientService.getRepresentativeAutocomplete(module,office,"test", 5));

    }

    @Test
    public void testGetRepresentativeAutocomplete() {
        when(personInjector.getRepresentativeAutocomplete(any(), any(), any(), any())).thenReturn(
                "testRepresentativeAutocomplete : tmefilingEM"
        );
        assertNotNull(representativeClientService.getRepresentativeAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testSearchRepresentative() {
        eu.ohim.sp.external.domain.person.Representative a = new eu.ohim.sp.external.domain.person.Representative();
        a.setPersonNumber("0001");
        a.setNationality("nationality");
        List<eu.ohim.sp.external.domain.person.Representative> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.searchRepresentative(any(), any(), any(), any(), any(), any())).thenReturn(list);
        List<Representative> representativeList = representativeClientService.searchRepresentative("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, representativeList.size());
        assertEquals("nationality", representativeList.get(0).getNationality());
    }

    @Test
    public void testMatchRepresentative() {
        eu.ohim.sp.external.domain.person.Representative a = new eu.ohim.sp.external.domain.person.Representative();
        a.setPersonNumber("0001");
        a.setNationality("UK");
        List<eu.ohim.sp.external.domain.person.Representative> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.matchRepresentative(any(), any(), any(), any())).thenReturn(list);
        Representative representative = new Representative();
        representative.setNationality("UK");
        List<Representative> representativeList = representativeClientService.matchRepresentative("tmefilig", "office", representative, 5);
        assertEquals(5, representativeList.size());
        assertEquals(representative.getNationality(), representativeList.get(0).getNationality());
    }


    @Test
    public void testSaveRepresentative() {
        when(personInjector.saveRepresentative(any(), any(), any(), any())).thenReturn(new Result());
        assertNotNull(representativeClientService.saveRepresentative("tmefiling", "EM", "user", new Representative()));
    }

}
