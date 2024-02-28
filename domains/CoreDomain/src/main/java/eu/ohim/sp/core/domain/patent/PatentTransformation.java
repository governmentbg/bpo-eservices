package eu.ohim.sp.core.domain.patent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PatentTransformation implements Serializable {

    private String countryCode;
    private String applicationNumber;
    private Date applicationDate;
    private String publicationNumber;
    private Date publicationDate;
    private String holderName;
    private boolean payedFees;
    private boolean imported;

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

    public boolean isPayedFees() {
        return payedFees;
    }

    public void setPayedFees(boolean payedFees) {
        this.payedFees = payedFees;
    }

    public boolean getImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }
}
