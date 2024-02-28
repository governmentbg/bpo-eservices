/*******************************************************************************
 * * $Id:: InternationalTradeMarkCodeTest.java 157283 2013-12-03 10:10:08Z velas#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.claim;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

/**
 * @author ionitdi
 */
public class InternationalTradeMarkCodeTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(InternationalTradeMarkCode.class);
	}

	@Test
	public void testValues() {
		for (InternationalTradeMarkCode code : InternationalTradeMarkCode
				.values()) {
			InternationalTradeMarkCode internationalTradeMarkCode = code;
			assertEquals(internationalTradeMarkCode.value(), code.value());
			assertEquals(internationalTradeMarkCode.toString(), code.toString());
		}
	}
}
