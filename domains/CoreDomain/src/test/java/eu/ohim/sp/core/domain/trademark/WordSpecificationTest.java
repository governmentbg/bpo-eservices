package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.common.CharacterSet;
import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class WordSpecificationTest {

	private static final short SERIES_IDENTIFIER = 1;
	private static final String TRANSCRIPTION_DETAILS = "transcriptionDetails";
	private static final String TRANSLITERATION_DETAILS = "transliterationDetails";
	private static final String WORD_ELEMENTS = "wordElements";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(WordSpecification.class);
	}

	@Test
	public void testEquals() {
		WordSpecification wordSpecification1 = new WordSpecification();
		wordSpecification1.setCharacterSet(CharacterSet.GEORGIAN.toString());
		wordSpecification1.setSeriesIdentifier(SERIES_IDENTIFIER);
		wordSpecification1.setTranscriptionDetails(TRANSCRIPTION_DETAILS);
		wordSpecification1.setTransliterationDetails(TRANSLITERATION_DETAILS);
		wordSpecification1.setWordElements(WORD_ELEMENTS);

		WordSpecification wordSpecification2 = new WordSpecification();
		wordSpecification2.setCharacterSet(CharacterSet.GEORGIAN.toString());
		wordSpecification2.setSeriesIdentifier(SERIES_IDENTIFIER);
		wordSpecification2.setTranscriptionDetails(TRANSCRIPTION_DETAILS);
		wordSpecification2.setTransliterationDetails(TRANSLITERATION_DETAILS);
		wordSpecification2.setWordElements(WORD_ELEMENTS);

		assertEquals(wordSpecification1.getCharacterSet(),
				wordSpecification2.getCharacterSet());
		assertEquals(wordSpecification1.getSeriesIdentifier(),
				wordSpecification2.getSeriesIdentifier());
		assertEquals(wordSpecification1.getTranscriptionDetails(),
				wordSpecification2.getTranscriptionDetails());
		assertEquals(wordSpecification1.getTransliterationDetails(),
				wordSpecification2.getTransliterationDetails());
		assertEquals(wordSpecification1.getWordElements(),
				wordSpecification2.getWordElements());
	}
}
