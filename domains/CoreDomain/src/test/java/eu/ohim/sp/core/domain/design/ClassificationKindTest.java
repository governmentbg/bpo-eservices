package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ClassificationKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassificationKind.class);
	}

	@Test
	public void testValues() {
		for (ClassificationKind kind : ClassificationKind.values()) {
			ClassificationKind classificationKind = kind;
			assertEquals(classificationKind.value(), kind.value());
		}
	}

}
