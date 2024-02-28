package eu.ohim.sp.core.domain.dossier.tasks.enums;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class TaskPropertiesKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TaskPropertiesKind.class);
	}

	@Test
	public void testValues() {
		for (TaskPropertiesKind kind : TaskPropertiesKind.values()) {
			TaskPropertiesKind taskPropertiesKind = kind;
			assertEquals(taskPropertiesKind.getValue(), kind.getValue());
		}
	}

}
