package eu.ohim.sp.core.domain.design;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class DesignerTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Designer.class);
	}

}
