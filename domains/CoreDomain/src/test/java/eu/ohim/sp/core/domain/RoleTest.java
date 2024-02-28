/*******************************************************************************
 * * $Id:: RoleTest.java 124681 2013-06-21 10:24:03Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain;

import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

/**
 * @author ionitdi
 */
public class RoleTest
{
    @Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(PersonRoleKind.class);
    }
}
