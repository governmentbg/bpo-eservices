/*******************************************************************************
 * * $Id:: RepresentativeAssociationFormTest.java 50053 2012-11-07 15:00:55Z ion#$
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
public class RepresentativeAssociationFormTest
{
    //@Test
    public void mainForm_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(RepresentativeAssociationForm.class, "name");
    }
}
