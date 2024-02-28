/*
 *  CoreDomain:: Person 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.id.Id;

/**
 * The Class Person.
 */
public class Person extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7853693876025929164L;

	/** The identifier. */
	private List<PersonIdentifier> identifiers;

	/** The kind. */
	private PersonKind kind;
	
	/** The name. */
	private PersonName name;

	/** The person number. */
	private String personNumber;

	/** The date of birth. */
	private Date dateOfBirth;

	/** The gender of the person */
	private Gender gender;

	/** The nationality. */
	private String nationality;

	/** The domicile locality. */
	private String domicileLocality;

	/** The domicile country. */
	private String domicileCountry;

	/** The legal entity. */
	private String legalForm;

	/** The incorporation country. */
	private String incorporationCountry;

	/** The incorporation state. */
	private String incorporationState;

	/** The privacy waiver. */
	private boolean privacyWaiver;

	/** The address. */
	private List<Address> addresses;

	/** The phone. */
	private List<Phone> phones;

	/** The email. */
	private List<Email> emails;

	/** The url. */
	private List<String> urls;

	/** The preferred correspondence kind. */
	private String preferredCorrespondenceKind;

	/**
	 * Gets the identifiers.
	 *
	 * @return the identifiers
	 */
	public List<PersonIdentifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Sets the identifiers.
	 *
	 * @param identifiers the new identifiers
	 */
	public void setIdentifiers(List<PersonIdentifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public PersonKind getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind the new kind
	 */
	public void setKind(PersonKind kind) {
		this.kind = kind;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public PersonName getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(PersonName name) {
		this.name = name;
	}

	/**
	 * Gets the person number.
	 *
	 * @return the person number
	 */
	public String getPersonNumber() {
		return personNumber;
	}

	/**
	 * Sets the person number.
	 *
	 * @param personNumber the new person number
	 */
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return DateUtil.cloneDate(dateOfBirth);
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = DateUtil.cloneDate(dateOfBirth);
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality the new nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the domicile locality.
	 *
	 * @return the domicile locality
	 */
	public String getDomicileLocality() {
		return domicileLocality;
	}

	/**
	 * Sets the domicile locality.
	 *
	 * @param domicileLocality the new domicile locality
	 */
	public void setDomicileLocality(String domicileLocality) {
		this.domicileLocality = domicileLocality;
	}

	/**
	 * Gets the domicile country.
	 *
	 * @return the domicile country
	 */
	public String getDomicileCountry() {
		return domicileCountry;
	}

	/**
	 * Sets the domicile country.
	 *
	 * @param domicileCountry the new domicile country
	 */
	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	/**
	 * Gets the legal form.
	 *
	 * @return the legal form
	 */
	public String getLegalForm() {
		return legalForm;
	}

	/**
	 * Sets the legal form.
	 *
	 * @param legalForm the new legal form
	 */
	public void setLegalForm(String legalForm) {
		this.legalForm = legalForm;
	}

	/**
	 * Gets the incorporation country.
	 *
	 * @return the incorporation country
	 */
	public String getIncorporationCountry() {
		return incorporationCountry;
	}

	/**
	 * Sets the incorporation country.
	 *
	 * @param incorporationCountry the new incorporation country
	 */
	public void setIncorporationCountry(String incorporationCountry) {
		this.incorporationCountry = incorporationCountry;
	}

	/**
	 * Gets the incorporation state.
	 *
	 * @return the incorporation state
	 */
	public String getIncorporationState() {
		return incorporationState;
	}

	/**
	 * Sets the incorporation state.
	 *
	 * @param incorporationState the new incorporation state
	 */
	public void setIncorporationState(String incorporationState) {
		this.incorporationState = incorporationState;
	}

	/**
	 * Checks if is privacy waiver.
	 *
	 * @return true, if is privacy waiver
	 */
	public boolean isPrivacyWaiver() {
		return privacyWaiver;
	}

	/**
	 * Sets the privacy waiver.
	 *
	 * @param privacyWaiver the new privacy waiver
	 */
	public void setPrivacyWaiver(boolean privacyWaiver) {
		this.privacyWaiver = privacyWaiver;
	}

	/**
	 * Gets the addresses.
	 *
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}

	/**
	 * Sets the addresses.
	 *
	 * @param addresses the new addresses
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	/**
	 * Gets the phones.
	 *
	 * @return the phones
	 */
	public List<Phone> getPhones() {
		return phones;
	}

	/**
	 * Sets the phones.
	 *
	 * @param phones the new phones
	 */
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	/**
	 * Gets the emails.
	 *
	 * @return the emails
	 */
	public List<Email> getEmails() {
		return emails;
	}

	/**
	 * Sets the emails.
	 *
	 * @param emails the new emails
	 */
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	/**
	 * Gets the urls.
	 *
	 * @return the urls
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * Sets the urls.
	 *
	 * @param urls the new urls
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * Gets the preferred correspondence kind.
	 *
	 * @return the preferred correspondence kind
	 */
	public String getPreferredCorrespondenceKind() {
		return preferredCorrespondenceKind;
	}

	/**
	 * Sets the preferred correspondence kind.
	 *
	 * @param preferredCorrespondenceKind the new preferred correspondence kind
	 */
	public void setPreferredCorrespondenceKind(
			String preferredCorrespondenceKind) {
		this.preferredCorrespondenceKind = preferredCorrespondenceKind;
	}

}