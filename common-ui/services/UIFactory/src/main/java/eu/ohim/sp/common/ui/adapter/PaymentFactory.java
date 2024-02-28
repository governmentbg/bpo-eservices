/*******************************************************************************
 * * $Id:: PaymentFactory.java 113496 2013-04-22 15:03:04Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.payment.Payment;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.payment.PaymentKind;
import eu.ohim.sp.core.domain.payment.PaymentStatusCode;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.person.PersonRoleKind;


/**
 * @author soriama
 * 
 */
@Component
public class PaymentFactory implements UIFactory<PaymentFee, PaymentForm> {

	/**
     * Object that accesses the CMS to get property values
     */
	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;
	
    /**
     * Receives as a parameter a UI {@link PaymentForm} object and converts it to a
     * core {@link Payment} object.
     * 
     * @param form the UI PaymentForm to convert
     * @return the core Payment object
     */
    @Override
    public PaymentFee convertTo(PaymentForm form) {
        if (form == null)
            return new PaymentFee();

        PaymentFee core = new PaymentFee();

        // Set payment details
        if (form.getPaymentResult() != null && form.getPaymentResult().getPaymentRequest() != null) {
            core.setIdentifier(form.getPaymentResult().getPaymentRequest().getPaymentRequestId());
        }

        // Set payment status
        if (form.getPaymentResult() != null) {
        	core.setStatus(form.getPaymentResult().getPaymentStatusCode());
        } else if (form.isPayLater()) {
        	core.setStatus(PaymentStatusCode.TO_FOLLOW);

        } else {
        	core.setStatus(PaymentStatusCode.UNDEFINED);
        }
        core.setPayLaterAttachedDocuments(listAttachedDocumentFactory.convertTo(form.getPayLaterAttachment()));

        // Set payment reference
        if (form.getPaymentResult() != null) {
            core.setReference(form.getPaymentResult().getExternalPaymentNumber());
        }

        // Sent the payment of method. Either the selected by the user in our UI (if available) or in the external
        // environment
        if (form.getPaymentMethod() != null && !form.getPaymentMethod().isEmpty()) {
            core.setKind(PaymentKind.valueOf(form.getPaymentMethod()));
        } else if (form.getPaymentResult() != null && form.getPaymentResult().getExternalPaymentMethod() != null) {
            for (PaymentKind p : PaymentKind.values()) {
                if (p.toString().toLowerCase()
                        .equals(form.getPaymentResult().getExternalPaymentMethod().toLowerCase().replace(" ", "_"))) {
                    core.setKind(p);
                    break;
                }
            }
        }

        if (form.getPayerType() != null && !form.getPayerType().isEmpty()) {
        	core.setPayerIdentifier(PersonRoleKind.valueOf(form.getPayerType()));
        }

        // Set payer personal details
        PersonRole personRole = new PersonRole();
        PersonName personName = new PersonName();
        personName.setFirstName(form.getName());
        personName.setLastName(form.getSurname());
        personName.setOrganizationName(form.getCompany());
        personRole.setName(personName);
        
        List<Address> addresses = new ArrayList<Address>();
        Address address = new Address();
        address.setStreet(form.getAddress().getStreet());
    	address.setCity(form.getAddress().getCity());
    	address.setPostCode(form.getAddress().getPostalCode());
    	address.setCountry(form.getAddress().getCountry());
    	address.setState(form.getAddress().getStateprovince());
    	address.setStreetNumber(form.getAddress().getHouseNumber());
        addresses.add(address);
        personRole.setAddresses(addresses);
        
        List<Email> emails = new ArrayList<Email>();
        Email email = new Email();
        email.setEmailAddress(form.getEmail());
        emails.add(email);
        personRole.setEmails(emails);
        
        core.setPayer(personRole);
        
//        Map<String,String> fees=form.getPaymentFees();
//        String currency=        configurationServiceDelegator
//                .getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.CURRENCY_CODE);
//        if(fees!=null && !fees.isEmpty()){
//        	Collection<PaymentFee> fees_core=new ArrayList<PaymentFee>();
//        	for(String fee_key:fees.keySet()){
//        		PaymentFee fee=new PaymentFee();
//        		fee.setCurrencyCode(currency);
//        		fee.setAmount(new Double(fees.get(fee_key)));
//        		fee.setIdentifier(fee_key);
//        		fees_core.add(fee);
//        	}
//        	core.setPaymentFeeDetails(fees_core);
//        }
        return core;
    }

    /**
     * Receives as a parameter a sp {@link PaymentForm} object and converts it to a {@link PaymentForm} object.
     * 
     * @param payment the payment to convert
     * @return the payment form object
     */
    @Override
    public PaymentForm convertFrom(PaymentFee payment) {
        PaymentForm paymentForm = new PaymentForm();

        if (payment != null) {
        	switch (payment.getStatus()) {
        		case UNDEFINED:
        			break;
        		case TO_FOLLOW:
        			paymentForm.setPayLater(true);
        			break;
        		default:
        			paymentForm.setPaymentResult(new PaymentResult());
        			paymentForm.getPaymentResult().setPaymentStatusCode(payment.getStatus());
        			break;
			}
            paymentForm.setPayLaterAttachment(listAttachedDocumentFactory.convertFrom(payment.getPayLaterAttachedDocuments()));
        	
            if (payment.getKind() != null) {
                for (PaymentKind paymentKindType : PaymentKind.values()) {
                    if (paymentKindType.equals(payment.getKind())) {
                        paymentForm.setPaymentMethod(paymentKindType.toString());
                    }
                }
            }

            if (payment.getPayerIdentifier() != null) {
                paymentForm.setPayerType(payment.getPayerIdentifier().name());
            }

            if (payment.getPayer() != null) {
            	if (payment.getPayer().getName() != null) {
            		paymentForm.setName(payment.getPayer().getName().getFirstName());
            		paymentForm.setSurname(payment.getPayer().getName().getLastName());
            		paymentForm.setCompany(payment.getPayer().getName().getOrganizationName());
            	}
            	if (payment.getPayer().getAddresses() != null) {
            		List<Address> addresses = payment.getPayer().getAddresses();
            		for (Address address : addresses) {
            			paymentForm.setAddress(new AddressForm());
                        paymentForm.getAddress().setCity(address.getCity());
                        paymentForm.getAddress().setStreet(address.getStreet());
                        paymentForm.getAddress().setPostalCode(address.getPostCode());
                        paymentForm.getAddress().setCountry(address.getCountry());
                        paymentForm.getAddress().setStateprovince(address.getState());
                        paymentForm.getAddress().setHouseNumber(address.getStreetNumber());
                        break;
					}
            	}
            }

//            if(payment.getPaymentFeeDetails()!=null && !payment.getPaymentFeeDetails().isEmpty()){
//            	Map<String,String> fees=new HashMap<String, String>();
//            	for(PaymentFee fee:payment.getPaymentFeeDetails()){
//            		/*PaymentFeeForm paymentFee=new PaymentFeeForm();
//            		paymentFee.setCurrencyCode(fee.getCurrencyCode());
//            		paymentFee.setFeeAmount(fee.getFeeAmount());
//            		paymentFee.setFeeIdentifier(fee.getFeeIdentifier());
//            		paymentFee.setFeeUnitAmount(fee.getFeeUnitAmount());
//            		paymentFee.setFeeUnitQuantity(fee.getFeeUnitQuantity());*/
//            		fees.put(fee.getFeeIdentifier(), fee.getFeeAmount()+"");
//            	}
//            	paymentForm.setPaymentFees(fees);
//            }

        }

        return paymentForm;
    }

    public PaymentFee convertToPaymentFeeDetails(FeesForm feesForm) {
        PaymentFee paymentFee = new PaymentFee();
        paymentFee.setAmount(feesForm.getTotalAmount());
        //paymentFee.setFeeUnitAmount(feesForm.getBasicFee());
//        paymentFee.setCurrencyCode(configurationServiceDelegator
//                .getValueFromGeneralComponent(ConfigurationServiceDelegatorInterface.CURRENCY_CODE));
        return paymentFee;
    }

}
