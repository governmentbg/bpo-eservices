/*******************************************************************************
 * * $Id:: TermsToBeValidatedTest.java 157090 2013-12-02 11:54:19Z velasca       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class TermsToBeValidatedTest {

	private static final String LANGUAGE = "en";
	private static final String NICE_CLASS = "niceClass";
	private static final String OFFICE = "EM";
	private static final String TERMS = "terms";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TermsToBeValidated.class);
	}

	@Test
	public void testEquals() {
		TermsToBeValidated termsToBeValidated1 = new TermsToBeValidated();
		termsToBeValidated1.setLanguage(LANGUAGE);
		termsToBeValidated1.setNiceClass(NICE_CLASS);
		termsToBeValidated1.setOffice(OFFICE);
		termsToBeValidated1.setTerms(TERMS);

		TermsToBeValidated termsToBeValidated2 = new TermsToBeValidated();
		termsToBeValidated2.setLanguage(LANGUAGE);
		termsToBeValidated2.setNiceClass(NICE_CLASS);
		termsToBeValidated2.setOffice(OFFICE);
		termsToBeValidated2.setTerms(TERMS);

		assertEquals(termsToBeValidated1.getLanguage(),
				termsToBeValidated2.getLanguage());
		assertEquals(termsToBeValidated1.getNiceClass(),
				termsToBeValidated2.getNiceClass());
		assertEquals(termsToBeValidated1.getOffice(),
				termsToBeValidated2.getOffice());
		assertEquals(termsToBeValidated1.getTerms(),
				termsToBeValidated2.getTerms());
	}

	@Test
	public void testToString() {
		TermsToBeValidated termsToBeValidated = new TermsToBeValidated();
		termsToBeValidated.setLanguage(LANGUAGE);
		termsToBeValidated.setNiceClass(NICE_CLASS);
		termsToBeValidated.setOffice(OFFICE);
		termsToBeValidated.setTerms(TERMS);

		assertEquals(termsToBeValidated.toString(),
				"TermsToBeValidated [office=" + termsToBeValidated.getOffice()
						+ ", language=" + termsToBeValidated.getLanguage()
						+ ", terms=" + termsToBeValidated.getTerms()
						+ ", niceClass=" + termsToBeValidated.getNiceClass()
						+ "]");
	}
}
