package eu.ohim.sp.core.document;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.utils.JcrUtil;
import eu.ohim.sp.core.utils.Tester;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;

/**
 * User: jaraful
 * Date: 02/08/13
 * Time: 16:32
 */
public class DocumentServiceTest {

	DocumentServiceRemote documentService = null;
	SystemConfigurationServiceRemote configurationService = null;

	private FODocument document1;
	private FODocument document2;
	private Document applicationXmlDocument;
	private Document mimetypeDocument;
	private Document containerXmlDocument;

	@Before
	public void setup() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			final Context context = new InitialContext(jndiProperties);
			documentService = (DocumentServiceRemote) context.lookup("ejb:core-document-management/DocumentService//DocumentService!eu.ohim.sp.core.document.DocumentServiceRemote");
			configurationService = (SystemConfigurationServiceRemote) context.lookup("ejb:core-configuration-management/SystemConfigurationService//SystemConfigurationService!eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote");
		} catch (NamingException e) {
			throw new SPException("There was an error while setting up the test");
		}

		// Creates the documents
		createDocuments();

		// Wipes the previous tests
		JcrUtil jcrUtil = new JcrUtil(configurationService);
		String repositoryType = configurationService.getValue("repository.type", "general");
		if("cmis".equals(repositoryType)){
			jcrUtil.wipeTestsCMIS();
		} else {
			jcrUtil.wipeTestsJCR();
		}
	}

	@Test
	public void testDocumentServiceAddDocumentOk() {
		Document persistedDocument = documentService.saveDocument(document1);
		assertNotNull(persistedDocument.getDocumentId());
	}

	@Test(expected = EJBException.class)
	public void testDocumentServiceAddDocumentFail() {
		Document persistedDocument1 = new Document();
		documentService.saveDocument(persistedDocument1);
	}

	@Test
	public void testDocumentServiceDeleteDocumentExists() {
		Document persistedDocument1 = documentService.saveDocument(document1);
		document1.setDocumentId(persistedDocument1.getDocumentId());
		Document resultDocument = documentService.deleteDocument(document1);
		Document deletedDocument = documentService.getDocument(persistedDocument1.getDocumentId());
		assertNull(deletedDocument.getDocumentId());
	}

	@Test
	public void testDocumentServiceDeleteDocumentNotExists() {
		document2.setDocumentId("abc");
		Document deletedDocument = documentService.deleteDocument(document2);
		assertNull(deletedDocument);
	}

	@Test
	public void testDocumentServiceSearchDeletedOk(){
		Document persistedDocument1 = documentService.saveDocument(document1);
		document1.setDocumentId(persistedDocument1.getDocumentId());
		documentService.deleteDocument(document1);

		String repositoryType = configurationService.getValue("repository.type", "general");
		if("jcr".equals(repositoryType)){
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put(FODocument.FILING_NUMBER, "111111");
			searchFilter.put("fileName", "geko.jpg");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertEquals(documentList.size(), 0);
		} else {
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put("cmis:name", "geko");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertEquals(documentList.size(), 0);
		}
	}

	@Test
	public void testDocumentServiceUpdateEmptyId() {
		Document updatedDocument = documentService.updateDocument(document2);
		assertNull(updatedDocument.getDocumentId());
	}

	@Test
	public void testDocumentServiceUpdateOk() {
		Document persistedDocument1 = documentService.saveDocument(document1);

		document1.setDocumentId(persistedDocument1.getDocumentId());
		document1.setFileName("smile2.jpg");

		Document updatedDocument = documentService.updateDocument(document1);
		assertEquals(updatedDocument.getFileName(), "smile2.jpg");
	}

	@Test
	public void testDocumentServiceWrongId() {
		document1.setDocumentId("asdf");
		Document updatedDocument = documentService.updateDocument(document1);
		assertEquals(updatedDocument.getFileName(), "geko.jpg");
	}

	@Test
	public void testDocumentServiceGetOk() {
		Document persistedDocument = documentService.saveDocument(document1);
		Document gotDocument = documentService.getDocument(persistedDocument.getDocumentId());
		assertEquals(persistedDocument.getDocumentId(), gotDocument.getDocumentId());
	}

	@Test
	public void testDocumentServiceGetFail() {
		document1.setDocumentId("34234");
		Document gotDocument = documentService.getDocument(document1.getDocumentId());
		assertNull(gotDocument.getDocumentId());
	}

	@Test
	public void testDocumentServiceSearchOk() {
		String repositoryType = configurationService.getValue("repository.type", "general");

		documentService.saveDocument(document1);
		documentService.saveDocument(document2);

		if("jcr".equals(repositoryType)){
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put(FODocument.FILING_NUMBER, "111111");
			searchFilter.put("fileName", "geko.jpg");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertEquals(documentList.size(), 1);
		} else {
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put("cmis:name", "geko");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertEquals(documentList.size(), 1);
		}
	}

	@Test
	public void testDocumentServiceSearchFail() {
		String repositoryType = configurationService.getValue("repository.type", "general");

		documentService.saveDocument(document1);
		documentService.saveDocument(document2);

		if("jcr".equals(repositoryType)){
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put(FODocument.FILING_NUMBER, "111111");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertNotEquals(documentList.size(), 1);
		} else {
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put("cmis:name", "smile2");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertNotEquals(documentList.size(), 1);
		}
	}

	@Test
	public void testDocumentServiceSearchLazyOk() {
		String repositoryType = configurationService.getValue("repository.type", "general");

		documentService.saveDocument(document1);
		documentService.saveDocument(document2);

		if("jcr".equals(repositoryType)){
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put(FODocument.FILING_NUMBER, "111111");
			searchFilter.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_XML.value());
			List<Document> documentList = documentService.searchDocument(searchFilter, true);

			for (Document document : documentList) {
				assertNull(document.getData());
			}
		} else {
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put("cmis:name", "smile");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);
			assertEquals(documentList.size(), 1);
		}
	}

	@Test
	public void testCompressedDocumentNotPersisted(){
		Document appDocument = documentService.saveDocument(applicationXmlDocument);
		Document mimDocument = documentService.saveDocument(mimetypeDocument);
		Document conDocument = documentService.saveDocument(containerXmlDocument);
		Document persistedDocument1 = documentService.saveDocument(document1);

		Map<String, Document> documentMap = new HashMap<String, Document>();
		documentMap.put("application.xml", documentService.getDocument(appDocument.getDocumentId()));
		documentMap.put("mimetype", documentService.getDocument(mimDocument.getDocumentId()));
		documentMap.put("container.xml", documentService.getDocument(conDocument.getDocumentId()));
		documentMap.put("document1", documentService.getDocument(persistedDocument1.getDocumentId()));

		Document compressedDocument = documentService.archiveDocuments(documentMap, "zip", false);

		assertNotNull(compressedDocument);
	}

	@Test
	public void testCompressedDocumentPersisted(){
		Document appDocument = documentService.saveDocument(applicationXmlDocument);
		Document mimDocument = documentService.saveDocument(mimetypeDocument);
		Document conDocument = documentService.saveDocument(containerXmlDocument);
		Document persistedDocument1 = documentService.saveDocument(document1);
		Document persistedDocument2 = documentService.saveDocument(document2);

		Map<String, Document> documentMap = new HashMap<String, Document>();
		documentMap.put("application.xml", documentService.getDocument(appDocument.getDocumentId()));
		documentMap.put("mimetype", documentService.getDocument(mimDocument.getDocumentId()));
		documentMap.put("container.xml", documentService.getDocument(conDocument.getDocumentId()));
		documentMap.put("ATTACHMENTS/ATT0001.jpg", documentService.getDocument(persistedDocument1.getDocumentId()));
		documentMap.put("ATTACHMENTS/ATT0002.jpg", documentService.getDocument(persistedDocument2.getDocumentId()));

		Document compressedDocument = documentService.archiveDocuments(documentMap, "zip", true);

		assertNotNull(compressedDocument);
	}

	@Test
	public void testUncompressedDocumentNotPersisted(){
		Document appDocument = documentService.saveDocument(applicationXmlDocument);
		Document mimDocument = documentService.saveDocument(mimetypeDocument);
		Document conDocument = documentService.saveDocument(containerXmlDocument);
		Document persistedDocument1 = documentService.saveDocument(document1);

		Map<String, Document> documentMap = new HashMap<String, Document>();
		documentMap.put("application.xml", documentService.getDocument(appDocument.getDocumentId()));
		documentMap.put("mimetype", documentService.getDocument(mimDocument.getDocumentId()));
		documentMap.put("container.xml", documentService.getDocument(conDocument.getDocumentId()));
		documentMap.put("document1", documentService.getDocument(persistedDocument1.getDocumentId()));

		Document compressedDocument = documentService.archiveDocuments(documentMap, "zip", false);

		assertNotNull(compressedDocument);

		Map<String, Document> resultMap = documentService.unarchiveDocuments(compressedDocument, "zip", false);

		assertNotNull(resultMap);
		assertNotEquals(resultMap.size(), 0);
	}

	@Test
	public void testUncompressedDocumentPersisted(){
		Document appDocument = documentService.saveDocument(applicationXmlDocument);
		Document mimDocument = documentService.saveDocument(mimetypeDocument);
		Document conDocument = documentService.saveDocument(containerXmlDocument);
		Document persistedDocument1 = documentService.saveDocument(document1);

		Map<String, Document> documentMap = new HashMap<String, Document>();
		documentMap.put("application.xml", documentService.getDocument(appDocument.getDocumentId()));
		documentMap.put("mimetype", documentService.getDocument(mimDocument.getDocumentId()));
		documentMap.put("container.xml", documentService.getDocument(conDocument.getDocumentId()));
		documentMap.put("document1", documentService.getDocument(persistedDocument1.getDocumentId()));

		Document compressedDocument = documentService.archiveDocuments(documentMap, "zip", true);

		assertNotNull(compressedDocument);

		Map<String, Document> resultMap = documentService.unarchiveDocuments(compressedDocument, "zip", true);

		assertNotNull(resultMap);
		assertNotEquals(resultMap.size(), 0);
	}

//	@Test
	public void performanceTest(){
		for(int i = 0; i<100; i++){
			long startTime = System.currentTimeMillis();
			documentService.saveDocument(document1);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			System.out.println(" - " + i + ": " + String.format("%.3f", (float)(totalTime/1000.0)));
		}
	}

//	@Test
	public void performanceSearchTest(){
		System.out.println("Adding documents");
		for(int i = 0; i<100; i++){
			long startTime = System.currentTimeMillis();
			documentService.saveDocument(document1);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			System.out.print(".");
		}
		Document persistedDocument = documentService.saveDocument(document2);

		System.out.println();
		for(int i = 0; i<100; i++){
			long startTime = System.currentTimeMillis();
			Map<String, String> searchFilter = new HashMap<String, String>();
			searchFilter.put(FODocument.FILING_NUMBER, "111111");
			searchFilter.put("fileName", "smile.jpg");
			List<Document> documentList = documentService.searchDocument(searchFilter, true);

			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;

			System.out.println(" - " + i + "(" + documentList.size() + "): " + String.format("%.3f", (float)(totalTime/1000.0)));
		}
	}

//	@Test
	public void testConcurrency(){
		int MAX_NODES = 10;

		Tester testers[] = new Tester[MAX_NODES];

		for(int i=0; i<MAX_NODES; i++){
			testers[i] = new Tester("tester"+i, documentService, document1);
		}

		for(Tester tester : testers){
			tester.start();
		}

		for(int i=0; i<testers.length; i++){
			try {
				testers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}

		for(int i=0; i<testers.length; i++){
			assertEquals("1111111111", testers[i].getResults());
		}
	}

	private void createDocuments() {
		// Document 1
		document1 = new FODocument();
		document1.setCustomProperties(new HashMap<String, String>());
		document1.setName("geko");
		document1.setApplicationType("TEST");
		document1.setFilingNumber("111111");
		document1.setApplicationStatus("draft");
		document1.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
		document1.setFileName("geko.jpg");
		document1.setFileFormat("image/jpeg");
		document1.setDateCreated(new Date());
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/geko.jpg");
			document1.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}

		// Document 2
		document2 = new FODocument();
		document2.setCustomProperties(new HashMap<String, String>());
		document2.setName("smile");
		document2.setApplicationType("TEST");
		document2.setFilingNumber("111111");
		document2.setApplicationStatus("draft");
		document2.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
		document2.setFileName("smile.jpg");
		document2.setFileFormat("image/jpeg");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/smile.jpg");
			document2.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}

		// Application XML Document
		applicationXmlDocument = new Document();
		applicationXmlDocument.setCustomProperties(new HashMap<String, String>());
		applicationXmlDocument.setName("application.xml");
		applicationXmlDocument.getCustomProperties().put(FODocument.APPLICATION_TYPE, "TEST");
		applicationXmlDocument.getCustomProperties().put(FODocument.MODULE, "application.xml");
		applicationXmlDocument.getCustomProperties().put(FODocument.FILING_NUMBER, "111111");
		applicationXmlDocument.getCustomProperties().put(FODocument.APPLICATION_STATUS, "draft");
		applicationXmlDocument.getCustomProperties().put("customPath", "application.xml");
		applicationXmlDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_XML.value());
		applicationXmlDocument.setFileName("application.xml");
		applicationXmlDocument.setFileFormat("application/xml");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/application.xml");
			applicationXmlDocument.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}

		// MimeType Document
		mimetypeDocument = new Document();
		mimetypeDocument.setCustomProperties(new HashMap<String, String>());
		mimetypeDocument.setName("mimetype");
		mimetypeDocument.getCustomProperties().put(FODocument.APPLICATION_TYPE, "TEST");
		mimetypeDocument.getCustomProperties().put(FODocument.MODULE, "mimetype");
		mimetypeDocument.getCustomProperties().put(FODocument.FILING_NUMBER, "111111");
		mimetypeDocument.getCustomProperties().put(FODocument.APPLICATION_STATUS, "draft");
		mimetypeDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_MIMETYPE.value());
		mimetypeDocument.getCustomProperties().put("customPath", "mimetype");
		mimetypeDocument.setFileName("mimetype");
		mimetypeDocument.setFileFormat("text");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/mimetype.txt");
			mimetypeDocument.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}

		// Container XML Document
		containerXmlDocument = new Document();
		containerXmlDocument.setCustomProperties(new HashMap<String, String>());
		containerXmlDocument.setName("container.xml");
		containerXmlDocument.getCustomProperties().put(FODocument.APPLICATION_TYPE, "TEST");
		containerXmlDocument.getCustomProperties().put(FODocument.MODULE, "container.xml");
		containerXmlDocument.getCustomProperties().put(FODocument.FILING_NUMBER, "111111");
		containerXmlDocument.getCustomProperties().put(FODocument.APPLICATION_STATUS, "draft");
		containerXmlDocument.getCustomProperties().put("customPath", "container.xml");
		containerXmlDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_CONTAINER.value());
		containerXmlDocument.setFileName("container.xml");
		containerXmlDocument.setFileFormat("application/xml");
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/container.xml");
			containerXmlDocument.setData(IOUtils.toByteArray(is));
		} catch (FileNotFoundException e) {
			throw new SPException("File not found");
		} catch (IOException e) {
			throw new SPException("IOException while reading the resource");
		}
	}
}
