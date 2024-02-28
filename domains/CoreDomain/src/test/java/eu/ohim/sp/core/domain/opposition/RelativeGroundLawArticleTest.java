package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class RelativeGroundLawArticleTest {

	private static final String VALUE = "value";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(RelativeGroundLawArticle.class);
	}

	@Test
	public void test() {
		RelativeGroundLawArticle relativeGroundLawArticle1 = new RelativeGroundLawArticle();
		relativeGroundLawArticle1.setReputationClaim(Boolean.TRUE);
		relativeGroundLawArticle1.setExclusivity(Boolean.TRUE);
		relativeGroundLawArticle1
				.setExcludedLawArticleReference(new ArrayList<String>());
		relativeGroundLawArticle1.getExcludedLawArticleReference().add(VALUE);

		RelativeGroundLawArticle relativeGroundLawArticle2 = new RelativeGroundLawArticle();
		relativeGroundLawArticle2.setReputationClaim(Boolean.TRUE);
		relativeGroundLawArticle2.setExclusivity(Boolean.TRUE);
		relativeGroundLawArticle2
				.setExcludedLawArticleReference(new ArrayList<String>());
		relativeGroundLawArticle2.getExcludedLawArticleReference().add(VALUE);

		assertEquals(relativeGroundLawArticle1.getReputationClaim(),
				relativeGroundLawArticle2.getReputationClaim());
		assertEquals(relativeGroundLawArticle1.getExclusivity(),
				relativeGroundLawArticle2.getExclusivity());
		assertEquals(relativeGroundLawArticle1.getExcludedLawArticleReference()
				.get(0), relativeGroundLawArticle2
				.getExcludedLawArticleReference().get(0));

	}

}
