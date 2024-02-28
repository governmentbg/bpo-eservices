package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PTParallelApplicationForm extends AbstractImportableForm {

    protected String countryCode;
    protected String applicationNumber;
    protected Date applicationDate;
    protected String publicationNumber;
    protected Date publicationDate;
    protected String holderName;

    public PTParallelApplicationForm createNewForm(){
        return new PTParallelApplicationForm();
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PTParallelApplicationForm form = createNewForm();
        form.setId(this.id);
        form.setImported(this.getImported());
        form.setCountryCode(this.countryCode);
        form.setApplicationDate(this.applicationDate);
        form.setApplicationNumber(this.applicationNumber);
        form.setPublicationDate(this.publicationDate);
        form.setPublicationNumber(this.publicationNumber);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PARALLEL_APPLICATION;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
