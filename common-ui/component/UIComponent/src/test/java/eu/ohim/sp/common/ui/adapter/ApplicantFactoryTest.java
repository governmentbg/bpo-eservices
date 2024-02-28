/*******************************************************************************
 * * $Id:: ApplicantFactoryTest.java 113496 2013-04-22 15:03:04Z karalch         $
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;

/**
 * @author ionitdi
 */
public class ApplicantFactoryTest
{
	@Mock
    LegalEntityFactory legalEntityFactory;

    @Mock
    NaturalPersonFactory naturalPersonFactory;

    @Mock
    NaturalPersonSpecialFactory naturalPersonSpecialFactory;

    @InjectMocks
    ApplicantFactory applicantFactory = new ApplicantFactory();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTo_nullFormObject_returnsNull()
    {
        /// Act
        Applicant result = applicantFactory.convertTo(null);

        /// Assert
        assertNull(result);
    }

    @Test
    public void convertTo_legalEntityForm_returnsLegalEntityCoreObject()
    {
        /// Arrange
        LegalEntityForm form = new LegalEntityForm();

        Applicant expected = new Applicant();
        expected.setPersonNumber("app id");

        when(legalEntityFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Applicant result = applicantFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getPersonNumber(), result.getPersonNumber());
    }

    @Test
    public void convertTo_naturalPersonForm_returnsNaturalPersonCoreObject()
    {
        /// Arrange
        NaturalPersonForm form = new NaturalPersonForm();

        Applicant expected = new Applicant();
        expected.setPersonNumber("app id");

        when(applicantFactory.convertTo(form)).thenReturn(expected);
        
        /// Act
        Applicant result = applicantFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getPersonNumber(), result.getPersonNumber());
    }

    @Test
    public void convertTo_naturalPersonSpecialForm_returnsNaturalPersonCoreObject()
    {
        /// Arrange
        NaturalPersonSpecialForm form = new NaturalPersonSpecialForm();

        Applicant expected = new Applicant();
        expected.setPersonNumber("app id");

        when(naturalPersonSpecialFactory.convertTo(form)).thenReturn(expected);

        /// Act
        Applicant result = applicantFactory.convertTo(form);

        /// Assert
        assertEquals(expected.getPersonNumber(), result.getPersonNumber());
    }

    @Test
    public void convertFrom_nullCore_returnsNullForm()
    {
        /// Act
        ApplicantForm result = applicantFactory.convertFrom(null);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void convertFrom_legalEntity_returnsLegalEntityKind()
    {
        /// Arrange
        Applicant core = new Applicant();
        core.setKind(PersonKind.LEGAL_ENTITY);

        LegalEntityForm expected = new LegalEntityForm();
        expected.setId("some id");

        when(legalEntityFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        ApplicantForm result = applicantFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    public void convertFrom_naturalPerson_returnsNaturalPersonKind()
    {
        /// Arrange
        Applicant core = new Applicant();
        core.setKind(PersonKind.NATURAL_PERSON);

        NaturalPersonForm expected = new NaturalPersonForm();
        expected.setId("some id");

        when(naturalPersonFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        ApplicantForm result = applicantFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    public void convertFrom_naturalPersonSpecial_returnsNaturalPersonSpecialKind()
    {
        /// Arrange
        Applicant core = new Applicant();
        core.setKind(PersonKind.NATURAL_PERSON_SPECIAL);

        NaturalPersonSpecialForm expected = new NaturalPersonSpecialForm();

        when(naturalPersonSpecialFactory.convertFrom(core)).thenReturn(expected);

        /// Act
        ApplicantForm result = applicantFactory.convertFrom(core);

        /// Assert
        assertEquals(expected.getType(), result.getType());
    }

    @Test
    public void convertFrom_otherKind_returnsNull()
    {
        /// Arrange
        Applicant core = new Applicant();
        core.setKind(PersonKind.OTHER);

        /// Act
        ApplicantForm result = applicantFactory.convertFrom(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void convertFrom_nullKind_returnsNull()
    {
        /// Arrange
        Applicant core = new Applicant();
        core.setKind(null);

        /// Act
        ApplicantForm result = applicantFactory.convertFrom(core);

        /// Assert
        assertEquals(null, result);
    }
}
