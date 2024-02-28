package eu.ohim.sp.core.domain.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class EmailTest {

	private static final String EMAIL_ADDRESS = "emailAddress";
	private static final String KIND = "kind";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Email.class);
	}

	@Test
	public void testEquals() {
		Email email1 = new Email();
		email1.setEmailAddress(EMAIL_ADDRESS);
		email1.setKind(KIND);

		Email email2 = new Email();
		email2.setEmailAddress(EMAIL_ADDRESS);
		email2.setKind(KIND);

		assertTrue(email1.equals(email2));
		assertTrue(email1.equals(email1));
		assertEquals(email1.hashCode(), email2.hashCode());
	}

	@Test
	public void testNotEquals() {
		Email email = new Email();
		email.setEmailAddress(EMAIL_ADDRESS);
		email.setKind(KIND);

		assertEquals(email.equals(null), false);
		assertEquals(email.equals(new Phone()), false);
	}

	@Test
	public void testToString() {
		Email email = new Email();
		email.setEmailAddress(EMAIL_ADDRESS);
		email.setKind(KIND);

		assertTrue(email.toString().contains(EMAIL_ADDRESS));
	}

}
