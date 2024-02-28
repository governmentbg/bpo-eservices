package eu.ohim.sp.core.domain.payment;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class FeeTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Fee.class);
	}

}
