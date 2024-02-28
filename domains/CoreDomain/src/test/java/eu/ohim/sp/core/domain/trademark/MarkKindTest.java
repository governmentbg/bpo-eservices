package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class MarkKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(MarkKind.class);
	}

	@Test
	public void testValues() {
		for (MarkKind kind : MarkKind.values()) {
			MarkKind markKind = kind;
			assertEquals(markKind.value(), kind.value());
			assertEquals(markKind.toString(), kind.toString());
		}
	}

}
