/*
 *  CoreDomain:: PersonRoleRelationship 28/08/13 15:27 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * The Class PersonRoleRelationship.
 */
public class PersonRoleRelationship extends Id implements Serializable {

	private static final long serialVersionUID = 2358398440240980997L;

	/** The person role relation kind. */
	private PersonRoleRelationKind personRoleRelationKind;

	/** The person role. */
	//TODO to check if it creates problem the cyclic reference
	private PersonRole personRole;

	/** The documents. */
	private List<AttachedDocument> documents;

	/**
	 * Gets the person role relation kind.
	 * 
	 * @return the person role relation kind
	 */
	public PersonRoleRelationKind getPersonRoleRelationKind() {
		return personRoleRelationKind;
	}

	/**
	 * Sets the person role relation kind.
	 * 
	 * @param personRoleRelationKind
	 *            the new person role relation kind
	 */
	public void setPersonRoleRelationKind(
			PersonRoleRelationKind personRoleRelationKind) {
		this.personRoleRelationKind = personRoleRelationKind;
	}

	/**
	 * Gets the person role.
	 * 
	 * @return the person role
	 */
	public PersonRole getPersonRole() {
		return personRole;
	}

	/**
	 * Sets the person role.
	 * 
	 * @param personRole
	 *            the new person role
	 */
	public void setPersonRole(PersonRole personRole) {
		this.personRole = personRole;
	}

	/**
	 * Gets the documents.
	 * 
	 * @return the documents
	 */
	public List<AttachedDocument> getDocuments() {
		return documents;
	}

	/**
	 * Sets the documents.
	 * 
	 * @param documents
	 *            the new documents
	 */
	public void setDocuments(List<AttachedDocument> documents) {
		this.documents = documents;
	}

}
