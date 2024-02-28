/*
 *  CoreDomain:: Payment 04/11/13 12:30 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
/**
 * 
 */
package eu.ohim.sp.core.domain.payment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * The Class Payment.
 *
 * @author ionitdi
 */
public class Payment extends Id implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5886658336543685591L;

	/** The kind. */
    private PaymentKind kind;
	
	/** The identifier. */
	private String identifier;
	
	/** The reference. */
	private String reference;
    
    /** The status. */
    private PaymentStatusCode status;

    /** The amount. */
    private Double amount;
    
    /** The currency code. */
    private String currencyCode;
    
    /** The date. */
    private Date date;
    
    /** The comment. */
    private String comment;

    /** The account. */
    private Account account;
    
    /** The card account. */
    private CardAccount cardAccount;
    
    /** The bank transfer. */
    private BankTransfer bankTransfer;
    
    /** The payer. */
    private PersonRole payer;
    
    /** The payer identifier. */
    private PersonRoleKind payerIdentifier;
    
    /** The attached documents. */
    private List<AttachedDocument> payLaterAttachedDocuments;
    
	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public PaymentKind getKind() {
		return kind;
	}
	
	/**
	 * Sets the kind.
	 *
	 * @param kind the new kind
	 */
	public void setKind(PaymentKind kind) {
		this.kind = kind;
	}
	
	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * Sets the reference.
	 *
	 * @param reference the new reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public PaymentStatusCode getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(PaymentStatusCode status) {
		this.status = status;
	}
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the new currency code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return DateUtil.cloneDate(date);
	}
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = DateUtil.cloneDate(date);
	}
	
	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	
	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
	/**
	 * Gets the card account.
	 *
	 * @return the card account
	 */
	public CardAccount getCardAccount() {
		return cardAccount;
	}
	
	/**
	 * Sets the card account.
	 *
	 * @param cardAccount the new card account
	 */
	public void setCardAccount(CardAccount cardAccount) {
		this.cardAccount = cardAccount;
	}
	
	/**
	 * Gets the bank transfer.
	 *
	 * @return the bank transfer
	 */
	public BankTransfer getBankTransfer() {
		return bankTransfer;
	}
	
	/**
	 * Sets the bank transfer.
	 *
	 * @param bankTransfer the new bank transfer
	 */
	public void setBankTransfer(BankTransfer bankTransfer) {
		this.bankTransfer = bankTransfer;
	}
	
	/**
	 * Gets the payer.
	 *
	 * @return the payer
	 */
	public PersonRole getPayer() {
		return payer;
	}
	
	/**
	 * Sets the payer.
	 *
	 * @param payer the new payer
	 */
	public void setPayer(PersonRole payer) {
		this.payer = payer;
	}
	
	/**
	 * Gets the payer identifier.
	 *
	 * @return the payer identifier
	 */
	public PersonRoleKind getPayerIdentifier() {
		return payerIdentifier;
	}
	
	/**
	 * Sets the payer identifier.
	 *
	 * @param payerIdentifier the new payer identifier
	 */
	public void setPayerIdentifier(PersonRoleKind payerIdentifier) {
		this.payerIdentifier = payerIdentifier;
	}

	/**
	 * @return the payLaterAttachedDocuments
	 */
	public List<AttachedDocument> getPayLaterAttachedDocuments() {
		return payLaterAttachedDocuments;
	}

	/**
	 * @param payLaterAttachedDocuments the payLaterAttachedDocuments to set
	 */
	public void setPayLaterAttachedDocuments(
			List<AttachedDocument> payLaterAttachedDocuments) {
		this.payLaterAttachedDocuments = payLaterAttachedDocuments;
	}
    
    
}
