package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class AbsoluteGroundLawArticleTest {

	private static final String LANGUAGE = "en";
	private static final String REFERENCE = "reference";
	private static final String TEXT = "text";

	@Test
    public void bean_testProperties() throws IntrospectionException
    {
        JavaBeanTester.test(AbsoluteGroundLawArticle.class);
    }
	
	@Test
	public void testEquals() {
		AbsoluteGroundLawArticle absoluteGroundLawArticle = new AbsoluteGroundLawArticle();
		absoluteGroundLawArticle.setLanguage(LANGUAGE);
		absoluteGroundLawArticle.setReference(REFERENCE);
		absoluteGroundLawArticle.setText(TEXT);

		AbsoluteGroundLawArticle absoluteGroundLawArticle2 = new AbsoluteGroundLawArticle();
		absoluteGroundLawArticle2.setLanguage(LANGUAGE);
		absoluteGroundLawArticle2.setReference(REFERENCE);
		absoluteGroundLawArticle2.setText(TEXT);

		assertEquals(absoluteGroundLawArticle.getLanguage(),
				absoluteGroundLawArticle2.getLanguage());
		assertEquals(absoluteGroundLawArticle.getReference(),
				absoluteGroundLawArticle2.getReference());
		assertEquals(absoluteGroundLawArticle.getText(),
				absoluteGroundLawArticle2.getText());
	}

}
