/*******************************************************************************
 * * $Id:: PaymentForm.java 110978 2013-04-05 15:06:40Z soriam#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.payment;

import java.util.Map;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;

/**
 * Stores all the necessary information for the Payment
 * 
 * @author ckara & ionitdi
 */
public class PaymentForm extends AbstractForm {

    private static final long serialVersionUID = 1L;

    private String payerType;

    private String paymentMethod;

    private String name;

    private String surname;

    private String company;

    private String email;

    private AddressForm address;

    private PaymentResult paymentResult;

    private String selectedPayer;

    private boolean checkPayerDetails;
    
    private boolean payLater;
    
    private boolean title;
    
    private FileWrapper payLaterAttachment;
    
    private boolean submitApplication;
    
    //private List<PaymentFeeForm> paymentFees;

    private Map<String,String> paymentFees;
    public PaymentForm() {
        address = new AddressForm();
        payLaterAttachment=new FileWrapper();
    }

    /**
     * Method that returns the name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Method that sets the name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method that returns the address
     * 
     * @return the address
     */
    public AddressForm getAddress() {
        return address;
    }

    /**
     * Method that sets the address
     * 
     * @param address the address to set
     */
    public void setAddress(AddressForm address) {
        this.address = address;
    }

    /**
     * Method that returns the surname
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Method that sets the surname
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Method that returns the company
     * 
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Method that sets the company
     * 
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Method that returns the payerType
     * 
     * @return the payerType
     */
    public String getPayerType() {
        return payerType;
    }

    /**
     * Method that sets the payerType
     * 
     * @param payerType the payerType to set
     */
    public void setPayerType(String payerType) {
        this.payerType = payerType;
    }

    /**
     * Method that returns the paymentMethod
     * 
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Method that sets the paymentMethod
     * 
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Method that returns the email
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that sets the email
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public PaymentForm clone() throws CloneNotSupportedException {

        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setAddress((AddressForm) address.clone());
        paymentForm.setId(id);
        paymentForm.setName(name);
        paymentForm.setSurname(surname);
        paymentForm.setPayLaterAttachment(this.getPayLaterAttachment());
        return paymentForm;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PAYMENT_SECTION;
    }

    /**
     * @return the paymentResult
     */
    public PaymentResult getPaymentResult() {
        return paymentResult;
    }

    /**
     * @param paymentResult the paymentResult to set
     */
    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }

    /**
     * @return the selectedPayer
     */
    public String getSelectedPayer() {
        return selectedPayer;
    }

    /**
     * @param selectedPayer the selectedPayer to set
     */
    public void setSelectedPayer(String selectedPayer) {
        this.selectedPayer = selectedPayer;
    }

    /**
     * @return the checkPayerDetails
     */
    public boolean isCheckPayerDetails() {
        return checkPayerDetails;
    }

    /**
     * @param checkPayerDetails the checkPayerDetails to set
     */
    public void setCheckPayerDetails(boolean checkPayerDetails) {
        this.checkPayerDetails = checkPayerDetails;
    }

	public Map<String, String> getPaymentFees() {
		return paymentFees;
	}

	public void setPaymentFees(Map<String, String> paymentFees) {
		this.paymentFees = paymentFees;
	}

	public boolean isPayLater() {
		return payLater;
	}

	public void setPayLater(boolean payLater) {
		this.payLater = payLater;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public FileWrapper getPayLaterAttachment() {
		return payLaterAttachment;
	}

	public void setPayLaterAttachment(FileWrapper payLaterAttachment) {
		this.payLaterAttachment = payLaterAttachment;
	}

	public boolean isSubmitApplication() {
		return submitApplication;
	}

	public void setSubmitApplication(boolean submitApplication) {
		this.submitApplication = submitApplication;
	}

	/*public List<PaymentFeeForm> getPaymentFees() {
		return paymentFees;
	}

	public void setPaymentFees(List<PaymentFeeForm> paymentFees) {
		this.paymentFees = paymentFees;
	}*/
	
	
    
    
}
