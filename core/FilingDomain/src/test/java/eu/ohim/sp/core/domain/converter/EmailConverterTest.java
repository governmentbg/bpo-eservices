package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Email;

public class EmailConverterTest {

	private static final String EMAIL = "someone@some.com";

	@Test
	public void testConvertStringToEmail() {
		EmailConverter emailConverter = new EmailConverter();
		Email email = (Email) emailConverter.convert(Email.class, EMAIL);
		assertEquals(email.getEmailAddress(), EMAIL);
	}

	@Test
	public void testConvertEmailToString() {
		EmailConverter emailConverter = new EmailConverter();
		Email email = new Email();
		email.setEmailAddress(EMAIL);

		String emailAddress = (String) emailConverter.convert(String.class,
				email);
		assertEquals(emailAddress, EMAIL);
	}

	@Test
	public void testConvertAnotherClassToEmail() {
		EmailConverter emailConverter = new EmailConverter();
		Email email = (Email) emailConverter.convert(Email.class,
				BigInteger.ONE);
		assertEquals(email, null);
	}

	@Test
	public void testConverter() {
		EmailConverter emailConverter = new EmailConverter();
		Email email = (Email) emailConverter.convert(null, EMAIL, Email.class,
				String.class);
		assertEquals(email.getEmailAddress(), EMAIL);
	}

}
