package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.ohim.sp.core.domain.opposition.LawArticle;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.filing.domain.tm.OppositionGroundArticleType;

public class LegalActVersionLawArticleConverterTest {

	private static int ARTICLE_ID = 1;
	private static String LANGUAGE = "en";
	private static String REFERENCE = "reference";
	private static String TEXT = "text";

	@Test
	public void testConvertLegalActVersion() {
		LegalActVersionLawArticleConverter converter = new LegalActVersionLawArticleConverter();
		LegalActVersion legalActVersion = new LegalActVersion();
		legalActVersion.setArticles(new ArrayList<LawArticle>());
		legalActVersion.getArticles().add(new LawArticle());
		legalActVersion.getArticles().get(0).setId(ARTICLE_ID);
		legalActVersion.getArticles().get(0).setLanguage(LANGUAGE);
		legalActVersion.getArticles().get(0).setReference(REFERENCE);
		legalActVersion.getArticles().get(0).setText(TEXT);

		List<OppositionGroundArticleType> opGroundArticles = (List<OppositionGroundArticleType>) converter
				.convert(null, legalActVersion);

		assertEquals(opGroundArticles.get(0)
				.getOppositionGroundArticleReference().getValue(), REFERENCE);
		assertEquals(opGroundArticles.get(0).getOppositionGroundArticleText()
				.getValue(), TEXT);
		assertEquals(opGroundArticles.get(0).getOppositionGroundLegalAct()
				.getValue(), null);
	}

	@Test
	public void testConvertNull() {
		LegalActVersionLawArticleConverter converter = new LegalActVersionLawArticleConverter();
		assertEquals(converter.convert(null, null, null, null), null);
	}

	@Test
	public void testConvertNullResult() {
		LegalActVersionLawArticleConverter converter = new LegalActVersionLawArticleConverter();
		assertEquals(converter.convert(null, null), null);
	}

}
