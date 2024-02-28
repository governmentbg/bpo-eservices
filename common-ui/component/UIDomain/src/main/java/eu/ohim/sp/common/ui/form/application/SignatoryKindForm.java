/*******************************************************************************
 * * $Id:: SignatoryKindForm.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.application;

public enum SignatoryKindForm
{
    APPLICANT("APPLICANT", "signature.signatoryType.applicant"),
    /* Excluded by DEVIMPFO-683
       EMPLOYEE_REPRESENTATIVE("EMPLOYEE_REPRESENTATIVE", "signature.signatoryType.employeeRepresentative"),
     */
    REPRESENTATIVE ("REPRESENTATIVE", "signature.signatoryType.representative"),
    OTHER("OTHER", "signature.signatoryType.other"),
    BAILIFF("BAILIFF", "signature.signatoryType.bailiff"),
    ASSIGNEE("ASSIGNEE", "signature.signatoryType.assignee")
	;

    private SignatoryKindForm(final String code, final String description)
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
