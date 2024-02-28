/*******************************************************************************
 * * $Id:: SortBy.java 14329 2012-10-29 13:02:02Z karalch                        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

/**
 * SoryBy enumeration
 */
public enum SortBy {
    TEXT("text"), NICECLASS("niceclass"), RELEVANCE("relevance");

    private final String value;

    SortBy(String value) {
        this.value=value;
    }

    public String value() { return value; }
}
