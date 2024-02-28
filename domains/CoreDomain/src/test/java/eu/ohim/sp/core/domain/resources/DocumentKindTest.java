/*******************************************************************************
 * * $Id:: DocumentKindTest.java 157283 2013-12-03 10:10:08Z velasca             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.resources;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class DocumentKindTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DocumentKind.class);
	}

	@Test
	public void testValues() {
		for (DocumentKind kind : DocumentKind.values()) {
			DocumentKind documentKind = kind;
			assertEquals(documentKind.value(), kind.value());
		}
	}
}
