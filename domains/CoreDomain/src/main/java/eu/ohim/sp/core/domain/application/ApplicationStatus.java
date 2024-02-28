/*******************************************************************************
 * * $Id:: ApplicationStatus.java 52279 2012-12-04 13:16:34Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.application;

/**
 * 
 * Constants values for the status of an application
 * 
 */
public enum ApplicationStatus {

    STATUS_INITIALIZED(1), STATUS_VALIDATION_SUCCESS(550),

    STATUS_CC_PAYMENT_DONE(512), STATUS_CC_PAYMENT_PENDING(513), STATUS_CC_PAYMENT_CANCELLED(514), STATUS_CC_PAYMENT_FAILURE(
            515),

    STATUS_SUBMITTED(5100), STATUS_EXPORTED(5200), STATUS_FAILED_TO_BE_TRANSFERRED_PERMANENTLY(5126), TRANSFER_OK(92), TRANSFER_ERROR(
            91);

    private Integer code;

    private ApplicationStatus(Integer code) {
        this.setCode(code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static ApplicationStatus fromCode(Integer v) {
        for (ApplicationStatus c : ApplicationStatus.values()) {
            if (c.code.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }

}
