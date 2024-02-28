package eu.ohim.sp.core.domain.claim;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ClaimTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Claim.class);
	}

}
