/*******************************************************************************
 * * $Id:: Account.java 121450 2013-06-05 14:22:44Z jaraful                      $
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

import java.io.Serializable;

import eu.ohim.sp.core.domain.common.Text;

/**
 * @author ionitdi
 *
 */
public class Account implements Serializable
{
	private static final long serialVersionUID = 5103077584685229626L;
	
	private String accountIdentifier;
	private String accountKind;
	private Text accountHolderName;
	private AccountDebitKind accountDebitKind;
	
	/**
	 * @return the accountIdentifier
	 */
	public String getAccountIdentifier()
	{
		return accountIdentifier;
	}
	
	/**
	 * @param accountIdentifier the accountIdentifier to set
	 */
	public void setAccountIdentifier(String accountIdentifier)
	{
		this.accountIdentifier = accountIdentifier;
	}
	
	/**
	 * @return the accountKind
	 */
	public String getAccountKind()
	{
		return accountKind;
	}
	
	/**
	 * @param accountKind the accountKind to set
	 */
	public void setAccountKind(String accountKind)
	{
		this.accountKind = accountKind;
	}
	
	/**
	 * @return the accountHolderName
	 */
	public Text getAccountHolderName()
	{
		return accountHolderName;
	}
	
	/**
	 * @param accountHolderName the accountHolderName to set
	 */
	public void setAccountHolderName(Text accountHolderName)
	{
		this.accountHolderName = accountHolderName;
	}
	
	/**
	 * @return the accountDebitKind
	 */
	public AccountDebitKind getAccountDebitKind()
	{
		return accountDebitKind;
	}
	
	/**
	 * @param accountDebitKind the accountDebitKind to set
	 */
	public void setAccountDebitKind(AccountDebitKind accountDebitKind)
	{
		this.accountDebitKind = accountDebitKind;
	}
}
