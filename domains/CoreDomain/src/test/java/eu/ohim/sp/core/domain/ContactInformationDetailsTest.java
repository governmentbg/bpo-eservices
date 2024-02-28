/*******************************************************************************
 * * $Id:: ContactInformationDetailsTest.java 121280 2013-06-05 06:58:22Z jarafu#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.contact.ContactInformationDetails;
import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class ContactInformationDetailsTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(ContactInformationDetails.class);
    }

    @Test
    public void hashCodeTest()
    {
    }
}
