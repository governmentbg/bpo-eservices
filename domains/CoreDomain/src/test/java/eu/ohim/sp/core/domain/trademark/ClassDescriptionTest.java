package eu.ohim.sp.core.domain.trademark;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ClassDescriptionTest {

	private static final String CLASS_NUMBER = "classNumber";
	private static final String LANGUAGE = "en";
	private static final String DESCRIPTION = "description";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ClassDescription.class);
	}

	@Test
	public void testEquals() {
		ClassDescription classDescription1 = new ClassDescription();
		classDescription1.setClassNumber(CLASS_NUMBER);
		classDescription1.setLanguage(LANGUAGE);
		classDescription1.setGoodsServicesDescription(DESCRIPTION);
		classDescription1.setFullClassCoverageIndicator(Boolean.TRUE);
		classDescription1
				.setClassificationTerms(new ArrayList<ClassificationTerm>());

		ClassDescription classDescription2 = new ClassDescription();
		classDescription2.setClassNumber(CLASS_NUMBER);
		classDescription2.setLanguage(LANGUAGE);
		classDescription2.setGoodsServicesDescription(DESCRIPTION);
		classDescription2.setFullClassCoverageIndicator(Boolean.TRUE);
		classDescription2
				.setClassificationTerms(new ArrayList<ClassificationTerm>());

		assertEquals(classDescription1.getClassNumber(),
				classDescription2.getClassNumber());
		assertEquals(classDescription1.getLanguage(),
				classDescription2.getLanguage());
		assertEquals(classDescription1.getGoodsServicesDescription(),
				classDescription2.getGoodsServicesDescription());
		assertEquals(classDescription1.isFullClassCoverageIndicator(),
				classDescription2.isFullClassCoverageIndicator());
		assertEquals(classDescription1.getClassificationTerms().size(),
				classDescription2.getClassificationTerms().size());

	}

}
