package eu.ohim.sp.external.document;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLConnection;
import java.util.*;

/**
 * User: jaraful
 * Date: 30/07/13
 * Time: 15:23
 */
@Dependent @CMISDocumentClient
public class CMISDocumentClientBean implements DocumentClient {

	private static final Logger LOGGER = Logger
			.getLogger(CMISDocumentClientBean.class);

	@EJB(lookup="java:global/configurationLocal")
	private ConfigurationService configurationService;
	
	@EJB(lookup = "java:global/businessRulesLocal")
	private RulesService businessRulesService;

    /**
     * *********** CMIS constants for Alfresco *********
     * CUSTOM_PROPERTIES_KEY = "custom:customPropertiesKey";
     * CUSTOM_PROPERTIES = "custom:customProperties";
     * DATE_CREATED = "custom:dateCreated";
     * DATE_RECEIVED = "custom:dateReceived";
     * FILE_DATE = "custom:fileDate";
     * CUSTOM_CC_DOCUMENT = "custom:ccdocument";
     */

	// CMIS constants for OpenCMIS
	private static final String CUSTOM_PROPERTIES_KEY = "customPropertiesKey";
	private static final String CUSTOM_PROPERTIES = "customProperties";
	private static final String DATE_CREATED = "dateCreated";
	private static final String DATE_RECEIVED = "dateReceived";
	private static final String FILE_DATE = "fileDate";
	private static final String CUSTOM_CC_DOCUMENT = "CCDocument";

	private static final String CMIS_FOLDER = "cmis:folder";
	private static final String BASIC_SQL = "select * from " + CUSTOM_CC_DOCUMENT + " where ";

	// Repository constants
	private static final String REPOSITORY_URL = "repository.url";
	private static final String REPOSITORY_NAME = "repository.name";
	private static final String REPOSITORY_USERNAME = "repository.username";
	private static final String REPOSITORY_PASSWORD = "repository.password";
	private static final String PACKAGE = "general";
	private static final String RULES_PACKAGE = "tmefiling";
	private static final String DOCUMENT_SET = "document_set";

	/**
	 * Adds a document to be persisted in the repository
	 *
	 * @param document  the document object with the information to be persisted
	 * @return the document with the information updated after being persisted
	 */
	@Override
	public Document addDocument(final Document document) {
		Session session = null;
		try {
			session = login();

			if(document instanceof FODocument){
				return addFODocument(session, document);
			} else if(document != null) {
				return addDefaultDocument(session, document);
			} else {
				throw new SPException("Type of document not available");
			}
		} finally {
			session = null;
		}
    }

	private Document addDefaultDocument(Session session, final Document document){
		ContentStream contentStream = null;

		// Checks if all the information is available
		if (StringUtils.isEmpty(document.getFileFormat()) ||
				StringUtils.isEmpty(document.getFileName()) ||
				!document.getCustomProperties().containsKey(FODocument.MODULE)) {
			throw new SPException("There are missing fields: fileFormat, fileName or filingNumber");
		}

		// Gets the folder where the document is going to be stored
		String path = getDocumentPath(document);
		Folder folder = prepareFolder(path, session);

		// Creates the document properties for the repository
		Map<String, Object> documentProperties = createDocumentProperties(document);

		// Creates the stream to store in the repository
		contentStream = createContentStream(document);

		// Stores the document in the repository and sets the ID to the document.
		ObjectId objectId = folder.createDocument(documentProperties, contentStream, VersioningState.NONE);
		document.setData(null);
		document.setDocumentId(objectId.getId());

		return document;
	}

	private Document addFODocument(Session session, final Document document){
		ContentStream contentStream = null;

		// Checks if all the information is available
		if (StringUtils.isEmpty(document.getFileFormat()) ||
				StringUtils.isEmpty(document.getFileName()) ||
				!document.getCustomProperties().containsKey(FODocument.FILING_NUMBER)) {
			throw new SPException("There are missing fields: fileFormat, fileName or filingNumber");
		}

		// Gets the folder where the document is going to be stored
		String path = getDocumentPath(document);
		Folder folder = prepareFolder(path, session);

		// Creates the document properties for the repository
		Map<String, Object> documentProperties = createDocumentProperties(document);

		// Creates the stream to store in the repository
		contentStream = createContentStream(document);

		// Stores the document in the repository and sets the ID to the document.
		ObjectId objectId = folder.createDocument(documentProperties, contentStream, VersioningState.NONE);
		document.setData(null);
		document.setDocumentId(objectId.getId());

		return document;
	}

	// Gets the path in the repository before inserting it in the repository
	private String getDocumentPath(Document document){
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(document);
		Map<String, Object> result = businessRulesService.calculate(RULES_PACKAGE, DOCUMENT_SET, objectList);
		return ((String) result.get("path"));
	}

	private Map<String, Object> createDocumentProperties(Document document){
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put(PropertyIds.OBJECT_TYPE_ID, CUSTOM_CC_DOCUMENT);
		properties.put(PropertyIds.NAME, document.getName());
		String mimetype = URLConnection.guessContentTypeFromName(document.getFileName());
		properties.put(PropertyIds.CONTENT_STREAM_MIME_TYPE, mimetype==null?"text":mimetype);
		properties.put(PropertyIds.CONTENT_STREAM_FILE_NAME, document.getFileName());
		properties.put(PropertyIds.CONTENT_STREAM_LENGTH, BigInteger.valueOf(document.getData().length));

		// Adds the custom properties
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		Iterator iterator = document.getCustomProperties().entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			keys.add((String) mapEntry.getKey());
			values.add((String) mapEntry.getValue());
		}
		properties.put(CUSTOM_PROPERTIES_KEY, keys);
		properties.put(CUSTOM_PROPERTIES, values);
		properties.put(DATE_CREATED, document.getDateCreated());
		properties.put(DATE_RECEIVED, document.getDateReceived());
		properties.put(FILE_DATE, document.getFileDate());

		return properties;
	}

	private ContentStream createContentStream(Document document){
		ContentStream contentStream = null;

		String mimetype = URLConnection.guessContentTypeFromName(document.getFileName());

		InputStream stream = new ByteArrayInputStream(document.getData());
		contentStream = new ContentStreamImpl(document.getFileName(),
				BigInteger.valueOf(document.getData().length),
				mimetype==null?"text":mimetype,
				stream);

		return contentStream;
	}

	private Folder prepareFolder(String path, Session session){
		// This variable is used to get the name of each folder in the path
		String[] paths = path.split("/");
		// This variable stores the current path during the process
		StringBuffer stringPath = new StringBuffer();
		stringPath.append("/");
		// Current folder during the process
		Folder currentFolder = (Folder) session.getObjectByPath(stringPath.toString());

		for (int i = 1; i < paths.length; i++) {
			try{
				if(stringPath.toString().equals("/")){
					stringPath.append(paths[i]);
				} else {
					stringPath.append("/");
					stringPath.append(paths[i]);
				}
				currentFolder = (Folder) session.getObjectByPath(stringPath.toString());
			} catch (CmisObjectNotFoundException confe){
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(PropertyIds.OBJECT_TYPE_ID, CMIS_FOLDER);
				properties.put(PropertyIds.NAME, paths[i]);

				currentFolder = currentFolder.createFolder(properties);
			}
		}
		return currentFolder;
	}

	/**
	 * Gets a document from the repository
	 *
	 * @param document the document object with the information needed to find it in the repository
	 * @return the document with the information updated
	 */
	@Override
	public Document getDocument(final Document document) {
		Session session = null;
		try {
			session = login();

			CmisObject object = session.getObject(document.getDocumentId());
			return cmisObject2Document(object);
		} catch (CmisObjectNotFoundException confe) {
			LOGGER.debug("The document with document id: " + document.getDocumentId() + " was not found");
			document.setDocumentId(null);
			return document;
		} finally {
			session = null;
		}
    }

	private Document cmisObject2Document(final CmisObject cmisObject){
		Document document = new Document();

		// Normal info
		Calendar calendar = null;

		calendar = cmisObject.getPropertyValue(DATE_CREATED);
		if(calendar != null){
			document.setDateCreated(new Date(calendar.getTimeInMillis()));
		}
		calendar = cmisObject.getPropertyValue(DATE_RECEIVED);
		if(calendar != null){
			document.setDateReceived(new Date(calendar.getTimeInMillis()));
		}
		calendar = cmisObject.getPropertyValue(FILE_DATE);
		if(calendar != null){
			document.setFileDate(new Date(calendar.getTimeInMillis()));
		}

		BigInteger fileSize = cmisObject.getPropertyValue(PropertyIds.CONTENT_STREAM_LENGTH);
		document.setFileSize(fileSize.longValue());

		// Custom properties
		List<String> keys = cmisObject.getPropertyValue(CUSTOM_PROPERTIES_KEY);
		List<String> values = cmisObject.getPropertyValue(CUSTOM_PROPERTIES);
		Map<String, String> customPropertiesMap = new HashMap();
		for(int i=0; i<keys.size(); i++){
			customPropertiesMap.put(keys.get(i), values.get(i));
		}
		document.setCustomProperties(customPropertiesMap);
		document.setDocumentId((String) cmisObject.getPropertyValue(PropertyIds.OBJECT_ID));

		// Data
		org.apache.chemistry.opencmis.client.api.Document repoDocument =
				(org.apache.chemistry.opencmis.client.api.Document) cmisObject;

		ContentStream contentStream = repoDocument.getContentStream();
		InputStream inputStream = contentStream.getStream();
		try {
			document.setData(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
			throw new SPException("There was an error retrieving the file from the repository", e);
		}

		return document;
	}

	/**
	 * Updates a document document that already exists in the repository. If the document
	 * doesn't exists then is created
	 *
	 * @param document the document object with the information to be persisted
	 * @return the document with the information updated and the ID of the repository
	 */
	@Override
	public Document updateDocument(final Document document) {
		org.apache.chemistry.opencmis.client.api.Document repoDocument = null;

		if(StringUtils.isEmpty(document.getDocumentId())){
			LOGGER.debug("Missing information when trying to update a document: documentId");
			return document;
		}

		Session session = login();

		// Gets the document
		try{
			repoDocument = (org.apache.chemistry.opencmis.client.api.Document) session.getObject(document.getDocumentId());
		} catch(CmisObjectNotFoundException confe){
			LOGGER.debug("	> Error: the document with id: " + document.getDocumentId() + " couldn't be found");
			return document;
		}

		// Creates the document properties and updates them
		Map<String, Object> propertiesMap = createDocumentProperties(document);
		// Removes the name property if it's the same to avoid exceptions due to openCMIS server restrictions.
		if(repoDocument.getPropertyValue(PropertyIds.NAME).equals(document.getName())){
			propertiesMap.remove(PropertyIds.NAME);
		}

		repoDocument.updateProperties(propertiesMap);

		// Checks if it has to modify the content
		if(document.getData() != null){
			ContentStream contentStream = createContentStream(document);
			repoDocument.setContentStream(contentStream, true);
		}

		// Gets the result object
		Document persistedDocument = cmisObject2Document(session.getObject(document.getDocumentId()));
		persistedDocument.setData(null);

		return persistedDocument;
	}

	/**
	 * Search a document in the repository
	 *
	 * @param searchFilter the search criteria for finding the document/s
	 * @param lazy         the lazy flag. If true, files are not retrieved. If false, everything is retrieved
	 * @return the list with the documents
	 */
	@Override
	public List<Document> searchDocument(Map<String, String> searchFilter, boolean lazy) {
		Session session = login();

		// Checks for the filtering
		if(searchFilter == null){
			throw new SPException("The search filter can't be empty");
		}
		if(searchFilter.size() == 0){
			throw new SPException("The search filter can't be empty");
		}

		// Prepares the query with the filters
		String query = prepareQuery(searchFilter);

		// Checks if the query is prepared
		if(query == null){
			throw new SPException("There was an error creating the query");
		}

		// Runs the query and gets the results.
		ItemIterable<QueryResult> queryResults = session.query(query, false);
		List<Document> documentList = new ArrayList();
		for(QueryResult qR : queryResults){
			addResult2List(qR, documentList);
		}

		return documentList;
	}

	private void addResult2List(QueryResult queryResult, List<Document> documentList){
		Document document = new Document();

		// Normal info
		document.setDateCreated((Date) queryResult.getPropertyById(DATE_CREATED).getFirstValue());
		document.setDateReceived((Date) queryResult.getPropertyById(DATE_RECEIVED).getFirstValue());
		document.setFileDate((Date) queryResult.getPropertyById(FILE_DATE).getFirstValue());
		BigInteger fileSize = (BigInteger) queryResult.getPropertyById(PropertyIds.CONTENT_STREAM_LENGTH).getFirstValue();
		document.setFileSize(fileSize.longValue());

		// Custom properties
		List<String> keys = queryResult.getPropertyMultivalueById(CUSTOM_PROPERTIES_KEY);
		List<String> values = queryResult.getPropertyMultivalueById(CUSTOM_PROPERTIES);
		Map<String, String> customPropertiesMap = new HashMap();
		for(int i=0; i<keys.size(); i++){
			customPropertiesMap.put(keys.get(i), values.get(i));
		}
		document.setCustomProperties(customPropertiesMap);
		document.setDocumentId((String) queryResult.getPropertyById(PropertyIds.OBJECT_ID).getFirstValue());

		documentList.add(document);
	}

	private String prepareQuery(Map<String, String> searchFilter){
		// Checks if all the information is available
		if(searchFilter == null){
			LOGGER.debug("	> Error: The search filter is empty");
			return null;
		}

		// Creates the basic SQL to search in the correct path. The filters used are removed
		// in order to not adding them again in the next step
		String sql = BASIC_SQL;

		// Adds to the sql the specific filters
		boolean firstFilter = true;
		Set<Map.Entry<String, String>> entries = searchFilter.entrySet();
		StringBuffer stringBuffer = new StringBuffer(sql);

		for(Map.Entry<String, String> entry : entries){
			stringBuffer.append((!firstFilter ? " and " : "") + entry.getKey() + "='" + entry.getValue() + "'");
			firstFilter = false;
		}
		sql = stringBuffer.toString();

		return sql;
	}

	/**
	 * Deletes a document in the repository using the document id
	 *
	 * @param document the document to be deleted
	 * @return true if it was deleted, false otherwise
	 */
	@Override
	public boolean deleteDocument(Document document) {
		// Checks if all the information needed is available
		if(StringUtils.isEmpty(document.getDocumentId())){
			throw new SPException("Missing information when trying to update a document: documentId");
		}

		Session session = login();

		try{
			CmisObject cmisObject = session.getObject(document.getDocumentId());
			cmisObject.delete();
		} catch (CmisObjectNotFoundException confe){
			return false;
		}

		try{
			// If there is no exception, then the object still exists
			session.getObject(document.getDocumentId());
			return false;
		} catch (CmisObjectNotFoundException confe){
			return true;
		}
	}

	// Log in the repository
	private Session login() {
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		String repositoryUrl = configurationService.getValue(REPOSITORY_URL, PACKAGE);
		String repositoryUsername = configurationService.getValue(REPOSITORY_USERNAME, PACKAGE);
		String repositoryPassword = configurationService.getValue(REPOSITORY_PASSWORD, PACKAGE);
		String repositoryName = configurationService.getValue(REPOSITORY_NAME, PACKAGE);
		if(repositoryUrl == null || repositoryUsername == null || repositoryPassword == null || repositoryName == null){
			throw new SPException("There's missing information for the repository. Please set up properties correctly");
		}

		// user credentials
		parameter.put(SessionParameter.USER, repositoryUsername);
		parameter.put(SessionParameter.PASSWORD, repositoryPassword);

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, repositoryUrl);
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.REPOSITORY_ID, repositoryName);

		Session session = factory.createSession(parameter);

		if(session != null){
		return session;
		} else {
			throw new SPException("Couldn't create a repository session");
		}
	}
}
