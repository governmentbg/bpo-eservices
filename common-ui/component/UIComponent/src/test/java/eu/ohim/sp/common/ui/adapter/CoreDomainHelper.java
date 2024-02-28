/*******************************************************************************
 * * $Id:: CoreDomainHelper.java 113496 2013-04-22 15:03:04Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.PersonForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonIdentifier;
import eu.ohim.sp.core.domain.person.Representative;

/**
 * @author ionitdi
 */
public class CoreDomainHelper
{
	public static <T extends PersonForm> T formPersonStub(T form)
    {
        form.setId("some id");
        form.setAddress(formAddressStub());
        form.setConsentForPublishingPersonalInfo(true);
        form.setCorrespondenceAddresses(null);
        form.setEmail("some mail");
        form.setFax("some fax");
        form.setImported(true);
        form.setNationalOfficialBusinessRegister("some nobr");
        form.setPhone("some phone");
        form.setReceiveCorrespondenceViaEmail(true);
        form.setTaxIdNumber("some tax no");
        form.setWebsite("some website");
        form.setImported(true);
        return form;
    }

    public static <T extends ApplicantForm> T formApplicantStub(T form)
    {
        form = formPersonStub(form);
        form.setDomicile("some domicile");
        form.setDomicileCountry("some country");
        return form;
    }

    public static <T extends RepresentativeForm> T formRepresentativeStub(T form)
    {
        form = formPersonStub(form);
        form.setNationality("some nationality");
        form.setDomicile("some domicile");
        form.setCountryOfDomicile("some country");
        form.setFeeByRepresentativeInfo(true);
        form.setReference("some reference");
        form.setReceiveCorrespondenceViaEmail(false);

        return form;
    }

    public static AddressForm formAddressStub()
    {
        AddressForm stub = new AddressForm();
        stub.setCity("some city");
        stub.setCountry("some country");
        stub.setPostalCode("some postal code");
        stub.setStateprovince("some state");
        stub.setStreet("some street");
        return stub;
    }

    public static Applicant coreApplicantStub()
    {
        Applicant expected = new Applicant();
        expected.setPersonNumber("some id");
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some nobr");
        identifiers.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some tax no");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);

        List<Address> addresses = new ArrayList<Address>();
        Address address = coreAddressStub();
        addresses.add(address);
        expected.setAddresses(addresses);

        List<Email> emails = new ArrayList<Email>();
        Email email = coreEmailStub();
        emails.add(email);
        expected.setEmails(emails);
        
        List<Phone> phones = new ArrayList<Phone>();
        Phone phone = corePhoneStub();
        phones.add(phone);
        phone = coreFaxStub();
        phones.add(phone);
        expected.setPhones(phones);
        
        List<String> urls = new ArrayList<String>();
        String url = "some website";
        urls.add(url);
        expected.setUrls(urls);
        
        expected.setNationality("some nationality");
        expected.setDomicileCountry("some domicile");
        expected.setDomicileLocality("some domicile");
        
        expected.setPrivacyWaiver(false);
        expected.setCorrespondenceAddresses(null);
        expected.setPreferredCorrespondenceKind("EMAIL");
        //expected.setPreferredCorrespondenceKind(PreferredCorrespondenceKind.EMAIL.toString());

        return expected;
    }

    public static Representative coreRepresentativeStub()
    {
        Representative expected = new Representative();
        expected.setPersonNumber("some id");
        List<PersonIdentifier> identifiers = new ArrayList<PersonIdentifier>();
        PersonIdentifier personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some nobr");
        personIdentifier.setIdentifierKindCode("VAT Number");
        identifiers.add(personIdentifier);
        personIdentifier = new PersonIdentifier();
        personIdentifier.setValue("some tax no");
        personIdentifier.setIdentifierKindCode("VAT Number");
        identifiers.add(personIdentifier);
        expected.setIdentifiers(identifiers);
        
        List<Address> addresses = new ArrayList<Address>();
        Address address = coreAddressStub();
        addresses.add(address);
        expected.setAddresses(addresses);

        List<Email> emails = new ArrayList<Email>();
        Email email = coreEmailStub();
        emails.add(email);
        expected.setEmails(emails);
        
        List<Phone> phones = new ArrayList<Phone>();
        Phone phone = corePhoneStub();
        phones.add(phone);
        phone = coreFaxStub();
        phones.add(phone);
        expected.setPhones(phones);
        
        List<String> urls = new ArrayList<String>();
        String url = "some website";
        urls.add(url);
        expected.setUrls(urls);
        
        expected.setNationality("some nationality");
        expected.setDomicileCountry("some country");
        expected.setDomicileLocality("some domicile");

        expected.setPrivacyWaiver(false);
        expected.setCorrespondenceAddresses(null);
        expected.setPreferredCorrespondenceKind(null);

        return expected;
    }

    public static Address coreAddressStub()
    {
        Address expected = new Address();
        expected.setCity("some city");
        expected.setCountry("some country");
        expected.setPostCode("some postal code");
        expected.setState("some state");
        expected.setStreet("some street");
        return expected;
    }
    
    public static Email coreEmailStub()
    {
    	Email expected = new Email();
    	expected.setEmailAddress("some mail");
    	expected.setKind(null);
    	return expected;
    }
    
    public static Phone corePhoneStub()
    {
    	Phone expected = new Phone();
    	expected.setNumber("some phone");
    	expected.setPhoneKind(PhoneKind.UNDEFINED);
    	return expected;
    }
    
    public static Phone coreFaxStub()
    {
    	Phone expected = new Phone();
    	expected.setNumber("some fax");
    	expected.setPhoneKind(PhoneKind.FAX);
    	return expected;
    }
}
