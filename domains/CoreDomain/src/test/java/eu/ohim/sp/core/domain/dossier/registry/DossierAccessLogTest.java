package eu.ohim.sp.core.domain.dossier.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.util.Date;

import org.junit.Test;

import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.util.JavaBeanTester;

public class DossierAccessLogTest {

	private static final int DOSSIER_ID = 1;
	private static final String USER_NAME = "userName";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(DossierAccessLog.class);
	}

	@Test
	public void testEquals() {
		Date date = new Date();

		DossierAccessLog dossierAccessLog1 = new DossierAccessLog();
		dossierAccessLog1.setDossierId(DOSSIER_ID);
		dossierAccessLog1.setLastUpdate(date);
		dossierAccessLog1.setUsername(USER_NAME);

		DossierAccessLog dossierAccessLog2 = new DossierAccessLog();
		dossierAccessLog2.setDossierId(DOSSIER_ID);
		dossierAccessLog2.setLastUpdate(date);
		dossierAccessLog2.setUsername(USER_NAME);

		assertTrue(dossierAccessLog1.equals(dossierAccessLog2));
		assertTrue(dossierAccessLog1.equals(dossierAccessLog1));
		assertEquals(dossierAccessLog1.hashCode(), dossierAccessLog2.hashCode());
	}

	@Test
	public void testNotEquals() {
		Date date = new Date();

		DossierAccessLog dossierAccessLog1 = new DossierAccessLog();
		dossierAccessLog1.setDossierId(DOSSIER_ID);
		dossierAccessLog1.setLastUpdate(date);
		dossierAccessLog1.setUsername(USER_NAME);

		assertEquals(dossierAccessLog1.equals(null), false);
		assertEquals(dossierAccessLog1.equals(new Applicant()), false);
	}

	@Test
	public void testToString() {
		DossierAccessLog dossierAccessLog = new DossierAccessLog();
		dossierAccessLog.setDossierId(DOSSIER_ID);
		dossierAccessLog.setLastUpdate(new Date());
		dossierAccessLog.setUsername(USER_NAME);

		assertTrue(dossierAccessLog.toString().contains(
				"username=" + dossierAccessLog.getUsername()));
	}

}
