package eu.ohim.sp.common.ui.form.application;

import java.util.Date;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.trademark.GSHelperForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class AppealForm extends AbstractImportableForm  implements java.io.Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String decisionNumber;
	private Date decisionDate;
	private AppealKindForm appealKind;
	private FileWrapper gExplanationGrounds;
	private String explanations;
	private String formMessages;
	private Date oppositionFilingDate;
	private GSHelperForm gsHelper;

	public AppealForm() {
		this.gsHelper = new GSHelperForm();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public AppealKindForm getAppealKind() {
		return appealKind;
	}

	public void setAppealKind(AppealKindForm appealKind) {
		this.appealKind = appealKind;
	}

	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.APPEAL;
	}

	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		AppealForm cloned = new AppealForm();
		cloned.setAppealKind(appealKind);
		cloned.setDecisionDate(decisionDate);
		cloned.setDecisionNumber(decisionNumber);
		cloned.setExplanations(explanations);
		cloned.setgExplanationGrounds(gExplanationGrounds);
		cloned.setFormMessages(formMessages);
		cloned.setId(getId());
		cloned.setOppositionFilingDate(oppositionFilingDate);
		cloned.setGsHelper((GSHelperForm)gsHelper.clone());
		return cloned;
	}

	public FileWrapper getgExplanationGrounds() {
		return gExplanationGrounds;
	}

	public void setgExplanationGrounds(FileWrapper gExplanationGrounds) {
		this.gExplanationGrounds = gExplanationGrounds;
	}

	public String getExplanations() {
		return explanations;
	}

	public void setExplanations(String explanations) {
		this.explanations = explanations;
	}

	public String getFormMessages() {
		return formMessages;
	}

	public void setFormMessages(String formMessages) {
		this.formMessages = formMessages;
	}

	public Date getOppositionFilingDate() {
		return oppositionFilingDate;
	}

	public void setOppositionFilingDate(Date oppositionFilingDate) {
		this.oppositionFilingDate = oppositionFilingDate;
	}

	public GSHelperForm getGsHelper() {
		return gsHelper;
	}

	public void setGsHelper(GSHelperForm gsHelper) {
		this.gsHelper = gsHelper;
	}
}
