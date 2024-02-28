package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class EarlierEntitlementRightKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(EarlierEntitlementRightKind.class);
	}

	@Test
	public void testValues() {
		for (EarlierEntitlementRightKind earlierEntitlementRightKind : EarlierEntitlementRightKind
				.values()) {
			EarlierEntitlementRightKind earlier = earlierEntitlementRightKind;
			assertEquals(earlier.toString(),
					earlierEntitlementRightKind.toString());
		}
	}
}
