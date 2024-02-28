package eu.ohim.sp.core.domain.payment.wrapper;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class PaymentRequestParamTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PaymentRequestParam.class);
	}

	@Test
	public void testValues() {
		for (PaymentRequestParam param : PaymentRequestParam.values()) {
			PaymentRequestParam paymentRequestParam = param;
			assertEquals(paymentRequestParam.toString(), param.toString());
		}
	}

}
