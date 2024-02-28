/*******************************************************************************
 * * $Id:: SearchModeTest.java 157090 2013-12-02 11:54:19Z velasca               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class SearchModeTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(SearchMode.class);
	}

	@Test
	public void testValues() {
		SearchMode modeExactMatch = SearchMode.EXACTMATCH;
		assertEquals(modeExactMatch.value(), SearchMode.EXACTMATCH.value());

		SearchMode modeFullPhrase = SearchMode.FULLPHRASE;
		assertEquals(modeFullPhrase.value(), SearchMode.FULLPHRASE.value());

		SearchMode modeWordsPrefix = SearchMode.WORDSPREFIX;
		assertEquals(modeWordsPrefix.value(), SearchMode.WORDSPREFIX.value());
	}
}
