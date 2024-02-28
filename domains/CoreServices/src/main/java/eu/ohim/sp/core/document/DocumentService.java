/*
 *  DocumentService:: DocumentService 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.document;

import java.util.List;
import java.util.Map;

import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * Core management Service that provides functionalities for uploading 
 * attachments and persisting files in the system
 * @author jaraful
 */
public interface DocumentService {

	/**
	 * The constant OVERWRITE_POLICY_TRUE.
	 */
	boolean OVERWRITE_POLICY_TRUE = true;
	/**
	 * The constant OVERWRITE_POLICY_FALSE.
	 */
	boolean OVERWRITE_POLICY_FALSE = false;


    
	/**
	 *
	 * Searches for documents that have the specific properties specified
	 * @param customProperties properties expected to be covered
	 * @param lazy if true the file data is not retrieved.
	 * @return a list of documents that fulfill the specific properties
	 */
	List<Document> searchDocument(Map<String, String> customProperties, boolean lazy);

	/**
	 * It loads a document with the specific documentId, that is expected to be retrieved
	 * @param documentId the id of the document
	 * @return a document with its content, if lightMode is false
	 */
	Document getDocument(String documentId);


	/**
	 * Gets content.
	 *
	 * @param documentId the document id
	 * @return the content
	 */
	Document getContent(String documentId);

	/**
	 * It persists a file to the external repository
	 * @param document the document to be persisted, it uses the internal id to retrieve its data
	 * @return document
	 */
	Document saveDocument(Document document);

	/**
	 * It updates the properties of this Document
	 * @param document the document to be updated
	 * @return the persisted Document
	 */
	Document updateDocument(Document document);

	/**
	 * It deletes the document, logical or not is up to the external implementation
	 * @param document the document to be deleted
	 * @return the deleted Document with its actual properties, before being deleted
	 */
	Document deleteDocument(Document document);

	/**
	 * It archives the specified documents
	 * @param archivedFiles a map that specifies the path to be stored in the archive
	 * @param format format specified, initially will be supported zip as it is now
	 * @param persisted if the resulting zip will be stored externally or just on the temporary location
	 * @return the archive document that may contain only internal reference if it is not persisted externally
	 */
	Document archiveDocuments(Map<String, Document> archivedFiles, String format, boolean persisted);

	/**
	 * Decompress the archive and generates either exteranally saved references or only internally.
	 * The caller can fill its document properties and then persist or update
	 * @param archivedDocuments the archive that we expect to decompress
	 * @param format the expected format
	 * @param persisted to persist externally the contents
	 * @return the resulting Documents with their path inside the zip
	 */
	Map<String, Document> unarchiveDocuments(Document archivedDocuments, String format, boolean persisted);

	/**
	 * Validates the Document provided in input by checking all the business
	 * rules that apply. It returns a list of Validation errors that will
	 * specify which rule has not passed the validation test.
	 *
	 * @param document             the document
	 * @return the list of possible errors
	 */
	ErrorList validateDocument(String module, Document document, RulesInformation rulesInformation);

	/**
	 * Fetch document data from document repository or from URI
	 * @param document the document
	 * @return the document binary data
	 */
	byte[] getData(Document document);
}