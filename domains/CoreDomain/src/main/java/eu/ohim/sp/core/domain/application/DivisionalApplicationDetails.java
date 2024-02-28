/*
 *  CoreDomain:: DivisionalApplicationDetails 19/08/13 10:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.application;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.common.Text;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author soriama
 *
 */
public class DivisionalApplicationDetails implements Serializable {

	private static final long serialVersionUID = 3905849580722726078L;
	
	private String initialApplicationNumber;
	private Date initialApplicationDate;
	private Boolean initialApplicationImported;
	private Text comment;
	private boolean imported;
	
	/**
	 * @return the initialApplicationNumber
	 */
	public String getInitialApplicationNumber() {
		return initialApplicationNumber;
	}
	/**
	 * @param initialApplicationNumber the initialApplicationNumber to set
	 */
	public void setInitialApplicationNumber(String initialApplicationNumber) {
		this.initialApplicationNumber = initialApplicationNumber;
	}
	/**
	 * @return the initialApplicationDate
	 */
	public Date getInitialApplicationDate() {
		return DateUtil.cloneDate(initialApplicationDate);
	}
	/**
	 * @param initialApplicationDate the initialApplicationDate to set
	 */
	public void setInitialApplicationDate(Date initialApplicationDate) {
		this.initialApplicationDate = DateUtil.cloneDate(initialApplicationDate);
	}
	/**
	 * @return the comment
	 */
	public Text getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(Text comment) {
		this.comment = comment;
	}

	public Boolean getInitialApplicationImported() {
		return initialApplicationImported;
	}

	public void setInitialApplicationImported(Boolean initialApplicationImported) {
		this.initialApplicationImported = initialApplicationImported;
	}

	public boolean getImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}
}
