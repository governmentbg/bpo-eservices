/*******************************************************************************
 * * $Id:: MatchProximityType.java 14329 2012-10-29 13:02:02Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification;

/**
 * MatchProximityType enumeration
 */
public enum MatchProximityType {
    TERM_MATCH("Term Match"),
    LINGUISTIC_MATCH("Linguistic match"),
    MATCH_TO_VERIFY("Match to verify"),
    NO_MATCH("No match");

    private final String value;

    MatchProximityType(String value) {
        this.value=value;
    }

    public String value() { return value; }
}
