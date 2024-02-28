package eu.ohim.sp.core.domain.claim;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class PartialClaimTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PartialClaim.class);
	}

}
