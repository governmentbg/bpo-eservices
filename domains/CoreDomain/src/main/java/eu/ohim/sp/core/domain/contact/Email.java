/*
 *  CoreDomain:: Email 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.contact;

import java.io.Serializable;

import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The Class Email.
 */
public class Email extends Id implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The kind. */
	private String kind;
	
	/** The email address. */
	private String emailAddress;

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind the new kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "Email [kind=" + kind + ", emailAddress=" + emailAddress + "]";
	}

}
