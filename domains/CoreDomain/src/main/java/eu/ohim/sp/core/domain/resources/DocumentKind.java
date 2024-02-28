/*******************************************************************************
 * * $Id:: DocumentKind.java 124359 2013-06-19 17:43:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.resources;

//used a string instead
@Deprecated
public enum DocumentKind {
    COLLECTIVE_TRADE_MARK_REGULATION("Collective Trade Mark Regulation"),
    COLLECTIVE_TRADE_MARK_REGULATION_TRANSLATION("Collective Trade Mark Regulation Translation"),
    COLLECTIVE_TRADE_MARK_REGULATION_APPLICANT("Collective Trade Mark Regulation Applicant"),
    PRIORITY_CERTIFICATE("Priority Certificate"),
    PRIORITY_CERTIFICATE_TRANSLATION("Priority Certificate Translation"),
    EXHIBITION_PRIORITY_CERTIFICATE("Exhibition Priority Certificate"),
    EXHIBITION_PRIORITY_CERTIFICATE_TRANSLATION("Exhibition Priority Certificate Translation"),
    SENIORITY_CERTIFICATE("Seniority Certificate"),
    SENIORITY_CERTIFICATE_TRANSLATION("Seniority Certificate Translation"),
    OTHER("Other"),
    APPLICATION_RECEIPT("Application Receipt");
    
    private final String value;

    DocumentKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }
}
