/*******************************************************************************
 * * $Id:: ImportApplicantControllerTest.java 50771 2012-11-14 15:10:27Z karalch $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.FilterImportable;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

/**
 * @author ionitdi
 */
public class ImportApplicantControllerTest
{
    @Mock
    FlowBean flowBean;

    @Mock
    PersonServiceInterface personService;

    @Mock
    HttpServletRequest request;

    @Mock
    BindingResult binding;

    @Mock
    FilterImportable filterImportable;

    @Mock
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    ImportApplicantController importApplicantController = new ImportApplicantController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.APPLICANT_ADD_MAXNUMBER, "eu.ohim.sp.core.person"))
        .thenReturn("5");
        ArrayList<String> types = new ArrayList<String>();
        types.add("university");
        types.add("legal_entity");
        types.add("natural_person");
        types.add("natural_person_special");
        types.add("university");
        types.add("legal_practitioner");
        
        when(configurationServiceDelegator.getApplicantTypes(null)).thenReturn(types);
    }

    @Test
    public void importForm_applicantDoesNotExist_returnsErrorView()
    {
        /// Arrange
    	ApplicantForm form = new ApplicantForm();
        form.setType(ApplicantKindForm.UNIVERSITY);
        List<String> values = new ArrayList<String>();
        
        when(personService.importApplicant(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getApplicantTypes(anyString())).thenReturn(values);
        
        

        /// Act
        ModelAndView result = importApplicantController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("errors/importError", result.getViewName());
    }

    @Test
    public void importForm_returnedApplicantIsLegalEntity_returnsLegalEntityForm()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();
        List<String> values = new ArrayList<String>();
        values.add(ApplicantKindForm.LEGAL_ENTITY.toString());
        
        when(personService.importApplicant(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getApplicantTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importApplicantController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("applicant/applicant_legalentity", result.getViewName());
    }

    @Test
    public void importForm_returnedApplicantIsNaturalPerson_returnsNaturalPersonForm()
    {
        /// Arrange
        NaturalPersonForm form = new NaturalPersonForm();
        List<String> values = new ArrayList<String>();
        values.add(ApplicantKindForm.NATURAL_PERSON.toString());
        
        when(personService.importApplicant(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getApplicantTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importApplicantController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("applicant/applicant_naturalperson", result.getViewName());
    }

    @Test
    public void importForm_returnedApplicantIsNaturalPersonSpecial_returnsNaturalPersonSpecialForm()
    {
        /// Arrange
        NaturalPersonSpecialForm form = new NaturalPersonSpecialForm();
        List<String> values = new ArrayList<String>();
        values.add("NATURAL_PERSON_SPECIAL");
        
        when(personService.importApplicant(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getApplicantTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importApplicantController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("applicant/applicant_naturalpersonspecial", result.getViewName());
    }

    @Test
    public void importForm_returnedApplicantIsOther_returnsErrorView()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();
        List<String> values = new ArrayList<String>();
        values.add(ApplicantKindForm.UNIVERSITY.toString());
        
        when(personService.importApplicant(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getApplicantTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importApplicantController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("errors/importError", result.getViewName());
    }
}
