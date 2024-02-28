package eu.ohim.sp.core.domain.claim;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ExhibitionTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Exhibition.class);
	}

}
