/*******************************************************************************
 * * $Id:: PaymentStatus.java 14329 2012-10-29 13:02:02Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.payment.wrapper;

/**
 * Status of the payment transaction result
 */
public enum PaymentStatus
{
    PAYMENT_OK("Payment was OK"),
    PAYMENT_CANCELLED("Payment was Cancelled"),
    PAYMENT_ERROR("There was an Error in the Payment Process")
    ;

    private PaymentStatus(final String value)
    {
        this.value = value;
    }

    private final String value;

    @Override
    public String toString()
    {
        return value;
    }
}
