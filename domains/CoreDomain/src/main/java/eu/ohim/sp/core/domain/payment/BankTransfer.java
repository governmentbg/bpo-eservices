/*******************************************************************************
 * * $Id:: BankTransfer.java 53083 2012-12-14 08:59:24Z virgida                  $
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
package eu.ohim.sp.core.domain.payment;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ionitdi
 * 
 */
public class BankTransfer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -228415368860317614L;
	private String bankTransferIdentifier;
	private Date bankTransferDate;
	private String originBankName;
	private String bankDestinationAccount;

	/**
	 * @return the bankTransferIdentifier
	 */
	public String getBankTransferIdentifier() {
		return bankTransferIdentifier;
	}

	/**
	 * @param bankTransferIdentifier
	 *            the bankTransferIdentifier to set
	 */
	public void setBankTransferIdentifier(String bankTransferIdentifier) {
		this.bankTransferIdentifier = bankTransferIdentifier;
	}

	/**
	 * @return the bankTransferDate
	 */
	public Date getBankTransferDate() {
		return DateUtil.cloneDate(bankTransferDate);
	}

	/**
	 * @param bankTransferDate
	 *            the bankTransferDate to set
	 */
	public void setBankTransferDate(Date bankTransferDate) {
		this.bankTransferDate = DateUtil.cloneDate(bankTransferDate);
	}

	/**
	 * @return the originBankName
	 */
	public String getOriginBankName() {
		return originBankName;
	}

	/**
	 * @param originBankName
	 *            the originBankName to set
	 */
	public void setOriginBankName(String originBankName) {
		this.originBankName = originBankName;
	}

	/**
	 * @return the bankDestinationAccount
	 */
	public String getBankDestinationAccount() {
		return bankDestinationAccount;
	}

	/**
	 * @param bankDestinationAccount
	 *            the bankDestinationAccount to set
	 */
	public void setBankDestinationAccount(String bankDestinationAccount) {
		this.bankDestinationAccount = bankDestinationAccount;
	}
}
