package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.person.PersonIdentifierForm;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.Representative;

public abstract class HolderAbstractFactory<T extends HolderForm> extends PersonAbstractFactory implements
		UIFactory<Holder, T> {

	
    protected Holder internalUiHolderToCoreHolder(HolderForm form) {
		if (form == null) {
			return new Holder();
		}

		Holder core = new Holder();
		if (form.getImported()) {
			List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();

			if(StringUtils.isNotEmpty(form.getId())){
				PersonIdentifier id = new PersonIdentifier();
				id.setValue(form.getId());
				identifiers.add(id);
			}
			core.setIdentifiers(identifiers);
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
//        if(form.getAddress() != null && !StringUtils.isBlank(form.getAddress().getCountry()))
//        {
//            for (Countries.Country country : configurationServiceDelegator.getCountries())
//            {
//                if (country.getCode().equals(form.getAddress().getCountry()))
//                {
//                    core.setIsEEA(country.isPartOfEEA());
//                    
//                    break;
//                }
//            }
//        }
		core.setPersonNumber(form.getId());

        List<Address> addressList = new ArrayList<Address>();
        Address address = super.getAddressFactory().convertFrom(form.getAddress());
        addressList.add(address);
		core.setAddresses(addressList);
		
		core.setDomicileLocality(form.getDomicile());
		core.setDomicileCountry(form.getDomicileCountry());
		
		core.setCorrespondenceAddresses(uiCorrespAddressesToCoreCorrespAddresses(form.getCorrespondenceAddresses()));

		//Contact information
        List<Phone> phoneList = new ArrayList<Phone>();
        
        if (StringUtils.isNotEmpty(form.getPhone())) {
			Phone phone = new Phone();
			phone.setNumber(form.getPhone());
			phone.setPhoneKind(PhoneKind.UNDEFINED);
			phoneList.add(phone);
        }
		
        if (StringUtils.isNotEmpty(form.getFax())) {
			Phone fax = new Phone();
			fax.setNumber(form.getFax());
			fax.setPhoneKind(PhoneKind.FAX);
			phoneList.add(fax);
        }
		
		core.setPhones(phoneList);
		
		if (StringUtils.isNotEmpty(form.getWebsite())) {
			List<String> urls = new ArrayList<String>();
			urls.add(form.getWebsite());
			core.setUrls(urls);
		}
		
		if (StringUtils.isNotEmpty(form.getEmail())) {
			List<Email> emails = new ArrayList<Email>();
			Email email = new Email();
			email.setEmailAddress(form.getEmail());
			emails.add(email);
			core.setEmails(emails);
		}
		
		core.setPrivacyWaiver(!form.isConsentForPublishingPersonalInfo());

		//core.setNationality(uiNationalityToCoreNationality(form.getNationality()));

		//core.setFeeByRepresentativeInfo(form.getFeeByRepresentativeInfo());
		//core.setImported(form.getImported());
		
		//core.setRepresentativeReference(form.getReference());
        form.setPartOfEEA(form.getPartOfEEA());

		return core;
	}


	protected HolderForm internalCoreHolderToUIHolder(Holder holder) {
		if (holder == null) {
			return new HolderForm();
		}

		HolderForm form = new HolderForm();
		
		if (holder.getKind() != null) {
			form.setType(EnumAdapter.coreHolderKindToFormHolderKind(holder.getKind()));
		}

		// Address
		if (holder.getAddresses() != null && !holder.getAddresses().isEmpty()) {
			
			Address address = holder.getAddresses().get(0);
			form.setAddress(super.getAddressFactory().convertTo(address));
			form.setCorrespondenceAddresses(
					coreCorrespondenceAddressesToUiCorrespAddresses(holder.getCorrespondenceAddresses()));

		}
		
		//Contact information
		coreContactInformationDetailsToUiContactDetails(holder, form);
		
		if (holder.getName() != null && holder.getName().getFirstName() != null) {
			form.setName(holder.getName().getFirstName());
		}
		
		form.setDomicile(holder.getDomicileLocality());
		form.setDomicileCountry(holder.getDomicileCountry());
		
		if (holder.getIdentifiers() != null && !holder.getIdentifiers().isEmpty()) {
			form.setPersonIdentifierForm(new PersonIdentifierForm());
			for (PersonIdentifier personIdentifier : holder.getIdentifiers()) {
				if (StringUtils.isNotEmpty(personIdentifier.getIdentifierKindCode())) {
					if (personIdentifier.getIdentifierKindCode().equals("Company Number")) {
						form.setNationalOfficialBusinessRegister(personIdentifier.getValue());
					}else {
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

		
		form.setConsentForPublishingPersonalInfo(!holder.isPrivacyWaiver());
		
        //form.setReference(representative.getRepresentativeReference());
        //form.setImported(representative.isImported());
        //form.setFeeByRepresentativeInfo(representative.isFeeByRepresentativeInfo());
		
		return form;
		
	}
	
	protected T createSubHolder(HolderForm holderForm, Class<T> clazz) {
		return holderForm.copyHol(clazz);
	}

}
