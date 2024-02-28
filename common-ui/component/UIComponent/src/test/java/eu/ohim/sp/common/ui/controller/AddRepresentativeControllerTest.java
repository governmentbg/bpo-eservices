/*******************************************************************************
 * * $Id:: AddRepresentativeControllerTest.java 53086 2012-12-14 09:01:44Z virgi#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.RepresentativeFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.util.ListHelper;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.configuration.ConfigurationServiceRemote;
import eu.ohim.sp.core.configuration.domain.services.xsd.AvailableServices;
import eu.ohim.sp.core.domain.person.Representative;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author ionitdi
 */
public class AddRepresentativeControllerTest
{
    @Mock
    FlowBean flowBean;

    @Mock
    PersonServiceInterface personService;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Mock
    ConfigurationServiceRemote systemConfigurationService;
    
    @Mock
    RepresentativeFactory representativeFactory;

    @Mock
    FormValidator validator;
    
    @Mock
    FlowScopeDetails flowScopeDetails;

    @InjectMocks
    AddRepresentativeController addRepresentativeController = new AddRepresentativeController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.REPRESENTATIVE_ADD_MAXNUMBER, "eu.ohim.sp.core.person"))
                .thenReturn("3");
        when(flowScopeDetails.getFlowModeId()).thenReturn("tm-transfer");
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.REPRESENTATIVE_ADD_MAXNUMBER_TRANSFER, ConfigurationServiceDelegatorInterface.PERSON_COMPONENT)).thenReturn("3");
        
    }

    @Test
    public void formBackingRepLegalEntity_nullId_returnsEmptyLegalEntityForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.formBackingRepLegalEntity(null);

        /// Assert
        assertEquals("representative/representative_legalentity", result.getViewName());
        assertEquals(new RepresentativeLegalEntityForm(), result.getModel().get("representativeLegalEntityForm"));
    }

    @Test
    public void formBackingRepLegalEntity_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(RepresentativeLegalEntityForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingRepLegalEntity_validId_returnsLegalEntityFormWithModel()
    {
        /// Arrange
        RepresentativeLegalEntityForm form = new RepresentativeLegalEntityForm();
        form.setId("some id");
        when(flowBean.getObject(RepresentativeForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals("representative/representative_legalentity", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitRepLegalEntity_emptyModel_throwsSPException()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(null, binding, false);
    }

    @Test
    public void onSubmitRepLegalEntity_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepLegalEntity_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(systemConfigurationService.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(false);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepLegalEntity_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepLegalEntity_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer(new Representative()));

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, false);

        /// Assert
        assertEquals("representative/representativeMatches", result.getViewName());
    }

    @Test
    public void onSubmitRepLegalEntity_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer());

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepLegalEntity_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepLegalEntity(new RepresentativeLegalEntityForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_legalentity", result.getViewName());
    }

    @Test
    public void formBackingRepNaturalPerson_nullId_returnsNaturalPersonForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.formBackingRepNaturalPerson(null);

        /// Assert
        assertEquals("representative/representative_naturalperson", result.getViewName());
        assertEquals(new RepresentativeNaturalPersonForm(), result.getModel().get("representativeNaturalPersonForm"));
    }

    @Test
    public void formBackingRepNaturalPerson_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(RepresentativeNaturalPersonForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingRepNaturalPerson_validId_returnsNaturalPersonFormWithModel()
    {
        /// Arrange
        RepresentativeNaturalPersonForm form = new RepresentativeNaturalPersonForm();
        form.setId("some id");
        when(flowBean.getObject(RepresentativeForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals("representative/representative_naturalperson", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitRepNaturalPerson_emptyModel_throwsSPException()
    {
        BindingResult binding = mock(BindingResult.class);

        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(null, binding, false);

        fail();
    }

    @Test
    public void onSubmitRepNaturalPerson_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepNaturalPerson_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(false);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepNaturalPerson_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepNaturalPerson_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer(new Representative()));

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("representative/representativeMatches", result.getViewName());
    }

    @Test
    public void onSubmitRepNaturalPerson_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer());

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitRepNaturalPerson_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitRepNaturalPerson(new RepresentativeNaturalPersonForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_naturalperson", result.getViewName());
    }

    @Test
    public void formBackingEmployeeRepresentative_nullId_returnsEmptyEmployeeRepresentativeForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.formBackingEmployeeRepresentative(null);

        /// Assert
        assertEquals("representative/representative_employeerepresentative", result.getViewName());
        assertEquals(new EmployeeRepresentativeForm(), result.getModel().get("representativeEmployeeRepresentativeForm"));
    }

    @Test
    public void formBackingEmployeeRepresentative_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(EmployeeRepresentativeForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingEmployeeRepresentative_validId_returnsEmployeeRepresentativeFormWithModel()
    {
        /// Arrange
        EmployeeRepresentativeForm form = new EmployeeRepresentativeForm();
        form.setId("some id");
        when(flowBean.getObject(RepresentativeForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals("representative/representative_employeerepresentative", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitEmployeeRepresentative_emptyModel_throwsSPException()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(null, binding, false);

        /// Assert
        fail();
    }

    @Test
    public void onSubmitEmployeeRepresentative_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitEmployeeRepresentative_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(false);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitEmployeeRepresentative_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitEmployeeRepresentative_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer(new Representative()));

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(), binding, false);

        /// Assert
        assertEquals("representative/representativeMatches", result.getViewName());
    }

    @Test
    public void onSubmitEmployeeRepresentative_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer());

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitEmployeeRepresentative_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitEmployeeRepresentative(new EmployeeRepresentativeForm(),
                                                                                         binding, false);

        /// Assert
        assertEquals("representative/representative_employeerepresentative", result.getViewName());
    }

    @Test
    public void formBackingLegalPractitioner_nullId_returnsEmptyLegalPractitionerForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.formBackingLegalPractitioner(null);

        /// Assert
        assertEquals("representative/representative_legalpractitioner", result.getViewName());
        assertEquals(new LegalPractitionerForm(), result.getModel().get("representativeLegalPractitionerForm"));
    }

    @Test
    public void formBackingLegalPractitioner_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(ProfessionalPractitionerForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingLegalPractitioner_validId_returnsLegalPractitionerFormWithModel()
    {
        /// Arrange
        LegalPractitionerForm form = new LegalPractitionerForm();
        form.setId("some id");
        when(flowBean.getObject(RepresentativeForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals("representative/representative_legalpractitioner", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitLegalPractitioner_emptyModel_throwsSPException()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(null, binding, false);

        /// Assert
        fail();
    }

    @Test
    public void onSubmitLegalPractitioner_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalPractitioner_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(false);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalPractitioner_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalPractitioner_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer(new Representative()));

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representativeMatches", result.getViewName());
    }

    @Test
    public void onSubmitLegalPractitioner_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer());

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitLegalPractitioner_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitLegalPractitioner(new LegalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_legalpractitioner", result.getViewName());
    }

    @Test
    public void formBackingProfessionalPractitioner_nullId_returnsEmptyProfessionalPractitionerForm()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.formBackingProfessionalPractitioner(null);

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
        assertEquals(new ProfessionalPractitionerForm(), result.getModel().get("representativeProfessionalPractitionerForm"));
    }

    @Test
    public void formBackingProfessionalPractitioner_invalidId_returnsNull()
    {
        /// Arrange
        when(flowBean.getObject(ProfessionalPractitionerForm.class, "some id")).thenReturn(null);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formBackingProfessionalPractitioner_validId_returnsProfessionalPractitionerFormWithModel()
    {
        /// Arrange
        ProfessionalPractitionerForm form = new ProfessionalPractitionerForm();
        form.setId("some id");
        when(flowBean.getObject(RepresentativeForm.class, "some id")).thenReturn(form);

        /// Act
        ModelAndView result = addRepresentativeController.getRepresentative("some id");

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
    }

    @Test(expected = SPException.class)
    public void onSubmitProfessionalPractitioner_emptyModel_throwsSPException()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(null, binding, false);

        /// Assert
        fail();
    }

    @Test
    public void onSubmitProfessionalPractitioner_ignoreMatchesEnabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, true);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitProfessionalPractitioner_matchServiceDisabled_doesNotCallMatchingService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(false);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, false);

        /// Assert
        verify(personService, times(0)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitProfessionalPractitioner_ignoreMatchesDisabledAndMatchServiceEnabled_callsMatchService()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, false);

        /// Assert
        verify(personService, times(1)).matchRepresentative(any(RepresentativeForm.class), anyInt());
        assertNotNull(result);
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitProfessionalPractitioner_matchesFound_returnsCorrectView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer(new Representative()));

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representativeMatches", result.getViewName());
    }

    @Test
    public void onSubmitProfessionalPractitioner_noMatchesFound_returnsCardListView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(false);
        when(configurationServiceDelegator.isServiceEnabled(AvailableServices.REPRESENTATIVE_MATCH.value())).thenReturn(true);
        when(configurationServiceDelegator.getValueFromGeneralComponent(
                ConfigurationServiceDelegatorInterface.REPRESENTATIVE_MATCH_MAXRESULTS)).thenReturn("3");
        when(personService.matchRepresentative(any(RepresentativeForm.class), anyInt())).thenAnswer(
                ListHelper.setupDummyListAnswer());

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_card_list", result.getViewName());
    }

    @Test
    public void onSubmitProfessionalPractitioner_failsValidationWithIgnoreMatchesDisabled_returnsFormView()
    {
        /// Arrange
        BindingResult binding = mock(BindingResult.class);
        when(binding.hasErrors()).thenReturn(true);

        /// Act
        ModelAndView result = addRepresentativeController.onSubmitProfessionalPractitioner(new ProfessionalPractitionerForm(), binding, false);

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
    }

    @Test
    public void chooseRepresentativeType_returnsCorrectView()
    {
        /// Arrange

        /// Act
        ModelAndView result = addRepresentativeController.chooseRepresentativeType();

        /// Assert
        assertEquals("representative/representative_choosetype", result.getViewName());
    }

}
