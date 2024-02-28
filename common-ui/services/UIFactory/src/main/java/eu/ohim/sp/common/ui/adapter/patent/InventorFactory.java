package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.PersonAbstractFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.PersonIdentifierForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRole;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventorFactory extends PersonAbstractFactory implements UIFactory<Inventor, InventorForm> {

	@Override
	public Inventor convertTo(InventorForm form) {
		Inventor core = new Inventor();

		if (form != null) {

			if (form.getImported()) {
				List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
				if(StringUtils.isNotEmpty(form.getNationalOfficialBusinessRegister())){
					PersonIdentifier nobr = new PersonIdentifier();
					nobr.setValue(form.getNationalOfficialBusinessRegister());
					nobr.setIdentifierKindCode("Company Number");
					identifiers.add(nobr);
				}
				if(StringUtils.isNotEmpty(form.getTaxIdNumber())){
					PersonIdentifier taxNo = new PersonIdentifier();
					taxNo.setValue(form.getTaxIdNumber());
					taxNo.setIdentifierKindCode("VAT Number");
					identifiers.add(taxNo);
				}
				if(StringUtils.isNotEmpty(form.getId())){
					PersonIdentifier id = new PersonIdentifier();
					id.setValue(form.getId());
					identifiers.add(id);
				}
				core.setIdentifiers(identifiers);
			}

			core.setWaiver(form.isWaiver());
			core.setBelongsToAGroup(form.isBelongsToAGroup());
			core.setGroupName(form.getGroupName());
			core.setImportedFromApplicant(form.isImportedFromApplicant());
			core.setNationality(form.getNationality());

			// Name
			PersonName name = new PersonName();
			name.setFirstName(form.getFirstName());
			name.setMiddleName(form.getMiddleName());
			name.setLastName(form.getSurname());
			core.setName(name);

			// Address
			List<Address> addresses = new ArrayList<Address>();
			if (form.getAddress() != null) {
				addresses.add(super.getAddressFactory().convertFrom(form.getAddress()));
			}
			core.setAddresses(addresses);

			// Email
			List<Email> emails = new ArrayList<Email>();
			if (form.getEmail() != null) {
				Email email = new Email();
				email.setEmailAddress(form.getEmail());
				emails.add(email);
			}
			core.setEmails(emails);

			// Website
			List<String> urls = new ArrayList<String>();
			if (form.getWebsite() != null) {
				urls.add(form.getWebsite());
			}
			core.setUrls(urls);

			//Phones information
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

			// Correspondence addresses
			core.setCorrespondenceAddresses(uiCorrespAddressesToCoreCorrespAddresses(form.getCorrespondenceAddresses()));

			// Correspondence emails and phones.
			addUiCorresspContactToCoreCorrespContact(form, core);
		}

		return core;
	}

	@Override
	public InventorForm convertFrom(Inventor core) {
		InventorForm form = convertFromPersonRole(core);

		if(core != null){
			form.setWaiver(core.isWaiver());
			form.setBelongsToAGroup(core.isBelongsToAGroup());
			form.setGroupName(core.getGroupName());
			form.setImportedFromApplicant(core.isImportedFromApplicant());
		}

		return form;
	}


	public InventorForm convertFromDesigner(Designer core){
		InventorForm form = convertFromPersonRole(core);
		if(core != null){
			form.setWaiver(core.isWaiver());
			form.setBelongsToAGroup(core.isBelongsToAGroup());
			form.setGroupName(core.getGroupName());
			form.setImportedFromApplicant(false);
		}
		return form;
	}

	private InventorForm convertFromPersonRole(PersonRole core){
		InventorForm form = new InventorForm();

		if (core != null) {
			if (core.getIdentifiers() != null && !core.getIdentifiers().isEmpty()) {
				form.setPersonIdentifierForm(new PersonIdentifierForm());
				for (PersonIdentifier personIdentifier : core.getIdentifiers()) {
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

			form.setNationality(core.getNationality());

			// Name
			if (core.getName() != null) {
				form.setFirstName(core.getName().getFirstName());
				form.setMiddleName(core.getName().getMiddleName());
				form.setSurname(core.getName().getLastName());
			}

			// Address
			if (CollectionUtils.isNotEmpty(core.getAddresses()) && CollectionUtils.size(core.getAddresses()) == 1) {
				Address address = core.getAddresses().get(0);
				form.setAddress(super.getAddressFactory().convertTo(address));
			}

			// Email
			if (CollectionUtils.isNotEmpty(core.getEmails()) && CollectionUtils.size(core.getEmails()) == 1) {
				Email email = core.getEmails().get(0);
				form.setEmail(email.getEmailAddress());
			}

			// Website
			if (CollectionUtils.isNotEmpty(core.getUrls()) && CollectionUtils.size(core.getUrls()) == 1) {
				form.setWebsite(core.getUrls().get(0));
			}

			// Phone and fax
			if (CollectionUtils.isNotEmpty(core.getPhones())) {
				for (Phone phone : core.getPhones()) {
					switch (phone.getPhoneKind()) {
						case UNDEFINED: case MOBILE_PHONE:
							form.setPhone(phone.getNumber());
							break;
						case FAX:
							form.setFax(phone.getNumber());
							break;
						case FIXED:
							form.setFixed(phone.getNumber());
						default:
							break;
					}
				}
			}

			// Correspondence addresses
			List<CorrespondenceAddressForm> correspondenceAddressesForms = form.getCorrespondenceAddresses();
			if (CollectionUtils.isNotEmpty(core.getCorrespondenceAddresses())) {
				correspondenceAddressesForms.addAll(coreCorrespondenceAddressesToUiCorrespAddresses(core.getCorrespondenceAddresses()));
			}

			// Correspondence emails and phones.
			addCoreCorrespContactToUiCorrespContact(core, form);
		}
		return form;
	}
}
