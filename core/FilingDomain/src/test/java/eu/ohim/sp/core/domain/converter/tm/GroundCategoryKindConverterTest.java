package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;

public class GroundCategoryKindConverterTest {

	private static final String ABSOLUTE_GROUNDS = "Absolute Ground Basis";
	private static final String OTHER = "Other";

	@Test
	public void testConvertToString() {
		GroundCategoryKindConverter converter = new GroundCategoryKindConverter();
		assertEquals(converter.convert(String.class,
				GroundCategoryKind.ABSOLUTE_GROUNDS), ABSOLUTE_GROUNDS);
		assertEquals(converter.convert(String.class,
				GroundCategoryKind.RELATIVE_GROUNDS), OTHER);
	}

	@Test
	public void testConvertToGroundCategoryKind() {
		GroundCategoryKindConverter converter = new GroundCategoryKindConverter();
		assertEquals(
				converter.convert(GroundCategoryKind.class, ABSOLUTE_GROUNDS),
				null);
	}

	@Test
	public void testConvert() {
		GroundCategoryKindConverter converter = new GroundCategoryKindConverter();
		assertEquals(converter.convert(null,
				GroundCategoryKind.ABSOLUTE_GROUNDS, GroundCategoryKind.class,
				String.class), ABSOLUTE_GROUNDS);
	}

}
