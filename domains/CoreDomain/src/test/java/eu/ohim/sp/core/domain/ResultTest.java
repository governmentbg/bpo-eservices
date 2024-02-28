/*******************************************************************************
 * * $Id:: ResultTest.java 121450 2013-06-05 14:22:44Z jaraful                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain;

import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class ResultTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(Result.class);
    }
}
