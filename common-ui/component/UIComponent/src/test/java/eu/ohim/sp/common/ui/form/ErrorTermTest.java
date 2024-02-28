/*******************************************************************************
 * * $Id:: ErrorTermTest.java 55918 2013-01-31 21:29:37Z karalch                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form;

import eu.ohim.sp.common.ui.form.classification.TermJSON;
import eu.ohim.sp.common.ui.util.JavaBeanTester;

import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class ErrorTermTest
{
    @Test
    public void mainForm_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(TermJSON.class);
    }
}
