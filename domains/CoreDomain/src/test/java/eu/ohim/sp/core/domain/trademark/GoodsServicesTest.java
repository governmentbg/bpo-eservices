/*******************************************************************************
 * * $Id:: GoodsServicesTest.java 157237 2013-12-02 17:00:12Z velasca            $
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
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class GoodsServicesTest {

	private static final String CLASS_NUMBER = "classNumber";
	private static final String DESCRIPTION = "description";
	private static final String LANGUAGE_CODE = "en";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassDescription.class);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testEquals() {
		GoodsServices goodsServices1 = new GoodsServices();
		goodsServices1.setClassNumber(CLASS_NUMBER);
		goodsServices1.setFullClassCoverageIndicator(Boolean.TRUE);
		goodsServices1.setGoodsServicesDescription(DESCRIPTION);
		goodsServices1.setLanguageCode(LANGUAGE_CODE);
		goodsServices1.setTerms(new ArrayList<ClassificationTerm>());

		GoodsServices goodsServices2 = new GoodsServices();
		goodsServices2.setClassNumber(CLASS_NUMBER);
		goodsServices2.setFullClassCoverageIndicator(Boolean.TRUE);
		goodsServices2.setGoodsServicesDescription(DESCRIPTION);
		goodsServices2.setLanguageCode(LANGUAGE_CODE);
		goodsServices2.setTerms(new ArrayList<ClassificationTerm>());

		assertEquals(goodsServices1.getClassNumber(),
				goodsServices2.getClassNumber());
		assertEquals(goodsServices1.isFullClassCoverageIndicator(),
				goodsServices2.isFullClassCoverageIndicator());
		assertEquals(goodsServices1.getGoodsServicesDescription(),
				goodsServices2.getGoodsServicesDescription());
		assertEquals(goodsServices1.getLanguageCode(),
				goodsServices2.getLanguageCode());
		assertEquals(goodsServices1.getTerms().size(), goodsServices2
				.getTerms().size());
	}
}
