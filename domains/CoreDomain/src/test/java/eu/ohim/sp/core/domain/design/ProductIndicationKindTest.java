package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ProductIndicationKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ProductIndicationKind.class);
	}

	@Test
	public void testValues() {
		for (ProductIndicationKind kind : ProductIndicationKind.values()) {
			ProductIndicationKind productIndicationKind = kind;
			assertEquals(productIndicationKind.value(), kind.value());
		}
	}
}
