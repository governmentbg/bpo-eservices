/*******************************************************************************
 * * $Id:: CardAccount.java 124359 2013-06-19 17:43:34Z karalch                  $
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
import eu.ohim.sp.core.domain.common.Text;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ionitdi
 *
 */
public class CardAccount implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2014615495208211050L;
	
	private String cardPrimaryAccountNumber;
	private String cardNetworkIdentifier;
	private CardKindCode cardKindCode;
	private String cardCustomerIdentifier;
	private Date cardValidityStartDate;
	private Date cardExpiryDate;
	private String cardIssuesIdentifier;
	private String cardIssueNumber;
	private String cardCV2Identifier;
	private String chipCodeType;
	private String cardChipApplicationIdentifier;
	private Text cardHolderName;
	
	/**
	 * @return the cardPrimaryAccountNumber
	 */
	public String getCardPrimaryAccountNumber()
	{
		return cardPrimaryAccountNumber;
	}
	
	/**
	 * @param cardPrimaryAccountNumber the cardPrimaryAccountNumber to set
	 */
	public void setCardPrimaryAccountNumber(String cardPrimaryAccountNumber)
	{
		this.cardPrimaryAccountNumber = cardPrimaryAccountNumber;
	}
	
	/**
	 * @return the cardNetworkIdentifier
	 */
	public String getCardNetworkIdentifier()
	{
		return cardNetworkIdentifier;
	}
	
	/**
	 * @param cardNetworkIdentifier the cardNetworkIdentifier to set
	 */
	public void setCardNetworkIdentifier(String cardNetworkIdentifier)
	{
		this.cardNetworkIdentifier = cardNetworkIdentifier;
	}
	
	/**
	 * @return the cardKindCode
	 */
	public CardKindCode getCardKindCode()
	{
		return cardKindCode;
	}
	
	/**
	 * @param cardKindCode the cardKindCode to set
	 */
	public void setCardKindCode(CardKindCode cardKindCode)
	{
		this.cardKindCode = cardKindCode;
	}
	
	/**
	 * @return the cardCustomerIdentifier
	 */
	public String getCardCustomerIdentifier()
	{
		return cardCustomerIdentifier;
	}
	
	/**
	 * @param cardCustomerIdentifier the cardCustomerIdentifier to set
	 */
	public void setCardCustomerIdentifier(String cardCustomerIdentifier)
	{
		this.cardCustomerIdentifier = cardCustomerIdentifier;
	}
	
	/**
	 * @return the cardValidityStartDate
	 */
	public Date getCardValidityStartDate()
	{
		return DateUtil.cloneDate(cardValidityStartDate);
	}
	
	/**
	 * @param cardValidityStartDate the cardValidityStartDate to set
	 */
	public void setCardValidityStartDate(Date cardValidityStartDate)
	{
		this.cardValidityStartDate = DateUtil.cloneDate(cardValidityStartDate);
	}
	
	/**
	 * @return the cardExpiryDate
	 */
	public Date getCardExpiryDate()
	{
		return DateUtil.cloneDate(cardExpiryDate);
	}
	
	/**
	 * @param cardExpiryDate the cardExpiryDate to set
	 */
	public void setCardExpiryDate(Date cardExpiryDate)
	{
		this.cardExpiryDate = DateUtil.cloneDate(cardExpiryDate);
	}
	
	/**
	 * @return the cardIssuesIdentifier
	 */
	public String getCardIssuesIdentifier()
	{
		return cardIssuesIdentifier;
	}
	
	/**
	 * @param cardIssuesIdentifier the cardIssuesIdentifier to set
	 */
	public void setCardIssuesIdentifier(String cardIssuesIdentifier)
	{
		this.cardIssuesIdentifier = cardIssuesIdentifier;
	}
	
	/**
	 * @return the cardIssueNumber
	 */
	public String getCardIssueNumber()
	{
		return cardIssueNumber;
	}
	
	/**
	 * @param cardIssueNumber the cardIssueNumber to set
	 */
	public void setCardIssueNumber(String cardIssueNumber)
	{
		this.cardIssueNumber = cardIssueNumber;
	}
	
	/**
	 * @return the cardCV2Identifier
	 */
	public String getCardCV2Identifier()
	{
		return cardCV2Identifier;
	}
	
	/**
	 * @param cardCV2Identifier the cardCV2Identifier to set
	 */
	public void setCardCV2Identifier(String cardCV2Identifier)
	{
		this.cardCV2Identifier = cardCV2Identifier;
	}
	
	/**
	 * @return the chipCodeType
	 */
	public String getChipCodeType()
	{
		return chipCodeType;
	}
	
	/**
	 * @param chipCodeType the chipCodeType to set
	 */
	public void setChipCodeType(String chipCodeType)
	{
		this.chipCodeType = chipCodeType;
	}
	
	/**
	 * @return the cardChipApplicationIdentifier
	 */
	public String getCardChipApplicationIdentifier()
	{
		return cardChipApplicationIdentifier;
	}
	
	/**
	 * @param cardChipApplicationIdentifier the cardChipApplicationIdentifier to set
	 */
	public void setCardChipApplicationIdentifier(String cardChipApplicationIdentifier)
	{
		this.cardChipApplicationIdentifier = cardChipApplicationIdentifier;
	}
	
	/**
	 * @return the cardHolderName
	 */
	public Text getCardHolderName()
	{
		return cardHolderName;
	}
	
	/**
	 * @param cardHolderName the cardHolderName to set
	 */
	public void setCardHolderName(Text cardHolderName)
	{
		this.cardHolderName = cardHolderName;
	}
}
