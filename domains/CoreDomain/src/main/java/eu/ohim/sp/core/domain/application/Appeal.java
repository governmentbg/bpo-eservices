package eu.ohim.sp.core.domain.application;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.GSHelperDetails;

public class Appeal extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String decisionNumber;
	private Date decisionDate;
	private AppealKind appealKind;
	private List <AttachedDocument> explanationsDocuments;
	private String explanationText;
	private Date oppositionFilingDate;
	private GSHelperDetails gsHelper;
	
	public String getDecisionNumber() {
		return decisionNumber;
	}
	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}
	public Date getDecisionDate() {
		return decisionDate;
	}
	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}
	public AppealKind getAppealKind() {
		return appealKind;
	}
	public void setAppealKind(AppealKind appealKind) {
		this.appealKind = appealKind;
	}
	public List<AttachedDocument> getExplanationsDocuments() {
		return explanationsDocuments;
	}
	public void setExplanationsDocuments(
			List<AttachedDocument> explanationsDocuments) {
		this.explanationsDocuments = explanationsDocuments;
	}
	public String getExplanationText() {
		return explanationText;
	}
	public void setExplanationText(String explanationText) {
		this.explanationText = explanationText;
	}
	public Date getOppositionFilingDate() {
		return oppositionFilingDate;
	}
	public void setOppositionFilingDate(Date oppositionFilingDate) {
		this.oppositionFilingDate = oppositionFilingDate;
	}

	public GSHelperDetails getGsHelper() {
		return gsHelper;
	}

	public void setGsHelper(GSHelperDetails gsHelper) {
		this.gsHelper = gsHelper;
	}
}
