package eu.ohim.sp.core.domain.dossier.letters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class RecipientTest {

	private static final int ID_PERSON = 1;
	private static final String NAME = "name";
	private static final String STREET = "street";
	private static final String STREET_NUMBER = "streetNumber";
	private static final String POST_CODE = "postCode";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String COUNTRY = "country";
	private static final String TELEPHONE = "23132132";
	private static final String EMAIL = "someone@some.com";
	private static final String NAME_FULL = "nameFull";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Recipient.class);
	}

	@Test
	public void testEquals() {
		Recipient recipient1 = new Recipient();
		recipient1.setCity(CITY);
		recipient1.setCountry(COUNTRY);
		recipient1.setEmail(EMAIL);
		recipient1.setIdPerson(ID_PERSON);
		recipient1.setName(NAME);
		recipient1.setNameFull(NAME_FULL);
		recipient1.setPostCode(POST_CODE);
		recipient1.setState(STATE);
		recipient1.setStreet(STREET);
		recipient1.setStreetNumber(STREET_NUMBER);
		recipient1.setTelephone(TELEPHONE);
		recipient1.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		Recipient recipient2 = new Recipient();
		recipient2.setCity(CITY);
		recipient2.setCountry(COUNTRY);
		recipient2.setEmail(EMAIL);
		recipient2.setIdPerson(ID_PERSON);
		recipient2.setName(NAME);
		recipient2.setNameFull(NAME_FULL);
		recipient2.setPostCode(POST_CODE);
		recipient2.setState(STATE);
		recipient2.setStreet(STREET);
		recipient2.setStreetNumber(STREET_NUMBER);
		recipient2.setTelephone(TELEPHONE);
		recipient2.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		assertTrue(recipient1.equals(recipient2));
		assertTrue(recipient1.equals(recipient1));
		assertEquals(recipient1.hashCode(), recipient2.hashCode());
	}

	@Test
	public void testNotEquals() {
		Recipient recipient = new Recipient();
		recipient.setCity(CITY);
		recipient.setCountry(COUNTRY);
		recipient.setEmail(EMAIL);
		recipient.setIdPerson(ID_PERSON);
		recipient.setName(NAME);
		recipient.setNameFull(NAME_FULL);
		recipient.setPostCode(POST_CODE);
		recipient.setState(STATE);
		recipient.setStreet(STREET);
		recipient.setStreetNumber(STREET_NUMBER);
		recipient.setTelephone(TELEPHONE);
		recipient.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);

		assertEquals(recipient.equals(null), false);
		assertEquals(recipient.equals(new LetterTemplate()), false);
	}
	
	@Test
	public void testToString() {
		Recipient recipient = new Recipient();
		recipient.setCity(CITY);
		recipient.setCountry(COUNTRY);
		recipient.setEmail(EMAIL);
		recipient.setIdPerson(ID_PERSON);
		recipient.setName(NAME);
		recipient.setNameFull(NAME_FULL);
		recipient.setPostCode(POST_CODE);
		recipient.setState(STATE);
		recipient.setStreet(STREET);
		recipient.setStreetNumber(STREET_NUMBER);
		recipient.setTelephone(TELEPHONE);
		recipient.setTypeMethod(LetterTypeSendMethod.ELECTRONIC);
		
		assertTrue(recipient.toString().contains(CITY));
	}

}
