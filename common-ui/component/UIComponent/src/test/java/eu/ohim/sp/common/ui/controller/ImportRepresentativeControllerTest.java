/*******************************************************************************
 * * $Id:: ImportRepresentativeControllerTest.java 50771 2012-11-14 15:10:27Z ka#$
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

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;

/**
 * @author ionitdi
 */
public class ImportRepresentativeControllerTest
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
    ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @InjectMocks
    ImportRepresentativeController importRepresentativeController = new ImportRepresentativeController();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(configurationServiceDelegator.getValue(ConfigurationServiceDelegatorInterface.REPRESENTATIVE_ADD_MAXNUMBER, "eu.ohim.sp.core.person"))
        .thenReturn("5");
        
        ArrayList<String> types = new ArrayList<String>();
        types.add("university");
        types.add("legal_entity");
        types.add("natural_person");
        types.add("natural_person_special");
        types.add("university");
        types.add("legal_practitioner");
        types.add("association");
        types.add("professional_practitioner");
        
        
        
        when(configurationServiceDelegator.getRepresentativeTypes(null)).thenReturn(types);
        
        
        
    }

    @Test
    public void importForm_representativeDoesNotExist_returnsErrorView()
    {
        /// Arrange
        RepresentativeForm form = new RepresentativeForm();
        form.setType(RepresentativeKindForm.PROFESSIONAL_PRACTITIONER);
        List<String> values = new ArrayList<String>();
        
		when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
		when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
		
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsLegalEntity_returnsLegalEntityForm()
    {
        /// Arrange
        RepresentativeLegalEntityForm form = new RepresentativeLegalEntityForm();
        List<String> values = new ArrayList<String>();
        values.add(RepresentativeKindForm.LEGAL_ENTITY.toString());
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_legalentity", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsNaturalPerson_returnsNaturalPersonForm()
    {
        /// Arrange
        RepresentativeNaturalPersonForm form = new RepresentativeNaturalPersonForm();
        List<String> values = new ArrayList<String>();
        values.add(RepresentativeKindForm.NATURAL_PERSON.toString());
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_naturalperson", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsLegalPractitioner_returnsLegalPractitionerForm()
    {
        /// Arrange
        LegalPractitionerForm form = new LegalPractitionerForm();
        List<String> values = new ArrayList<String>();
        values.add(RepresentativeKindForm.LEGAL_PRACTITIONER.toString());
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_legalpractitioner", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsProfessionalPractitioner_returnsProfessionalPractitionerForm()
    {
        /// Arrange
        ProfessionalPractitionerForm form = new ProfessionalPractitionerForm();
        List<String> values = new ArrayList<String>();
        values.add("PROFESSIONAL_PRACTITIONER");
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsAssociation_returnsAssociationForm()
    {
        /// Arrange
        RepresentativeAssociationForm form = new RepresentativeAssociationForm();
        List<String> values = new ArrayList<String>();
        values.add(RepresentativeKindForm.ASSOCIATION.toString());
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_association", result.getViewName());
    }

    @Test
    public void importForm_returnedRepresentativeIsOther_returnsErrorView()
    {
        /// Arrange
        RepresentativeAssociationForm form = new RepresentativeAssociationForm();
        form.setType(RepresentativeKindForm.PROFESSIONAL_PRACTITIONER);
        List<String> values = new ArrayList<String>();
        
        when(personService.importRepresentative(eq("some id"), anyString())).thenReturn(form);
        when(configurationServiceDelegator.getRepresentativeTypes(anyString())).thenReturn(values);
        
        /// Act
        ModelAndView result = importRepresentativeController.importForm(request, "some id", binding);

        /// Assert
        assertEquals("representative/representative_professionalpractitioner", result.getViewName());
    }
}