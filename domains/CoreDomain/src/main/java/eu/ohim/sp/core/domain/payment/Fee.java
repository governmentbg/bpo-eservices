/*
 *  CoreDomain:: Fee 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.payment;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class Fee.
 */
public class Fee implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The fee type. */
    private FeeType feeType;
    
    /** The amount. */
    private Double amount;
    
    /** The quantity. */
    private Integer quantity;
    
    /** The unit amount. */
    private Double unitAmount;
    
    /** The legal date. */
    private Date legalDate;
    
    /** The status. */
    private String status;
    
    /** The status date. */
    private Date statusDate;

	private String expirationExtentYearsFromEntitlement;
	private Date expirationExtentNewDate;

	/**
	 * Gets fee type.
	 *
	 * @return the fee type
	 */
	public FeeType getFeeType() {
		return feeType;
	}

	/**
	 * Sets fee type.
	 *
	 * @param feeType the fee type
	 */
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}

	/**
	 * Gets amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets amount.
	 *
	 * @param amount the amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets quantity.
	 *
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets quantity.
	 *
	 * @param quantity the quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets legal date.
	 *
	 * @return the legal date
	 */
	public Date getLegalDate() {
		return DateUtil.cloneDate(legalDate);
	}

	/**
	 * Sets legal date.
	 *
	 * @param legalDate the legal date
	 */
	public void setLegalDate(Date legalDate) {
		this.legalDate = DateUtil.cloneDate(legalDate);
	}

	/**
	 * Gets unit amount.
	 *
	 * @return the unit amount
	 */
	public Double getUnitAmount() {
		return unitAmount;
	}

	/**
	 * Sets unit amount.
	 *
	 * @param unitAmount the unit amount
	 */
	public void setUnitAmount(Double unitAmount) {
		this.unitAmount = unitAmount;
	}

	/**
	 * Gets status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets status.
	 *
	 * @param status the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets status date.
	 *
	 * @return the status date
	 */
	public Date getStatusDate() {
		return DateUtil.cloneDate(statusDate);
	}

	/**
	 * Sets status date.
	 *
	 * @param statusDate the status date
	 */
	public void setStatusDate(Date statusDate) {
		this.statusDate = DateUtil.cloneDate(statusDate);
	}

	public String getExpirationExtentYearsFromEntitlement() {
		return expirationExtentYearsFromEntitlement;
	}

	public void setExpirationExtentYearsFromEntitlement(String expirationExtentYearsFromEntitlement) {
		this.expirationExtentYearsFromEntitlement = expirationExtentYearsFromEntitlement;
	}

	public Date getExpirationExtentNewDate() {
		return expirationExtentNewDate;
	}

	public void setExpirationExtentNewDate(Date expirationExtentNewDate) {
		this.expirationExtentNewDate = expirationExtentNewDate;
	}
}
