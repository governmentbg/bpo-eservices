package eu.ohim.sp.core.domain.person;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class GenderTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Gender.class);
	}

	@Test
	public void testValues() {
		Gender female = Gender.FEMALE;
		Gender male = Gender.MALE;

		assertEquals(female.value(), Gender.FEMALE.value());
		assertEquals(male.value(), Gender.MALE.value());
	}

}
