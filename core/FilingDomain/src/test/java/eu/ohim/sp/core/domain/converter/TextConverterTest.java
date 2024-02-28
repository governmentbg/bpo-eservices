package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.filing.domain.tm.Text;

public class TextConverterTest {

	private static final String VALUE = "value";
	private static final String LANGUAGE = "en";

	@Test
	public void testConverterString() {
		TextConverter converter = new TextConverter();
		Text text = (Text) converter.convert(Text.class, new Text(VALUE,
				LANGUAGE));
		assertEquals(text.getValue(), VALUE);
	}

	@Test
	public void testConverterEnum() {
		TextConverter converter = new TextConverter();
		assertEquals(converter.convert(PhoneKind.class, PhoneKind.FIXED), null);
	}

	@Test
	public void testConverter() {
		TextConverter converter = new TextConverter();
		Text text = (Text) converter.convert(null, new Text(VALUE, LANGUAGE),
				Text.class, null);
		assertEquals(text.getLanguage(), LANGUAGE);
	}

}
