/*******************************************************************************
 * * $Id:: Seniority.java 118543 2013-05-20 11:29:37Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
/**
 * 
 */
package eu.ohim.sp.core.domain.claim;

import eu.ohim.sp.common.util.DateUtil;

import java.util.Date;


/**
 * The Class Seniority.
 * 
 * @author ionitdi
 */
public class Seniority extends PartialClaim {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4981470152440649887L;

	/** The office. */
	private String office;

	/** The kind. */
	private SeniorityKind kind;

	/** The filing date. */
	private Date filingDate;

	/** The filing number. */
	private String filingNumber;

	/** The registration date. */
	private Date registrationDate;

	/** The registration number. */
	private String registrationNumber;

	/** The international trade mark code. */
	private InternationalTradeMarkCode internationalTradeMarkCode;

	/** The certicate attached indicator. */
	private boolean certicateAttachedIndicator;

	/**
	 * Gets the office.
	 * 
	 * @return the office
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * Sets the office.
	 * 
	 * @param office
	 *            the new office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * Gets the kind.
	 * 
	 * @return the kind
	 */
	public SeniorityKind getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 * 
	 * @param kind
	 *            the new kind
	 */
	public void setKind(SeniorityKind kind) {
		this.kind = kind;
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
	 * Gets the registration date.
	 * 
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Sets the registration date.
	 * 
	 * @param registrationDate
	 *            the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = DateUtil.cloneDate(registrationDate);
	}

	/**
	 * Gets the registration number.
	 * 
	 * @return the registration number
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Sets the registration number.
	 * 
	 * @param registrationNumber
	 *            the new registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Gets the international trade mark code.
	 * 
	 * @return the international trade mark code
	 */
	public InternationalTradeMarkCode getInternationalTradeMarkCode() {
		return internationalTradeMarkCode;
	}

	/**
	 * Sets the international trade mark code.
	 * 
	 * @param internationalTradeMarkCode
	 *            the new international trade mark code
	 */
	public void setInternationalTradeMarkCode(
			InternationalTradeMarkCode internationalTradeMarkCode) {
		this.internationalTradeMarkCode = internationalTradeMarkCode;
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

}
