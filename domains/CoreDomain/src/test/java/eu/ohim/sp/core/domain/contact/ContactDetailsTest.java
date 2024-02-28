package eu.ohim.sp.core.domain.contact;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ContactDetailsTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ContactDetails.class);
	}

}
