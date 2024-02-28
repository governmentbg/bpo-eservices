/*******************************************************************************
 * * $Id:: ProfessionalPractitionerTypeTest.java 49264 2012-10-29 13:23:34Z kara#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.common.ui.util.JavaBeanTester;

import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class ProfessionalPractitionerTypeTest
{
    @Test
    public void mainForm_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(ProfessionalPractitionerType.class);
    }
}
