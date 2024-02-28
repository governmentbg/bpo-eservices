/*
 *  CoreDomain:: TradeMarkApplication 03/09/13 09:33 karalch $
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
package eu.ohim.sp.core.domain.trademark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.PaymentFee;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

/**
 * The Class TradeMarkApplication.
 */
public class TradeMarkApplication extends Id implements Serializable, IPApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4145649964683884663L;

	/** The application filing number. */
	private String applicationFilingNumber;

    /** The application filing date. */
	private Date applicationFilingDate;

    /** The application type. */
    private String applicationType;

    /** The trade mark. */
	private TradeMark tradeMark;

	/** The fees. */
	private List<Fee> fees;
	
	/** The payments. */
	private List<PaymentFee> payments;
	
	/** The user */
	private String user;

	private String userEmail;

	/** True document indicator */
	private Boolean trueDocumentsIndicator;
	private Boolean certificateRequestedIndicator;

	private List<AttachedDocument> documents;

	/** Flag for fast track trademark applications*/
	private Boolean fastTrack;

	private Boolean willPayOnline;

    /**
	 * Gets the application filing number.
	 *
	 * @return the application filing number
	 */
	public String getApplicationFilingNumber() {
		return applicationFilingNumber;
	}
	
	/**
	 * Sets the application filing number.
	 *
	 * @param applicationFilingNumber the new application filing number
	 */
	public void setApplicationFilingNumber(String applicationFilingNumber) {
		this.applicationFilingNumber = applicationFilingNumber;
	}

    public String getApplicationNumber() {
        return (tradeMark!=null?tradeMark.getApplicationNumber():null);
    }

    public void setApplicationNumber(String applicationNumber) {
        if (tradeMark==null) {
            tradeMark = new TradeMark();
        }
        tradeMark.setApplicationNumber(applicationNumber);
    }

    /**
	 * Gets the application filing date.
	 *
	 * @return the application filing date
	 */
	public Date getApplicationFilingDate() {
		return DateUtil.cloneDate(applicationFilingDate);
	}
	
	/**
	 * Sets the application filing date.
	 *
	 * @param applicationFilingDate the new application filing date
	 */
	public void setApplicationFilingDate(Date applicationFilingDate) {
		this.applicationFilingDate = DateUtil.cloneDate(applicationFilingDate);
	}

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    /**
	 * Gets the trade mark.
	 *
	 * @return the trade mark
	 */
	public TradeMark getTradeMark() {
		return tradeMark;
	}
	
	/**
	 * Sets the trade mark.
	 *
	 * @param tradeMark the new trade mark
	 */
	public void setTradeMark(TradeMark tradeMark) {
		this.tradeMark = tradeMark;
	}
	
	/**
	 * Gets the fees.
	 *
	 * @return the fees
	 */
	public List<Fee> getFees() {
		return fees;
	}
	
	/**
	 * Sets the fees.
	 *
	 * @param fees the new fees
	 */
	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}
	
	/**
	 * Gets the payments.
	 *
	 * @return the payments
	 */
	public List<PaymentFee> getPayments() {
		return payments;
	}
	
	/**
	 * Sets the payments.
	 *
	 * @param payments the new payments
	 */
	public void setPayments(List<PaymentFee> payments) {
		this.payments = payments;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

    public List<AttachedDocument> getDocuments() {
		if(documents == null){
			documents = new ArrayList<>();
		}
        return documents;
    }

    public void setDocuments(List<AttachedDocument> documents) {
        this.documents = documents;
    }

	public Boolean getTrueDocumentsIndicator() {
		return trueDocumentsIndicator;
	}

	public void setTrueDocumentsIndicator(Boolean trueDocumentsIndicator) {
		this.trueDocumentsIndicator = trueDocumentsIndicator;
	}

	public Boolean getCertificateRequestedIndicator() {
		return certificateRequestedIndicator;
	}

	public void setCertificateRequestedIndicator(Boolean certificateRequestedIndicator) {
		this.certificateRequestedIndicator = certificateRequestedIndicator;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public Boolean getElectronicCorrespondenceRequested() {
		if(tradeMark != null && tradeMark.getContactDetails() != null && tradeMark.getContactDetails().size()>0){
			Optional<Boolean> optionalReduceResult = tradeMark.getContactDetails().stream().map(c-> c.getElectronicCorrespondence()).reduce( (c1, c2) -> c1 != null && c1 && c2 != null && c2);
			if(optionalReduceResult.isPresent()){
				return optionalReduceResult.get();
			}
		}
		return false;
	}

	public Boolean getFastTrack() {
		return fastTrack;
	}

	public void setFastTrack(Boolean fastTrack) {
		this.fastTrack = fastTrack;
	}

	public Boolean getWillPayOnline() {
		return willPayOnline;
	}

	public void setWillPayOnline(Boolean willPayOnline) {
		this.willPayOnline = willPayOnline;
	}

	@Override
	public String getTitleApplication() {
		if(tradeMark != null && tradeMark.getWordSpecifications() != null && tradeMark.getWordSpecifications().size()>0 &&
			tradeMark.getWordSpecifications().get(0).getWordElements() != null){
			return tradeMark.getWordSpecifications().get(0).getWordElements();
		}
		return null;
	}

	@Override
	public String getAppSubtype() {
		return null;
	}
}
