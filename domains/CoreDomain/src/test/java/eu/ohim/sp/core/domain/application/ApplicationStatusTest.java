package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ApplicationStatusTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationStatus.class);
	}

	@Test
	public void testValues() {
		for (ApplicationStatus status : ApplicationStatus.values()) {
			ApplicationStatus applicationStatus = status;
			assertEquals(applicationStatus.getCode(), status.getCode());
		}
	}

	@Test
	public void testFromCode() {
		for (ApplicationStatus status : ApplicationStatus.values()) {
			Integer code = status.getCode();
			assertEquals(ApplicationStatus.fromCode(code), status);
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromCodeExpectedException() {
		ApplicationStatus.fromCode(-1);
	}

}
