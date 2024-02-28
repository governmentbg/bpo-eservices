package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.PersonIdentifierForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AssigneeAbstractFactory<T extends AssigneeForm> extends PersonAbstractFactory implements
		UIFactory<Assignee, T> {

	
    protected Assignee internalUiAssigneeToCoreAssignee(AssigneeForm form) {
		if (form == null) {
			return new Assignee();
		}

		Assignee core = new Assignee();
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

		core.setPersonNumber(form.getId());

        List<Address> addressList = new ArrayList<Address>();
        Address address = super.getAddressFactory().convertFrom(form.getAddress());
        addressList.add(address);
		core.setAddresses(addressList);
		
		core.setDomicileLocality(form.getDomicile());
		core.setDomicileCountry(form.getDomicileCountry());
		
		core.setCorrespondenceAddresses(uiCorrespAddressesToCoreCorrespAddresses(form.getCorrespondenceAddresses()));
		
		addUiCorresspContactToCoreCorrespContact(form, core);

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


	protected AssigneeForm internalCoreAssigneeToUIAssignee(Assignee assignee) {
		if (assignee == null) {
			return new AssigneeForm();
		}

		AssigneeForm form = new AssigneeForm();
		
		if (assignee.getKind() != null) {
			form.setType(EnumAdapter.coreAssigneeKindToFormAssigneeKind(assignee.getKind()));
		}

		// Address
		if (assignee.getAddresses() != null && !assignee.getAddresses().isEmpty()) {
			
			Address address = assignee.getAddresses().get(0);
			form.setAddress(super.getAddressFactory().convertTo(address));
			form.setCorrespondenceAddresses(coreCorrespondenceAddressesToUiCorrespAddresses(assignee.getCorrespondenceAddresses()));

		}
		
		addCoreCorrespContactToUiCorrespContact(assignee, form);
		
		//Contact information
		coreContactInformationDetailsToUiContactDetails(assignee, form);
		
		if (assignee.getName() != null && assignee.getName().getFirstName() != null) {
			form.setName(assignee.getName().getFirstName());
		}
		
		form.setDomicile(assignee.getDomicileLocality());
		form.setDomicileCountry(assignee.getDomicileCountry());

		if (assignee.getIdentifiers() != null && !assignee.getIdentifiers().isEmpty()) {
			form.setPersonIdentifierForm(new PersonIdentifierForm());
			for (PersonIdentifier personIdentifier : assignee.getIdentifiers()) {
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
		
		form.setConsentForPublishingPersonalInfo(!assignee.isPrivacyWaiver());
		
        //form.setReference(representative.getRepresentativeReference());
        //form.setImported(representative.isImported());
        //form.setFeeByRepresentativeInfo(representative.isFeeByRepresentativeInfo());
		
		return form;
		
	}
	
	protected T createSubAssignee(AssigneeForm assigneeForm, Class<T> clazz) {
		return assigneeForm.copyAss(clazz);
	}

}
