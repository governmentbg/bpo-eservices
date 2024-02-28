package eu.ohim.sp.core.domain.patent;

import eu.ohim.sp.core.domain.claim.Claim;
import eu.ohim.sp.core.domain.claim.IPPriority;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raya
 * 11.04.2019
 */
public class PatentPriority extends Claim implements IPPriority, Serializable {
    /** The filing office. */
    private String filingOffice;

    /** The filing number. */
    private String filingNumber;

    /** The filing date. */
    private Date filingDate;

    /** The certicate attached indicator. */
    private boolean certicateAttachedIndicator;

    /** The translation attached indicator. */
    private boolean translationAttachedIndicator;

    @Override
    public boolean isCerticateAttachedIndicator() {
        return certicateAttachedIndicator;
    }

    @Override
    public void setCerticateAttachedIndicator(boolean certicateAttachedIndicator) {
        this.certicateAttachedIndicator = certicateAttachedIndicator;
    }

    @Override
    public boolean isTranslationAttachedIndicator() {
        return translationAttachedIndicator;
    }

    @Override
    public void setTranslationAttachedIndicator(boolean translationAttachedIndicator) {
        this.translationAttachedIndicator = translationAttachedIndicator;
    }

    @Override
    public String getFilingOffice() {
        return filingOffice;
    }

    @Override
    public void setFilingOffice(String filingOffice) {
        this.filingOffice = filingOffice;
    }

    @Override
    public String getFilingNumber() {
        return filingNumber;
    }

    @Override
    public void setFilingNumber(String filingNumber) {
        this.filingNumber = filingNumber;
    }

    @Override
    public Date getFilingDate() {
        return filingDate;
    }

    @Override
    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }
}
