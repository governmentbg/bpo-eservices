package eu.ohim.sp.core.domain.dossier.registry;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class HistoryEntryTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(HistoryEntry.class);
	}

}
