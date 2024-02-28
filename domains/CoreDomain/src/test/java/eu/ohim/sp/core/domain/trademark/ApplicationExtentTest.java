package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class ApplicationExtentTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ApplicationExtent.class);
	}

	@Test
	public void testValues() {
		ApplicationExtent allGoods = ApplicationExtent.ALL_GOODS_AND_SERVICES;
		assertEquals(allGoods.value(),
				ApplicationExtent.ALL_GOODS_AND_SERVICES.value());
		assertEquals(allGoods.toString(),
				ApplicationExtent.ALL_GOODS_AND_SERVICES.toString());

		ApplicationExtent partialGodds = ApplicationExtent.PARTIAL_GOODS_AND_SERVICES;
		assertEquals(partialGodds.value(),
				ApplicationExtent.PARTIAL_GOODS_AND_SERVICES.value());
		assertEquals(partialGodds.toString(),
				ApplicationExtent.PARTIAL_GOODS_AND_SERVICES.toString());
	}

}
