package eu.ohim.sp.ui.tmefiling.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.tmefiling.ui.form.validator.TMPriorityFormValidator;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.flow.TMFlowBean;

public class AddPriorityControllerTest {

	@InjectMocks
	private AddPriorityController addPriorityController;
	
	@Mock
	private FlowBeanImpl flowBean;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Mock
	private TMPriorityFormValidator tmPriorityFormValidator;
    
	@Mock
	private FormValidator validator;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void initBinderTest(){
		WebDataBinder binder = mock(WebDataBinder.class);
		addPriorityController.initBinder(binder);
		
		verify(binder).setValidator(tmPriorityFormValidator);
	}
	
	
	@Test
	public void formBackingPriorityTest(){
		
		String detailsView = "detailsView";
		String id = "id";
		String maxNumber="23";
		when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber);
		ModelAndView r = addPriorityController.formBackingPriority(id , detailsView);
		
		assertNotNull(r);
	}
	
	@Test
	public void onSubmitPriorityTest(){
		
		String detailsView = "detailsView";
		
		String maxNumber="23";
		when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.CLAIM_PRIORITY_ADD_MAXNUMBER, ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber);
		PriorityForm command = mock(PriorityForm.class);
		BindingResult result = mock(BindingResult.class);
		String claimTable = "claimTablew";
		ModelAndView r = addPriorityController.onSubmitPriority(command, result, detailsView, claimTable);
		
		assertNotNull(r);
	}
	
}
