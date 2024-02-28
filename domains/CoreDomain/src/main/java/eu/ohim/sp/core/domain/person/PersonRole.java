/*
 *  CoreDomain:: PersonRole 27/08/13 15:27 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import java.util.List;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;

/**
 * The Class PersonRole.
 */
public class PersonRole extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4353636587316941212L;

	private int personRoleId;

	/** The correspondence addresses. */
	private List<Address> correspondenceAddresses;

	/** The phone. */
	private List<Phone> correspondencePhones;

	/** The email. */
	private List<Email> correspondenceEmails;

	/** The person role relationships. */
	private List<PersonRoleRelationship> personRoleRelationships;

	/** The order. */
	private Integer order;

    private String reference;

	/**
	 * Gets the correspondence phones.
	 * 
	 * @return the correspondence phones
	 */
	public List<Phone> getCorrespondencePhones() {
		return correspondencePhones;
	}

	/**
	 * Sets the correspondence phones.
	 * 
	 * @param correspondencePhones
	 *            the new correspondence phones
	 */
	public void setCorrespondencePhones(List<Phone> correspondencePhones) {
		this.correspondencePhones = correspondencePhones;
	}

	/**
	 * Gets the correspondence emails.
	 * 
	 * @return the correspondence emails
	 */
	public List<Email> getCorrespondenceEmails() {
		return correspondenceEmails;
	}

	/**
	 * Sets the correspondence emails.
	 * 
	 * @param correspondenceEmails
	 *            the new correspondence emails
	 */
	public void setCorrespondenceEmails(List<Email> correspondenceEmails) {
		this.correspondenceEmails = correspondenceEmails;
	}

	/**
	 * Gets the order.
	 * 
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order
	 *            the new order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * Gets the person role relationships.
	 *
	 * @return the person role relationships
	 */
	public List<PersonRoleRelationship> getPersonRoleRelationships() {
		return personRoleRelationships;
	}

	/**
	 * Sets the person role relationships.
	 *
	 * @param personRoleRelationships the new person role relationships
	 */
	public void setPersonRoleRelationships(
			List<PersonRoleRelationship> personRoleRelationships) {
		this.personRoleRelationships = personRoleRelationships;
	}

	/**
	 * Gets the correspondence addresses.
	 * 
	 * @return the correspondence addresses
	 */
	public List<Address> getCorrespondenceAddresses() {
		return correspondenceAddresses;
	}

	/**
	 * Sets the correspondence addresses.
	 * 
	 * @param correspondenceAddresses
	 *            the new correspondence addresses
	 */
	public void setCorrespondenceAddresses(List<Address> correspondenceAddresses) {
		this.correspondenceAddresses = correspondenceAddresses;
	}

	public int getPersonRoleId() {
		return personRoleId;
	}

	public void setPersonRoleId(int personRoleId) {
		this.personRoleId = personRoleId;
	}

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
