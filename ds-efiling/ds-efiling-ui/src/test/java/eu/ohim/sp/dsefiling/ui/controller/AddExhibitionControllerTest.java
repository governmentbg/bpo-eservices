package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
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
public class AddExhibitionControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    FormValidator validator;

    @InjectMocks
    AddExhibitionController addClaimController = new AddExhibitionController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CLAIM_EXHIBITION_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn("3");
    }
    
    @Test
    public void getExhPriorityInvalidId() {
    	when(dsFlowBean.getObject(DSExhPriorityForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addClaimController.formBackingExhPriority("some id", "claim/claim_exhibition_details_wizard");

    	assertEquals(new DSExhPriorityForm(), result.getModel().get("exhPriorityForm"));
    }
    
    @Test
    public void addExhPriority() {
    	ModelAndView result = addClaimController.formBackingExhPriority(null, "claim/claim_exhibition_details_wizard");
    	
    	assertEquals("claim/claim_exhibition_details_wizard", result.getViewName());
    	assertEquals(new DSExhPriorityForm(), result.getModel().get("exhPriorityForm"));
    }
    
    @Test
    public void getExhPriority() {
    	DSExhPriorityForm form = new DSExhPriorityForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DSExhPriorityForm.class, "some id")).thenReturn(form);

    	ModelAndView result = addClaimController.formBackingExhPriority("some id", "claim/claim_exhibition_details_wizard");
    
    	assertEquals("claim/claim_exhibition_details_wizard", result.getViewName());
    }
    
    @Test
    public void saveExhPriority() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DSExhPriorityForm form = new DSExhPriorityForm();
    	
    	ModelAndView result = addClaimController.onSubmitExhPriority(form, binding, "claim/claim_exhibition_details_wizard", "claim/claim_table_common");

    	assertEquals("claim/claim_table_common", result.getViewName());
    }
    
    @Test
    public void saveExhPriority_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DSExhPriorityForm form = new DSExhPriorityForm();
    	ModelAndView result = addClaimController.onSubmitExhPriority(form, binding, "claim/claim_exhibition_details_wizard", "claim/claim_table_common");

    	assertEquals("claim/claim_exhibition_details_wizard", result.getViewName());
    }
}
