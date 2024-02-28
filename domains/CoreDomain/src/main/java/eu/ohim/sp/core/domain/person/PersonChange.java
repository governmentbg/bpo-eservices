/*
 *  CoreDomain:: Representative 10/10/13 14:34 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import java.io.Serializable;

/**
 * The Class PersonChange.
 */
public class PersonChange implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** The kind of PersonChange. */
	private PersonChangeKind personChangeKind;

	/** The current representative. */
	private Representative currentPerson;

    /** The updated representative. */
    private Representative updatedPerson;

    public PersonChangeKind getPersonChangeKind() {
        return personChangeKind;
    }

    public void setPersonChangeKind(PersonChangeKind personChangeKind) {
        this.personChangeKind = personChangeKind;
    }

    public Representative getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Representative currentPerson) {
        this.currentPerson = currentPerson;
    }

    public Representative getUpdatedPerson() {
        return updatedPerson;
    }

    public void setUpdatedPerson(Representative updatedPerson) {
        this.updatedPerson = updatedPerson;
    }

    // Methods for XML dozer mappings
    public Representative getCurrentRepresentative() {
        if (isRepresentative()) {
            return currentPerson;
        }
        return null;
    }
    public Representative getUpdatedRepresentative() {
        if (isRepresentative()) {
            return updatedPerson;
        }
        return null;
    }
    public Representative getCurrentCorrespondent() {
        if (!isRepresentative()) {
            return currentPerson;
        }
        return null;
    }
    public Representative getUpdatedCorrespondent() {
        if (!isRepresentative()) {
            return updatedPerson;
        }
        return null;
    }

    public void setCurrentRepresentative(Representative currentRepresentative) {
        setCurrentPerson(currentRepresentative);
    }

    public void setUpdatedRepresentative(Representative updatedRepresentative) {
        setUpdatedPerson(updatedRepresentative);
    }

    public void setCurrentCorrespondent(Representative currentCorrespondent) {
        setCurrentPerson(currentCorrespondent);
    }

    public void setUpdatedCorrespondent(Representative updatedCorrespondent) {
        setUpdatedPerson(updatedCorrespondent);
    }

    private boolean isRepresentative() {
        if (PersonChangeKind.ADD_NEW_REPRESENTATIVE.equals(personChangeKind) ||
                PersonChangeKind.REPLACE_REPRESENTATIVE.equals(personChangeKind) ||
                PersonChangeKind.REMOVE_REPRESENTATIVE.equals(personChangeKind) ||
                PersonChangeKind.CHANGE_REPRESENTATIVE_ADDRESS.equals(personChangeKind) ||
                PersonChangeKind.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS.equals(personChangeKind)) {
            return true;
        }
        return false;
    }
}
