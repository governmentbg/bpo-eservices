package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.patent.RegKind;
import eu.ohim.sp.core.domain.patent.SVKind;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PatentForm extends AbstractImportableForm {

    private String patentTitle;
    private String patentTitleSecondLang;
    private String patentAbstract;
    private String patentAbstractSecondLang;
    private List<PatentViewForm> patentViews;

    private FileWrapper patentDescriptions;
    private FileWrapper patentClaims;
    private FileWrapper patentDrawings;
    private FileWrapper sequencesListing;

    private String pagesCount;
    private String claimsCount;
    private String independentClaimsCount;
    private String drawingsCount;
    private String drawingNumber;

    private String applicationNumber;
    private String registrationNumber;
    private Date applicationDate;
    private Date registrationDate;
    private Date registrationPublicationDate;

    private Boolean patentValidated;
    private String externalReferenceNumber;
    private String patentPublicationsInfo;

    private SVKind svKind;
    private String titleTransliteration;
    private String taxon;
    private String latinClassification;


    private RegKind regKind;
    private String firstPermissionBGNumber;
    private Date firstPermissionBGDate;
    private Date firstPermissionBGNotificationDate;
    private String firstPermissionEUNumber;
    private Date firstPermissionEUDate;

    public PatentForm() {
        patentViews = new ArrayList<>();
        patentDescriptions = new FileWrapper();
        patentClaims = new FileWrapper();
        patentDrawings = new FileWrapper();
        sequencesListing = new FileWrapper();
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PatentForm patentForm = new PatentForm();
        patentForm.setId(this.id);
        patentForm.setImported(this.getImported());
        patentForm.setPatentAbstract(this.patentAbstract);
        patentForm.setPatentTitle(this.patentTitle);
        patentForm.setPatentClaims(this.patentClaims);
        patentForm.setPatentDescriptions(this.patentDescriptions);
        patentForm.setPatentDrawings(this.patentDrawings);
        patentForm.setSequencesListing(this.sequencesListing);
        patentForm.setPagesCount(this.pagesCount);
        patentForm.setClaimsCount(this.claimsCount);
        patentForm.setIndependentClaimsCount(this.independentClaimsCount);
        patentForm.setDrawingsCount(this.drawingsCount);
        patentForm.setDrawingNumber(this.drawingNumber);
        patentForm.setPatentTitleSecondLang(this.patentTitleSecondLang);
        patentForm.setPatentAbstractSecondLang(this.patentAbstractSecondLang);
        if(this.patentViews != null){
            List<PatentViewForm> patentViewForms = new ArrayList<>();
            for(PatentViewForm view: this.patentViews){
                try {
                    patentViewForms.add((PatentViewForm) view.clone());
                } catch (CloneNotSupportedException e) {
                    throw new SPException("Clone not supported", e, "error.form.cloneNotSupported");
                }
            }
            patentForm.setPatentViews(patentViewForms);
        }

        patentForm.setApplicationNumber(this.applicationNumber);
        patentForm.setRegistrationNumber(this.registrationNumber);
        patentForm.setApplicationDate(this.applicationDate);
        patentForm.setRegistrationDate(this.registrationDate);
        patentForm.setRegistrationPublicationDate(this.registrationPublicationDate);
        patentForm.setPatentValidated(this.patentValidated);
        patentForm.setExternalReferenceNumber(this.externalReferenceNumber);
        patentForm.setPatentPublicationsInfo(this.patentPublicationsInfo);
        patentForm.setSvKind(this.svKind);
        patentForm.setTitleTransliteration(this.titleTransliteration);
        patentForm.setTaxon(this.getTaxon());
        patentForm.setLatinClassification(this.getLatinClassification());
        patentForm.setRegKind(this.getRegKind());
        patentForm.setFirstPermissionBGNumber(this.getFirstPermissionBGNumber());
        patentForm.setFirstPermissionBGDate(this.getFirstPermissionBGDate());
        patentForm.setFirstPermissionBGNotificationDate(this.getFirstPermissionBGNotificationDate());
        patentForm.setFirstPermissionEUNumber(this.getFirstPermissionEUNumber());
        patentForm.setFirstPermissionEUDate(this.getFirstPermissionEUDate());

        return patentForm;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PATENT;
    }

    public String getPatentTitle() {
        return patentTitle;
    }

    public void setPatentTitle(String patentTitle) {
        this.patentTitle = patentTitle;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public void setPatentAbstract(String patentAbstract) {
        this.patentAbstract = patentAbstract;
    }

    public List<PatentViewForm> getPatentViews() {
        return patentViews;
    }

    public void setPatentViews(List<PatentViewForm> patentViews) {
        this.patentViews = patentViews;
    }

    public FileWrapper getPatentDescriptions() {
        if (patentDescriptions == null) {
            patentDescriptions = new FileWrapper();
        }
        return patentDescriptions;
    }

    public void setPatentDescriptions(FileWrapper patentDescriptions) {
        this.patentDescriptions = patentDescriptions;
    }

    public FileWrapper getPatentClaims() {
        if (patentClaims == null) {
            patentClaims = new FileWrapper();
        }
        return patentClaims;
    }

    public void setPatentClaims(FileWrapper patentClaims) {
        this.patentClaims = patentClaims;
    }

    public FileWrapper getPatentDrawings() {
        if (patentDrawings == null) {
            patentDrawings = new FileWrapper();
        }
        return patentDrawings;
    }

    public void setPatentDrawings(FileWrapper patentDrawings) {
        this.patentDrawings = patentDrawings;
    }



    public String getPatentTitleSecondLang() {
        return patentTitleSecondLang;
    }

    public void setPatentTitleSecondLang(String patentTitleSecondLang) {
        this.patentTitleSecondLang = patentTitleSecondLang;
    }

    public String getPatentAbstractSecondLang() {
        return patentAbstractSecondLang;
    }

    public void setPatentAbstractSecondLang(String patentAbstractSecondLang) {
        this.patentAbstractSecondLang = patentAbstractSecondLang;
    }

    public String getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(String pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getClaimsCount() {
        return claimsCount;
    }

    public void setClaimsCount(String claimsCount) {
        this.claimsCount = claimsCount;
    }

    public String getDrawingsCount() {
        return drawingsCount;
    }

    public void setDrawingsCount(String drawingsCount) {
        this.drawingsCount = drawingsCount;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public String getIndependentClaimsCount() {
        return independentClaimsCount;
    }

    public void setIndependentClaimsCount(String independentClaimsCount) {
        this.independentClaimsCount = independentClaimsCount;
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

    public Date getRegistrationPublicationDate() {
        return registrationPublicationDate;
    }

    public void setRegistrationPublicationDate(Date registrationPublicationDate) {
        this.registrationPublicationDate = registrationPublicationDate;
    }

    public Boolean getPatentValidated() {
        return patentValidated;
    }

    public void setPatentValidated(Boolean patentValidated) {
        this.patentValidated = patentValidated;
    }

    public String getExternalReferenceNumber() {
        return externalReferenceNumber;
    }

    public void setExternalReferenceNumber(String externalReferenceNumber) {
        this.externalReferenceNumber = externalReferenceNumber;
    }

    public String getPatentPublicationsInfo() {
        return patentPublicationsInfo;
    }

    public void setPatentPublicationsInfo(String patentPublicationsInfo) {
        this.patentPublicationsInfo = patentPublicationsInfo;
    }

    public SVKind getSvKind() {
        return svKind;
    }

    public void setSvKind(SVKind svKind) {
        this.svKind = svKind;
    }

    public String getTitleTransliteration() {
        return titleTransliteration;
    }

    public void setTitleTransliteration(String titleTransliteration) {
        this.titleTransliteration = titleTransliteration;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public String getLatinClassification() {
        return latinClassification;
    }

    public void setLatinClassification(String latinClassification) {
        this.latinClassification = latinClassification;
    }

    public RegKind getRegKind() {
        return regKind;
    }

    public void setRegKind(RegKind regKind) {
        this.regKind = regKind;
    }

    public String getFirstPermissionBGNumber() {
        return firstPermissionBGNumber;
    }

    public void setFirstPermissionBGNumber(String firstPermissionBGNumber) {
        this.firstPermissionBGNumber = firstPermissionBGNumber;
    }

    public Date getFirstPermissionBGDate() {
        return firstPermissionBGDate;
    }

    public void setFirstPermissionBGDate(Date firstPermissionBGDate) {
        this.firstPermissionBGDate = firstPermissionBGDate;
    }

    public Date getFirstPermissionBGNotificationDate() {
        return firstPermissionBGNotificationDate;
    }

    public void setFirstPermissionBGNotificationDate(Date firstPermissionBGNotificationDate) {
        this.firstPermissionBGNotificationDate = firstPermissionBGNotificationDate;
    }

    public String getFirstPermissionEUNumber() {
        return firstPermissionEUNumber;
    }

    public void setFirstPermissionEUNumber(String firstPermissionEUNumber) {
        this.firstPermissionEUNumber = firstPermissionEUNumber;
    }

    public Date getFirstPermissionEUDate() {
        return firstPermissionEUDate;
    }

    public void setFirstPermissionEUDate(Date firstPermissionEUDate) {
        this.firstPermissionEUDate = firstPermissionEUDate;
    }

    public FileWrapper getSequencesListing() {
        if (sequencesListing == null) {
            sequencesListing = new FileWrapper();
        }
        return sequencesListing;
    }

    public void setSequencesListing(FileWrapper sequencesListing) {
        this.sequencesListing = sequencesListing;
    }
}
