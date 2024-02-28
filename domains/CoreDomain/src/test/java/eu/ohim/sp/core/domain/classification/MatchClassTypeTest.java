/*******************************************************************************
 * * $Id:: MatchClassTypeTest.java 157283 2013-12-03 10:10:08Z velasca           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class MatchClassTypeTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(MatchClassType.class);
	}

	@Test
	public void testValues() {
		for (MatchClassType classType : MatchClassType.values()) {
			MatchClassType matchClassType = classType;
			assertEquals(matchClassType.value(), classType.value());
		}
	}
}
