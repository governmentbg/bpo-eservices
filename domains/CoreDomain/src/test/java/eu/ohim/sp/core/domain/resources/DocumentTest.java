/*
 *  CoreDomain:: DocumentTest 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.resources;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import eu.ohim.sp.core.util.JavaBeanTester;

/**
 * @author ionitdi
 */
public class DocumentTest {

	private static final String DOCUMENT_ID = "documentId";
	private static final String COMMENT = "comment";
	private static final String FILE_FORMAT = "fileFormat";
	private static final String FILE_NAME = "fileName";
	private static final String LANGUAGE = "language";
	private static final String NAME = "name";
	private static final String URI = "uri";
	private static final String TEMPORARY_REFERENCE = "temporaryReference";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Document.class);
	}

	@Test
	public void bean_testByte() throws IntrospectionException {
		byte[] test = new String("test").getBytes();
		Document documentW = new Document();
		documentW.setData(test);

		try {
			assertEquals(new String(test, "UTF-8"),
					new String(documentW.getData(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		assertArrayEquals(test, documentW.getData());

		documentW = new Document();
		assertArrayEquals(null, documentW.getData());
	}

	@Test
	public void testEquals() {
		Document document1 = new Document();
		document1.setComment("some comment");
		document1.setDocumentId("123");
		Document document2 = new Document();
		document2.setComment("some comment");
		document2.setDocumentId("123");

		assertTrue(document1.equals(document2));
		assertEquals(document1.hashCode(), document2.hashCode());
	}
	
	@Test
	public void testEquals2() {
		Document document = new Document();
		document.setComment("some comment");
		document.setDocumentId("123");

		assertTrue(document.equals(document));
	}

	@Test
	public void testNotEquals() {
		Document document1 = new Document();
		document1.setComment("some comment");
		Document document2 = new Document();

		assertFalse(document1.equals(document2));
		assertFalse(document1.equals(null));
	}

	@Test
	public void testCustomPropertiesDocumentId() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setDocumentId(DOCUMENT_ID);

		assertTrue(document.getCustomProperties().containsKey(DOCUMENT_ID));
	}

	@Test
	public void testCustomPropertiesDocumentComment() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setComment(COMMENT);

		assertTrue(document.getCustomProperties().containsKey(COMMENT));
	}

	@Test
	public void testCustomPropertiesDocumentFileFormat() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setFileFormat(FILE_FORMAT);

		assertTrue(document.getCustomProperties().containsKey(FILE_FORMAT));
	}

	@Test
	public void testCustomPropertiesDocumentFileName() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setFileName(FILE_NAME);

		assertTrue(document.getCustomProperties().containsKey(FILE_NAME));
	}

	@Test
	public void testCustomPropertiesDocumentLanguage() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setLanguage(LANGUAGE);

		assertTrue(document.getCustomProperties().containsKey(LANGUAGE));
	}

	@Test
	public void testCustomPropertiesDocumentName() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setName(NAME);

		assertTrue(document.getCustomProperties().containsKey(NAME));
	}

	@Test
	public void testCustomPropertiesDocumentUri() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setUri(URI);

		assertTrue(document.getCustomProperties().containsKey(URI));
	}

	@Test
	public void testCustomPropertiesDocumentTemporaryReference() {
		Document document = new Document();
		document.setCustomProperties(null);
		document.setTemporaryReference(TEMPORARY_REFERENCE);

		assertTrue(document.getCustomProperties().containsKey(
				TEMPORARY_REFERENCE));
	}

}
