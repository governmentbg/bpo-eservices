/*******************************************************************************
 * * $Id:: TransformationPriority.java 53083 2012-12-14 08:59:24Z virgida        $
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
 * The Class TransformationPriority.
 *
 * @author ionitdi
 */
public class TransformationPriority extends Claim {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4257044108098417706L;

	/** The registration number. */
	private String registrationNumber;
	
	/** The registration date. */
	private Date registrationDate;
	
	/** The cancellation date. */
	private Date cancellationDate;
	
	/** The priority date. */
	private Date priorityDate;

	/** The country code */
	private String transformationCountryCode;
	
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
	 * @param registrationNumber the new registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = DateUtil.cloneDate(registrationDate);
	}
	
	/**
	 * Gets the cancellation date.
	 *
	 * @return the cancellation date
	 */
	public Date getCancellationDate() {
		return DateUtil.cloneDate(cancellationDate);
	}
	
	/**
	 * Sets the cancellation date.
	 *
	 * @param cancellationDate the new cancellation date
	 */
	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = DateUtil.cloneDate(cancellationDate);
	}
	
	/**
	 * Gets the priority date.
	 *
	 * @return the priority date
	 */
	public Date getPriorityDate() {
		return DateUtil.cloneDate(priorityDate);
	}
	
	/**
	 * Sets the priority date.
	 *
	 * @param priorityDate the new priority date
	 */
	public void setPriorityDate(Date priorityDate) {
		this.priorityDate = DateUtil.cloneDate(priorityDate);
	}

    /**
     * Gets the priority date.
     *
     * @return the country code
     */
    public String getTransformationCountryCode() {
        return transformationCountryCode;
    }

    /**
     * Sets the country code.
     *
     * @param transformationCountryCode the new country code
     */
    public void setTransformationCountryCode(String transformationCountryCode) {
        this.transformationCountryCode = transformationCountryCode;
    }
}
