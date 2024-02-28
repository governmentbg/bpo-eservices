package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.filing.domain.tm.Identifier;

public class LimitedTradeMarkRecordConverterTest {

	private static final String APPLICATION_NUMBER = "123546";
	private static final String LABEL_APPLICATION_NUMBER = "Application Number";
	private static final String REGISTRATION_NUMBER = "654321";
	private static final String LABEL_REGISTRATION_NUMBER = "Registration Number";

	@Test
	public void testConvertApplicationNumberToIdentifier() {
		LimitedTradeMarkRecordConverter converter = new LimitedTradeMarkRecordConverter();
		LimitedTradeMark tradeMark = new LimitedTradeMark();
		tradeMark.setApplicationNumber(APPLICATION_NUMBER);
		Identifier identifier = (Identifier) converter.convert(
				LimitedTradeMark.class, tradeMark);
		assertEquals(identifier.getIdentifierKindCode(),
				LABEL_APPLICATION_NUMBER);
		assertEquals(identifier.getValue(), APPLICATION_NUMBER);
	}

	@Test
	public void testConvertRegistrationNumberToIdentifier() {
		LimitedTradeMarkRecordConverter converter = new LimitedTradeMarkRecordConverter();
		LimitedTradeMark tradeMark = new LimitedTradeMark();
		tradeMark.setRegistrationNumber(REGISTRATION_NUMBER);
		Identifier identifier = (Identifier) converter.convert(
				LimitedTradeMark.class, tradeMark);
		assertEquals(identifier.getIdentifierKindCode(),
				LABEL_REGISTRATION_NUMBER);
		assertEquals(identifier.getValue(), REGISTRATION_NUMBER);
	}

	@Test
	public void testConvert() {
		LimitedTradeMarkRecordConverter converter = new LimitedTradeMarkRecordConverter();
		LimitedTradeMark tradeMark = new LimitedTradeMark();
		tradeMark.setApplicationNumber(APPLICATION_NUMBER);
		Identifier identifier = (Identifier) converter.convert(null, tradeMark,
				LimitedTradeMark.class, String.class);
		assertEquals(identifier.getIdentifierKindCode(),
				LABEL_APPLICATION_NUMBER);
		assertEquals(identifier.getValue(), APPLICATION_NUMBER);
	}

	@Test
	public void testConvertNull() {
		LimitedTradeMarkRecordConverter converter = new LimitedTradeMarkRecordConverter();
		assertEquals(converter.convert(String.class, APPLICATION_NUMBER), null);
	}

}
