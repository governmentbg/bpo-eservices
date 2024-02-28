package eu.ohim.sp.external.inside.designer;

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
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.external.person.inside.DesignerClientBean;
import org.springframework.test.util.ReflectionTestUtils;

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
    PersonInjector personInjector;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        designerClientService.init();
        ReflectionTestUtils.setField(designerClientService, "personInjector", personInjector);
    }

    @Test
    public void testGetDesigner() {
        eu.ohim.sp.external.domain.person.Designer a = new eu.ohim.sp.external.domain.person.Designer();
        a.setPersonNumber("0001");
        when(personInjector.getDesigner(any(), any(), any())).thenReturn(a);
        assertEquals("0001", designerClientService.getDesigner("tmefiling", "EM", "0001").getPersonNumber());
    }

    @Test
    public void testGetDesignerNull() {
        when(personInjector.getDesigner(any(), any(), any())).thenReturn(null);
        assertNull(designerClientService.getDesigner("tmefiling", "EM", null));
    }

    @Test
    public void testDesignerAutocomplete() {
        String module = "tmefiling";
        String office = "EM";

        when(personInjector.getDesignerAutocomplete(any(), any(), any(), any())).thenReturn(
                "testDesignerAutocomplete : tmefilingEM"
        );

        assertEquals("testDesignerAutocomplete : " + module + office, designerClientService.getDesignerAutocomplete(module, office, "test", 5));

    }

    @Test
    public void testSearchDesigner() {
        eu.ohim.sp.external.domain.person.Designer a = new eu.ohim.sp.external.domain.person.Designer();
        a.setPersonNumber("0001");
        a.setNationality("nationality");
        List<eu.ohim.sp.external.domain.person.Designer> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.searchDesigner(any(), any(), any(), any(), any(), any())).thenReturn(list);
        List<Designer> designerList = designerClientService.searchDesigner("tmefiling", "EM", "0001", "name", "nationality", 5);
        assertEquals(5, designerList.size());
        assertEquals("nationality", designerList.get(0).getNationality());
    }

    @Test
    public void testMatchDesigner() {
        eu.ohim.sp.external.domain.person.Designer a = new eu.ohim.sp.external.domain.person.Designer();
        a.setPersonNumber("0001");
        a.setNationality("UK");
        List<eu.ohim.sp.external.domain.person.Designer> list = new ArrayList<>();
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        list.add(a);
        when(personInjector.matchDesigner(any(), any(), any(), any())).thenReturn(list);
        Designer designer = new Designer();
        designer.setNationality("UK");
        List<Designer> designerList = designerClientService.matchDesigner("tmefilig", "office", designer, 5);
        assertEquals(5, designerList.size());
        assertEquals(designer.getNationality(), designerList.get(0).getNationality());
    }

    @Test
    public void testGetDesignerAutocomplete() {
        when(personInjector.getDesignerAutocomplete(any(), any(), any(), any())).thenReturn(
                "testDesignerAutocomplete : tmefilingEM"
        );
        assertNotNull(designerClientService.getDesignerAutocomplete("tmefilig", "office", "Geor", 5));
    }

    @Test
    public void testGetDesignerAutocompleteError() {
        when(personInjector.getDesignerAutocomplete(any(), any(), any(), any())).thenReturn(null);
        assertNull(designerClientService.getDesignerAutocomplete("", "office", "Geor", 5));
    }

    @Test
    public void testSaveDesigner() {
        when(personInjector.saveDesigner(any(), any(), any(), any())).thenReturn(new Result());
        assertNotNull(designerClientService.saveDesigner("tmefiling", "EM", "user", new Designer()));
    }
}
