/*******************************************************************************
 * * $Id:: LegalStatusType.java 49264 2012-10-29 13:23:34Z albemar         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

/**
 * @author albemar
 */
public enum LegalStatusType
{
    LEGAL_PERSON("Legal Person", "representative.field.legalStatus.legalPerson"),
    NATURAL_PERSON("Natural Person", "representative.field.legalStatus.naturalPerson");

    private LegalStatusType(final String code, final String description)
    {
        this.code = code;
        this.description = description;
    }

    private final String code;
    private final String description;

    @Override
    public String toString()
    {
        return description;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }
}
