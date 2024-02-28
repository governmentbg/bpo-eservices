package eu.ohim.sp.core.domain.trademark;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class MarkDescriptionTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(MarkDescription.class);
	}

}
