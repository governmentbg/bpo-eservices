package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
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
public class AddDivisionalApplicationControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    FormValidator validator;

    @InjectMocks
    AddDivisionalApplicationController addDivisionalApplicationController = new AddDivisionalApplicationController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getDivisionalApplicationInvalidId() {
    	when(dsFlowBean.getObject(DSDivisionalApplicationForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addDivisionalApplicationController.getDivisionalApplication("some id");

    	assertEquals(new DSDivisionalApplicationForm(), result.getModel().get("divisionalApplicationForm"));
    }

    @Test
    public void addDivisionalApplication() {
    	ModelAndView result = addDivisionalApplicationController.addDivisionalApplication();
    	
    	assertEquals("divisionalApplication/divisional_application", result.getViewName());
    	assertEquals(new DSDivisionalApplicationForm(), result.getModel().get("divisionalApplicationForm"));
    }
    
    @Test
    public void getDivisionalApplication() {
    	DSDivisionalApplicationForm form = new DSDivisionalApplicationForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DSDivisionalApplicationForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDivisionalApplicationController.getDivisionalApplication("some id");
    
    	assertEquals("divisionalApplication/divisional_application", result.getViewName());
    }
    
    @Test
    public void saveDivisionalApplication() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DSDivisionalApplicationForm form = new DSDivisionalApplicationForm();
    	ModelAndView result = addDivisionalApplicationController.saveDivisionalApplication(form, binding);

    	assertEquals("divisionalApplication/divisional_application_table", result.getViewName());
    }

    @Test
    public void saveDesignView_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DSDivisionalApplicationForm form = new DSDivisionalApplicationForm();
    	ModelAndView result = addDivisionalApplicationController.saveDivisionalApplication(form, binding);

    	assertEquals("divisionalApplication/divisional_application", result.getViewName());
    }
    
}
