package eu.ohim.sp.eservices.ui.controller.payment;

import eu.ohim.sp.common.ui.flow.section.PaymentFlowBean;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.payment.PayerKindForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the Ajax calls on the Payment Options section
 * 
 * @author monteca
 * 
 */
@Controller
public class ESPaymentOptionsController {

    @Autowired
    private PaymentFlowBean flowBean;

    /**
     * 
     * @param fb
     * @param result
     * @return
     */
    @RequestMapping(value = "getDSPaymentOptions", method = RequestMethod.GET)
    public ModelAndView loadPayerTypes(@ModelAttribute ESFlowBeanImpl fb, BindingResult result) {
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
	private void setRepresentativeAsPayer(PaymentForm fbPaymentForm, ESFlowBeanImpl fb) {
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
	private void setApplicantAsPayer(PaymentForm fbPaymentForm, ESFlowBeanImpl fb) {
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
    private void setOtherAsPayer(PaymentForm fbPaymentForm, ESFlowBeanImpl fb) {
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