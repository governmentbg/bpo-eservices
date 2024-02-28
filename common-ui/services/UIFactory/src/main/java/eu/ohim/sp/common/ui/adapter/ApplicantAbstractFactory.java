/*******************************************************************************
 * * $Id:: ApplicantAbstractFactory.java 113496 2013-04-22 15:03:04Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.NationalIdType;
import eu.ohim.sp.common.ui.form.person.PersonIdentifierForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Factory that should be extended by all
 * factories that convert Forms that extend either Applicant
 * @author karalch
 *
 */
public abstract class ApplicantAbstractFactory<T extends ApplicantForm> extends PersonAbstractFactory implements UIFactory<Applicant, T> {
	
	private static final String PREFERRED_CORRESPONDENCE_KIND_EMAIL = "Email";
	
	/**
	 * Receives as a parameter a core {@link Applicant} object and converts it to the
	 * base {@link ApplicantForm} object.
	 *
	 * @param applicant
	 * @return applicantForm corresponding to the Applicant
	 */
    protected ApplicantForm internalCoreApplicantToUIApplicant(Applicant applicant) {
		if (applicant == null) {
			return new ApplicantForm();
		}

		ApplicantForm form = new ApplicantForm();

		if (applicant.getKind() != null) {
			form.setType(EnumAdapter.coreApplicantKindToFormApplicantKind(applicant.getKind()));
		}

		// Address
		if (applicant.getAddresses() != null && !applicant.getAddresses().isEmpty()) {
			Address address = applicant.getAddresses().get(0);
			form.setAddress(super.getAddressFactory().convertTo(address));
			// Contact information
			coreContactInformationDetailsToUiContactDetails(applicant, form);			
		}
		
		form.setDomicile(applicant.getDomicileLocality());
		form.setDomicileCountry(applicant.getDomicileCountry());

        form.setCorrespondenceAddresses(coreCorrespondenceAddressesToUiCorrespAddresses(applicant.getCorrespondenceAddresses()));
		// Correspondence emails and phones.
		addCoreCorrespContactToUiCorrespContact(applicant, form);
		
		if(applicant.getOwner() != null){
			form.setApplicantIsOwner(applicant.getOwner());
		}
        
//        if (applicant.getIdentifiers() != null && !applicant.getIdentifiers().isEmpty()) {
//        	for (PersonIdentifier personIdentifier : applicant.getIdentifiers()) {
//        		if (StringUtils.isNotEmpty(personIdentifier.getValue())) {
//        			form.setId(personIdentifier.getValue());
//        		}
//        	}
//        }
        if (applicant.getIdentifiers() != null && !applicant.getIdentifiers().isEmpty()) {
            form.setPersonIdentifierForm(new PersonIdentifierForm());
        	for (PersonIdentifier personIdentifier : applicant.getIdentifiers()) {
        		if (StringUtils.isNotEmpty(personIdentifier.getIdentifierKindCode())) {
					if (personIdentifier.getIdentifierKindCode().equals("Company Number")) {
						form.setNationalOfficialBusinessRegister(personIdentifier.getValue());
					} else if(NationalIdType.fromValue(personIdentifier.getIdentifierKindCode()) != null) {
						form.setNationalId(personIdentifier.getValue());
						form.setNationalIdType(NationalIdType.fromValue(personIdentifier.getIdentifierKindCode()));
					} else {
						if (personIdentifier.getIdentifierKindCode().equals("VAT Number")) {
								// Core VAT Number is the same core identifier for two different UI values.
								form.setTaxIdNumber(personIdentifier.getValue());
								form.getPersonIdentifierForm().setAfimi(personIdentifier.getValue());//Greek specific
						}else {
							if (personIdentifier.getIdentifierKindCode().equals("Tax Office")) {
								form.getPersonIdentifierForm().setDoy(personIdentifier.getValue());
							}	
						}
					}
        		} else {
					if(StringUtils.isNotEmpty(personIdentifier.getValue())){
						form.setId(personIdentifier.getValue());
						form.setImported(true);
					}
				}

			}
        }
        
        if (StringUtils.isNotEmpty(applicant.getPreferredCorrespondenceKind())) {
        	form.setReceiveCorrespondenceViaEmail(true);
        }
//        form.setId(applicant.getPersonNumber());
        form.setConsentForPublishingPersonalInfo(!applicant.isPrivacyWaiver());
//      form.setPartOfEEA(applicant.getIsEEA());

		return form;
	}


	/**
	 * Receives as a parameter a UI ApplicantForm object and converts it to a
	 * core Applicant object.
	 * 
	 * @param form
	 *            the UI ApplicantForm to convert
	 * @return the core Applicant object
	 */
    public Applicant internalUiApplicantToCoreApplicant(ApplicantForm form) {
		if (form == null) {
			return new Applicant();
		}

		Applicant core = new Applicant();

		if (form.getImported()) {
			List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
			if(StringUtils.isNotEmpty(form.getId())){
				PersonIdentifier id = new PersonIdentifier();
				id.setValue(form.getId());
				identifiers.add(id);
			}
			core.setIdentifiers(identifiers);
		}

		if(StringUtils.isNotEmpty(form.getNationalId()) && form.getNationalIdType() != null){
			PersonIdentifier natId = new PersonIdentifier();
			natId.setValue(form.getNationalId());
			natId.setIdentifierKindCode(form.getNationalIdType().getValue());
			if(core.getIdentifiers() == null){
				core.setIdentifiers(new ArrayList<>());
			}
			core.getIdentifiers().add(natId);
		}

		if(StringUtils.isNotEmpty(form.getNationalOfficialBusinessRegister())){
			PersonIdentifier nobr = new PersonIdentifier();
			nobr.setValue(form.getNationalOfficialBusinessRegister());
			nobr.setIdentifierKindCode("Company Number");
			if(core.getIdentifiers() == null){
				core.setIdentifiers(new ArrayList<>());
			}
			core.getIdentifiers().add(nobr);
		}

        if(StringUtils.isNotEmpty(form.getTaxIdNumber())){
            PersonIdentifier taxNo = new PersonIdentifier();
            taxNo.setValue(form.getTaxIdNumber());
            taxNo.setIdentifierKindCode("VAT Number");
            if(core.getIdentifiers() == null){
                core.setIdentifiers(new ArrayList<>());
            }
            core.getIdentifiers().add(taxNo);
        }
		
		core.setPersonNumber(form.getId());
		core.setKind(EnumAdapter.formApplicantKindToCoreApplicantKind(form.getType()));

		List<Address> addresses = new ArrayList<Address>();
        if(form.getAddress() != null)
        {
        	Address address = new Address();
        	address = super.getAddressFactory().convertFrom(form.getAddress());
        	if (address != null) {
        		addresses.add(address);
        	}
        }
        core.setAddresses(addresses);
        
        core.setDomicileLocality(form.getDomicile());
        core.setDomicileCountry(form.getDomicileCountry());

		core.setCorrespondenceAddresses(uiCorrespAddressesToCoreCorrespAddresses(form.getCorrespondenceAddresses()));

		// Correspondence emails and phones.
		addUiCorresspContactToCoreCorrespContact(form, core);

		//Contact information
		List<Phone> phoneList = new ArrayList<Phone>();
        
        if (StringUtils.isNotEmpty(form.getPhone())) {
			Phone phone = new Phone();
			phone.setNumber(form.getPhone());
			phone.setPhoneKind(PhoneKind.MOBILE_PHONE);
			phoneList.add(phone);
        }
        
        if (StringUtils.isNotEmpty(form.getFixed())) {
			Phone phone = new Phone();
			phone.setNumber(form.getFixed());
			phone.setPhoneKind(PhoneKind.FIXED);
			phoneList.add(phone);
        }        
		
        if (StringUtils.isNotEmpty(form.getFax())) {
			Phone fax = new Phone();
			fax.setNumber(form.getFax());
			fax.setPhoneKind(PhoneKind.FAX);
			phoneList.add(fax);
        }
		
		core.setPhones(phoneList);
		
		List<String> urls = new ArrayList<String>();
		if (StringUtils.isNotEmpty(form.getWebsite())) {
			urls.add(form.getWebsite());
		}
		core.setUrls(urls);

		List<Email> emails = new ArrayList<Email>();
		if (StringUtils.isNotEmpty(form.getEmail())) {
			Email email = new Email();
			email.setEmailAddress(form.getEmail());
			emails.add(email);
		}
		core.setEmails(emails);

		core.setPrivacyWaiver(!form.isConsentForPublishingPersonalInfo());

		core.setPreferredCorrespondenceKind(form.isReceiveCorrespondenceViaEmail() ? PREFERRED_CORRESPONDENCE_KIND_EMAIL : null);
		
		core.setOwner(form.isApplicantIsOwner());
	
		return core;
	}

	protected T createSubApplicant(ApplicantForm applicant, Class<T> clazz) {
		return applicant.copyApp(clazz);
	}


}
