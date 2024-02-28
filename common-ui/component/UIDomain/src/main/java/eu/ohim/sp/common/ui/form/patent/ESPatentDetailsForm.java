package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.InventorForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;
import java.util.List;

/**
 * Created by Raya
 * 11.12.2019
 */
public class ESPatentDetailsForm extends AbstractImportableForm {

    private String patentTitle;
    private String patentTitleSecondLang;
    private String patentAbstract;
    private String patentAbstractSecondLang;

    private String applicationNumber;
    private String registrationNumber;
    private Date applicationDate;
    private Date entitlementDate;
    private Date registrationDate;
    private Date expirationDate;

    private String patentCurrentStatus;
    private String patentGroupStatus;
    private Date patentCurrentStatusDate;

    private List<ApplicantForm> applicants;
    private List<RepresentativeForm> representatives;
    private List<InventorForm> inventors;

    private String formWarnings;
    private Boolean unpublished;

    private List<String> ipcClasses;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        ESPatentDetailsForm form = new ESPatentDetailsForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setPatentTitle(this.patentTitle);
        form.setPatentAbstractSecondLang(this.patentAbstractSecondLang);
        form.setPatentAbstract(this.patentAbstract);
        form.setPatentAbstractSecondLang(this.patentAbstractSecondLang);
        form.setApplicationNumber(this.applicationNumber);
        form.setRegistrationNumber(this.registrationNumber);
        form.setApplicationDate(this.applicationDate);
        form.setRegistrationDate(this.registrationDate);
        form.setEntitlementDate(this.entitlementDate);
        form.setExpirationDate(this.expirationDate);
        form.setPatentCurrentStatus(this.patentCurrentStatus);
        form.setPatentGroupStatus(this.patentGroupStatus);
        form.setPatentCurrentStatusDate(this.patentCurrentStatusDate);
        form.setApplicants(this.applicants);
        form.setRepresentatives(this.representatives);
        form.setInventors(this.inventors);
        form.setFormWarnings(this.formWarnings);
        form.setUnpublished(this.unpublished);
        form.setIpcClasses(this.ipcClasses);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PATENT_DETAILS;
    }

    public String getPatentTitle() {
        return patentTitle;
    }

    public void setPatentTitle(String patentTitle) {
        this.patentTitle = patentTitle;
    }

    public String getPatentTitleSecondLang() {
        return patentTitleSecondLang;
    }

    public void setPatentTitleSecondLang(String patentTitleSecondLang) {
        this.patentTitleSecondLang = patentTitleSecondLang;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public void setPatentAbstract(String patentAbstract) {
        this.patentAbstract = patentAbstract;
    }

    public String getPatentAbstractSecondLang() {
        return patentAbstractSecondLang;
    }

    public void setPatentAbstractSecondLang(String patentAbstractSecondLang) {
        this.patentAbstractSecondLang = patentAbstractSecondLang;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPatentCurrentStatus() {
        return patentCurrentStatus;
    }

    public void setPatentCurrentStatus(String patentCurrentStatus) {
        this.patentCurrentStatus = patentCurrentStatus;
    }

    public Date getPatentCurrentStatusDate() {
        return patentCurrentStatusDate;
    }

    public void setPatentCurrentStatusDate(Date patentCurrentStatusDate) {
        this.patentCurrentStatusDate = patentCurrentStatusDate;
    }

    public List<ApplicantForm> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ApplicantForm> applicants) {
        this.applicants = applicants;
    }

    public List<RepresentativeForm> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<RepresentativeForm> representatives) {
        this.representatives = representatives;
    }

    public List<InventorForm> getInventors() {
        return inventors;
    }

    public void setInventors(List<InventorForm> inventors) {
        this.inventors = inventors;
    }

    public String getFormWarnings() {
        return formWarnings;
    }

    public void setFormWarnings(String formWarnings) {
        this.formWarnings = formWarnings;
    }

    public String getPatentGroupStatus() {
        return patentGroupStatus;
    }

    public void setPatentGroupStatus(String patentGroupStatus) {
        this.patentGroupStatus = patentGroupStatus;
    }

    public Boolean getUnpublished() {
        return unpublished;
    }

    public void setUnpublished(Boolean unpublished) {
        this.unpublished = unpublished;
    }

    public Date getEntitlementDate() {
        return entitlementDate;
    }

    public void setEntitlementDate(Date entitlementDate) {
        this.entitlementDate = entitlementDate;
    }

    public List<String> getIpcClasses() {
        return ipcClasses;
    }

    public void setIpcClasses(List<String> ipcClasses) {
        this.ipcClasses = ipcClasses;
    }
}
