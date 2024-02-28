package eu.ohim.sp.core.domain.person;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class TransliteratedPersonNameTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TransliteratedPersonName.class);
	}

}
