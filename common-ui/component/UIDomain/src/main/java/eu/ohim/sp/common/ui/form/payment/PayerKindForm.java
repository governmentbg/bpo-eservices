/*******************************************************************************
 * * $Id:: PayerKindForm.java 49264 2012-10-29 13:23:34Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.payment;

public enum PayerKindForm
{
	APPLICANT("APPLICANT", "payment.payer.type.applicant"),
	REPRESENTATIVE("REPRESENTATIVE", "payment.payer.type.representative"),
	OTHER("OTHER", "payment.payer.type.other"),	
	;

    private PayerKindForm(final String code, final String description)
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
