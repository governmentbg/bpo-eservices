/*******************************************************************************
 * * $Id:: EnumAdapter.java 53086 2012-12-14 09:01:44Z virgida                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.application.AppealKindForm;
import eu.ohim.sp.common.ui.form.application.SignatoryKindForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityKindForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.AssigneeKindForm;
import eu.ohim.sp.common.ui.form.person.HolderKindForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.core.domain.application.AppealKind;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.RepresentativeKind;

public class EnumAdapter {
    /**
     * Receives as a parameter the type of applicant used in the UI and returns
     * the core version of the type.
     *
     * @param form
     *            the UI Applicant kind
     * @return the corresponding NameKind object
     */
    public static PersonKind formApplicantKindToCoreApplicantKind(ApplicantKindForm form) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case LEGAL_ENTITY:
                return PersonKind.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return PersonKind.NATURAL_PERSON;
            case NATURAL_PERSON_SPECIAL:
                return PersonKind.NATURAL_PERSON_SPECIAL;
        }
        return PersonKind.OTHER;
    }



    /**
     * Receives as a parameter the type of applicant used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core NameKind object
     * @return the corresponding ApplicantKind used in UI
     */
    public static ApplicantKindForm coreApplicantKindToFormApplicantKind(PersonKind core) {
        if (core == null) {
            return null;
        }
        switch (core) {
            case LEGAL_ENTITY:
                return ApplicantKindForm.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return ApplicantKindForm.NATURAL_PERSON;
            case NATURAL_PERSON_SPECIAL:
                return ApplicantKindForm.NATURAL_PERSON_SPECIAL;
            default:
                return null;
        }
    }

    /**
     * Receives as a parameter the type of representative used in the UI and
     * returns the core version of the type.
     *
     * @param form
     *            the UI Representative kind
     * @param hasEconomicConnections
     *            contains whether the representative has economic connections
     * @return the corresponding RepresentativeKind object
     */
    public static RepresentativeKind formRepresentativeKindToCoreRepresentativeKind(RepresentativeKindForm form,
                                                                                    boolean hasEconomicConnections) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case EMPLOYEE_REPRESENTATIVE: {
                if (hasEconomicConnections) {
                    return RepresentativeKind.EMPLOYEE_WITH_ECONOMIC_CONNECTIONS;
                }
                return RepresentativeKind.EMPLOYEE;
            }
            case LEGAL_PRACTITIONER:
                return RepresentativeKind.LAWYER;
            case PROFESSIONAL_PRACTITIONER:
                return RepresentativeKind.PROFESSIONAL_REPRESENTATIVE;
            case ASSOCIATION:
                return RepresentativeKind.ASSOCIATION;
            case LAWYER_COMPANY:
                return RepresentativeKind.LAWYER_COMPANY;
            case LAWYER_ASSOCIATION:
                return RepresentativeKind.LAWYER_ASSOCIATION;
            case TEMPORARY_REPRESENTATIVE:
                return RepresentativeKind.TEMPORARY_REPRESENTATIVE;
            case INTELLECTUAL_PROPERTY_REPRESENTATIVE:
                return RepresentativeKind.INTELLECTUAL_PROPERTY_REPRESENTATIVE;
            case NATURAL_PERSON:
            case LEGAL_ENTITY:
            default:
                return RepresentativeKind.OTHER;
        }
    }

    /**
     * Receives as a parameter the type of representative used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core RepresentativeKind object
     * @return the corresponding RepresentativeKind used in UI
     */
    public static RepresentativeKindForm coreRepresentativeKindToFormRepresentativeKind(RepresentativeKind core, PersonKind kind) {
        if (core == null) {
            if(kind != null){
                switch (kind) {
                    case NATURAL_PERSON:
                        return RepresentativeKindForm.NATURAL_PERSON;
                    case LEGAL_ENTITY:
                        return RepresentativeKindForm.LEGAL_ENTITY;
                }
            }
        }else{
            switch (core) {
                case EMPLOYEE:
                case EMPLOYEE_WITH_ECONOMIC_CONNECTIONS:
                    return RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE;
                case LAWYER:
                    return RepresentativeKindForm.LEGAL_PRACTITIONER;
                case PROFESSIONAL_REPRESENTATIVE:
                    return RepresentativeKindForm.PROFESSIONAL_PRACTITIONER;
                case ASSOCIATION:
                    return RepresentativeKindForm.ASSOCIATION;
                case LAWYER_COMPANY:
                    return RepresentativeKindForm.LAWYER_COMPANY;
                case LAWYER_ASSOCIATION:
                    return RepresentativeKindForm.LAWYER_ASSOCIATION;
                case TEMPORARY_REPRESENTATIVE:
                    return RepresentativeKindForm.TEMPORARY_REPRESENTATIVE;
                case INTELLECTUAL_PROPERTY_REPRESENTATIVE:
                    return RepresentativeKindForm.INTELLECTUAL_PROPERTY_REPRESENTATIVE;
                case OTHER:
                    switch (kind) {
                        case NATURAL_PERSON:
                            return RepresentativeKindForm.NATURAL_PERSON;
                        case LEGAL_ENTITY:
                            return RepresentativeKindForm.LEGAL_ENTITY;
                    }
            }
        }
        return null;
    }


    /**
     * Receives as a parameter the type of representative used in the UI and
     * returns the core version of the type.
     *
     * @param form
     *            the UI Representative kind
     * @return the corresponding RepresentativeKind object
     */
    public static PersonKind formHolderKindToCoreHolderKind(HolderKindForm form) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case LEGAL_ENTITY:
                return PersonKind.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return PersonKind.NATURAL_PERSON;
        }
        return PersonKind.OTHER;
    }

    /**
     * Receives as a parameter the type of representative used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core RepresentativeKind object
     * @return the corresponding RepresentativeKind used in UI
     */
    public static HolderKindForm coreHolderKindToFormHolderKind(PersonKind core) {
        if (core == null) {
            return null;
        }
        switch (core) {
            case LEGAL_ENTITY:
                return HolderKindForm.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return HolderKindForm.NATURAL_PERSON;
            default:
                return null;
        }
    }

    /**
     * Receives as a parameter the type of representative used in the UI and
     * returns the core version of the type.
     *
     * @param form
     *            the UI Assignee kind
     *
     * @return the corresponding AssigneeKind object
     */
    public static PersonKind formAssigneeKindToCoreAssigneeKind(AssigneeKindForm form) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case LEGAL_ENTITY:
                return PersonKind.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return PersonKind.NATURAL_PERSON;
        }
        return PersonKind.OTHER;
    }

    /**
     * Receives as a parameter the type of representative used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core RepresentativeKind object
     * @return the corresponding RepresentativeKind used in UI
     */
    public static AssigneeKindForm coreAssigneeKindToFormAssigneeKind(PersonKind core) {
        if (core == null) {
            return null;
        }
        switch (core) {
            case LEGAL_ENTITY:
                return AssigneeKindForm.LEGAL_ENTITY;
            case NATURAL_PERSON:
                return AssigneeKindForm.NATURAL_PERSON;
            case NATURAL_PERSON_SPECIAL:
                return AssigneeKindForm.NATURAL_PERSON_SPECIAL;
            default:
                return null;
        }
    }


    /**
     * Receives as a parameter the type of signatory used in the UI and returns
     * the core version of the type.
     *
     * @param form
     *            the UI SignatoryKindForm
     * @return the corresponding core Role object
     */
    public static PersonRoleKind formSignatoryKindToCoreSignatoryKind(SignatoryKindForm form) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case APPLICANT:
                return PersonRoleKind.APPLICANT;
            /*
                case EMPLOYEE_REPRESENTATIVE:
                return PersonRoleKind.EMPLOYEE;
            */
            case REPRESENTATIVE:
                return PersonRoleKind.REPRESENTATIVE;
            case OTHER:
                return PersonRoleKind.OTHER;
            case ASSIGNEE:
                return PersonRoleKind.ASSIGNEE;
            case BAILIFF:
                return PersonRoleKind.BAILIFF;
            default:
                return null;
        }
    }

    /**
     * Receives as a parameter the type of signatory used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core Role object
     * @return the corresponding SignatoryKindForm used in UI
     */
    public static SignatoryKindForm coreSignatoryKindToFormSignatoryKind(PersonRoleKind core) {
        if (core == null) {
            return null;
        }
        switch (core) {
            case APPLICANT:
                return SignatoryKindForm.APPLICANT;
            /*
            case EMPLOYEE:
                return SignatoryKindForm.EMPLOYEE_REPRESENTATIVE;
            */
            case REPRESENTATIVE:
                return SignatoryKindForm.REPRESENTATIVE;
            case OTHER:
                return SignatoryKindForm.OTHER;
            case BAILIFF:
                return SignatoryKindForm.BAILIFF;
            case ASSIGNEE:
                return SignatoryKindForm.ASSIGNEE;
            default:
                return null;
        }
    }

    /**
     * Receives as a parameter the type of seniority used in the UI and returns
     * the core version of the type.
     *
     * @param form
     *            the UI SeniorityKindForm
     * @return the corresponding core SeniorityKind object
     */
    public static SeniorityKind formSeniorityKindToCoreSeniorityKind(SeniorityKindForm form) {
        if (form == null) {
            return null;
        }
        switch (form) {
            case INTERNATIONAL_TRADE_MARK:
                return SeniorityKind.INTERNATIONAL_TRADE_MARK;
            case NATIONAL_OR_REGIONAL_TRADE_MARK:
                return SeniorityKind.NATIONAL_OR_REGIONAL_TRADE_MARK;
            default:
                return null;
        }
    }

    /**
     * Receives as a parameter the type of seniority used in the core and
     * returns the UI version of the type.
     *
     * @param core
     *            the core SeniorityKind object
     * @return the corresponding SeniorityKindForm used in UI
     */
    public static SeniorityKindForm coreSeniorityKindToFormSeniorityKind(SeniorityKind core) {
        if (core == null) {
            return null;
        }
        switch (core) {
            case INTERNATIONAL_TRADE_MARK:
                return SeniorityKindForm.INTERNATIONAL_TRADE_MARK;
            case NATIONAL_OR_REGIONAL_TRADE_MARK:
                return SeniorityKindForm.NATIONAL_OR_REGIONAL_TRADE_MARK;
            default:
                return null;
        }
    }

    public static AppealKindForm coreAppealKindToFormAppealKind(AppealKind core){
        if(core == null){
            return null;
        }
        switch(core) {
            case APPEAL_AGAINST_OPPOSITION_DECISION: return AppealKindForm.APPEAL_AGAINST_OPPOSITION_DECISION;
            case APPEAL_AGAINST_REFUSAL: return AppealKindForm.APPEAL_AGAINST_REFUSAL;
            case APPEAL_AGAINST_TERMINATION: return AppealKindForm.APPEAL_AGAINST_TERMINATION;
            default: return null;
        }
    }

    public static AppealKind formAppealKindToCoreAppealKind(AppealKindForm form){
        if(form == null){
            return null;
        }
        switch(form) {
            case APPEAL_AGAINST_OPPOSITION_DECISION: return AppealKind.APPEAL_AGAINST_OPPOSITION_DECISION;
            case APPEAL_AGAINST_REFUSAL: return AppealKind.APPEAL_AGAINST_REFUSAL;
            case APPEAL_AGAINST_TERMINATION: return AppealKind.APPEAL_AGAINST_TERMINATION;
            default: return null;
        }
    }
}
