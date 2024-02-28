package eu.ohim.sp.core.domain.payment;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class PaymentKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PaymentKind.class);
	}

	@Test
	public void testValues() {
		for (PaymentKind kind : PaymentKind.values()) {
			PaymentKind paymentKind = kind;
			assertEquals(paymentKind.value(), kind.value());
		}
	}

}
