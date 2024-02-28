package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.controller.AddApplicationCAController;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author serrajo
 */
public class AddApplicationCAControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    FormValidator validator;

    @InjectMocks
    AddApplicationCAController addApplicationCAController = new AddApplicationCAController();
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("3");
    }
    
    @Test
    public void getApplicationCAInvalidId() {
    	when(dsFlowBean.getObject(ApplicationCAForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addApplicationCAController.formBackingApplicationCA("some id");

    	assertEquals(new ApplicationCAForm(), result.getModel().get("applicationCAForm"));
    }

    @Test
    public void addApplicationCA() {
    	ModelAndView result = addApplicationCAController.formBackingApplicationCA(null);
    	
    	assertEquals("common/correspondent/applicationCA", result.getViewName());
    	assertEquals(new ApplicationCAForm(), result.getModel().get("applicationCAForm"));
    }
    
    @Test
    public void getApplicationCA() {
    	ApplicationCAForm form = new ApplicationCAForm();
    	CorrespondenceAddressForm corresAddress = new CorrespondenceAddressForm();
    	AddressForm addressForm = new AddressForm();
    	corresAddress.setAddressForm(addressForm);
    	form.setId("some id");
    	form.setCorrespondenceAddressForm(corresAddress);
    	when(dsFlowBean.getObject(ApplicationCAForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addApplicationCAController.formBackingApplicationCA("some id");
    
    	assertEquals("common/correspondent/applicationCA", result.getViewName());
    }
    
    @Test
    public void saveApplicationCA() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	ModelAndView result = addApplicationCAController.onSubmitApplicationCA(new ApplicationCAForm(), binding);

    	assertEquals("common/correspondent/applicationCA_card_list", result.getViewName());
    }
    
    @Test
    public void saveApplicationCA_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	ModelAndView result = addApplicationCAController.onSubmitApplicationCA(new ApplicationCAForm(), binding);

    	assertEquals("common/correspondent/applicationCA", result.getViewName());
    }

}
