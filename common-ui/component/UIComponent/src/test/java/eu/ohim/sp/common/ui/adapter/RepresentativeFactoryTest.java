/*******************************************************************************
 * * $Id:: RepresentativeFactoryTest.java 113496 2013-04-22 15:03:04Z karalch    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class RepresentativeFactoryTest
{
	@Mock
    RepresentativeLegalEntityFactory representativeLegalEntityFactory;

    @Mock
    RepresentativeNaturalPersonFactory representativeNaturalPersonFactory;

    @Mock
    ProfessionalPractitionerFactory professionalPractitionerFactory;

    @Mock
    LegalPractitionerFactory legalPractitionerFactory;

    @Mock
    EmployeeRepresentativeFactory employeeRepresentativeFactory;

    @Mock
    RepresentativeAssociationFactory representativeAssociationFactory;


    @InjectMocks
    RepresentativeFactory representativeFactory = new RepresentativeFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTo_nullFormObject_returnsNull()
    {
        /// Act
        Representative result = representativeFactory.convertTo(null);

        /// Assert
        assertNull(result);
    }

    @Test
    public void convertTo_representativeLegalEntityForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        RepresentativeLegalEntityForm form = new RepresentativeLegalEntityForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(representativeLegalEntityFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertTo_representativeNaturalPersonForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        RepresentativeNaturalPersonForm form = new RepresentativeNaturalPersonForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(representativeNaturalPersonFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertTo_professionalPractitionerForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        ProfessionalPractitionerForm form = new ProfessionalPractitionerForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(professionalPractitionerFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertTo_legalPractitionerForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        LegalPractitionerForm form = new LegalPractitionerForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(legalPractitionerFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertTo_employeeRepresentativeForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        EmployeeRepresentativeForm form = new EmployeeRepresentativeForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(employeeRepresentativeFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertTo_representativeAssociationForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        RepresentativeAssociationForm form = new RepresentativeAssociationForm();

        Representative expected = new Representative();
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("rep id");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        when(representativeAssociationFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Representative result = representativeFactory.convertTo(form);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(null);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void convertFrom_representativeLegalEntity_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.OTHER);
        core.setKind(PersonKind.LEGAL_ENTITY);

        RepresentativeLegalEntityForm expected = new RepresentativeLegalEntityForm();
        expected.setId("some id");

        when(representativeLegalEntityFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_representativeNaturalPerson_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.OTHER);
        core.setKind(PersonKind.NATURAL_PERSON);

        RepresentativeNaturalPersonForm expected = new RepresentativeNaturalPersonForm();
        expected.setId("some id");

        when(representativeNaturalPersonFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_representativeAssociation_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.ASSOCIATION);
        core.setKind(PersonKind.OTHER);

        RepresentativeAssociationForm expected = new RepresentativeAssociationForm();
        expected.setId("some id");

        when(representativeAssociationFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_professionalPractitioner_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.PROFESSIONAL_REPRESENTATIVE);
        core.setKind(PersonKind.OTHER);

        ProfessionalPractitionerForm expected = new ProfessionalPractitionerForm();
        expected.setId("some id");

        when(professionalPractitionerFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_legalPractitioner_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.LAWYER);
        core.setKind(PersonKind.OTHER);

        LegalPractitionerForm expected = new LegalPractitionerForm();
        expected.setId("some id");

        when(legalPractitionerFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_employeeRepresentative_returnsCorrectKind()
    {
        /// Arramge
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.EMPLOYEE);
        core.setKind(PersonKind.OTHER);

        EmployeeRepresentativeForm expected = new EmployeeRepresentativeForm();
        expected.setId("some id");

        when(employeeRepresentativeFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(expected, result);
    }

    @Test
    public void convertFrom_otherKind_returnsNull()
    {
        /// Arrange
        Representative core = new Representative();
        core.setRepresentativeKind(RepresentativeKind.OTHER);
        core.setKind(PersonKind.OTHER);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void convertFrom_nullKind_returnsNull()
    {
        /// Arrange
        Representative core = new Representative();
        RepresentativeKind kindNull = null;
        core.setRepresentativeKind(kindNull);

        /// Act
        RepresentativeForm result = representativeFactory.convertFrom(core);

        /// Assert
        assertEquals(null, result);
    }
}
