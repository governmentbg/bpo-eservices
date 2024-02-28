package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ClassificationErrorEnumTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassificationErrorEnum.class);
	}

	@Test
	public void testValues() {
		ClassificationErrorEnum hint = ClassificationErrorEnum.HINT;
		assertEquals(hint.value(), ClassificationErrorEnum.HINT.value());

		ClassificationErrorEnum none = ClassificationErrorEnum.NONE;
		assertEquals(none.value(), ClassificationErrorEnum.NONE.value());

		ClassificationErrorEnum notOk = ClassificationErrorEnum.NOT_OK;
		assertEquals(notOk.value(), ClassificationErrorEnum.NOT_OK.value());
	}

}
