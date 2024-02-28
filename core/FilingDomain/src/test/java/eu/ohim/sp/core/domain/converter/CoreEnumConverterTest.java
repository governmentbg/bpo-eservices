package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.filing.domain.tm.DocumentKind;

public class CoreEnumConverterTest {

	private static final String VALUE = "value";
	private static final String EMAIL = "someone@some.com";

	@Test
	public void testConvertNull() {
		CoreEnumConverter coreEnumConverter = new CoreEnumConverter();
		assertEquals(coreEnumConverter.convert(null, null), null);
	}

	@Test
	public void testConvertString() {
		CoreEnumConverter coreEnumConverter = new CoreEnumConverter();
		assertEquals(coreEnumConverter.convert(String.class, VALUE), VALUE);
	}

	@Test
	public void testConvertMethod() {
		CoreEnumConverter coreEnumConverter = new CoreEnumConverter();
		assertEquals(coreEnumConverter.convert(String.class,
				DocumentKind.APPLICATION_RECEIPT),
				DocumentKind.APPLICATION_RECEIPT.value());
	}

	@Test
	public void testConvertEmail() {
		CoreEnumConverter coreEnumConverter = new CoreEnumConverter();
		Email email = new Email();
		email.setEmailAddress(EMAIL);
		String mail = (String) coreEnumConverter.convert(String.class, email);
		assertEquals(mail, EMAIL);
	}

	@Test
	public void testConvert() {
		CoreEnumConverter coreEnumConverter = new CoreEnumConverter();
		String email = (String) coreEnumConverter.convert(null, EMAIL,
				String.class, Email.class);
		assertEquals(email, EMAIL);
	}
}
