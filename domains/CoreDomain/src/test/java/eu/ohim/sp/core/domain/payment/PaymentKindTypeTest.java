/*******************************************************************************
 * * $Id:: PaymentKindTypeTest.java 156942 2013-11-29 16:45:49Z velasca          $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.payment;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class PaymentKindTypeTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(PaymentKind.class);
    }
}
