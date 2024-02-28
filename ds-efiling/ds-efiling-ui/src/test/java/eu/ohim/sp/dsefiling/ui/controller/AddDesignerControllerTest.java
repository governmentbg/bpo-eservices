package eu.ohim.sp.dsefiling.ui.controller;

import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
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
public class AddDesignerControllerTest {
    @Mock
    DSFlowBean dsFlowBean;

    @Mock
    PersonServiceInterface personService;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    DesignerFactory designerFactory;

    @Mock
    FormValidator validator;

    @InjectMocks
    AddDesignerController addDesignerController = new AddDesignerController();
    
    @Mock
    DSDesignsService designsService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.DESIGNER_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.PERSON_COMPONENT)).thenReturn("3");
    }
    
    @Test
    public void getDesignerInvalidId() {
    	when(dsFlowBean.getObject(DesignerForm.class, "some id")).thenReturn(null);

    	ModelAndView result = addDesignerController.getDesigner("some id");

    	assertEquals(null, result);
    }

    @Test
    public void addDesignerWaiver() {
    	ModelAndView result = addDesignerController.formWaiver(null);
    	
    	assertEquals("designers/designer_waiver", result.getViewName());
    	assertEquals(new DesignerForm(), result.getModel().get("designerForm"));
    }
    
    @Test
    public void getDesignerWaiver() {
    	DesignerForm form = new DesignerForm();
    	form.setId("some id");
    	form.setWaiver(true);
    	when(dsFlowBean.getObject(DesignerForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignerController.getDesigner("some id");
    
    	assertEquals("designers/designer_waiver", result.getViewName());
    }
    
    @Test
    public void saveDesignerWaiver() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DesignerForm form = new DesignerForm();
    	form.setWaiver(true);
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_card_list", result.getViewName());
    }
    
    @Test
    public void saveDesignerWaiver_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DesignerForm form = new DesignerForm();
    	form.setWaiver(true);
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_waiver", result.getViewName());
    }

    @Test
    public void addDesignerBelongsToAGroup() {
    	ModelAndView result = addDesignerController.formBelongsToAGroup(null);
    	
    	assertEquals("designers/designer_group", result.getViewName());
    	assertEquals(new DesignerForm(), result.getModel().get("designerForm"));
    }
    
    @Test
    public void getDesignerBelongsToAGroup() {
    	DesignerForm form = new DesignerForm();
    	form.setId("some id");
    	form.setBelongsToAGroup(true);
    	when(dsFlowBean.getObject(DesignerForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignerController.getDesigner("some id");
    
    	assertEquals("designers/designer_group", result.getViewName());
    }
    
    @Test
    public void saveDesignerBelongsToAGroup() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DesignerForm form = new DesignerForm();
    	form.setBelongsToAGroup(true);
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_card_list", result.getViewName());
    }
    
    @Test
    public void saveDesignerBelongsToAGroup_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DesignerForm form = new DesignerForm();
    	form.setBelongsToAGroup(true);
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_group", result.getViewName());
    }
    
    @Test
    public void addDesignerNotBelongsToAGroup() {
    	ModelAndView result = addDesignerController.formNotBelongsToAGroup(null);
    	
    	assertEquals("designers/designer_notagroup", result.getViewName());
    	assertEquals(new DesignerForm(), result.getModel().get("designerForm"));
    }
    
    @Test
    public void getDesignerNotBelongsToAGroup() {
    	DesignerForm form = new DesignerForm();
    	form.setId("some id");
    	when(dsFlowBean.getObject(DesignerForm.class, "some id")).thenReturn(form);
    	
    	ModelAndView result = addDesignerController.getDesigner("some id");
    
    	assertEquals("designers/designer_notagroup", result.getViewName());
    }
    
    @Test
    public void saveDesignerNotBelongsToAGroup() {
    	BindingResult binding = mock(BindingResult.class);
    	
    	DesignerForm form = new DesignerForm();
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_card_list", result.getViewName());
    }
    
    @Test
    public void saveDesignerNotBelongsToAGroup_failsValidation() {
    	BindingResult binding = mock(BindingResult.class);
    	when(binding.hasErrors()).thenReturn(true);

    	DesignerForm form = new DesignerForm();
    	ModelAndView result = addDesignerController.onSubmitDesigner(form, binding, false);

    	assertEquals("designers/designer_notagroup", result.getViewName());
    }
    
}
