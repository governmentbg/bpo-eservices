package eu.ohim.sp.core.domain.payment;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class FeeListTest {

	private static final String CODE = "code";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(FeeList.class);
	}

	@Test
	public void testEquals() {
		FeeList feeList1 = new FeeList();
		feeList1.getFeeTypeList().add(new FeeType());
		feeList1.getFeeTypeList().get(0).setCode(CODE);

		FeeList feeList2 = new FeeList(new ArrayList<FeeType>());
		feeList2.getFeeTypeList().add(new FeeType());
		feeList2.getFeeTypeList().get(0).setCode(CODE);

		assertEquals(feeList1.getFeeTypeList().get(0).getCode(), feeList2
				.getFeeTypeList().get(0).getCode());
	}

}
