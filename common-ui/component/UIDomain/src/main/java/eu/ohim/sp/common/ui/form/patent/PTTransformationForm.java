package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTTransformationForm extends AbstractImportableForm {

    protected String countryCode;
    protected String applicationNumber;
    protected Date applicationDate;
    protected String publicationNumber;
    protected Date publicationDate;
    protected String holderName;
    protected boolean payedFees;


    public PTTransformationForm createNewForm(){
        return new PTTransformationForm();
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PTTransformationForm form = createNewForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setApplicationNumber(this.getApplicationNumber());
        form.setApplicationDate(this.getApplicationDate());
        form.setPublicationNumber(this.getPublicationNumber());
        form.setPublicationDate(this.getPublicationDate());
        form.setCountryCode(this.getCountryCode());
        form.setHolderName(this.getHolderName());
        form.setPayedFees(this.isPayedFees());
        return form;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.TRANSFORMATION;
    }

    public boolean isPayedFees() {
        return payedFees;
    }

    public void setPayedFees(boolean payedFees) {
        this.payedFees = payedFees;
    }
}
