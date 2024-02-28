package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class RevocationGroundLawArticleTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(RevocationGroundLawArticle.class);
	}

	@Test
	public void testEquals() {
		RevocationGroundLawArticle revocationGroundLawArticle1 = new RevocationGroundLawArticle();
		revocationGroundLawArticle1.setNonUse(Boolean.TRUE);
		revocationGroundLawArticle1.setNonUsePeriod(5);

		RevocationGroundLawArticle revocationGroundLawArticle2 = new RevocationGroundLawArticle();
		revocationGroundLawArticle2.setNonUse(Boolean.TRUE);
		revocationGroundLawArticle2.setNonUsePeriod(5);

		assertEquals(revocationGroundLawArticle1.getNonUse(),
				revocationGroundLawArticle2.getNonUse());
		assertEquals(revocationGroundLawArticle1.getNonUsePeriod(),
				revocationGroundLawArticle2.getNonUsePeriod());
	}

}
