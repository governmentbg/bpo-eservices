/*
 *  CoreDomain:: Priority 04/10/13 20:46 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.claim;

import eu.ohim.sp.common.util.DateUtil;

import java.util.Date;

/**
 * The Class Priority.
 * 
 * @author ionitdi
 */
@Deprecated
public class Priority extends PartialClaim implements IPPriority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891033100988557164L;

	/** The filing office. */
	private String filingOffice;

	/** The filing number. */
	private String filingNumber;

	/** The filing date. */
	private Date filingDate;

	/** The certicate attached indicator. */
	private boolean certicateAttachedIndicator;

	/** The translation attached indicator. */
	private boolean translationAttachedIndicator;

	/**
	 * Gets the filing office.
	 * 
	 * @return the filing office
	 */
	public String getFilingOffice() {
		return filingOffice;
	}

	/**
	 * Sets the filing office.
	 * 
	 * @param filingOffice
	 *            the new filing office
	 */
	public void setFilingOffice(String filingOffice) {
		this.filingOffice = filingOffice;
	}

	/**
	 * Gets the filing number.
	 * 
	 * @return the filing number
	 */
	public String getFilingNumber() {
		return filingNumber;
	}

	/**
	 * Sets the filing number.
	 * 
	 * @param filingNumber
	 *            the new filing number
	 */
	public void setFilingNumber(String filingNumber) {
		this.filingNumber = filingNumber;
	}

	/**
	 * Gets the filing date.
	 * 
	 * @return the filing date
	 */
	public Date getFilingDate() {
		return DateUtil.cloneDate(filingDate);
	}

	/**
	 * Sets the filing date.
	 * 
	 * @param filingDate
	 *            the new filing date
	 */
	public void setFilingDate(Date filingDate) {
		this.filingDate = DateUtil.cloneDate(filingDate);
	}

	/**
	 * Checks if is certicate attached indicator.
	 * 
	 * @return true, if is certicate attached indicator
	 */
	public boolean isCerticateAttachedIndicator() {
		return certicateAttachedIndicator;
	}

	/**
	 * Sets the certicate attached indicator.
	 * 
	 * @param certicateAttachedIndicator
	 *            the new certicate attached indicator
	 */
	public void setCerticateAttachedIndicator(boolean certicateAttachedIndicator) {
		this.certicateAttachedIndicator = certicateAttachedIndicator;
	}

	/**
	 * Checks if is translation attached indicator.
	 * 
	 * @return true, if is translation attached indicator
	 */
	public boolean isTranslationAttachedIndicator() {
		return translationAttachedIndicator;
	}

	/**
	 * Sets the translation attached indicator.
	 * 
	 * @param translationAttachedIndicator
	 *            the new translation attached indicator
	 */
	public void setTranslationAttachedIndicator(
			boolean translationAttachedIndicator) {
		this.translationAttachedIndicator = translationAttachedIndicator;
	}

}
