package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ApplicationKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationKind.class);
	}

	@Test
	public void testValues() {
		for (ApplicationKind kind : ApplicationKind.values()) {
			ApplicationKind applicationKind = kind;
			assertEquals(applicationKind.value(), kind.value());
		}
	}

}
