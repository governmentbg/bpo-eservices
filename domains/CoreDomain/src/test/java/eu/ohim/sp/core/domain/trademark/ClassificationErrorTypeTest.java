package eu.ohim.sp.core.domain.trademark;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class ClassificationErrorTypeTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassificationErrorType.class);
	}

}
