package eu.ohim.sp.core.domain.application;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class EServiceApplicationTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(EServiceApplication.class);
	}

}
