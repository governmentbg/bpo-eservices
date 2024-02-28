package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class DesignTest {

	private static final String SKIP_PROPERTY = "currentStatus";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Design.class, SKIP_PROPERTY);
	}

	@Test
	public void testCurrentStatusEquals() {
		Design design1 = new Design();
		design1.setCurrentStatus(DesignStatusCode.APPLICATION_PUBLISHED);

		Design design2 = new Design();
		design2.setCurrentStatus(DesignStatusCode.APPLICATION_PUBLISHED);

		assertEquals(design1.getCurrentStatus(), design2.getCurrentStatus());

	}

}
