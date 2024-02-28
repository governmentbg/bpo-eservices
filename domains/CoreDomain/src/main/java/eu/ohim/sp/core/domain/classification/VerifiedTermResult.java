/*******************************************************************************
 * * $Id:: VerifiedTermResult.java 49260 2012-10-29 13:02:02Z karalch            $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification;

/**
 * VerifiedTermResult enumeration
 */
public enum VerifiedTermResult
{
    OK("OK"), HINT("Hint"), NOT_OK("Not OK"), NONE("None");

    private final String value;

    VerifiedTermResult(String value)
    {
        this.value = value;
    }

    public String value()
    {
        return value;
    }
}
