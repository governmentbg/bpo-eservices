package eu.ohim.sp.core.domain.classification.wrapper;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ClassScopeTest {

	private static final String CLASS_NUMBER = "classNumber";
	private static final String DESCRIPTION = "description";
	private static final String LANGUAGE = "en";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassScope.class);
	}

	@Test
	public void testEquals() {
		ClassScope classScope1 = new ClassScope();
		classScope1.setClassNumber(CLASS_NUMBER);
		classScope1.setDescription(DESCRIPTION);
		classScope1.setLanguage(LANGUAGE);
		
		ClassScope classScope2 = new ClassScope();
		classScope2.setClassNumber(CLASS_NUMBER);
		classScope2.setDescription(DESCRIPTION);
		classScope2.setLanguage(LANGUAGE);
		
		assertEquals(classScope1.getClassNumber(), classScope2.getClassNumber());
		assertEquals(classScope1.getDescription(), classScope2.getDescription());
		assertEquals(classScope1.getLanguage(), classScope2.getLanguage());
	}

}
