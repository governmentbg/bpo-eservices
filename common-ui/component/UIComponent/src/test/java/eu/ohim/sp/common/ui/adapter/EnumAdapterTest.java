/*******************************************************************************
 * * $Id:: EnumAdapterTest.java 113496 2013-04-22 15:03:04Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.common.ui.form.application.SignatoryKindForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityKindForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

/**
 * @author ionitdi
 */
public class EnumAdapterTest
{
	@Test
    public void formApplicantKindToCoreApplicantKind_case0()
    {
        /// Arrange
        ApplicantKindForm form = null;

        /// Act
        PersonKind result = EnumAdapter.formApplicantKindToCoreApplicantKind(form);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formApplicantKindToCoreApplicantKind_case1()
    {
        /// Arrange
        ApplicantKindForm form = ApplicantKindForm.LEGAL_ENTITY;

        /// Act
        PersonKind result = EnumAdapter.formApplicantKindToCoreApplicantKind(form);

        /// Assert
        assertEquals(PersonKind.LEGAL_ENTITY, result);
    }

    @Test
    public void formApplicantKindToCoreApplicantKind_case2()
    {
        /// Arrange
        ApplicantKindForm form = ApplicantKindForm.NATURAL_PERSON;

        /// Act
        PersonKind result = EnumAdapter.formApplicantKindToCoreApplicantKind(form);

        /// Assert
        assertEquals(PersonKind.NATURAL_PERSON, result);
    }

    @Test
    public void formApplicantKindToCoreApplicantKind_case3()
    {
        /// Arrange
        ApplicantKindForm form = ApplicantKindForm.NATURAL_PERSON_SPECIAL;

        /// Act
        PersonKind result = EnumAdapter.formApplicantKindToCoreApplicantKind(form);

        /// Assert
        assertEquals(PersonKind.NATURAL_PERSON_SPECIAL, result);
    }

    @Test
    public void coreApplicantKindToFormApplicantKind_case0()
    {
        /// Arrange
    	PersonKind core = null;

        /// Act
        ApplicantKindForm result = EnumAdapter.coreApplicantKindToFormApplicantKind(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void coreApplicantKindToFormApplicantKind_case1()
    {
        /// Arrange
    	PersonKind core = PersonKind.LEGAL_ENTITY;

        /// Act
        ApplicantKindForm result = EnumAdapter.coreApplicantKindToFormApplicantKind(core);

        /// Assert
        assertEquals(ApplicantKindForm.LEGAL_ENTITY, result);
    }

    @Test
    public void coreApplicantKindToFormApplicantKind_case2()
    {
        /// Arrange
    	PersonKind core = PersonKind.NATURAL_PERSON;

        /// Act
        ApplicantKindForm result = EnumAdapter.coreApplicantKindToFormApplicantKind(core);

        /// Assert
        assertEquals(ApplicantKindForm.NATURAL_PERSON, result);
    }

    @Test
    public void coreApplicantKindToFormApplicantKind_case3()
    {
        /// Arrange
    	PersonKind core = PersonKind.NATURAL_PERSON_SPECIAL;

        /// Act
        ApplicantKindForm result = EnumAdapter.coreApplicantKindToFormApplicantKind(core);

        /// Assert
        assertEquals(ApplicantKindForm.NATURAL_PERSON_SPECIAL, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case0()
    {
        /// Arrange
        RepresentativeKindForm form = null;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case1()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.ASSOCIATION;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.ASSOCIATION, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case2()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.LEGAL_PRACTITIONER;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.LAWYER, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case3()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.PROFESSIONAL_PRACTITIONER;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.PROFESSIONAL_REPRESENTATIVE, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case4()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.NATURAL_PERSON;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.OTHER, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case5()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.LEGAL_ENTITY;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.OTHER, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case6()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, false);

        /// Assert
        assertEquals(RepresentativeKind.EMPLOYEE, result);
    }

    @Test
    public void formRepresentativeKindToCoreRepresentativeKind_case7()
    {
        /// Arrange
        RepresentativeKindForm form = RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE;

        /// Act
        RepresentativeKind result = EnumAdapter.formRepresentativeKindToCoreRepresentativeKind(form, true);

        /// Assert
        assertEquals(RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case0()
    {
        /// Arrange
        RepresentativeKind core = null;
        PersonKind kind = null;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case1()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.PROFESSIONAL_REPRESENTATIVE;
        PersonKind kind = null;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.PROFESSIONAL_PRACTITIONER, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case2()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.EMPLOYEE;
        PersonKind kind = null;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case3()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS;
        PersonKind kind = null;
        
        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case4()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.LAWYER;
        PersonKind kind = null;
        
        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.LEGAL_PRACTITIONER, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case5()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.OTHER;
        PersonKind kind = PersonKind.LEGAL_ENTITY;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.LEGAL_ENTITY, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case6()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.OTHER;
        PersonKind kind = PersonKind.NATURAL_PERSON;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(RepresentativeKindForm.NATURAL_PERSON, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case7()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.ASSOCIATION;
        PersonKind kind = null;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, null);

        /// Assert
        assertEquals(RepresentativeKindForm.ASSOCIATION, result);
    }

    @Test
    public void coreRepresentativeKindToFormRepresentativeKind_case8()
    {
        /// Arrange
        RepresentativeKind core = RepresentativeKind.OTHER;
        PersonKind kind = PersonKind.OTHER;

        /// Act
        RepresentativeKindForm result = EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(core, kind);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formSignatoryKindToCoreSignatoryKind_case0()
    {
        /// Arrange
        SignatoryKindForm form = null;

        /// Act
        PersonRoleKind result = EnumAdapter.formSignatoryKindToCoreSignatoryKind(form);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formSignatoryKindToCoreSignatoryKind_case1()
    {
        /// Arrange
        SignatoryKindForm form = SignatoryKindForm.APPLICANT;

        /// Act
        PersonRoleKind result = EnumAdapter.formSignatoryKindToCoreSignatoryKind(form);

        /// Assert
        assertEquals(PersonRoleKind.APPLICANT, result);
    }
    /*
    @Test
    public void formSignatoryKindToCoreSignatoryKind_case2()
    {
        /// Arrange
        SignatoryKindForm form = SignatoryKindForm.EMPLOYEE_REPRESENTATIVE;

        /// Act
        PersonRoleKind result = EnumAdapter.formSignatoryKindToCoreSignatoryKind(form);

        /// Assert
        assertEquals(PersonRoleKind.EMPLOYEE, result);
    }
    */
    @Test
    public void formSignatoryKindToCoreSignatoryKind_case3()
    {
        /// Arrange
        SignatoryKindForm form = SignatoryKindForm.REPRESENTATIVE;

        /// Act
        PersonRoleKind result = EnumAdapter.formSignatoryKindToCoreSignatoryKind(form);

        /// Assert
        assertEquals(PersonRoleKind.REPRESENTATIVE, result);
    }

    @Test
    public void formSignatoryKindToCoreSignatoryKind_case4()
    {
        /// Arrange
        SignatoryKindForm form = SignatoryKindForm.OTHER;

        /// Act
        PersonRoleKind result = EnumAdapter.formSignatoryKindToCoreSignatoryKind(form);

        /// Assert
        assertEquals(PersonRoleKind.OTHER, result);
    }


    @Test
    public void coreSignatoryKindToFormSignatoryKind_case0()
    {
        /// Arrange
    	PersonRoleKind core = null;

        /// Act
        SignatoryKindForm result = EnumAdapter.coreSignatoryKindToFormSignatoryKind(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void coreSignatoryKindToFormSignatoryKind_case1()
    {
        /// Arrange
    	PersonRoleKind core = PersonRoleKind.APPLICANT;

        /// Act
        SignatoryKindForm result = EnumAdapter.coreSignatoryKindToFormSignatoryKind(core);

        /// Assert
        assertEquals(SignatoryKindForm.APPLICANT, result);
    }
    /*
    @Test
    public void coreSignatoryKindToFormSignatoryKind_case2()
    {
        /// Arrange
    	PersonRoleKind core = PersonRoleKind.EMPLOYEE;

        /// Act
        SignatoryKindForm result = EnumAdapter.coreSignatoryKindToFormSignatoryKind(core);

        /// Assert
        assertEquals(SignatoryKindForm.EMPLOYEE_REPRESENTATIVE, result);
    }
    */
    @Test
    public void coreSignatoryKindToFormSignatoryKind_case3()
    {
        /// Arrange
    	PersonRoleKind core = PersonRoleKind.REPRESENTATIVE;

        /// Act
        SignatoryKindForm result = EnumAdapter.coreSignatoryKindToFormSignatoryKind(core);

        /// Assert
        assertEquals(SignatoryKindForm.REPRESENTATIVE, result);
    }

    @Test
    public void coreSignatoryKindToFormSignatoryKind_case4()
    {
        /// Arrange
    	PersonRoleKind core = PersonRoleKind.OTHER;

        /// Act
        SignatoryKindForm result = EnumAdapter.coreSignatoryKindToFormSignatoryKind(core);

        /// Assert
        assertEquals(SignatoryKindForm.OTHER, result);
    }

    @Test
    public void formSeniorityKindToCoreSeniorityKind_case0()
    {
        /// Arrange
        SeniorityKindForm form = null;

        /// Act
        SeniorityKind result = EnumAdapter.formSeniorityKindToCoreSeniorityKind(form);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void formSeniorityKindToCoreSeniorityKind_case1()
    {
        /// Arrange
        SeniorityKindForm form = SeniorityKindForm.INTERNATIONAL_TRADE_MARK;

        /// Act
        SeniorityKind result = EnumAdapter.formSeniorityKindToCoreSeniorityKind(form);

        /// Assert
        assertEquals(SeniorityKind.INTERNATIONAL_TRADE_MARK, result);
    }

    @Test
    public void formSeniorityKindToCoreSeniorityKind_case2()
    {
        /// Arrange
        SeniorityKindForm form = SeniorityKindForm.NATIONAL_OR_REGIONAL_TRADE_MARK;

        /// Act
        SeniorityKind result = EnumAdapter.formSeniorityKindToCoreSeniorityKind(form);

        /// Assert
        assertEquals(SeniorityKind.NATIONAL_OR_REGIONAL_TRADE_MARK, result);
    }

    @Test
    public void coreSeniorityKindToFormSeniorityKind_case0()
    {
        /// Arrange
        SeniorityKind core = null;

        /// Act
        SeniorityKindForm result = EnumAdapter.coreSeniorityKindToFormSeniorityKind(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void coreSeniorityKindToFormSeniorityKind_case1()
    {
        /// Arrange
        SeniorityKind core = SeniorityKind.CTM;

        /// Act
        SeniorityKindForm result = EnumAdapter.coreSeniorityKindToFormSeniorityKind(core);

        /// Assert
        assertEquals(null, result);
    }

    @Test
    public void coreSeniorityKindToFormSeniorityKind_case2()
    {
        /// Arrange
        SeniorityKind core = SeniorityKind.INTERNATIONAL_TRADE_MARK;

        /// Act
        SeniorityKindForm result = EnumAdapter.coreSeniorityKindToFormSeniorityKind(core);

        /// Assert
        assertEquals(SeniorityKindForm.INTERNATIONAL_TRADE_MARK, result);
    }

    @Test
    public void coreSeniorityKindToFormSeniorityKind_case3()
    {
        /// Arrange
        SeniorityKind core = SeniorityKind.NATIONAL_OR_REGIONAL_TRADE_MARK;

        /// Act
        SeniorityKindForm result = EnumAdapter.coreSeniorityKindToFormSeniorityKind(core);

        /// Assert
        assertEquals(SeniorityKindForm.NATIONAL_OR_REGIONAL_TRADE_MARK, result);
    }
}
