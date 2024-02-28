/*******************************************************************************
 * * $Id:: PhoneTest.java 157283 2013-12-03 10:10:08Z velasca                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class PhoneTest {
	private static final String INTERNATIONAL_PREFIX = "prefix";
	private static final String NUMBER = "1321231231";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Phone.class);
	}

	@Test
	public void testEquals() {
		Phone phone1 = new Phone();
		phone1.setInternationalPrefix(INTERNATIONAL_PREFIX);
		phone1.setNumber(NUMBER);
		phone1.setPhoneKind(PhoneKind.MOBILE_PHONE);

		Phone phone2 = new Phone();
		phone2.setInternationalPrefix(INTERNATIONAL_PREFIX);
		phone2.setNumber(NUMBER);
		phone2.setPhoneKind(PhoneKind.MOBILE_PHONE);

		assertTrue(phone1.equals(phone2));
		assertTrue(phone1.equals(phone1));
		assertEquals(phone1.hashCode(), phone2.hashCode());
	}

	@Test
	public void testToString() {
		Phone phone = new Phone();
		phone.setInternationalPrefix(INTERNATIONAL_PREFIX);
		phone.setNumber(NUMBER);
		phone.setPhoneKind(PhoneKind.MOBILE_PHONE);

		assertTrue(phone.toString().contains(NUMBER));
	}
}
