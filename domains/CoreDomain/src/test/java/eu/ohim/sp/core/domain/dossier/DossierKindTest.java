package eu.ohim.sp.core.domain.dossier;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DossierKindTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DossierKind.class);
	}
	
	@Test
	public void testValues() {
		for (DossierKind kind : DossierKind.values()) {
			DossierKind dossierKind = kind;
			assertEquals(dossierKind.value(), kind.value());
		}
	}

}
