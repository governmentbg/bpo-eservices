/*
 *  CoreDomain:: Entitlement 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.application;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

public class Entitlement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntitlementKind kind;
	
	/** The attached documents. */
	private List<AttachedDocument> attachedDocuments;
	
	private String description;
	
	private Date dateOfTransfer;

	public EntitlementKind getKind() {
		return kind;
	}

	public void setKind(EntitlementKind kind) {
		this.kind = kind;
	}

	public List<AttachedDocument> getAttachedDocuments() {
		return attachedDocuments;
	}

	public void setAttachedDocuments(List<AttachedDocument> attachedDocuments) {
		this.attachedDocuments = attachedDocuments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateOfTransfer() {
		return DateUtil.cloneDate(dateOfTransfer);
	}

	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = DateUtil.cloneDate(dateOfTransfer);
	}
	
	
}
