/*
 * $Id:: PaymentOptionsController.java 08/11/2012 11:06:39 $
 * . * .
 * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * . RR R . in the Internal Market (trade marks and designs)
 * * RRR *
 * . RR RR . ALL RIGHTS RESERVED
 * * . _ . *
 */
package eu.ohim.sp.common.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.flow.section.PaymentFlowBean;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.payment.PayerKindForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeKindForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;

/**
 * Controller for the Ajax calls on the Payment Options section
 * 
 * @author monteca
 * 
 */
@Controller
public class PaymentOptionsController {

    @Autowired
    PaymentFlowBean flowBean;

    @RequestMapping(value = "getPaymentOptions", method = RequestMethod.GET)
    public ModelAndView loadPayerTypes(@ModelAttribute PaymentFlowBean fb, BindingResult result) {
        ModelAndView view = new ModelAndView("payment/payment_options");
        PaymentForm fbPaymentForm = flowBean.getPaymentForm();
        fbPaymentForm.setPayerType(fb.getPaymentForm().getPayerType());
        fbPaymentForm.setPaymentMethod(fb.getPaymentForm().getPaymentMethod());
        fbPaymentForm.setSelectedPayer(fb.getPaymentForm().getSelectedPayer());
        fbPaymentForm.setCheckPayerDetails(fb.getPaymentForm().isCheckPayerDetails());

        // If the type of payer is an applicant or representative, fill the payer details
        if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.APPLICANT.getCode())) {
            ApplicantForm applicant = flowBean.getObject(ApplicantForm.class, fb.getPaymentForm().getSelectedPayer());
            if (applicant != null) {
                fbPaymentForm.setAddress((AddressForm) applicant.getAddress().clone());
                fbPaymentForm.setEmail(applicant.getEmail());
                if (applicant.getType().equals(ApplicantKindForm.LEGAL_ENTITY)) {
                    fbPaymentForm.setCompany(((LegalEntityForm) applicant).getName());
                    fbPaymentForm.setName(null);
                    fbPaymentForm.setSurname(null);
                } else if (applicant.getType().equals(ApplicantKindForm.NATURAL_PERSON)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((NaturalPersonForm) applicant).getFirstName());
                    fbPaymentForm.setSurname(((NaturalPersonForm) applicant).getSurname());
                } else if (applicant.getType().equals(ApplicantKindForm.NATURAL_PERSON_SPECIAL)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((NaturalPersonSpecialForm) applicant).getName());
                    fbPaymentForm.setSurname(null);
                }
            } else {
                clearPaymentForm(fbPaymentForm, true);
            }
        } else if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.REPRESENTATIVE.getCode())) {
            RepresentativeForm representative = flowBean.getObject(RepresentativeForm.class, fb.getPaymentForm()
                    .getSelectedPayer());
            if (representative != null) {
                fbPaymentForm.setAddress((AddressForm) representative.getAddress().clone());
                fbPaymentForm.setEmail(representative.getEmail());
                if (representative.getType().equals(RepresentativeKindForm.ASSOCIATION)) {
                    fbPaymentForm.setCompany(((RepresentativeAssociationForm) representative).getAssociationName());
                    fbPaymentForm.setName(null);
                    fbPaymentForm.setSurname(null);
                } else if (representative.getType().equals(RepresentativeKindForm.EMPLOYEE_REPRESENTATIVE)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((EmployeeRepresentativeForm) representative).getFirstName());
                    fbPaymentForm.setSurname(((EmployeeRepresentativeForm) representative).getSurname());
                } else if (representative.getType().equals(RepresentativeKindForm.LEGAL_ENTITY)) {
                    fbPaymentForm.setCompany(((RepresentativeLegalEntityForm) representative).getNameOfLegalEntity());
                    fbPaymentForm.setName(null);
                    fbPaymentForm.setSurname(null);
                } else if (representative.getType().equals(RepresentativeKindForm.LEGAL_PRACTITIONER)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((LegalPractitionerForm) representative).getFirstName());
                    fbPaymentForm.setSurname(((LegalPractitionerForm) representative).getSurname());
                } else if (representative.getType().equals(RepresentativeKindForm.NATURAL_PERSON)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((RepresentativeNaturalPersonForm) representative).getFirstName());
                    fbPaymentForm.setSurname(((RepresentativeNaturalPersonForm) representative).getSurname());
                } else if (representative.getType().equals(RepresentativeKindForm.PROFESSIONAL_PRACTITIONER)) {
                    fbPaymentForm.setCompany(null);
                    fbPaymentForm.setName(((ProfessionalPractitionerForm) representative).getFirstName());
                    fbPaymentForm.setSurname(((ProfessionalPractitionerForm) representative).getSurname());
                }
            } else {
                clearPaymentForm(fbPaymentForm, true);
            }
        } else if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.OTHER.getCode())) {
            fbPaymentForm.setAddress(fb.getPaymentForm().getAddress());
            fbPaymentForm.setEmail(fb.getPaymentForm().getEmail());
            fbPaymentForm.setCompany(fb.getPaymentForm().getCompany());
            fbPaymentForm.setName(fb.getPaymentForm().getName());
            fbPaymentForm.setSurname(fb.getPaymentForm().getSurname());
        } else {
            clearPaymentForm(fbPaymentForm, false);
        }
        return view;
    }

    private void clearPaymentForm(PaymentForm paymentForm, Boolean clearAll) {
        paymentForm.setAddress(new AddressForm());
        paymentForm.setEmail(null);
        paymentForm.setCompany(null);
        paymentForm.setName(null);
        paymentForm.setSurname(null);
        if (clearAll) {
            paymentForm.setPayerType(null);
            paymentForm.setSelectedPayer(null);
        }
    }

}
