package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Date;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class EntitlementTest {

	private static final String SKIP_PROPERTY = "kind";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Entitlement.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		Entitlement entitlement = new Entitlement();
		entitlement.setAttachedDocuments(new ArrayList<AttachedDocument>());
		entitlement.getAttachedDocuments().add(new AttachedDocument());
		entitlement.setDateOfTransfer(new Date());
		entitlement.setDescription("description");
		entitlement
				.setKind(EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY);

		assertEquals(entitlement.getAttachedDocuments().size(), 1);
		assertEquals(entitlement.getKind(),
				EntitlementKind.ACCORDING_TO_ASSOCIATION_TO_COMPANY);
	}

}
