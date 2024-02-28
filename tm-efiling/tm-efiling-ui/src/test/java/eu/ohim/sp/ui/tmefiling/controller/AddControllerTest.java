package eu.ohim.sp.ui.tmefiling.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;

@RunWith(MockitoJUnitRunner.class)
public class AddControllerTest {

	@InjectMocks
	private AddController addController;

	@InjectMocks
	private AddExhibitionPriorityController addExhibitionPriorityController;

	@Mock
	private FlowBean flowBean;

	@Mock
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Mock
	private FormValidator validator;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void formBackingExhPriorityTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String id = "id";
		String detailsView = "details";
		ModelAndView r = addExhibitionPriorityController.formBackingExhPriority(id, detailsView);

		assertNotNull(r);

	}

	@Test
	public void formBackingSeniorityTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String id = "id";
		String detailsView = "details";
		ModelAndView r = addController.formBackingSeniority(id, detailsView);

		assertNotNull(r);

	}

	@Test
	public void formBackingTransformationTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String id = "id";
		String detailsView = "details";
		ModelAndView r = addController.formBackingTransformation(id, detailsView);

		assertNotNull(r);

	}

	@Test
	public void onSubmitExhPriorityTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String detailsView = "details";
		String claimTable = "claimtable";
		BindingResult result = mock(BindingResult.class);
		ExhPriorityForm command = mock(ExhPriorityForm.class);

		ModelAndView r = addExhibitionPriorityController.onSubmitExhPriority(command, result, detailsView, claimTable);

		assertNotNull(r);
	}

	@Test
	public void onSubmitSeniorityTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String detailsView = "details";
		String claimTable = "claimtable";
		BindingResult result = mock(BindingResult.class);

		SeniorityForm command = mock(SeniorityForm.class);
		ModelAndView r = addController.onSubmitSeniority(command, result, detailsView, claimTable);

		assertNotNull(r);
	}

	@Test
	public void onSubmitTransformationTest() {
		int maxNumber = 23;
		when(configurationServiceDelegator.getValue(
				ConfigurationServiceDelegatorInterface.CLAIM_CONVERSION_ADD_MAXNUMBER,
				ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT)).thenReturn(maxNumber + "");

		String detailsView = "details";
		String claimTable = "claimtable";
		BindingResult result = mock(BindingResult.class);

		TransformationForm command = mock(TransformationForm.class);
		ModelAndView r = addController.onSubmitTransformation(command, result, detailsView, claimTable);

		assertNotNull(r);
	}
}
