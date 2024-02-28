/*******************************************************************************
 * * $Id:: PaymentResultTest.java 53083 2012-12-14 08:59:24Z virgida             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain;

import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class PaymentResultTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(PaymentResult.class);
    }
}
