/*******************************************************************************
 * * $Id:: DraftApplication.java 14330 2012-10-29 13:07:33Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.application;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.*;

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
    /** all data except status code should not be trusted currently */
    private DraftStatus currentStatus;
    private Date dtCreated;

    private String tyApplication;
    private Boolean eCorrespondence;
    private String titleApplication;
	private String appSubtype;

	private DraftSignStatus currentSignStatus;
	private Set<DraftSignStatus> signStatuses;
	private String applicationReference;
    
    public DraftApplication() {
        statuses = new TreeSet<DraftStatus>(new SerializedStatusComparator());
		signStatuses = new TreeSet<DraftSignStatus>(new SerializedSignStatusComparator());
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

	public DraftStatus getCurrentStatus() {
        return currentStatus;
	}

	public void setCurrentStatus(DraftStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getDtCreated() {
		return DateUtil.cloneDate(dtCreated);
	}

	public void setDtCreated(Date dtCreated) {
		this.dtCreated = DateUtil.cloneDate(dtCreated);
	}

	public String getTyApplication() {
		return tyApplication;
	}

	public void setTyApplication(String tyApplication) {
		this.tyApplication = tyApplication;
	}

    static class SerializedStatusComparator implements Comparator<DraftStatus>, Serializable {
		private static final long serialVersionUID = 1L;
        @Override
        public int compare(DraftStatus o1, DraftStatus o2) {
            if (o1.getModifiedDate()==null
                    && o2.getModifiedDate()==null) {
                return 0;
            } else if (o1.getModifiedDate()==null) {
                return 1;
            } else if (o2.getModifiedDate()==null) {
                return -1;
            } else {
                return (int) (o2.getModifiedDate().getTime()-o1.getModifiedDate().getTime());
            }
        }
    }

	static class SerializedSignStatusComparator implements Comparator<DraftSignStatus>, Serializable {
		@Override
		public int compare(DraftSignStatus o1, DraftSignStatus o2) {
			if (o1.getModifiedDate()==null
					&& o2.getModifiedDate()==null) {
				return 0;
			} else if (o1.getModifiedDate()==null) {
				return 1;
			} else if (o2.getModifiedDate()==null) {
				return -1;
			} else {
				return (int) (o2.getModifiedDate().getTime()-o1.getModifiedDate().getTime());
			}
		}
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
