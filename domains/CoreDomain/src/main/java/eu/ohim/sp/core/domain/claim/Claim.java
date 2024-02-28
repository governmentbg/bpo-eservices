package eu.ohim.sp.core.domain.claim;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * The Class Claim.
 */
public class Claim extends Id implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7299621663240079538L;

	/** The attached documents. */
	private List<AttachedDocument> attachedDocuments;

	/** The status. */
	private String status;
	
	/** The status date. */
	private Date statusDate;
	
	/**
	 * Gets the attached documents.
	 *
	 * @return the attached documents
	 */
	public List<AttachedDocument> getAttachedDocuments() {
		return attachedDocuments;
	}
	
	/**
	 * Sets the documents.
	 *
	 * @param attachedDocuments the new documents
	 */
	public void setAttachedDocuments(List<AttachedDocument> attachedDocuments) {
		this.attachedDocuments = attachedDocuments;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the status date.
	 *
	 * @return the status date
	 */
	public Date getStatusDate() {
		return DateUtil.cloneDate(statusDate);
	}
	
	/**
	 * Sets the status date.
	 *
	 * @param statusDate the new status date
	 */
	public void setStatusDate(Date statusDate) {
		this.statusDate = DateUtil.cloneDate(statusDate);
	}
	
}
