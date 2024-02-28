/*
 *  DocumentService:: DocumentService 21/10/13 21:47 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.document;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.document.utils.EPUBUtil;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.external.document.DocumentClient;
import eu.ohim.sp.external.document.JCRDocumentClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.common.SPException;

import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;


/**
 * The type Document service.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class DocumentServiceBean implements DocumentServiceLocal,
		DocumentServiceRemote {

	private static final Logger LOGGER = Logger.getLogger(DocumentService.class);

	@Inject @JCRDocumentClient
	private DocumentClient documentClient;

	@EJB(lookup="java:global/businessRulesLocal")
	private RulesService businessRulesService;
	
    private static final String DOCUMENT_SET = "document_set";

	public List<Document> searchDocument(Map<String, String> criteria, boolean lazy) {
		return documentClient.searchDocument(criteria, lazy);
	}

	/**
	 * Gets folder documents.
	 *
	 * @param repository the repository
	 * @param location   the location
	 * @return the folder documents
	 */
	public List<Document> getFolderDocuments(String repository,
											 String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getDocument(String documentId) {
		if (documentId != null) {
			Document document = new Document();
			document.setDocumentId(documentId);

			return documentClient.getDocument(document);
		} else {
			throw new SPException("DocumentId can't be null when getting a document from the repository");
		}
	}

	@Override
	public Document getContent(String documentId) {
		return getDocument(documentId);
	}

	@Override
	public Document saveDocument(Document document) {
        if (document != null) {
            LOGGER.debug("Save Document : " + document.getFileName());
            Document document1 = documentClient.addDocument(document);
            return document1;
		} else {
            LOGGER.error("Save Document : Empty");
            throw new SPException("The document can't be null when saving it");
		}
	}

	@Override
	public Document updateDocument(Document document) {
		if (document != null) {
            return documentClient.updateDocument(document);
		} else {
			throw new SPException("The document can't be null when updating it");
		}
	}

	@Override
	public Document deleteDocument(Document document) {
		if (document != null) {
			if (documentClient.deleteDocument(document)) {
				document.setDocumentId(null);
				return document;
			} else {
				return null;
			}
		} else {
			throw new SPException("The document can't be null when deleting it");
		}
	}


	@Override
	public ErrorList validateDocument(String module, Document document, RulesInformation rulesInformation) {
		
		if (document != null) {
            LOGGER.debug(">>> Validate Document START" );
		}
		
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
		// Prepares the objects to insert in the session
		
        // Prepares the objects to insert in the session
     	objects.add(document);
        objects.add(rulesInformation);     	

        if(rulesInformation.getCustomObjects().containsKey("sectionName")){
        	Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
			objects.add(section);
		} 
		if(rulesInformation.getCustomObjects().containsKey("sections")){
			objects.add(rulesInformation.getCustomObjects().get("sections"));
		}

        
        
     	// Starts the check
        ErrorList errors = businessRulesService.validate(module, DOCUMENT_SET, objects);
        
     	if (LOGGER.isDebugEnabled()) {
     		LOGGER.debug("<<< Validate Document END");
		}
        
        return errors;
	}

	/**
	 * It archives the specified documents
	 *
	 * @param archivedFiles a map that specifies the path to be stored in the archive
	 * @param format        format specified, initially will be supported epub as it is now
	 * @param persisted     if the resulting zip will be stored externally or just on the temporary location
	 * @return the archive document that may contain only internal reference if it is not persisted externally
	 */
	public Document archiveDocuments(Map<String, Document> archivedFiles, String format, boolean persisted) {
		EPUBUtil compressionUtil = new EPUBUtil(documentClient);

		return compressionUtil.createArchiveDocument(archivedFiles, persisted);
	}

	/**
	 * Decompress the archive and generates either exteranally saved references or only internally.
	 * The caller can fill its document properties and then persist or update
	 *
	 * @param archivedDocuments the archive that we expect to decompress
	 * @param format            the expected format
	 * @param persisted         to persist externally the contents
	 * @return the resulting Documents with their path inside the zip
	 */
	public Map<String, Document> unarchiveDocuments(Document archivedDocuments, String format, boolean persisted) {
		EPUBUtil compressionUtil = new EPUBUtil(documentClient);

		return compressionUtil.unarchiveDocuments(archivedDocuments, persisted);
	}

    /**
     * Fetch document data from document repository or from URI
     * @param document the document
     * @return the document binary data
     */
	public byte[] getData(Document document) {
		byte[] retrievedData = null;
		if (StringUtils.isNotBlank(document.getDocumentId())) {
			Document imageDocument = getDocument(document.getDocumentId());
			retrievedData = imageDocument.getData();
		} else if (StringUtils.isNotBlank(document.getUri())) {
			InputStream in = null;
			// To be revised what to do with this access
			try {
				URL url = new URL(document.getUri());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(10000);
				conn.connect();
				in = conn.getInputStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				IOUtils.copy(in, output);
				retrievedData = output.toByteArray();
			} catch (IOException e) {
				LOGGER.debug("	>>> fillApplicationDocuments: " + e);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
		return retrievedData;
	}

}