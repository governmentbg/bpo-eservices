/*
 *  ApplicationServiceDao:: DraftApplication 09/08/13 16:12 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.application;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity Bean that holds information about a draft application
 * 
 * @author karalch
 * 
 */
public class DraftApplication implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5435725979916570284L;

    private Long id;

    /** The user that created this draft application */
    private String username;
    /** The application id of this draft application */
    private String applicationId;
    /** The provisional id of this draft application */
    private String provisionalId;
    /** The office related to this draft application */
    private String office;
    /** The statuses related to this draft application */
    private Set<DraftStatus> statuses;
    /** The payment id of this draft application */
    private String paymentId;
    private Status currentStatus;
    private Date dtCreated;
    private TypeApplication tyApplication;
    private String titleApplication;
    private String appSubtype;

    private DraftSignStatus currentSignStatus;
    private Set<DraftSignStatus> signStatuses;
    private Boolean eCorrespondence;
    private String applicationReference;
    
    public DraftApplication() {
        statuses = new HashSet<DraftStatus>();
        signStatuses = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getProvisionalId() {
        return provisionalId;
    }

    public void setProvisionalId(String provisionalId) {
        this.provisionalId = provisionalId;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Set<DraftStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<DraftStatus> statuses) {
        this.statuses = statuses;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

	public Date getDtCreated() {
		return DateUtil.cloneDate(dtCreated);
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = DateUtil.cloneDate(dtCreated);
	}

	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public TypeApplication getTyApplication() {
		return tyApplication;
	}

	public void setTyApplication(TypeApplication tyApplication) {
		this.tyApplication = tyApplication;
	}

    public DraftSignStatus getCurrentSignStatus() {
        return currentSignStatus;
    }

    public void setCurrentSignStatus(DraftSignStatus currentSignStatus) {
        this.currentSignStatus = currentSignStatus;
    }

    public Set<DraftSignStatus> getSignStatuses() {
        return signStatuses;
    }

    public void setSignStatuses(Set<DraftSignStatus> signStatuses) {
        this.signStatuses = signStatuses;
    }

    public Boolean geteCorrespondence() {
        return eCorrespondence;
    }

    public void seteCorrespondence(Boolean eCorrespondence) {
        this.eCorrespondence = eCorrespondence;
    }

    public String getApplicationReference() {
        return applicationReference;
    }

    public void setApplicationReference(String applicationReference) {
        this.applicationReference = applicationReference;
    }

    public String getTitleApplication() {
        return titleApplication;
    }

    public void setTitleApplication(String titleApplication) {
        this.titleApplication = titleApplication;
    }

    public String getAppSubtype() {
        return appSubtype;
    }

    public void setAppSubtype(String appSubtype) {
        this.appSubtype = appSubtype;
    }
}
