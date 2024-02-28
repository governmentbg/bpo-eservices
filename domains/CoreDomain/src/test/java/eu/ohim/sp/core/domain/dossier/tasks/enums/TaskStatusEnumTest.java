package eu.ohim.sp.core.domain.dossier.tasks.enums;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class TaskStatusEnumTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(TaskStatusEnum.class);
	}

	@Test
	public void testValues() {
		TaskStatusEnum completed = TaskStatusEnum.COMPLETED;
		assertEquals(completed.getValue(), TaskStatusEnum.COMPLETED.getValue());

		TaskStatusEnum pending = TaskStatusEnum.PENDING;
		assertEquals(pending.getValue(), TaskStatusEnum.PENDING.getValue());
	}

}
