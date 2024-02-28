package eu.ohim.sp.core.domain.application;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.util.Date;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class DraftStatusTest {

	private static final String SKIP_PROPERTY = "status";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DraftStatus.class, SKIP_PROPERTY);
	}

	@Test
	public void testStatus() {
		DraftStatus draftStatus = new DraftStatus(
				ApplicationStatus.STATUS_INITIALIZED);
		assertEquals(draftStatus.getStatus(),
				ApplicationStatus.STATUS_INITIALIZED);

		draftStatus.setStatus(ApplicationStatus.STATUS_SUBMITTED);
		assertEquals(draftStatus.getStatus(),
				ApplicationStatus.STATUS_SUBMITTED);

	}
	
	@Test
	public void testEquals() {
		DraftStatus draftStatus = new DraftStatus();
		Date date = new Date();
		draftStatus.setModifiedDate(date);
		draftStatus.setMessage("message");
		
		assertEquals(draftStatus.getModifiedDate(), date);
		assertEquals(draftStatus.getMessage(), "message");
	}

}
