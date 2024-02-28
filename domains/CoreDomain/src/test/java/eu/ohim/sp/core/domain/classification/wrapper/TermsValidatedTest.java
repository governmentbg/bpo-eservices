package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class TermsValidatedTest {

	private static final String DESCRIPTION = "description";
	private static final String LANGUAGE = "en";
	private static final String NICE_CLASS = "niceClass";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TermsValidated.class);
	}

	@Test
	public void testEquals() {
		List<Term> terms = new ArrayList<Term>();
		terms.add(new Term());
		terms.get(0).setLang(LANGUAGE);

		TermsValidated termsValidated1 = new TermsValidated();
		termsValidated1.setDescription(DESCRIPTION);
		termsValidated1.setLanguage(LANGUAGE);
		termsValidated1.setNiceClass(NICE_CLASS);
		termsValidated1.setTerms(terms);

		TermsValidated termsValidated2 = new TermsValidated(NICE_CLASS, terms,
				LANGUAGE, DESCRIPTION);

		assertEquals(termsValidated1.getDescription(),
				termsValidated2.getDescription());
		assertEquals(termsValidated1.getLanguage(),
				termsValidated2.getLanguage());
		assertEquals(termsValidated1.getNiceClass(),
				termsValidated2.getNiceClass());
		assertEquals(termsValidated1.getTerms().get(0).getLang(),
				termsValidated2.getTerms().get(0).getLang());

	}

}
