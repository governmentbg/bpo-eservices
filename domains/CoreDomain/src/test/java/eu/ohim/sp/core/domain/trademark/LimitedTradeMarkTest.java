package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class LimitedTradeMarkTest {

	private static final String SKIP_PROPERTY = "applicationExtent";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(LimitedTradeMark.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		LimitedTradeMark limitedTradeMark1 = new LimitedTradeMark();
		limitedTradeMark1
				.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
		limitedTradeMark1
				.setLimitedClassDescriptions(new ArrayList<ClassDescription>());

		LimitedTradeMark limitedTradeMark2 = new LimitedTradeMark();
		limitedTradeMark2
				.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
		limitedTradeMark2
				.setLimitedClassDescriptions(new ArrayList<ClassDescription>());

		assertEquals(limitedTradeMark1.getApplicationExtent(),
				limitedTradeMark2.getApplicationExtent());
		assertEquals(limitedTradeMark1.getLimitedClassDescriptions().size(),
				limitedTradeMark2.getLimitedClassDescriptions().size());
	}
}
