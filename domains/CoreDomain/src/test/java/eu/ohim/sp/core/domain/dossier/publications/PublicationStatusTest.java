package eu.ohim.sp.core.domain.dossier.publications;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

public class PublicationStatusTest {

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(PublicationStatus.class);
	}
	
	@Test
	public void testValues() {
		for (PublicationStatus status : PublicationStatus.values()) {
			PublicationStatus publicationStatus = status;
			assertEquals(publicationStatus.getValue(), status.getValue());
		}
	}

}
