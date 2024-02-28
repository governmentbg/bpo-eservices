/*******************************************************************************
 * * $Id:: ErrorEnumTest.java 53083 2012-12-14 08:59:24Z virgida                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification;

import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class ErrorEnumTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(ClassificationErrorEnum.class);
    }
}
