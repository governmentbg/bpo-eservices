package eu.ohim.sp.external.register.inside;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.injectors.DesignInjector;
import eu.ohim.sp.external.injectors.ImageInjector;
import eu.ohim.sp.external.injectors.PersonInjector;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 30/06/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageInjector.class)
public class DesignSearchClientBeanTest {

    @InjectMocks
    DesignSearchClientBean designClientService;

    @Mock
    DesignInjector designInjector;

    @Mock
    PersonInjector personInjector;

    @Mock
    ConfigurationService systemConfigurationServiceInterface;
    
    @Before
    public void setup() {
        PowerMockito.mockStatic(ImageInjector.class);
        MockitoAnnotations.initMocks(this);
        designClientService.init();
        ReflectionTestUtils.setField(designClientService, "person_injector", personInjector);
        ReflectionTestUtils.setField(designClientService, "ds_injector", designInjector);
    }

    @Test
    public void testGetDesign() {
        BDDMockito.given(ImageInjector.inject(Matchers.<Design>any())).willReturn(null);
        Design d = new Design();
        d.setRegistrationNumber("0001");
        when(designInjector.getDesign(any(), any())).thenReturn(d);
        eu.ohim.sp.core.domain.design.Design design = designClientService.getDesign("EM", "0001");
        assertEquals(design.getRegistrationNumber(), "0001");
    }

    @Test
    public void testGetDesignError() {
        BDDMockito.given(ImageInjector.inject(Matchers.<Design>any())).willReturn(null);
        when(designInjector.getDesign(any(), any())).thenReturn(null);
        eu.ohim.sp.core.domain.design.Design design = designClientService.getDesign("", "0001");
        assertNull(design);
    }

    @Test(expected = NotImplementedException.class)
    public void testGetDesignAutocomplete() {
        String office = "EM";
        String text = "test";

        String response = designClientService.getDesignAutocomplete(office, text, 5);

        assertEquals(response, "getDesignAutocomplete"+office+text);
    }

    @Test(expected = NotImplementedException.class)
    public void testGetDesignAutocompleteError() {
        String office = "";
        String text = "test";

        String response = designClientService.getDesignAutocomplete(office, text, 5);

    }

    @Test
    public void testGetDesignApplcation() {
        BDDMockito.given(ImageInjector.inject(Matchers.<Design>any())).willReturn(null);
        DesignApplication d = new DesignApplication();
        d.setReceivingOffice("EM");
        d.setApplicationNumber("0001");
        when(designInjector.getDesignApplication(any(), any(), any())).thenReturn(d);
        eu.ohim.sp.core.domain.design.DesignApplication designApplication = designClientService.getDesignApplication("EM", "0001", null, true);
        assertEquals(designApplication.getReceivingOffice(), "EM");
        assertEquals(designApplication.getApplicationNumber(), "0001");
    }

    @Test
    public void testGetDesignApplcationError() {
        BDDMockito.given(ImageInjector.inject(Matchers.<Design>any())).willReturn(null);
        when(designInjector.getDesignApplication(any(), any(), any())).thenReturn(null);
        eu.ohim.sp.core.domain.design.DesignApplication designApplication = designClientService.getDesignApplication("", "0001", null,true);
        assertNull(designApplication);
    }

}
