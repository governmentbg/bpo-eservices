/*
 *  CoreDomain:: OppositionRelativeGrounds 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.opposition;

import java.util.Date;
import java.util.List;

import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;

public class OppositionRelativeGrounds extends OppositionGround {

	private static final long serialVersionUID = 1L;

	private EarlierEntitlementRightKind earlierEntitlementRightType;
	private String category = "relative";
	private String earlierTradeMarkCategory;
	private LimitedTradeMark earlierTradeMarkDetails;
	private List<DesignApplication> earlierDesignDetails;
	private String typeRight;
	private String typeRightDetails;
	private String areaActivity;
	private String registrationCountry;
	
	private String opponentEntitlementText;
	private String opponentEntitlementKind;
	
	private List <RelativeGroundLawArticle> relativeGroundLawArticles;

	private Boolean reputationClaimed;
	private String reputationClaimExplanation;
	private List<AttachedDocument> reputationClaimDocuments;
	private List <String> reputationClaimCountries;

	private Date dateOfFame;
	private String relatedApplicationsNumbers;

	private String earlierRightDescription;

	/**
	 * @return the earlierEntitlementRightType
	 */
	public EarlierEntitlementRightKind getEarlierEntitlementRightType() {
		return earlierEntitlementRightType;
	}
	/**
	 * @param earlierEntitlementRightType the earlierEntitlementRightType to set
	 */
	public void setEarlierEntitlementRightType(
			EarlierEntitlementRightKind earlierEntitlementRightType) {
		this.earlierEntitlementRightType = earlierEntitlementRightType;
	}
	/**
	 * @return the earlierTradeMarkCategory
	 */
	public String getEarlierTradeMarkCategory() {
		return earlierTradeMarkCategory;
	}
	/**
	 * @param earlierTradeMarkCategory the earlierTradeMarkCategory to set
	 */
	public void setEarlierTradeMarkCategory(String earlierTradeMarkCategory) {
		this.earlierTradeMarkCategory = earlierTradeMarkCategory;
	}

	/**
	 * @return the earlierTradeMarkDetails
	 */
	public LimitedTradeMark getEarlierTradeMarkDetails() {
		return earlierTradeMarkDetails;
	}
	/**
	 * @param earlierTradeMarkDetails the earlierTradeMarkDetails to set
	 */
	public void setEarlierTradeMarkDetails(LimitedTradeMark earlierTradeMarkDetails) {
		this.earlierTradeMarkDetails = earlierTradeMarkDetails;
	}

	public List<DesignApplication> getEarlierDesignDetails() {
		return earlierDesignDetails;
	}

	public void setEarlierDesignDetails(List<DesignApplication> earlierDesignDetails) {
		this.earlierDesignDetails = earlierDesignDetails;
	}

	/**
	 * @return the typeRight
	 */
	public String getTypeRight() {
		return typeRight;
	}
	/**
	 * @param typeRight the typeRight to set
	 */
	public void setTypeRight(String typeRight) {
		this.typeRight = typeRight;
	}
	/**
	 * @return the typeRightDetails
	 */
	public String getTypeRightDetails() {
		return typeRightDetails;
	}
	/**
	 * @param typeRightDetails the typeRightDetails to set
	 */
	public void setTypeRightDetails(String typeRightDetails) {
		this.typeRightDetails = typeRightDetails;
	}
	/**
	 * @return the areaActivity
	 */
	public String getAreaActivity() {
		return areaActivity;
	}
	/**
	 * @param areaActivity the areaActivity to set
	 */
	public void setAreaActivity(String areaActivity) {
		this.areaActivity = areaActivity;
	}
	/**
	 * @return the registrationCountry
	 */
	public String getRegistrationCountry() {
		return registrationCountry;
	}
	/**
	 * @param registrationCountry the registrationCountry to set
	 */
	public void setRegistrationCountry(String registrationCountry) {
		this.registrationCountry = registrationCountry;
	}
	/**
	 * @return the opponentEntitlementText
	 */
	public String getOpponentEntitlementText() {
		return opponentEntitlementText;
	}
	/**
	 * @param opponentEntitlementText the opponentEntitlementText to set
	 */
	public void setOpponentEntitlementText(String opponentEntitlementText) {
		this.opponentEntitlementText = opponentEntitlementText;
	}
	/**
	 * @return the opponentEntitlementKind
	 */
	public String getOpponentEntitlementKind() {
		return opponentEntitlementKind;
	}
	/**
	 * @param opponentEntitlementKind the opponentEntitlementKind to set
	 */
	public void setOpponentEntitlementKind(String opponentEntitlementKind) {
		this.opponentEntitlementKind = opponentEntitlementKind;
	}
	/**
	 * @return the reputationClaimed
	 */
	public Boolean getReputationClaimed() {
		return reputationClaimed;
	}
	/**
	 * @param reputationClaimed the reputationClaimed to set
	 */
	public void setReputationClaimed(Boolean reputationClaimed) {
		this.reputationClaimed = reputationClaimed;
	}
	/**
	 * @return the reputationClaimExplanation
	 */
	public String getReputationClaimExplanation() {
		return reputationClaimExplanation;
	}
	/**
	 * @param reputationClaimExplanation the reputationClaimExplanation to set
	 */
	public void setReputationClaimExplanation(String reputationClaimExplanation) {
		this.reputationClaimExplanation = reputationClaimExplanation;
	}

	/**
	 * @return the reputationClaimDocuments
	 */
	public List<AttachedDocument> getReputationClaimDocuments() {
		return reputationClaimDocuments;
	}
	/**
	 * @param reputationClaimDocuments the reputationClaimDocuments to set
	 */
	public void setReputationClaimDocuments(
			List<AttachedDocument> reputationClaimDocuments) {
		this.reputationClaimDocuments = reputationClaimDocuments;
	}
	/**
	 * @return the reputationClaimCountries
	 */
	public List<String> getReputationClaimCountries() {
		return reputationClaimCountries;
	}
	/**
	 * @param reputationClaimCountries the reputationClaimCountries to set
	 */
	public void setReputationClaimCountries(List<String> reputationClaimCountries) {
		this.reputationClaimCountries = reputationClaimCountries;
	}
	
	/**
	 * @return the relativeGroundLawArticles
	 */
	public List<RelativeGroundLawArticle> getRelativeGroundLawArticles() {
		return relativeGroundLawArticles;
	}
	/**
	 * @param relativeGroundLawArticles the relativeGroundLawArticles to set
	 */
	public void setRelativeGroundLawArticles(
			List<RelativeGroundLawArticle> relativeGroundLawArticles) {
		this.relativeGroundLawArticles = relativeGroundLawArticles;
	}

    public Date getDateOfFame() {
        return dateOfFame;
    }

    public void setDateOfFame(Date dateOfFame) {
        this.dateOfFame = dateOfFame;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRelatedApplicationsNumbers() {
		return relatedApplicationsNumbers;
	}

	public void setRelatedApplicationsNumbers(String relatedApplicationsNumbers) {
		this.relatedApplicationsNumbers = relatedApplicationsNumbers;
	}

	public String getEarlierRightDescription() {
		return earlierRightDescription;
	}

	public void setEarlierRightDescription(String earlierRightDescription) {
		this.earlierRightDescription = earlierRightDescription;
	}
}
