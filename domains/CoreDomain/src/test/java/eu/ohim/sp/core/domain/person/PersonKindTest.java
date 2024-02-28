package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class PersonKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonKind.class);
	}

	@Test
	public void testValues() {
		PersonKind personKindCorporation = PersonKind.CORPORATION_BY_CIVIL_LAW;
		PersonKind personKindLegalEntity = PersonKind.LEGAL_ENTITY;
		PersonKind personKindNaturalPerson = PersonKind.NATURAL_PERSON;
		PersonKind personKindNaturalPersonSpecial = PersonKind.NATURAL_PERSON_SPECIAL;
		PersonKind personKindOther = PersonKind.OTHER;

		assertEquals(personKindCorporation.value(),
				PersonKind.CORPORATION_BY_CIVIL_LAW.value());
		assertEquals(personKindLegalEntity.value(),
				PersonKind.LEGAL_ENTITY.value());
		assertEquals(personKindNaturalPerson.value(),
				PersonKind.NATURAL_PERSON.value());
		assertEquals(personKindNaturalPersonSpecial.value(),
				PersonKind.NATURAL_PERSON_SPECIAL.value());
		assertEquals(personKindOther.value(), PersonKind.OTHER.value());
	}

}
