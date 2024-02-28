package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;

public class GroundCategoryKindTextConverterTest {

	@Test
	public void testConvertToAbsoluteGroundsString() {
		GroundCategoryKindTextConverter converter = new GroundCategoryKindTextConverter();
		assertEquals(converter.convert(GroundCategoryKind.class,
				GroundCategoryKind.ABSOLUTE_GROUNDS.toString()),
				GroundCategoryKind.ABSOLUTE_GROUNDS);
	}
	
	@Test
	public void testConvertToRevocationGroundsString() {
		GroundCategoryKindTextConverter converter = new GroundCategoryKindTextConverter();
		assertEquals(converter.convert(GroundCategoryKind.class,
				GroundCategoryKind.REVOCATION_GROUNDS.toString()),
				GroundCategoryKind.REVOCATION_GROUNDS);
	}

	@Test
	public void testConvertToGroundCategoryKind() {
		GroundCategoryKindTextConverter converter = new GroundCategoryKindTextConverter();
		assertEquals(converter.convert(String.class,
				GroundCategoryKind.ABSOLUTE_GROUNDS),
				GroundCategoryKind.ABSOLUTE_GROUNDS.toString());
		assertEquals(converter.convert(String.class,
				GroundCategoryKind.RELATIVE_GROUNDS),
				null);
	}

	@Test
	public void testConvert() {
		GroundCategoryKindTextConverter converter = new GroundCategoryKindTextConverter();
		assertEquals(converter.convert(null,
				GroundCategoryKind.ABSOLUTE_GROUNDS, GroundCategoryKind.class,
				String.class), GroundCategoryKind.ABSOLUTE_GROUNDS.toString());
	}
}
