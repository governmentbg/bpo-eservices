package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ApplicationBasicKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationBasicKind.class);
	}

	@Test
	public void testValues() {
		for (ApplicationBasicKind kind : ApplicationBasicKind.values()) {
			ApplicationBasicKind applicationBasicKind = kind;
			assertEquals(applicationBasicKind.value(), kind.value());
		}
	}

}
