package eu.ohim.sp.core.domain.dossier.tasks.enums;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class SortTaskKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(SortTaskKind.class);
	}
	
	@Test
	public void testValues() {
		for (SortTaskKind kind : SortTaskKind.values()) {
			SortTaskKind sortTaskKind = kind;
			assertEquals(sortTaskKind.getValue(), kind.getValue());
		}
	}

}
