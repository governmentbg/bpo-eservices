package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
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
public class AddPriorityControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    FormValidator validator;

    @InjectMocks
    AddPriorityController addPriorityController = new AddPriorityController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("3");
    }
    
    @Test
    public void getPriorityInvalidId() {
    	when(dsFlowBean.getObject(DSPriorityForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addPriorityController.formBackingPriority("some id", "claim/claim_priority_details_wizard");

    	assertEquals(new DSPriorityForm(), result.getModel().get("priorityForm"));
    }
    

    @Test
    public void addPriority() {
    	ModelAndView result = addPriorityController.formBackingPriority(null, "claim/claim_priority_details_wizard");
    	
    	assertEquals("claim/claim_priority_details_wizard", result.getViewName());
    	assertEquals(new DSPriorityForm(), result.getModel().get("priorityForm"));
    }
    
    @Test
    public void getPriority() {
    	DSPriorityForm form = new DSPriorityForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DSPriorityForm.class, "some id")).thenReturn(form);

    	ModelAndView result = addPriorityController.formBackingPriority("some id", "claim/claim_priority_details_wizard");
    
    	assertEquals("claim/claim_priority_details_wizard", result.getViewName());
    }
    
    @Test
    public void savePriority() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DSPriorityForm form = new DSPriorityForm();
    	
    	ModelAndView result = addPriorityController.onSubmitPriority(form, binding, "claim/claim_priority_details_wizard", "claim/claim_table_common");

    	assertEquals("claim/claim_table_common", result.getViewName());
    }

    @Test
    public void savePriority_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DSPriorityForm form = new DSPriorityForm();
    	ModelAndView result = addPriorityController.onSubmitPriority(form, binding, "claim/claim_priority_details_wizard", "claim/claim_table_common");

    	assertEquals("claim/claim_priority_details_wizard", result.getViewName());
    }
    
}
