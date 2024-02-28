package eu.ohim.sp.core.domain.contact;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class CorrespondenceKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(CorrespondenceKind.class);
	}

	@Test
	public void testValues() {
		for (CorrespondenceKind kind : CorrespondenceKind.values()) {
			CorrespondenceKind correspondenceKind = kind;
			assertEquals(correspondenceKind.value(), kind.value());
		}
	}
}
