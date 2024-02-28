package eu.ohim.sp.dsefiling.ui.controller.payment;

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
import eu.ohim.sp.common.ui.form.person.EmployeeRepresentativeForm;
import eu.ohim.sp.common.ui.form.person.LegalEntityForm;
import eu.ohim.sp.common.ui.form.person.LegalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonSpecialForm;
import eu.ohim.sp.common.ui.form.person.ProfessionalPractitionerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeAssociationForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeLegalEntityForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeNaturalPersonForm;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;

/**
 * Controller for the Ajax calls on the Payment Options section
 * 
 * @author monteca
 * 
 */
@Controller
public class DSPaymentOptionsController {

    @Autowired
    private PaymentFlowBean flowBean;

    /**
     * 
     * @param fb
     * @param result
     * @return
     */
    @RequestMapping(value = "getDSPaymentOptions", method = RequestMethod.GET)
    public ModelAndView loadPayerTypes(@ModelAttribute DSFlowBeanImpl fb, BindingResult result) {
        PaymentForm fbPaymentForm = flowBean.getPaymentForm();
        fbPaymentForm.setPayerType(fb.getPaymentForm().getPayerType());
        fbPaymentForm.setPaymentMethod(fb.getPaymentForm().getPaymentMethod());
        fbPaymentForm.setSelectedPayer(fb.getPaymentForm().getSelectedPayer());
        fbPaymentForm.setCheckPayerDetails(fb.getPaymentForm().isCheckPayerDetails());

        // If the type of payer is an applicant or representative, fill the payer details
        // TODO Could the payer be a designer??
        if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.APPLICANT.getCode())) {
        	setApplicantAsPayer(fbPaymentForm, fb);
        } else if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.REPRESENTATIVE.getCode())) {
        	setRepresentativeAsPayer(fbPaymentForm, fb);
        } else if (fb.getPaymentForm().getPayerType().equals(PayerKindForm.OTHER.getCode())) {
        	setOtherAsPayer(fbPaymentForm, fb);
        } else {
            clearPaymentForm(fbPaymentForm, false);
        }
        
        return new ModelAndView("payment/payment_section");
    }

    /**
     * 
     * @param fbPaymentForm
     * @param fb
     */
	private void setRepresentativeAsPayer(PaymentForm fbPaymentForm, DSFlowBeanImpl fb) {
    	RepresentativeForm representative = flowBean.getObject(RepresentativeForm.class, fb.getPaymentForm().getSelectedPayer());
        if (representative != null) {
            fbPaymentForm.setAddress((AddressForm) representative.getAddress().clone());
            fbPaymentForm.setEmail(representative.getEmail());
            String company = null;
            String name = null;
            String surname = null;
            switch (representative.getType()) {
            	case ASSOCIATION:
            		company = ((RepresentativeAssociationForm) representative).getAssociationName();
            		break;
            	case EMPLOYEE_REPRESENTATIVE:
            		name = ((EmployeeRepresentativeForm) representative).getFirstName();
            		surname = ((EmployeeRepresentativeForm) representative).getSurname();
            		break;
            	case LEGAL_ENTITY:
            		company = ((RepresentativeLegalEntityForm) representative).getNameOfLegalEntity();
            		break;
            	case LEGAL_PRACTITIONER:
            		name = ((LegalPractitionerForm) representative).getFirstName();
            		surname = ((LegalPractitionerForm) representative).getSurname();
            		break;
            	case NATURAL_PERSON: 
            		name = ((RepresentativeNaturalPersonForm) representative).getFirstName();
            		surname = ((RepresentativeNaturalPersonForm) representative).getSurname();
            		break;
            	case PROFESSIONAL_PRACTITIONER:
            		name = ((ProfessionalPractitionerForm) representative).getFirstName();
            		surname = ((ProfessionalPractitionerForm) representative).getSurname();
            		break;
            	default:
            		break;
            }
            fbPaymentForm.setCompany(company);
    		fbPaymentForm.setName(name);
    		fbPaymentForm.setSurname(surname);
        } else {
            clearPaymentForm(fbPaymentForm, true);
        }
	}

	/**
	 * 
	 * @param fbPaymentForm
	 * @param fb
	 */
	private void setApplicantAsPayer(PaymentForm fbPaymentForm, DSFlowBeanImpl fb) {
	    ApplicantForm applicant = flowBean.getObject(ApplicantForm.class, fb.getPaymentForm().getSelectedPayer());
	    if (applicant != null) {
	        fbPaymentForm.setAddress((AddressForm) applicant.getAddress().clone());
	        fbPaymentForm.setEmail(applicant.getEmail());
	        String company = null;
            String name = null;
            String surname = null;
	        switch (applicant.getType()) {
	        	case LEGAL_ENTITY:
	        		company = ((LegalEntityForm) applicant).getName();
	        		break;
	        	case NATURAL_PERSON:
	        		name = ((NaturalPersonForm) applicant).getFirstName();
	        		surname = ((NaturalPersonForm) applicant).getSurname();
	        		break;
	        	case NATURAL_PERSON_SPECIAL:
	        		name = ((NaturalPersonSpecialForm) applicant).getName();
	        		break;
	        	default:
	        		break;
	        }
	        fbPaymentForm.setCompany(company);
    		fbPaymentForm.setName(name);
    		fbPaymentForm.setSurname(surname);
	    } else {
	        clearPaymentForm(fbPaymentForm, true);
	    }
    }
    
	/**
     * 
     * @param fbPaymentForm
     * @param fb
     */
    private void setOtherAsPayer(PaymentForm fbPaymentForm, DSFlowBeanImpl fb) {
    	fbPaymentForm.setAddress(fb.getPaymentForm().getAddress());
        fbPaymentForm.setEmail(fb.getPaymentForm().getEmail());
        fbPaymentForm.setCompany(fb.getPaymentForm().getCompany());
        fbPaymentForm.setName(fb.getPaymentForm().getName());
        fbPaymentForm.setSurname(fb.getPaymentForm().getSurname());
	}
    
    /**
     * 
     * @param paymentForm
     * @param clearAll
     */
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