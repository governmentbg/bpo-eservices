package eu.ohim.sp.core.domain.trademark;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class TMeServiceApplicationTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TMeServiceApplication.class);
	}

}
