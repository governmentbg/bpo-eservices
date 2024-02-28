/*******************************************************************************
 * * $Id:: PersonAbstractFactory.java 113496 2013-04-22 15:03:04Z karalch $
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.ContactInformationDetails;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Person;
import eu.ohim.sp.core.domain.person.PersonRole;

/**
 * Abstract Factory that should be extended by all
 * factories that convert Forms that extend either Applicant
 * or Representative
 * 
 * @author karalch
 * 
 */
public abstract class PersonAbstractFactory {

    @Autowired
    private AddressFactory addressFactory;

    /**
     * Does the transformation of core nationality to ui nationality
     * 
     * @param core nationality
     * @return ui nationality
     */
    protected String coreNationalityToUiNationality(String core) {
        return core;
    }

    /**
     * Does the transformation of ui nationality to core nationality
     * 
     * @param nationality
     * @return core nationality
     */
    protected String uiNationalityToCoreNationality(String nationality) {
        return nationality;
    }

    /**
     * Creates a list of core object of address books
     * 
     * @param form the received list of address form objects
     * @return the list of core object of address books
     */
    protected List<Address> uiCorrespAddressesToCoreCorrespAddresses(List<CorrespondenceAddressForm> form) {
        if (form == null) {
            return null;
        }

        List<Address> correspAddressBookList = new ArrayList<Address>();
        for (CorrespondenceAddressForm caForm : form) {
            Address correspAddressBook = new Address();
            if (caForm.getAddressForm() != null) {
                correspAddressBook = addressFactory.convertFrom(caForm.getAddressForm());
            }

            if (StringUtils.isNotEmpty(caForm.getCorrespondenceName())) {
                correspAddressBook.setPostalName(caForm.getCorrespondenceName());
            }
            /*
             * if(!StringUtils.isBlank(caForm.getCorrespondenceEmail()))
             * {
             * correspAddressBook.setContactInformationDetails(new ContactInformationDetails());
             * correspAddressBook.getContactInformationDetails().setEmail(caForm.getCorrespondenceEmail());
             * if(!StringUtil.isBlank(caForm.getCorrespondencePhone()))
             * {
             * correspAddressBook.getContactInformationDetails().setPhone(new Phone());
             * correspAddressBook.getContactInformationDetails().getPhone()
             * .setNumber(caForm.getCorrespondencePhone());
             * }
             * }
             */
            if (correspAddressBook != null) {
                correspAddressBook.setOtherIndicator(caForm.isSubmittedJurisdictionIndicator());
            }
            correspAddressBookList.add(correspAddressBook);
        }

        return correspAddressBookList;
    }

    /**
     * Transforms from the list of core AddressBook to the list of ui Address Book
     * 
     * @param core the list of Address Book
     * @return the ui of Address Book
     */
    protected List<CorrespondenceAddressForm> coreCorrespondenceAddressesToUiCorrespAddresses(List<Address> core) {
        if (core == null) {
            return null;
        }
        List<CorrespondenceAddressForm> form = new ArrayList<CorrespondenceAddressForm>();

        for (Address address : core) {
            CorrespondenceAddressForm formAddress = new CorrespondenceAddressForm();
            formAddress.setAddressForm(addressFactory.convertTo(address));

            if (StringUtils.isNotEmpty(address.getPostalName())) {
                formAddress.setCorrespondenceName(address.getPostalName());
            }
            /*
             * if(address.getContactInformationDetails() != null)
             * {
             * formAddress.setCorrespondenceEmail(address.getContactInformationDetails().getEmail());
             * if(address.getContactInformationDetails().getPhone() != null)
             * {
             * formAddress.setCorrespondencePhone(addressBook.getContactInformationDetails().getPhone().getNumber());
             * }
             * }
             */
            form.add(formAddress);
        }

        return form;
    }

    /**
     * 
     * @param personForm
     * @param personCore
     */
    protected void addUiCorresspContactToCoreCorrespContact(PersonForm personForm, PersonRole personCore) {
        if (personForm.getCorrespondenceAddresses() != null) {
            List<Phone> phoneCAList = new ArrayList<Phone>();
            List<Email> emailCAList = new ArrayList<Email>();
            for (CorrespondenceAddressForm caForm : personForm.getCorrespondenceAddresses()) {
                // Address 1 - Phone 1 or empty
                // Address 1 - Fax 1 or empty
                // Address 2 - Phone 2 or empty
                // Address 2 - Fax 2 or empty
                // ...

                if (caForm.getCorrespondencePhone() != null) {
                    Phone phone = new Phone();
                    phone.setPhoneKind(PhoneKind.MOBILE_PHONE);
                    phone.setNumber(caForm.getCorrespondencePhone());
                    phoneCAList.add(phone);
                }

                if (caForm.getCorrespondenceFax() != null) {
                    Phone fax = new Phone();
                    fax.setPhoneKind(PhoneKind.FAX);
                    fax.setNumber(caForm.getCorrespondenceFax());
                    phoneCAList.add(fax);
                }

                // Address 1 - Email 1 or empty
                // Address 2 - Email 2 or empty
                // Address 3 - Email 3 or empty
                // ...
                if (caForm.getCorrespondenceEmail() != null) {
                    Email email = new Email();
                    email.setEmailAddress(caForm.getCorrespondenceEmail());
                    emailCAList.add(email);
                }

            }

            personCore.setCorrespondencePhones(phoneCAList);
            personCore.setCorrespondenceEmails(emailCAList);
        }

    }

    /**
     * 
     * @param personCore
     * @param personForm
     */
    protected void addCoreCorrespContactToUiCorrespContact(PersonRole personCore, PersonForm personForm) {
        List<CorrespondenceAddressForm> correspondenceAddressesForms = personForm.getCorrespondenceAddresses();
        List<Email> emails = personCore.getCorrespondenceEmails();
        List<Phone> phones = personCore.getCorrespondencePhones();

        if (CollectionUtils.isNotEmpty(emails)) {
            int size = emails.size();
            Email email;
            for (int i = 0; i < size; i++) {
                email = emails.get(i);
                if (email != null) {
                    correspondenceAddressesForms.get(i).setCorrespondenceEmail(email.getEmailAddress());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(phones)) {
            int size = phones.size();
            Phone phone;
            for (int i = 0; i < size; i++) {
                phone = phones.get(i);
                if (phone != null) {
                    switch (phone.getPhoneKind()) {
                        case MOBILE_PHONE:
                            correspondenceAddressesForms.get(i).setCorrespondencePhone(phone.getNumber());
                            break;
                        case FAX:
                            correspondenceAddressesForms.get(i - 1).setCorrespondenceFax(phone.getNumber());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * Creates a contact information details object filled with the received person form object
     * 
     * @param form the received person form
     * @return the created contact information details
     */
    @Deprecated
    protected ContactInformationDetails uiContactDetailsToCoreContactInformationDetails(PersonForm form) {
        ContactInformationDetails contactInformationDetails = new ContactInformationDetails();

        List<Phone> phoneList = new ArrayList<Phone>();
        Phone phone = new Phone();

        phone.setNumber(form.getPhone());
        phoneList.add(phone);
        contactInformationDetails.setPhone(phoneList);

        List<String> faxList = new ArrayList<String>();
        String fax = form.getFax();
        faxList.add(fax);
        contactInformationDetails.setFax(faxList);

        List<String> emailList = new ArrayList<String>();
        String email = form.getEmail();
        emailList.add(email);
        contactInformationDetails.setEmail(emailList);

        List<String> urlList = new ArrayList<String>();
        String url = form.getWebsite();
        urlList.add(url);
        contactInformationDetails.setUrl(urlList);

        return contactInformationDetails;
    }

    /**
     * Fills the contact information details of the form
     * 
     * @param person the received contact information details
     * @param form the form that will be filled
     */
    protected void coreContactInformationDetailsToUiContactDetails(Person person, PersonForm form) {
        if (person != null) {
            if (person.getEmails() != null && !person.getEmails().isEmpty()) {
                for (Email email : person.getEmails()) {
                    if (StringUtils.isNotEmpty(email.getEmailAddress())) {
                        form.setEmail(email.getEmailAddress());
                        break;
                    }
                }
            }
            if (person.getPhones() != null && !person.getPhones().isEmpty()) {
                for (Phone phone : person.getPhones()) {
                    if (StringUtils.isNotEmpty(phone.getNumber()) && phone.getPhoneKind() != null) {
                        switch (phone.getPhoneKind()) {
                            case MOBILE_PHONE:
                                form.setPhone(phone.getNumber());
                                break;
                            case FIXED:
                                form.setFixed(phone.getNumber());
                                break;
                            case FAX:
                                form.setFax(phone.getNumber());
                                break;
                            case OTHER:
                                form.setPhone(phone.getNumber());
                                break;
                            default:
                                form.setFixed(phone.getNumber());
                                break;
                        }
                    }
                }
            }
            if (person.getUrls() != null && !person.getUrls().isEmpty()) {
                for (String url : person.getUrls()) {
                    form.setWebsite(url);
                    break;
                }
            }
        }
    }

    protected AddressFactory getAddressFactory() {
        return addressFactory;
    }

    protected void setAddressFactory(AddressFactory addressFactory) {
        this.addressFactory = addressFactory;
    }

}
