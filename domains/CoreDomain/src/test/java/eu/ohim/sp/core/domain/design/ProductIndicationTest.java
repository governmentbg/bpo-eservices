package eu.ohim.sp.core.domain.design;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.ArrayList;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class ProductIndicationTest {

	private static final String SKIP_PROPERTY = "kind";
	private static final String DESCRIPTION = "description";
	private static final String VERSION = "version";
	private static String LANGUAGE_CODE = "languageCode";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(ProductIndication.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		ProductIndication productIndication1 = new ProductIndication();
		productIndication1.setClasses(new ArrayList<ProductIndicationClass>());
		productIndication1.setDescription(DESCRIPTION);
		productIndication1.setKind(ProductIndicationKind.SET_COMPOSITION);
		productIndication1.setLanguageCode(LANGUAGE_CODE);
		productIndication1.setVersion(VERSION);

		ProductIndication productIndication2 = new ProductIndication();
		productIndication2.setClasses(new ArrayList<ProductIndicationClass>());
		productIndication2.setDescription(DESCRIPTION);
		productIndication2.setKind(ProductIndicationKind.SET_COMPOSITION);
		productIndication2.setLanguageCode(LANGUAGE_CODE);
		productIndication2.setVersion(VERSION);

		assertEquals(productIndication1.getClasses().size(), productIndication2
				.getClasses().size());
		assertEquals(productIndication1.getDescription(),
				productIndication2.getDescription());
		assertEquals(productIndication1.getKind(), productIndication2.getKind());
		assertEquals(productIndication1.getLanguageCode(),
				productIndication2.getLanguageCode());
		assertEquals(productIndication1.getVersion(),
				productIndication2.getVersion());
	}

}
