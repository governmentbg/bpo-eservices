package eu.ohim.sp.core.domain.user;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class CriteriaTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Criteria.class);
	}

}
