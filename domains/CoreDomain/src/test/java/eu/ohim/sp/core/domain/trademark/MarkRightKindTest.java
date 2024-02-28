package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class MarkRightKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(MarkRightKind.class);
	}

	@Test(expected=Exception.class)
	public void testValues() {
		assertEquals(MarkRightKind.valueOf(""), null);
	}

}
