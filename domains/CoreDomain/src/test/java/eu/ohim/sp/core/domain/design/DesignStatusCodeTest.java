package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DesignStatusCodeTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DesignStatusCode.class);
	}

	@Test
	public void testValues() {
		for (DesignStatusCode code : DesignStatusCode.values()) {
			DesignStatusCode designStatusCode = code;
			assertEquals(designStatusCode.value(), code.value());
		}
	}

}
