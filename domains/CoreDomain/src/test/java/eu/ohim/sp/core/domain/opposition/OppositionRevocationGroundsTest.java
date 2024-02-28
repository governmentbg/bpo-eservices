package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class OppositionRevocationGroundsTest {

	private static final String LANGUAGE = "en";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(OppositionRevocationGrounds.class);
	}

	@Test
	public void testEquals() {
		OppositionRevocationGrounds oppositionRevocationGrounds1 = new OppositionRevocationGrounds();
		oppositionRevocationGrounds1
				.setRevocationGroundLawArticles(new ArrayList<RevocationGroundLawArticle>());
		oppositionRevocationGrounds1.getRevocationGroundLawArticles().add(
				new RevocationGroundLawArticle());
		oppositionRevocationGrounds1.getRevocationGroundLawArticles().get(0)
				.setLanguage(LANGUAGE);

		OppositionRevocationGrounds oppositionRevocationGrounds2 = new OppositionRevocationGrounds();
		oppositionRevocationGrounds2
				.setRevocationGroundLawArticles(new ArrayList<RevocationGroundLawArticle>());
		oppositionRevocationGrounds2.getRevocationGroundLawArticles().add(
				new RevocationGroundLawArticle());
		oppositionRevocationGrounds2.getRevocationGroundLawArticles().get(0)
				.setLanguage(LANGUAGE);

		assertEquals(oppositionRevocationGrounds1
				.getRevocationGroundLawArticles().get(0).getLanguage(),
				oppositionRevocationGrounds2.getRevocationGroundLawArticles()
						.get(0).getLanguage());
	}
}
