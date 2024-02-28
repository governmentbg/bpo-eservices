/*
 *  CoreDomain:: ApplicationKind 19/08/13 10:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.application;

/**
 * Enumeration that provides the supported and expected
 * kinds of applications
 */
public enum ApplicationKind {
    TRADEMARK_APPLICATION("TrademarkApplication"),
    TRADEMARK_OPPOSITION("TrademarkOpposition"),
    TRADEMARK_CANCELLATION("TrademarkCancellation"),
    TRADEMARK_RECORDAL("TrademarkRecordal"),
    DESIGN_APPLICATION("DesignApplication"),
    DESIGN_INVALIDITY("DesignInvalidity"),
    DESIGN_RECORDAL("DesignRecordal");

    private final String value;

    ApplicationKind(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}