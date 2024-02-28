package eu.ohim.sp.core.domain.converter.ds;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.ohim.sp.core.domain.application.EntitlementKind;

public class EntitlementKindEnumConverterTest {

	private static final String EXT = "value";
	private static final String ACCORDING_TO_ASSOCIATION_TO_COMPANY = "According to association of a company";
	private static final String DESIGN_IS_NOT_OFFICIARY = "Design is not officiary";
	private static final String DESIGN_IS_OFFICIARY = "Design is officiary";
	private static final String DUE_TO_SUCESSION = "Due to succession";
	private static final String NOT_APPLICANTS_WITH_WAIVED = "Not applicant with waived";
	private static final String OTHER_GROUNDS = "Other grounds";
	private static final String TRANSFER_OF_RIGHTS = "Transfer of rights";

	@Test
	public void testConvertTo() {
		EntitlementKindEnumConverter converter = new EntitlementKindEnumConverter();

		assertEquals(converter.convertTo(
				EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY, EXT),
				ACCORDING_TO_ASSOCIATION_TO_COMPANY);
		assertEquals(converter.convertTo(
				EntitlementKind.DESIGN_IS_NOT_OFFICIARY, EXT),
				DESIGN_IS_NOT_OFFICIARY);
		assertEquals(
				converter.convertTo(EntitlementKind.DESIGN_IS_OFFICIARY, EXT),
				DESIGN_IS_OFFICIARY);
		assertEquals(
				converter.convertTo(EntitlementKind.DUE_TO_SUCESSION, EXT),
				DUE_TO_SUCESSION);
		assertEquals(converter.convertTo(
				EntitlementKind.NOT_APPLICANTS_WITH_WAIVED, EXT),
				NOT_APPLICANTS_WITH_WAIVED);
		assertEquals(converter.convertTo(EntitlementKind.OTHER_GROUNDS, EXT),
				OTHER_GROUNDS);
		assertEquals(
				converter.convertTo(EntitlementKind.TRANSFER_OF_RIGHTS, EXT),
				TRANSFER_OF_RIGHTS);
	}

	@Test
	public void testConvertFrom() {
		EntitlementKindEnumConverter converter = new EntitlementKindEnumConverter();

		assertEquals(converter.convertFrom(ACCORDING_TO_ASSOCIATION_TO_COMPANY,
				EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY),
				EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY);

		assertEquals(converter.convertFrom(DESIGN_IS_NOT_OFFICIARY,
				EntitlementKind.DESIGN_IS_NOT_OFFICIARY),
				EntitlementKind.DESIGN_IS_NOT_OFFICIARY);

		assertEquals(converter.convertFrom(DESIGN_IS_OFFICIARY,
				EntitlementKind.DESIGN_IS_OFFICIARY),
				EntitlementKind.DESIGN_IS_OFFICIARY);

		assertEquals(converter.convertFrom(DUE_TO_SUCESSION,
				EntitlementKind.DUE_TO_SUCESSION),
				EntitlementKind.DUE_TO_SUCESSION);

		assertEquals(converter.convertFrom(NOT_APPLICANTS_WITH_WAIVED,
				EntitlementKind.NOT_APPLICANTS_WITH_WAIVED),
				EntitlementKind.NOT_APPLICANTS_WITH_WAIVED);

		assertEquals(converter.convertFrom(OTHER_GROUNDS,
				EntitlementKind.OTHER_GROUNDS), EntitlementKind.OTHER_GROUNDS);

		assertEquals(converter.convertFrom(TRANSFER_OF_RIGHTS,
				EntitlementKind.TRANSFER_OF_RIGHTS),
				EntitlementKind.TRANSFER_OF_RIGHTS);

	}

}
