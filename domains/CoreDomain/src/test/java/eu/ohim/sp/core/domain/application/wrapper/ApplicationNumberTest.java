package eu.ohim.sp.core.domain.application.wrapper;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class ApplicationNumberTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationNumber.class);
	}

}
