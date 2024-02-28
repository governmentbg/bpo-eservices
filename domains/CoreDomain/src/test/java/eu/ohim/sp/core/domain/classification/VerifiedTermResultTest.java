/*******************************************************************************
 * * $Id:: VerifiedTermResultTest.java 157283 2013-12-03 10:10:08Z velasca       $
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

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class VerifiedTermResultTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(VerifiedTermResult.class);
	}

	@Test
	public void testValues() {
		for (VerifiedTermResult result : VerifiedTermResult.values()) {
			VerifiedTermResult verifiedTermResult = result;
			assertEquals(verifiedTermResult.value(), result.value());
		}
	}
}
