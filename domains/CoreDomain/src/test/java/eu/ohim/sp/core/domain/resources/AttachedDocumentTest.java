package eu.ohim.sp.core.domain.resources;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class AttachedDocumentTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(AttachedDocument.class);
	}

}
