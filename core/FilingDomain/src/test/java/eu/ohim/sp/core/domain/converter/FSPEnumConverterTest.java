package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.core.domain.dossier.DossierKind;

public class FSPEnumConverterTest {

	private static final String VALUE = "value";

	@Test
	public void testConvertNull() {
		FSPEnumConverter converter = new FSPEnumConverter();
		assertEquals(converter.convert(null, null), null);
	}

	@Test
	public void testConvertEmptyString() {
		FSPEnumConverter converter = new FSPEnumConverter();
		assertEquals(converter.convert(String.class, ""), null);
	}

	@Test
	public void testConvertString() {
		FSPEnumConverter converter = new FSPEnumConverter();
		assertEquals(converter.convert(String.class, VALUE), VALUE);
	}

	@Test
	public void testConvertEnum() {
		FSPEnumConverter converter = new FSPEnumConverter();
		assertEquals(converter.convert(DossierKind.class,
				DossierKind.TRADEMARK_APPLICATION),
				DossierKind.TRADEMARK_APPLICATION);
	}

	@Test
	public void testConvert() {
		FSPEnumConverter converter = new FSPEnumConverter();
		assertEquals(
				converter.convert(VALUE, VALUE, String.class, String.class),
				VALUE);
	}

}
