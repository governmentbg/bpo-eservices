/*******************************************************************************
 * * $Id:: FileWrapperTest.java 49264 2012-10-29 13:23:34Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form;

import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.util.JavaBeanTester;

import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class FileWrapperTest
{
    @Test
    public void mainForm_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(FileWrapper.class);
    }
}
