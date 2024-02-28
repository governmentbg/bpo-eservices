package eu.ohim.sp.core.domain.payment;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class FeeIdentifierKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(FeeIdentifierKind.class);
	}

	@Test
	public void testValues() {
		for (FeeIdentifierKind kind : FeeIdentifierKind.values()) {
			FeeIdentifierKind feeIdentifierKind = kind;
			assertEquals(feeIdentifierKind.toString(), kind.toString());
		}
	}

}
