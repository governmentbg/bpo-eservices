package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class EntitlementKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(EntitlementKind.class);
	}

	@Test
	public void testValues() {
		for (EntitlementKind kind : EntitlementKind.values()) {
			EntitlementKind entitlementKind = kind;
			assertEquals(entitlementKind.getCode(), kind.getCode());
		}
	}

}
