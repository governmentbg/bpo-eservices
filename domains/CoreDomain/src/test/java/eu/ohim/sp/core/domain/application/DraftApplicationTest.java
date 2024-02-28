package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DraftApplicationTest {

	private static final String SKIP_PROPERTY = "statuses";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DraftApplication.class, SKIP_PROPERTY);
	}

	@Test
	public void testEquals() {
		DraftApplication draftApplication1 = new DraftApplication();
		draftApplication1.setApplicationId("applicationId");
		draftApplication1.setDtCreated(new Date());
		draftApplication1.setOffice("office");
		draftApplication1.setPaymentId("paymentId");
		draftApplication1.setProvisionalId("123121");
		draftApplication1.setTyApplication("tyApplication");
		draftApplication1.setUsername("username");

		draftApplication1.setStatuses(new HashSet<DraftStatus>());
		draftApplication1.getStatuses().add(
				new DraftStatus(ApplicationStatus.STATUS_INITIALIZED));
		
		DraftApplication draftApplication2 = draftApplication1;
		
		assertEquals(draftApplication1.getStatuses().size(), 1);
		assertEquals(draftApplication1, draftApplication2);
	}
}
