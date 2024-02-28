/*******************************************************************************
 * * $Id:: MarkFeatureTest.java 157237 2013-12-02 17:00:12Z velasca              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class MarkFeatureTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(MarkFeature.class);
	}

	@Test
	public void testValues() {
		for (MarkFeature feature : MarkFeature.values()) {
			MarkFeature markFeature = feature;
			assertEquals(markFeature.value(), feature.value());
			assertEquals(markFeature.toString(), feature.toString());
		}
	}
}
