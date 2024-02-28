package eu.ohim.sp.core.domain.dossier.tasks;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class ProcessDefinitionTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ProcessDefinition.class);
	}

}
