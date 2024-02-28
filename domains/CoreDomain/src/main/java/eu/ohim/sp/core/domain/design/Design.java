/*
 * CoreDomain:: Design 02/10/13 16:05 KARALCH $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

/**
 * The Class Design
 */
public class Design extends Id implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6825764739771987524L;

    /** The application language. */
    private String applicationLanguage;

    /** The second language. */
    private String secondLanguage;

    private String designIdentifier;

    private String applicationNumber;
    private String registrationNumber;

    private String registrationOffice;

    private Date registrationDate;

    private Date expiryDate;

    /** The divisional application details. */
    private DesignDivisionalApplicationDetails divisionalApplicationDetails;

    /** The designers. */
    private List<Designer> designers;

    private List<DesignView> views;

    private List<ProductIndication> productIndications;

    private boolean ornamentationIndicator;

    /** The priorities. */
    private List<Priority> priorities;

    /** The exhibition priorities. */
    private List<ExhibitionPriority> exhibitionPriorities;

    private boolean publicationDefermentIndicator;

    private Date publicationDefermentTillDate;

    private String verbalElements;

    private String verbalElementsEn;

    private String description;

    private String distinctiveFeatures;

    private String colours;

    private DesignStatusCode currentStatus;

    private Date currentStatusDate;

    private boolean specimenIndicator;

    private boolean divisionalApplication;

    private boolean designerWaiverIndicator;

    private int numberOfRenewals;

    private Boolean selected;
    private Boolean unpublished;

    public String getRegistrationOffice() {
        return registrationOffice;
    }

    public void setRegistrationOffice(String registrationOffice) {
        this.registrationOffice = registrationOffice;
    }

    public Date getExpiryDate() {
        return DateUtil.cloneDate(expiryDate);
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = DateUtil.cloneDate(expiryDate);
    }

    public DesignDivisionalApplicationDetails getDivisionalApplicationDetails() {
        return divisionalApplicationDetails;
    }

    public void setDivisionalApplicationDetails(DesignDivisionalApplicationDetails divisionalApplicationDetails) {
        this.divisionalApplicationDetails = divisionalApplicationDetails;
    }

    public List<Designer> getDesigners() {
        return designers;
    }

    public void setDesigners(List<Designer> designers) {
        this.designers = designers;
    }

    public List<DesignView> getViews() {
        return views;
    }

    public void setViews(List<DesignView> views) {
        this.views = views;
    }

    public List<ProductIndication> getProductIndications() {
        return productIndications;
    }

    public void setProductIndications(List<ProductIndication> productIndications) {
        this.productIndications = productIndications;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<ExhibitionPriority> getExhibitionPriorities() {
        return exhibitionPriorities;
    }

    public void setExhibitionPriorities(List<ExhibitionPriority> exhibitionPriorities) {
        this.exhibitionPriorities = exhibitionPriorities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistinctiveFeatures() {
        return distinctiveFeatures;
    }

    public void setDistinctiveFeatures(String distinctiveFeatures) {
        this.distinctiveFeatures = distinctiveFeatures;
    }

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public String getApplicationLanguage() {
        return applicationLanguage;
    }

    public void setApplicationLanguage(String applicationLanguage) {
        this.applicationLanguage = applicationLanguage;
    }

    public String getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public String getDesignIdentifier() {
        return designIdentifier;
    }

    public void setDesignIdentifier(String designIdentifier) {
        this.designIdentifier = designIdentifier;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Date getRegistrationDate() {
        return DateUtil.cloneDate(registrationDate);
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = DateUtil.cloneDate(registrationDate);
    }

    public boolean isOrnamentationIndicator() {
        return ornamentationIndicator;
    }

    public void setOrnamentationIndicator(boolean ornamentationIndicator) {
        this.ornamentationIndicator = ornamentationIndicator;
    }

    public boolean isPublicationDefermentIndicator() {
        return publicationDefermentIndicator;
    }

    public void setPublicationDefermentIndicator(boolean publicationDefermentIndicator) {
        this.publicationDefermentIndicator = publicationDefermentIndicator;
    }

    public Date getPublicationDefermentTillDate() {
        return DateUtil.cloneDate(publicationDefermentTillDate);
    }

    public void setPublicationDefermentTillDate(Date publicationDefermentTillDate) {
        this.publicationDefermentTillDate = DateUtil.cloneDate(publicationDefermentTillDate);
    }

    public String getVerbalElements() {
        return verbalElements;
    }

    public void setVerbalElements(String verbalElements) {
        this.verbalElements = verbalElements;
    }

    public DesignStatusCode getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(DesignStatusCode currentStatus) {
        this.currentStatus = currentStatus;
    }

    public boolean isSpecimenIndicator() {
        return specimenIndicator;
    }

    public void setSpecimenIndicator(boolean specimenIndicator) {
        this.specimenIndicator = specimenIndicator;
    }

    public Date getCurrentStatusDate() {
        return DateUtil.cloneDate(currentStatusDate);
    }

    public void setCurrentStatusDate(Date currentStatusDate) {
        this.currentStatusDate = DateUtil.cloneDate(currentStatusDate);
    }

    public boolean isDivisionalApplication() {
        return divisionalApplication;
    }

    public void setDivisionalApplication(boolean divisionalApplication) {
        this.divisionalApplication = divisionalApplication;
    }

    public boolean isDesignerWaiverIndicator() {
        return designerWaiverIndicator;
    }

    public void setDesignerWaiverIndicator(boolean designerWaiverIndicator) {
        this.designerWaiverIndicator = designerWaiverIndicator;
    }

    /**
     * @return the numberOfRenewals
     */
    public int getNumberOfRenewals() {
        return numberOfRenewals;
    }

    /**
     * @param numberOfRenewals the numberOfRenewals to set
     */
    public void setNumberOfRenewals(int numberOfRenewals) {
        this.numberOfRenewals = numberOfRenewals;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getUnpublished() {
        return unpublished;
    }

    public void setUnpublished(Boolean unpublished) {
        this.unpublished = unpublished;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getVerbalElementsEn() {
        return verbalElementsEn;
    }

    public void setVerbalElementsEn(String verbalElementsEn) {
        this.verbalElementsEn = verbalElementsEn;
    }
}
