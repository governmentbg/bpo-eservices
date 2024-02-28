package eu.ohim.sp.core.domain.person;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class PersonIdentifierTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PersonIdentifier.class);
	}

}
