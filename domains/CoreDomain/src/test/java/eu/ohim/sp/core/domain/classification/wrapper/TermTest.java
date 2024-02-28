/*******************************************************************************
 * * $Id:: TermTest.java 157090 2013-12-02 11:54:19Z velasca                     $
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
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.classification.VerifiedTermResult;
import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class TermTest {

	private static final String LANG = "en";
	private static final String TERM_ID = "termId";
	private static final String TEXT = "text";
	private static final String VERIFICATION_ASSESSMENT = "verificationAssessment";
	private static final Integer NICE_CLASS = 4;

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Term.class);
	}

	@Test
	public void testEquals() {
		Term term1 = new Term();
		term1.setLang(LANG);
		term1.setMatchedTerms(new HashSet<MatchedTerm>());
		term1.setNiceClass(NICE_CLASS);
		term1.setParentIds(new ArrayList<String>());
		term1.setScope(new ArrayList<String>());
		term1.setScopeAcceptability(Boolean.TRUE);
		term1.setTermId(TERM_ID);
		term1.setText(TEXT);
		term1.setVerificationAssessment(VERIFICATION_ASSESSMENT);
		term1.setVerificationResult(VerifiedTermResult.OK);

		Term term2 = new Term();
		term2.setLang(LANG);
		term2.setMatchedTerms(new HashSet<MatchedTerm>());
		term2.setNiceClass(NICE_CLASS);
		term2.setParentIds(new ArrayList<String>());
		term2.setScope(new ArrayList<String>());
		term2.setScopeAcceptability(Boolean.TRUE);
		term2.setTermId(TERM_ID);
		term2.setText(TEXT);
		term2.setVerificationAssessment(VERIFICATION_ASSESSMENT);
		term2.setVerificationResult(VerifiedTermResult.OK);

		assertEquals(term1.getLang(), term2.getLang());
		assertEquals(term1.getMatchedTerms().size(), term2.getMatchedTerms()
				.size());
		assertEquals(term1.getNiceClass(), term2.getNiceClass());
		assertEquals(term1.getParentIds().size(), term2.getParentIds().size());
		assertEquals(term1.getScope().size(), term2.getScope().size());
		assertEquals(term1.isScopeAcceptability(), term2.isScopeAcceptability());
		assertEquals(term1.getTermId(), term2.getTermId());
		assertEquals(term1.getText(), term2.getText());
		assertEquals(term1.getVerificationAssessment(),
				term2.getVerificationAssessment());
		assertEquals(term1.getVerificationResult(),
				term2.getVerificationResult());
	}
}
