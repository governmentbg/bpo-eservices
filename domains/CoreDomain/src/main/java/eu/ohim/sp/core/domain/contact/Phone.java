/*
 *  CoreDomain:: Phone 04/10/13 17:56 KARALCH $
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
 * The Class Phone.
 */
public class Phone extends Id implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -149509012549875968L;

	/** The phone kind. */
	private PhoneKind phoneKind;

	/** The international prefix. */
	private String internationalPrefix;

	/** The number. */
	private String number;

	/**
	 * Gets the phone kind.
	 * 
	 * @return the phone kind
	 */
	public PhoneKind getPhoneKind() {
		return phoneKind;
	}

	/**
	 * Sets the phone kind.
	 * 
	 * @param phoneKind
	 *            the new phone kind
	 */
	public void setPhoneKind(PhoneKind phoneKind) {
		this.phoneKind = phoneKind;
	}

	/**
	 * Gets the international prefix.
	 * 
	 * @return the international prefix
	 */
	public String getInternationalPrefix() {
		return internationalPrefix;
	}

	/**
	 * Sets the international prefix.
	 * 
	 * @param internationalPrefix
	 *            the new international prefix
	 */
	public void setInternationalPrefix(String internationalPrefix) {
		this.internationalPrefix = internationalPrefix;
	}

	/**
	 * Gets the number.
	 * 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 * 
	 * @param number
	 *            the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "Phone [phoneKind=" + phoneKind + ", internationalPrefix="
				+ internationalPrefix + ", number=" + number + "]";
	}

}
