package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.Date;

/**
 * Created by Raya
 * 13.05.2019
 */
public class PCTForm extends AbstractImportableForm {

    private String applicationNumber;
    private Date applicationDate;
    private String publicationNumber;
    private Date publicationDate;
    private String holderName;
    private boolean payedFees;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PCTForm form = new PCTForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setApplicationDate(this.applicationDate);
        form.setApplicationNumber(this.getApplicationNumber());
        form.setHolderName(this.holderName);
        form.setPublicationDate(this.publicationDate);
        form.setPublicationNumber(this.publicationNumber);
        form.setPayedFees(this.payedFees);
        return form;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PCT;
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

    public boolean isPayedFees() {
        return payedFees;
    }

    public void setPayedFees(boolean payedFees) {
        this.payedFees = payedFees;
    }
}
