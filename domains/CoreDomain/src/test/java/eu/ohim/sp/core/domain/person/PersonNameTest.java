package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.common.CharacterSet;
import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class PersonNameTest {

	private static final String FIRST_NAME = "firstName";
	private static final String MIDDLE_NAME = "middleName";
	private static final String LAST_NAME = "lastName";
	private static final String SECOND_LAST_NAME = "secondLastName";
	private static final String ORGANIZATION_NAME = "organizationName";
	private static final String TITLE = "title";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonName.class);
	}

	@Test
	public void testEquals() {
		PersonName personName1 = new PersonName();
		personName1.setCharacterSet(CharacterSet.GEORGIAN);
		personName1.setFirstName(FIRST_NAME);
		personName1.setLastName(LAST_NAME);
		personName1.setMiddleName(MIDDLE_NAME);
		personName1.setOrganizationName(ORGANIZATION_NAME);
		personName1.setSecondLastName(SECOND_LAST_NAME);
		personName1.setTitle(TITLE);

		PersonName personName2 = new PersonName();
		personName2.setCharacterSet(CharacterSet.GEORGIAN);
		personName2.setFirstName(FIRST_NAME);
		personName2.setLastName(LAST_NAME);
		personName2.setMiddleName(MIDDLE_NAME);
		personName2.setOrganizationName(ORGANIZATION_NAME);
		personName2.setSecondLastName(SECOND_LAST_NAME);
		personName2.setTitle(TITLE);

		assertEquals(personName1, personName2);
		assertEquals(personName1.hashCode(), personName2.hashCode());
	}

	@Test
	public void testToString() {
		PersonName personName = new PersonName();
		personName.setCharacterSet(CharacterSet.GEORGIAN);
		personName.setFirstName(FIRST_NAME);
		personName.setLastName(LAST_NAME);
		personName.setMiddleName(MIDDLE_NAME);
		personName.setOrganizationName(ORGANIZATION_NAME);
		personName.setSecondLastName(SECOND_LAST_NAME);
		personName.setTitle(TITLE);

		assertEquals(
				personName.toString(),
				"PersonName [characterSet=" + personName.getCharacterSet()
						+ ", firstName=" + personName.getFirstName()
						+ ", middleName=" + personName.getMiddleName()
						+ ", lastName=" + personName.getLastName()
						+ ", secondLastName=" + personName.getSecondLastName()
						+ ", organizationName="
						+ personName.getOrganizationName() + "]");
	}
}
