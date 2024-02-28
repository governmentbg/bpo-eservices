package eu.ohim.sp.core.domain.resources;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

public class FODocumentTest {

	private static final String FILE_NAME = "fileName";
	private static final String FILE_FORMAT = "XML";
	public static final String FILING_NUMBER = "filingNumber";
	public static final String ATTACHMENT_TYPE = "attachmentType";
	public static final String OFFICE = "office";
	public static final String MODULE = "module";
	public static final String APPLICATION_STATUS = "applicationStatus";
	public static final String APPLICATION_TYPE = "applicationType";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(FODocument.class);
	}

	@Test
	public void testCustomProperties() {
		FODocument foDocument = new FODocument("", FILE_NAME, FILE_FORMAT,
				OFFICE, MODULE, FILING_NUMBER, ATTACHMENT_TYPE,
				APPLICATION_TYPE, APPLICATION_STATUS, null);

		assertTrue(foDocument.getCustomProperties().containsKey(OFFICE));
		assertTrue(foDocument.getCustomProperties().containsKey(MODULE));
		assertTrue(foDocument.getCustomProperties().containsKey(FILING_NUMBER));
		assertTrue(foDocument.getCustomProperties()
				.containsKey(ATTACHMENT_TYPE));
		assertTrue(foDocument.getCustomProperties().containsKey(
				APPLICATION_TYPE));
		assertTrue(foDocument.getCustomProperties().containsKey(
				APPLICATION_STATUS));

	}

	@Test
	public void testCustomPropertiesEmpty() {
		FODocument foDocument = new FODocument("", "", "", "", "", "", "", "",
				"", null);

		assertFalse(foDocument.getCustomProperties().containsKey(OFFICE));
		assertFalse(foDocument.getCustomProperties().containsKey(MODULE));
		assertFalse(foDocument.getCustomProperties().containsKey(FILING_NUMBER));
		assertFalse(foDocument.getCustomProperties().containsKey(
				ATTACHMENT_TYPE));
		assertFalse(foDocument.getCustomProperties().containsKey(
				APPLICATION_TYPE));
		assertFalse(foDocument.getCustomProperties().containsKey(
				APPLICATION_STATUS));

	}

}
