package eu.ohim.sp.core.domain.opposition;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

public abstract class OppositionGround extends Id implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private LegalActVersion legalActVersion;
	private String explanationText;
	private List <AttachedDocument> explanationsDocuments;
	private List <AttachedDocument> proposalDecideDocuments;
	private List <AttachedDocument> evidenceDocuments;
	private List <AttachedDocument> globalDocuments;
	private GroundCategoryKind groundCategory;
	/**
	 * @return the explanationText
	 */
	public String getExplanationText() {
		return explanationText;
	}
	/**
	 * @param explanationText the explanationText to set
	 */
	public void setExplanationText(String explanationText) {
		this.explanationText = explanationText;
	}
	/**
	 * @return the legalActVersion
	 */
	public LegalActVersion getLegalActVersion() {
		return legalActVersion;
	}
	/**
	 * @param legalActVersion the legalActVersion to set
	 */
	public void setLegalActVersion(LegalActVersion legalActVersion) {
		this.legalActVersion = legalActVersion;
	}
	/**
	 * @return the explanationsDocuments
	 */
	public List<AttachedDocument> getExplanationsDocuments() {
		return explanationsDocuments;
	}
	/**
	 * @param explanationsDocuments the explanationsDocuments to set
	 */
	public void setExplanationsDocuments(
			List<AttachedDocument> explanationsDocuments) {
		this.explanationsDocuments = explanationsDocuments;
	}
	/**
	 * @return the proposalDecideDocuments
	 */
	public List<AttachedDocument> getProposalDecideDocuments() {
		return proposalDecideDocuments;
	}
	/**
	 * @param proposalDecideDocuments the proposalDecideDocuments to set
	 */
	public void setProposalDecideDocuments(
			List<AttachedDocument> proposalDecideDocuments) {
		this.proposalDecideDocuments = proposalDecideDocuments;
	}
	/**
	 * @return the evidenceDocuments
	 */
	public List<AttachedDocument> getEvidenceDocuments() {
		return evidenceDocuments;
	}
	/**
	 * @param evidenceDocuments the evidenceDocuments to set
	 */
	public void setEvidenceDocuments(List<AttachedDocument> evidenceDocuments) {
		this.evidenceDocuments = evidenceDocuments;
	}
	/**
	 * @return the globalDocuments
	 */
	public List<AttachedDocument> getGlobalDocuments() {
		return globalDocuments;
	}
	/**
	 * @param globalDocuments the globalDocuments to set
	 */
	public void setGlobalDocuments(List<AttachedDocument> globalDocuments) {
		this.globalDocuments = globalDocuments;
	}
	/**
	 * @return the groundCategory
	 */
	public GroundCategoryKind getGroundCategory() {
		return groundCategory;
	}
	/**
	 * @param groundCategory the groundCategory to set
	 */
	public void setGroundCategory(GroundCategoryKind groundCategory) {
		this.groundCategory = groundCategory;
	}
	
	
	
	
}
