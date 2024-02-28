/*******************************************************************************
 * * $Id:: TaxonomyCriteriaTest.java 157090 2013-12-02 11:54:19Z velasca         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class TaxonomyCriteriaTest {
	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TaxonomyCriteria.class);
	}
}
