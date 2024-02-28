package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;

public class PhoneConverterTest {

	private static final String NUMBER = "123123123";

	@Test
	public void testConvertToString() {
		PhoneConverter converter = new PhoneConverter();
		Phone phone = new Phone();
		phone.setNumber(NUMBER);
		phone.setPhoneKind(PhoneKind.FAX);

		String result = (String) converter.convert(null, phone,
				String.class, Phone.class);

		assertEquals(result, NUMBER);
	}

	@Test
	public void testConvertToPhone() {
		PhoneConverter converter = new PhoneConverter();
		Phone result = (Phone) converter.convert(null, NUMBER,
				Phone.class, String.class);

		assertEquals(result.getNumber(), NUMBER);
	}

}
