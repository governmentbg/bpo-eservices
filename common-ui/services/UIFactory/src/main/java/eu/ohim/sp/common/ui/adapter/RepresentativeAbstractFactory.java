/*******************************************************************************
 * * $Id:: RepresentativeAbstractFactory.java 113496 2013-04-22 15:03:04Z karalc#$
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

import eu.ohim.sp.common.ui.form.resources.FileAttachmentStatus;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.domain.resources.Document;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.common.ui.form.person.PersonIdentifierForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.PersonRoleRelationship;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.person.RepresentedPartyKindCode;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * Abstract Factory that should be extended by all
 * factories that convert Forms that extend either Representative
 * 
 * @author karalch
 * 
 */
public abstract class RepresentativeAbstractFactory<T extends RepresentativeForm> extends PersonAbstractFactory
        implements UIFactory<Representative, T> {

    @Autowired
    private AttachedDocumentFactory attachedDocumentFactory;

    @Autowired
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    /**
     * Receives as a parameter a UI RepresentativeForm object and converts it to
     * a core Representative object.
     * 
     * @param form
     *            the UI RepresentativeForm to convert
     * @return the core Representative object
     */
    protected Representative internalUiRepresentativeToCoreRepresentative(RepresentativeForm form) {
        if (form == null) {
            return new Representative();
        }

        Representative core = new Representative();
        if (form.getImported()) {
            List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
            if(form.getId() != null){
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
            taxNo.setIdentifierKindCode("VAT number");
            if(core.getIdentifiers() == null){
                core.setIdentifiers(new ArrayList<>());
            }
            core.getIdentifiers().add(taxNo);
        }

        // Identifiers


        if (form.getPersonIdentifierForm() != null && (form.getPersonIdentifierForm().getDoy() != null || form.getPersonIdentifierForm().getAfimi()!= null)) {
            List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
            if (!StringUtils.isEmpty(form.getPersonIdentifierForm().getAfimi())) {
                PersonIdentifier personalIdent = new PersonIdentifier();
                personalIdent.setValue(form.getPersonIdentifierForm().getAfimi());
                personalIdent.setIdentifierKindCode("VAT Number");
                identifiers.add(personalIdent);
            }
            if (!StringUtils.isEmpty(form.getPersonIdentifierForm().getDoy())) {
                PersonIdentifier personalIdent = new PersonIdentifier();
                personalIdent.setValue(form.getPersonIdentifierForm().getDoy());
                personalIdent.setIdentifierKindCode("Tax Office");
                identifiers.add(personalIdent);
            }
            core.setIdentifiers(identifiers);
        }

        // TODO: change code!!!
        if (form.isRepresentativeIsOwner()) {
            core.setRepresentedPartyKindCode(RepresentedPartyKindCode.ASSIGNEE);
        } else {
            core.setRepresentedPartyKindCode(RepresentedPartyKindCode.OWNER);
        }

        core.setPersonNumber(form.getId());

        List<Address> addressList = new ArrayList<Address>();
        Address address = super.getAddressFactory().convertFrom(form.getAddress());
        addressList.add(address);
        core.setAddresses(addressList);

        core.setDomicileLocality(form.getDomicile());
        core.setDomicileCountry(form.getCountryOfDomicile());

        core.setCorrespondenceAddresses(uiCorrespAddressesToCoreCorrespAddresses(form.getCorrespondenceAddresses()));

        // Correspondence emails and phones.
        addUiCorresspContactToCoreCorrespContact(form, core);

        // Contact information
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

        core.setNationality(uiNationalityToCoreNationality(form.getNationality()));

        // core.setFeeByRepresentativeInfo(form.getFeeByRepresentativeInfo());
        // core.setImported(form.getImported());

        // core.setRepresentativeReference(form.getReference());
        core.setReference(form.getReference());
        if (form.getRepresentativeAttachment() != null
                && !CollectionUtils.isEmpty(form.getRepresentativeAttachment().getStoredFiles())) {
            if (core.getPersonRoleRelationships() == null) {
                core.setPersonRoleRelationships(new ArrayList<PersonRoleRelationship>());
            }
            PersonRoleRelationship personRoleRelationship = new PersonRoleRelationship();
            personRoleRelationship.setDocuments(new ArrayList<AttachedDocument>());
            for (StoredFile storedFile : form.getRepresentativeAttachment().getStoredFiles()) {
                AttachedDocument repDoc = attachedDocumentFactory.convertTo(storedFile);
                if(repDoc.getDocument() != null){
                    repDoc.getDocument().setStatus("Attached");
                }
                personRoleRelationship.getDocuments().add(repDoc);
            }
            core.getPersonRoleRelationships().add(personRoleRelationship);
        } else if(form.getRepresentativeAttachment() != null && CollectionUtils.isEmpty(form.getRepresentativeAttachment().getStoredFiles()) &&
            form.getRepresentativeAttachment().getAttachmentStatus() != null &&
            form.getRepresentativeAttachment().getAttachmentStatus() != FileAttachmentStatus.NOT_ATTACHED){

            if (core.getPersonRoleRelationships() == null) {
                core.setPersonRoleRelationships(new ArrayList<PersonRoleRelationship>());
            }
            PersonRoleRelationship personRoleRelationship = new PersonRoleRelationship();
            personRoleRelationship.setDocuments(new ArrayList<AttachedDocument>());
            AttachedDocument emptyDoc = new AttachedDocument();
            Document fake = new Document();
            emptyDoc.setDocument(fake);
            personRoleRelationship.getDocuments().add(emptyDoc);
            core.getPersonRoleRelationships().add(personRoleRelationship);
            fake.setStatus(form.getRepresentativeAttachment().getAttachmentStatus().getValue());
        }

        core.setPowValidityEndDate(form.getPowValidityEndDate());
        core.setPowReauthorizationIndicator(form.getPowReauthorizationIndicator());
        core.setPowNote(form.getPowNote());
        core.setPowValidityIndefiniteIndicator(form.getPowValidityIndefiniteIndicator());
        core.setPowRevokesPreviousIndicator(form.getPowRevokesPreviousIndicator());

        return core;
    }

    protected RepresentativeForm internalCoreRepresentativeToUIRepresentative(Representative representative) {
        if (representative == null) {
            return new RepresentativeForm();
        }

        RepresentativeForm form = new RepresentativeForm();
        form.setType(EnumAdapter.coreRepresentativeKindToFormRepresentativeKind(representative.getRepresentativeKind(),
                representative.getKind()));

        // Address
        if (representative.getAddresses() != null && !representative.getAddresses().isEmpty()) {
            Address address = representative.getAddresses().get(0);
            form.setAddress(super.getAddressFactory().convertTo(address));
        }

        // Correspondence addresses
        form.setCorrespondenceAddresses(coreCorrespondenceAddressesToUiCorrespAddresses(representative
                .getCorrespondenceAddresses()));
        // Correspondence emails and phones.
        addCoreCorrespContactToUiCorrespContact(representative, form);

        // Contact information
        coreContactInformationDetailsToUiContactDetails(representative, form);

        if (representative.getName() != null && representative.getName().getFirstName() != null) {
            form.setName(representative.getName().getFirstName());
        }

        if (representative.getName() != null && representative.getName().getLastName() != null) {
            form.setName(representative.getName().getLastName());
        }

        form.setDomicile(representative.getDomicileLocality());
        form.setCountryOfDomicile(representative.getDomicileCountry());

        if (representative.getRepresentedPartyKindCode() == RepresentedPartyKindCode.ASSIGNEE) {
            form.setRepresentativeIsOwner(true);
        } else {
            form.setRepresentativeIsOwner(false);
        }

//        form.setId(representative.getPersonNumber());

        if (representative.getIdentifiers() != null && !representative.getIdentifiers().isEmpty()) {
            form.setPersonIdentifierForm(new PersonIdentifierForm());
            for (PersonIdentifier personIdentifier : representative.getIdentifiers()) {
                if (StringUtils.isNotEmpty(personIdentifier.getIdentifierKindCode())) {
                    if (personIdentifier.getIdentifierKindCode().equals("Company Number")) {
                        form.setNationalOfficialBusinessRegister(personIdentifier.getValue());
                    } else {
                        if (personIdentifier.getIdentifierKindCode().equals("VAT Number")) {
                            // Core VAT Number is the same core identifier for two different UI values.
                            form.setTaxIdNumber(personIdentifier.getValue());
                            form.getPersonIdentifierForm().setAfimi(personIdentifier.getValue()); // Greek specific
                        } else {
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

        form.setConsentForPublishingPersonalInfo(!representative.isPrivacyWaiver());
        form.setNationality(coreNationalityToUiNationality(representative.getNationality()));
        form.setReference(representative.getReference());

        // form.setImported(representative.isImported());
        // form.setFeeByRepresentativeInfo(representative.isFeeByRepresentativeInfo());

        form.setRepresentativeAttachment(new FileWrapper());
        if(!CollectionUtils.isEmpty(representative.getPersonRoleRelationships())){
            if(representative.getPersonRoleRelationships().get(0).getDocuments() != null &&
                representative.getPersonRoleRelationships().get(0).getDocuments().size() > 0 &&
                representative.getPersonRoleRelationships().get(0).getDocuments().get(0).getDocument() != null) {
                if (representative.getPersonRoleRelationships().get(0).getDocuments().get(0).getDocument().getDocumentId() != null){
                    form.getRepresentativeAttachment().setAttachmentStatus(FileAttachmentStatus.ATTACHED);
                    form.setRepresentativeAttachment(listAttachedDocumentFactory.convertFrom(representative
                        .getPersonRoleRelationships().get(0).getDocuments()));
                } else if(representative.getPersonRoleRelationships().get(0).getDocuments().get(0).getDocument().getStatus() != null) {
                    form.getRepresentativeAttachment().setAttachmentStatus(FileAttachmentStatus.fromValue(representative.getPersonRoleRelationships().get(0).getDocuments().get(0).getDocument().getStatus()));
                }
            }
        } else {
            form.getRepresentativeAttachment().setAttachmentStatus(null);
        }

        form.setPowValidityEndDate(representative.getPowValidityEndDate());
        form.setPowReauthorizationIndicator(representative.getPowReauthorizationIndicator());
        form.setPowNote(representative.getPowNote());
        form.setPowValidityIndefiniteIndicator(representative.getPowValidityIndefiniteIndicator());
        form.setPowRevokesPreviousIndicator(representative.getPowRevokesPreviousIndicator());

        return form;

    }

    protected T createSubRepresentative(RepresentativeForm representativeForm, Class<T> clazz) {
        return representativeForm.copyRep(clazz);
    }

}
