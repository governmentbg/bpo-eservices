/*******************************************************************************
 * * $Id:: AddApplicantControllerTest.java 53086 2012-12-14 09:01:44Z virgida    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.ListHelper;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.ConfigurationServiceRemote;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.person.Applicant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author ionitdi
 */
public class AddApplicantControllerTest {
    @Mock
    FlowBean flowBean;

    @Mock
    PersonServiceInterface personService;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;
    
    @Mock
    ApplicantFactory applicantFactory;

    @Mock
    FormValidator validator;
    
    @Mock
    FlowScopeDetails flowScopeDetails;

    @InjectMocks
    AddApplicantController addApplicantController = new AddApplicantController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER, "eu.ohim.sp.core.person"))
            .thenReturn("3");
        when(flowScopeDetails.getFlowModeId()).thenReturn("tm-transfer");
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER_TRANSFER, ConfigurationServiceDelegatorInterface.PERSON_COMPONENT)).thenReturn("3");
    }


    @Test
    public void formBackingLegalEntity_nullId_returnsEmptyLegalEntityForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addApplicantController.formBackingLegalEntity(null);

        /// Assert
        assertEquals("applicant/applicant_legalentity", result.getViewName());
        assertEquals(new LegalEntityForm(), result.getModel().get("applicantLegalEntityForm"));
    }

    @Test
    public void formBackingLegalEntity_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(LegalEntityForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingLegalEntity_validId_returnsLegalEntityFormWithModel()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();
        form.setId("some id");
        when(flowBean.getObject(ApplicantForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals("applicant/applicant_legalentity", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitLegalEntity_emptyModel_throwsSPException()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(null, binding, false);
    }

    @Test
    public void onSubmitLegalEntity_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalEntity_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(false);

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalEntity_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalEntity_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(new ApplicantForm()));

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicantMatches", result.getViewName());
    }

    @Test
    public void onSubmitLegalEntity_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(null));

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalEntity_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addApplicantController.onSubmitLegalEntity(new LegalEntityForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_legalentity", result.getViewName());
    }

    @Test
    public void formBackingNaturalPerson_nullId_returnsNaturalPersonForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addApplicantController.formBackingNaturalPerson(null);

        /// Assert
        assertEquals("applicant/applicant_naturalperson", result.getViewName());
        assertEquals(new NaturalPersonForm(), result.getModel().get("applicantNaturalPersonForm"));
    }

    @Test
    public void formBackingNaturalPerson_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(NaturalPersonForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingNaturalPerson_validId_returnsNaturalPersonFormWithModel()
    {
        /// Arrange
        NaturalPersonForm form = new NaturalPersonForm();
        form.setId("some id");
        when(flowBean.getObject(ApplicantForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals("applicant/applicant_naturalperson", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitNaturalPerson_emptyModel_throwsSPException()
    {
        BindingResult binding = mock(BindingResult.class);

        ModelAndView result = addApplicantController.onSubmitNaturalPerson(null, binding, false);

        fail();
    }

    @Test
    public void onSubmitNaturalPerson_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPerson_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(false);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPerson_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPerson_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(new ApplicantForm()));

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicantMatches", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPerson_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(null));

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPerson_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPerson(new NaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_naturalperson", result.getViewName());
    }

    @Test
    public void formBackingNaturalPersonSpecial_nullId_returnsNaturalPersonSpecialForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addApplicantController.formBackingNaturalPersonSpecial(null);

        /// Assert
        assertEquals("applicant/applicant_naturalpersonspecial", result.getViewName());
        assertEquals(new NaturalPersonSpecialForm(), result.getModel().get("applicantNaturalPersonSpecialForm"));
    }

    @Test
    public void formBackingNaturalPersonSpecial_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(NaturalPersonSpecialForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingNaturalPersonSpecial_validId_returnsNaturalPersonSpecialFormWithModel()
    {
        /// Arrange
        NaturalPersonSpecialForm form = new NaturalPersonSpecialForm();
        form.setId("some id");
        when(flowBean.getObject(ApplicantForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addApplicantController.getApplicant("some id");

        /// Assert
        assertEquals("applicant/applicant_naturalpersonspecial", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitNaturalPersonSpecial_emptyModel_throwsSPException()
    {
        BindingResult binding = mock(BindingResult.class);

        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(null, binding, false);
    }

    @Test
    public void onSubmitNaturalPersonSpecial_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPersonSpecial_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(false);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPersonSpecial_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchApplicant(any(ApplicantForm.class), anyInt());
        assertNotNull(result);
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPersonSpecial_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(new ApplicantForm()));

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicantMatches", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPersonSpecial_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.APPLICANT_MATCH.value()))
                .thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.APPLICANT_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchApplicant(any(ApplicantForm.class), anyInt()))
                .thenAnswer(ListHelper.setupDummyListAnswer(null));

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_card_list", result.getViewName());
    }

    @Test
    public void onSubmitNaturalPersonSpecial_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addApplicantController.onSubmitNaturalPersonSpecial(new NaturalPersonSpecialForm(), binding, false);

        /// Assert
        assertEquals("applicant/applicant_naturalpersonspecial", result.getViewName());
    }

    @Test
    public void chooseApplicantType_returnsCorrectView()
    {
        /// Arrange

        /// Act
        ModelAndView result = addApplicantController.chooseApplicantType();

        /// Assert
        assertEquals("applicant/applicant_choosetype", result.getViewName());
    }

}
