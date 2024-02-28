/*******************************************************************************
 * * $Id:: ApplicantTest.java 156942 2013-11-29 16:45:49Z velasca                $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.util.Date;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class ApplicantTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(Applicant.class);
    }
    
    //TODO to fix if equals is added
    public void equalsTest_equals()
    {
        Applicant core1 = new Applicant();
        Applicant core2 = new Applicant();

        assertTrue(core1.equals(core2));
    }

    @Test
    public void equalsTest_1()
    {
        Applicant core1 = new Applicant();

        assertFalse(core1.equals(null));
    }

    @Test
    public void equalsTest_2()
    {
        Applicant core1 = new Applicant();
        core1.setDateOfBirth(new Date());

        Applicant core2 = new Applicant();

        assertFalse(core1.equals(core2));
    }

}
