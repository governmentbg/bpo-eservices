package eu.ohim.sp.core.domain.design;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class DesignViewTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DesignView.class);
	}

}
