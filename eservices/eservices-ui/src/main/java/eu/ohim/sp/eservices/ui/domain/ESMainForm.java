package eu.ohim.sp.eservices.ui.domain;

import java.io.Serializable;

import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;

public class ESMainForm extends MainForm implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Boolean personalDataSection;
	private Boolean markRepresentationSection;
	private Boolean designSection;
	private Boolean designerSection;
	private Boolean claimSection;
	private Boolean languageSection;
	private Boolean signatureSection;
	private Boolean gsSection;
	private Boolean referenceSection;
	private Boolean paymentDataSection;
	private Boolean divisionalSection;
	private Boolean otherAttachments;
	private Boolean applicantionCADataSection;
	private Boolean licenceDataSection;
	private Boolean groundsSection;
	private Boolean securityMeasureSection;
	private Boolean gsHelperDataSection;
	private Boolean appealSection;
	private Boolean patentSection;
	private Boolean holderIsInventorSection;
	private Boolean partialInvaliditySection;
	private Boolean userdoc;

	private Boolean smallCompany;
	private FileWrapper smallCompanyFiles;
	private Boolean licenceAvailability;

	public ESMainForm() {
		smallCompanyFiles = new FileWrapper();
		smallCompany = false;
		licenceAvailability = false;
	}

	public Boolean getGroundsSection() {
		return groundsSection;
	}
	public void setGroundsSection(Boolean groundsSection) {
		this.groundsSection = groundsSection;
	}

	public Boolean getPersonalDataSection() {
		return personalDataSection;
	}
	public void setPersonalDataSection(Boolean personalDataSection) {
		this.personalDataSection = personalDataSection;
	}
	public Boolean getClaimSection() {
		return claimSection;
	}
	public void setClaimSection(Boolean claimSection) {
		this.claimSection = claimSection;
	}
	public Boolean getLanguageSection() {
		return languageSection;
	}
	public void setLanguageSection(Boolean languageSection) {
		this.languageSection = languageSection;
	}
	public Boolean getMarkRepresentationSection() {
		return markRepresentationSection;
	}
	public void setMarkRepresentationSection(Boolean markRepresentationSection) {
		this.markRepresentationSection = markRepresentationSection;
	}
	public Boolean getSignatureSection() {
		return signatureSection;
	}
	public void setSignatureSection(Boolean signatureSection) {
		this.signatureSection = signatureSection;
	}
	public Boolean getGsSection() {
		return gsSection;
	}
	public void setGsSection(Boolean gsSection) {
		this.gsSection = gsSection;
	}
	public Boolean getPaymentDataSection() {
		return paymentDataSection;
	}
	public void setPaymentDataSection(Boolean paymentDataSection) {
		this.paymentDataSection = paymentDataSection;
	}
	public Boolean getReferenceSection() {
		return referenceSection;
	}
	public void setReferenceSection(Boolean referenceSection) {
		this.referenceSection = referenceSection;
	}
	public Boolean getDivisionalSection() {
		return divisionalSection;
	}
	public void setDivisionalSection(Boolean divisionalSection) {
		this.divisionalSection = divisionalSection;
	}
	public Boolean getOtherAttachments() {
		return otherAttachments;
	}
	public void setOtherAttachments(Boolean otherAttachments) {
		this.otherAttachments = otherAttachments;
	}

	public Boolean getHolderIsInventorSection() {
		return holderIsInventorSection;
	}

	public void setHolderIsInventorSection(Boolean holderIsInventorSection) {
		this.holderIsInventorSection = holderIsInventorSection;
	}

	public Boolean getPartialInvaliditySection() {
		return partialInvaliditySection;
	}

	public void setPartialInvaliditySection(Boolean partialInvaliditySection) {
		this.partialInvaliditySection = partialInvaliditySection;
	}

	public Boolean getDesignSection() {
		return designSection;
	}
	public void setDesignSection(Boolean designSection) {
		this.designSection = designSection;
	}

	public Boolean getDesignerSection() {
		return designerSection;
	}

	public void setDesignerSection(Boolean designerSection) {
		this.designerSection = designerSection;
	}

	public Boolean getApplicantionCADataSection() {
		return applicantionCADataSection;
	}
	public void setApplicantionCADataSection(Boolean applicantionCADataSection) {
		this.applicantionCADataSection = applicantionCADataSection;
	}
	public Boolean getLicenceDataSection() {
		return licenceDataSection;
	}
	public void setLicenceDataSection(Boolean licenceDataSection) {
		this.licenceDataSection = licenceDataSection;
	}
	public Boolean getSecurityMeasureSection() {
		return securityMeasureSection;
	}
	public void setSecurityMeasureSection(Boolean securityMeasureSection) {
		this.securityMeasureSection = securityMeasureSection;
	}
	public Boolean getGsHelperDataSection() {
		return gsHelperDataSection;
	}
	public void setGsHelperDataSection(Boolean gsHelperDataSection) {
		this.gsHelperDataSection = gsHelperDataSection;
	}
	public Boolean getAppealSection() {
		return appealSection;
	}
	public void setAppealSection(Boolean appealSection) {
		this.appealSection = appealSection;
	}

	public Boolean getPatentSection() {
		return patentSection;
	}

	public void setPatentSection(Boolean patentSection) {
		this.patentSection = patentSection;
	}

	public Boolean getSmallCompany() {
		return smallCompany;
	}

	public void setSmallCompany(Boolean smallCompany) {
		this.smallCompany = smallCompany;
	}

	public FileWrapper getSmallCompanyFiles() {
		return smallCompanyFiles;
	}

	public void setSmallCompanyFiles(FileWrapper smallCompanyFiles) {
		this.smallCompanyFiles = smallCompanyFiles;
	}

	public Boolean getLicenceAvailability() {
		return licenceAvailability;
	}

	public void setLicenceAvailability(Boolean licenceAvailability) {
		this.licenceAvailability = licenceAvailability;
	}

	public Boolean getUserdoc() {
		return userdoc;
	}

	public void setUserdoc(Boolean userdoc) {
		this.userdoc = userdoc;
	}
}
