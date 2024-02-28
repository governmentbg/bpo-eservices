/*
 *  CoreDomain:: ClassificationTerm 15/10/13 13:28 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.trademark;

import eu.ohim.sp.common.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ClassificationTerm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3171972263079094276L;

	private String termText;
	private ClassificationErrorType termAssessment;
	private ClassificationErrorType niceTermAssessment;
	private Date assessmentDate;
	private String examinationStatus;
	private Date examinationStatusDate;
	
	public String getTermText() {
		return termText;
	}
	public void setTermText(String termText) {
		this.termText = termText;
	}
	public ClassificationErrorType getTermAssessment() {
		return termAssessment;
	}
	public void setTermAssessment(ClassificationErrorType termAssessment) {
		this.termAssessment = termAssessment;
	}
	public Date getAssessmentDate() {
		return DateUtil.cloneDate(assessmentDate);
	}
	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = DateUtil.cloneDate(assessmentDate);
	}
	public String getExaminationStatus() {
		return examinationStatus;
	}
	public void setExaminationStatus(String examinationStatus) {
		this.examinationStatus = examinationStatus;
	}
	public Date getExaminationStatusDate() {
		return DateUtil.cloneDate(examinationStatusDate);
	}
	public void setExaminationStatusDate(Date examinationStatusDate) {
		this.examinationStatusDate = DateUtil.cloneDate(examinationStatusDate);
	}

	public ClassificationErrorType getNiceTermAssessment() {
		return niceTermAssessment;
	}

	public void setNiceTermAssessment(ClassificationErrorType niceTermAssessment) {
		this.niceTermAssessment = niceTermAssessment;
	}
}
