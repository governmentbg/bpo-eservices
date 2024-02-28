package eu.ohim.sp.core.domain.dossier.tasks;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ValidationResultTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ValidationResult.class);
	}

}
