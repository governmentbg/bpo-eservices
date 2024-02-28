/*
 *  CoreDomain:: ExhibitionPriority 09/08/13 16:12 karalch $
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
package eu.ohim.sp.core.domain.claim;

import eu.ohim.sp.common.util.DateUtil;

import java.util.Date;

/**
 * The Class ExhibitionPriority.
 *
 * @author ionitdi
 */
public class ExhibitionPriority extends Claim {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1953549262469246457L;

	/** The exhibition. */
	private Exhibition exhibition;
	
	/** The date. */
	private Date date;
	
	/** The first display date. */
	@Deprecated
	private Date firstDisplayDate;
	
	/**
	 * Gets the exhibition.
	 *
	 * @return the exhibition
	 */
	public Exhibition getExhibition() {
		return exhibition;
	}
	
	/**
	 * Sets the exhibition.
	 *
	 * @param exhibition the new exhibition
	 */
	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
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
	 * Gets the first display date.
	 *
	 * @return the first display date
	 */
	public Date getFirstDisplayDate() {
		return DateUtil.cloneDate(firstDisplayDate);
	}
	
	/**
	 * Sets the first display date.
	 *
	 * @param firstDisplayDate the new first display date
	 */
	public void setFirstDisplayDate(Date firstDisplayDate) {
		this.firstDisplayDate = DateUtil.cloneDate(firstDisplayDate);
	}
	
	
}
