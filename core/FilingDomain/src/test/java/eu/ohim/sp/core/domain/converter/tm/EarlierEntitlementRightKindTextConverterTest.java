package eu.ohim.sp.core.domain.converter.tm;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;

public class EarlierEntitlementRightKindTextConverterTest {

	@Test
	public void testConvertToString() {
		EarlierEntitlementRightKindTextConverter converter = new EarlierEntitlementRightKindTextConverter();
		assertEquals(converter.convert(EarlierEntitlementRightKind.class,
				EarlierEntitlementRightKind.BUSINESS_NAME),
				EarlierEntitlementRightKind.BUSINESS_NAME.toString());
	}

	@Test
	public void testConvertToEarlierEntitlementRightKind() {
		EarlierEntitlementRightKindTextConverter converter = new EarlierEntitlementRightKindTextConverter();
		for (EarlierEntitlementRightKind value : EarlierEntitlementRightKind
				.values()) {
			assertEquals(converter.convert(String.class, value.toString()),
					value);
		}
	}

	@Test
	public void testConvert() {
		EarlierEntitlementRightKindTextConverter converter = new EarlierEntitlementRightKindTextConverter();
		assertEquals(converter.convert(null,
				EarlierEntitlementRightKind.CONTESTED_APPLICATION,
				String.class, EarlierEntitlementRightKind.class),
				EarlierEntitlementRightKind.CONTESTED_APPLICATION.toString());
	}
}
