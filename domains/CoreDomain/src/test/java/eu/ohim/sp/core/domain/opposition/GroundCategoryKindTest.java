package eu.ohim.sp.core.domain.opposition;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class GroundCategoryKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(GroundCategoryKind.class);
	}

	@Test
	public void testValues() {
		GroundCategoryKind groundCategoryKindAbsoluteGrounds = GroundCategoryKind.ABSOLUTE_GROUNDS;
		GroundCategoryKind groundCategoryKindRelativeGrounds = GroundCategoryKind.RELATIVE_GROUNDS;
		GroundCategoryKind groundCategoryKindRevocationGrounds = GroundCategoryKind.REVOCATION_GROUNDS;

		assertEquals(groundCategoryKindAbsoluteGrounds.toString(),
				GroundCategoryKind.ABSOLUTE_GROUNDS.toString());
		assertEquals(groundCategoryKindRelativeGrounds.toString(),
				GroundCategoryKind.RELATIVE_GROUNDS.toString());
		assertEquals(groundCategoryKindRevocationGrounds.toString(),
				GroundCategoryKind.REVOCATION_GROUNDS.toString());
	}

}
