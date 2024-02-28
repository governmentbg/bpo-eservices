package eu.ohim.sp.core.domain.design;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DesignDivisionalApplicationDetailsTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DesignDivisionalApplicationDetails.class);
	}

}
