package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.domain.contact.CorrespondenceKind;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class DesignApplicationTest {

	private static final String SKIP_PROPERTY = "correspondenceKind";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DesignApplication.class, SKIP_PROPERTY);
	}

	@Test
	public void testCorrespondenceKindEquals() {
		DesignApplication designApplication1 = new DesignApplication();
		designApplication1.setCorrespondenceKind(CorrespondenceKind.EMAIL);

		DesignApplication designApplication2 = new DesignApplication();
		designApplication2.setCorrespondenceKind(CorrespondenceKind.EMAIL);

		assertEquals(designApplication1.getCorrespondenceKind(),
				designApplication2.getCorrespondenceKind());
	}

}
