/*
 *  CoreDomain:: AddressTest 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain;

import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import java.beans.IntrospectionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author ionitdi
 */
public class AddressTest {
    @Test
    public void bean_testProperties() throws IntrospectionException {
        JavaBeanTester.test(Address.class);
    }

    @Test
    public void hashCodeTest() {
        Address core1 = new Address();
        core1.setCity("some text");

        Address core2 = new Address();
        core2.setCity("some text");

        assertEquals(core1.hashCode(), core2.hashCode());
    }

    @Test
    public void testEquals() {
        Address core1 = new Address();
        core1.setCity("some text");
        core1.setCountry("country");
        Address core2 = new Address();
        core2.setCity("some text");
        core2.setCountry("country");
        assertTrue(core2.equals(core1));
    }

    @Test
    public void testNotEquals() {
        Address core1 = new Address();
        core1.setCity("some text");
        core1.setCountry("country");
        Address core2 = new Address();
        core2.setCity("some text");
        core2.setCountry("another country");
        assertFalse(core2.equals(core1));
    }

}
