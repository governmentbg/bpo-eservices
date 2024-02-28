package eu.ohim.sp.external.document;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.rules.RulesService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import static eu.ohim.sp.external.document.JcrConstants.*;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.jcr.*;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;

/**
 *
 * User: jaraful
 * Date: 30/07/13
 * Time: 15:23
 */
@SuppressWarnings("ALL")
@Dependent @JCRDocumentClient
public class JCRDocumentClientBean implements DocumentClient {
    private static final Logger LOGGER = Logger.getLogger(JCRDocumentClientBean.class);

    // Custom constants
    private static final String SEPARATOR = "/";

    // Repository constants
    static final String DOCUMENT_SET = "document_set";
    public static final String DELETE_FLAG = "#DELETE.@ROOT#";

    @EJB(lookup = "java:global/businessRulesLocal")
    private RulesService businessRulesService;

    @Resource(name = "java:/jcrJCAXA")
    private Repository repository;

    private Credentials credentials;

    @PostConstruct
    public void init() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("jackrabbit/user.properties"));
        credentials = new SimpleCredentials(properties.getProperty("username"), properties.getProperty("password").toCharArray());
    }

    /**
     * Adds a document to be persisted in the repository
     *
     * @param document the document object with the information to be persisted
     * @return the document with the information updated after being persisted
     */
    @Override
    public Document addDocument(final Document document) {
        Session session = null;

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("	- Document values:");
            for (String property : document.getCustomProperties().keySet()) {
                LOGGER.debug("		- " + property + " = " + document.getCustomProperties().get(property));
            }
        }

        // Checks if all the information is available
        if (StringUtils.isNotEmpty(document.getDocumentId())) {
            throw new SPException("Adding documents with a document id is not allowed");
        }
        if (document.getFileName() == null || document.getCustomProperties().get("applicationType") == null) {
            throw new SPException("There is missing information: fileName, applicationType");
        } else if (document.getName() == null) {
            document.setName(document.getFileName());
        }
		if(!(document instanceof FODocument)){
			if(document.getCustomProperties().get("customPath") == null){
				throw new SPException("There is missing information: customPath");
			}
		}

        try {
            // Login in the repository
            session = repository.login(credentials);

			// Sets the module
			setDocumentModule(document);

            // Gets the path
            String path = getDocumentPath(document);

            // Prepares the path before creating the document
            prepareFolder(path, session);
            // Creates the node
            String nodeId = createDocumentNode(session, path, document);

            // Prepares the document before returning it
            document.setDocumentId(nodeId);
            document.setData(null);

            return document;
        } catch (RepositoryException e) {
            throw new SPException("Failed to login to repository", e);
        } finally {
            if (session != null) {
                try {
                    session.save();
                } catch (RepositoryException e) {
                    LOGGER.debug("There was an error saving the session when adding a document", e);
                }
                session.logout();
            }
        }
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
            session = repository.login(credentials);

            // Gets the document from the repository
            Document persistedDocument = getDocumentFromNode(session, document.getDocumentId());

            if (persistedDocument == null) {
                document.setDocumentId(null);
                return document;
            } else {
                persistedDocument.setDocumentId(document.getDocumentId());
                return persistedDocument;
            }
        } catch (RepositoryException e) {
            throw new SPException("Failed to login to repository", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
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
        Session session = null;

        // Checks if all the information is available
        if (document.getFileName() == null) {
            throw new SPException("There is missing information: fileName");
        } else {
            if (document.getName() == null) {
                document.setName(document.getFileName());
            }
        }

        try {
            // Gets the session
            session = repository.login(credentials);

            // Updates the document and remove the data
            Document updatedDocument = updateDocumentNode(session, document);

            if (updatedDocument == null) {
                document.setDocumentId(null);
                return document;
            }

            updatedDocument.setData(null);

            return updatedDocument;
        } catch (RepositoryException e) {
            throw new SPException("There was an error updating the document", e);
        } finally {
            // Closes the session before finishing.
            if (session != null) {
                try {
                    session.save();
                } catch (RepositoryException e) {
					LOGGER.debug("There was an error saving the session when updating a document", e);
                }
                session.logout();
            }
        }
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
        Session session = null;
        boolean firstFilter = true;
        StringBuffer sql = new StringBuffer();

        // Checks for the filtering
        if (searchFilter == null) {
            throw new SPException("The search filter can't be empty");
        }
        if (searchFilter.size() == 0) {
            throw new SPException("The search filter can't be empty");
        }

        try {
            // Logs in the repository
            session = repository.login(credentials);

            // Prepares the basic sql
            sql.append("select * from [nt:unstructured] where ");

            // Loops the search filter and adds the constraints to the sql
            Iterator iterator = searchFilter.entrySet().iterator();

            while (iterator.hasNext()) {
                if (firstFilter) {
                    firstFilter = false;
                } else {
                    sql.append(" and ");
                }
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                sql.append(mapEntry.getKey());
                sql.append("='");
                sql.append(mapEntry.getValue());
                sql.append("'");
            }

            // Makes the query to the repository
            QueryManager qm = session.getWorkspace().getQueryManager();
            Query query = qm.createQuery(sql.toString(), Query.JCR_SQL2);
            Iterator<Node> nodeIterator = query.execute().getNodes();

            // Gets the documents in a list to return
            List<Document> documentList = new ArrayList<Document>();
            while (nodeIterator.hasNext()) {
                Node nextNode = nodeIterator.next();
                Document retrievedDocument = getDocumentFromNode(session, nextNode.getParent().getIdentifier());
                if (retrievedDocument != null) {
                    documentList.add(retrievedDocument);
                }
            }

            return documentList;
        } catch (InvalidQueryException e) {
            throw new SPException("There was an error in the search query", e);
        } catch (RepositoryException e) {
            throw new SPException("There was an error in the repository while searching documents", e);
        } finally {
            // Closes the session before finishing
            if (session != null) {
                session.logout();
            }
        }
    }

    /**
     * Deletes a document in the repository using the document id
     *
     * @param document the document to be deleted
     * @return true if it was deleted, false otherwise
     */
    @Override
    public boolean deleteDocument(final Document document) {
        if (document == null) {
            throw new SPException("There was an error deleting a document. The document is null");
        }
        if (document.getDocumentId() == null) {
            throw new SPException("There was an error deleting a document. The document id is mandatory");
        }

        LOGGER.debug("Deleting node: " + document.getDocumentId());

        Session session = null;
        try {
            // Logs in the repository
            session = repository.login(credentials);

            // Searches the document and remove it
            Node docNode = session.getNodeByIdentifier(document.getDocumentId());
            Node parent = docNode.getParent();
            docNode.remove();

            if(DELETE_FLAG.equals(document.getComment())) {
                parent.remove();
            }

            return true;
        } catch (RepositoryException e) {
            return false;
        } finally {
            if (session != null) {
                try {
                    session.save();
                } catch (RepositoryException e) {
					LOGGER.debug("There was an error saving the session when deleting a document", e);
                }
                session.logout();
            }
        }
    }

    // Gets the path in the repository before inserting it in the repository
    private String getDocumentPath(Document document) {
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(document);
        Map<String, Object> result = businessRulesService.calculate(document.getModule(), DOCUMENT_SET, objectList);
        return ((String) result.get("path"));
    }

    // Method for converting from core object to Ocm object
    private String createDocumentNode(Session session, String path, final Document document) {
        Node aiNode, docNode, cpNode;
        try {
            // Gets the application id node
            aiNode = session.getNode(path);
            Document preparedDocument = renameDocument(document, nextValueNumber(aiNode));
            // Creates the document node
            docNode = aiNode.addNode(preparedDocument.getName());
            addNormalProperties2Node(preparedDocument, docNode);
            // Creates the custom properties node
            cpNode = docNode.addNode("customProperties");
            addCustomProperties2Node(preparedDocument.getCustomProperties(), cpNode);
            // Creates the file node
            if (preparedDocument.getData() != null) {
                createFileNode(session, docNode, preparedDocument);
            } else {
                LOGGER.debug(" > WARNING: Saving a document without data");
            }

            return docNode.getIdentifier();
        } catch (RepositoryException e) {
            throw new SPException("There was an error creating the document node", e);
        }
    }

    // Method for renaming the attachments
    private Document renameDocument(Document document, int newValue) {
        boolean attType = true;
        // Checks if the document has attachment type
        if (document.getCustomProperties().containsKey("attachmentType") &&
                !document.getCustomProperties().get("attachmentType").equals(FormatXML.APPLICATION_OTHER.value())) {
            attType = false;
        }

        // Checks if the attachment must be renamed
        if (attType) {
            document.getCustomProperties().put("realName", document.getName());
            document.setName("ATT" + String.format("%04d", newValue));
        }
        return document;
    }

    // Method for updating the document persisted
    private Document updateDocumentNode(Session session, final Document document) {
        Node docNode, cpNode;
        try {
            // Gets the document from the repository and removes the customProperties and file
            try {
                docNode = session.getNodeByIdentifier(document.getDocumentId());
            } catch (RepositoryException re) {
                LOGGER.error("There was an error retrieving the document when updating.", re);
                return null;
            }

            NodeIterator iterator = docNode.getNodes();
            while (iterator.hasNext()) {
                Node currentNode = (Node) iterator.next();
                currentNode.remove();
            }
            // Updates the node properties
            addNormalProperties2Node(document, docNode);
            // Creates the new custom properties
            cpNode = docNode.addNode("customProperties");
            addCustomProperties2Node(document.getCustomProperties(), cpNode);
            // Creates the new file node
            if (document.getData() != null) {
                createFileNode(session, docNode, document);
            } else {
                LOGGER.info(" > WARNING: Updating a document without data");
            }

            return document;
        } catch (ItemNotFoundException e) {
            document.setDocumentId(null);
            return document;
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new SPException("There was an error trying to update the document in the repository", e);
        }
    }

    // Method for updating the next document id (used for renaming the documents)
    private int nextValueNumber(Node documentNode) {
        long nextValue = 0;
        // Gets the current value
        try {
            nextValue = documentNode.getProperty("nextValue").getLong();
        } catch (RepositoryException e) {
            nextValue = 1;
        }

        // Sets the new updated one
        try {
            documentNode.setProperty("nextValue", nextValue + 1);
            return (int) nextValue;
        } catch (RepositoryException e) {
            LOGGER.error(e);
            throw new SPException("There was an error setting the 'nextValue' property", e);
        }
    }

    // Method for adding the normal properties to the node
    private void addNormalProperties2Node(Document document, Node node) {
        Calendar calendar = Calendar.getInstance();
        try {
            // Adds the dates
            if (document.getDateCreated() != null) {
                calendar.setTime(document.getDateCreated());
                node.setProperty("dateCreated", calendar);
            } else {
                node.setProperty("dateCreated", (Calendar) null);
            }

            if (document.getDateReceived() != null) {
                calendar.setTime(document.getDateReceived());
                node.setProperty("dateReceived", calendar);
            } else {
                node.setProperty("dateReceived", (Calendar) null);
            }

            if (document.getFileDate() != null) {
                calendar.setTime(document.getFileDate());
                node.setProperty("fileDate", calendar);
            } else {
                node.setProperty("fileDate", (Calendar) null);
            }
            // Adds the fileSize
            node.setProperty("fileSize", document.getData().length);
        } catch (RepositoryException e) {
            throw new SPException("There was an error creating the normal properties", e);
        }
    }

    // Method for adding the custom properties to the document node
    private void addCustomProperties2Node(Map<String, String> customProperties, Node node) {
        try {
            Iterator iterator = customProperties.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                node.setProperty((String) mapEntry.getKey(), (String) mapEntry.getValue());
            }
        } catch (RepositoryException e) {
            throw new SPException("There was an error adding the custom properties to the node", e);
        }
    }

    // Method for adding the binary information to the document node
    public Node createFileNode(Session session, Node parentNode, Document document) {
        try {
            Node node = parentNode.addNode(document.getFileName(), NT_FILE);
            Node contentNode = node.addNode(JCR_CONTENT, NT_RESOURCE);
            String mimetype = URLConnection.guessContentTypeFromName(document.getFileName());
            contentNode.setProperty(JCR_DATA, session.getValueFactory().createBinary(new ByteArrayInputStream(document.getData())));
            contentNode.setProperty(JCR_MIMETYPE, "".equals(mimetype) ? "text" : mimetype);
            contentNode.setProperty(JCR_ENCODING, "");
            contentNode.setProperty(JCR_LASTMODIFIED, new Date().getTime());

            return contentNode;
        } catch (RepositoryException e) {
            throw new SPException("There was an error while creating the file node", e);
        }
    }

    // Transforms the object in the repository to a core object
    private Document getDocumentFromNode(Session session, String documentId) {
        Document document = new Document();
        LOGGER.info("Retrieving document id : " + documentId);
        try {
            Node node = session.getNodeByIdentifier(documentId);

            // Gets the normal values
            document.setDateCreated(getRepositoryDate("dateCreated", node));
            document.setDateReceived(getRepositoryDate("dateReceived", node));
            document.setFileDate(getRepositoryDate("fileDate", node));

            // Gets the custom properties
            document.setCustomProperties(getNodeCustomProperties(node.getNode("customProperties")));

            // Gets the data
            document.setData(getNodeData(node));
            if (document.getData() != null) {
                document.setFileSize(Long.valueOf(document.getData().length));
            } else {
                LOGGER.debug(" > WARNING: Getting a document without data");
                document.setFileSize(Long.valueOf(0));
            }

            // Sets the Id
            document.setDocumentId(documentId);

            return document;
        } catch (RepositoryException e) {
            LOGGER.warn("Cannot add this document ; " + e);
            return null;
        }
    }

    // Method for getting the dates from the repository
    private Date getRepositoryDate(String dateName, Node node) {
        try {
            if (node.hasProperty(dateName)) {
                Calendar calendar = node.getProperty(dateName).getDate();
                return new Date(calendar.getTimeInMillis());
            } else {
                return null;
            }
        } catch (RepositoryException e) {
            LOGGER.warn("There was an error during the creation of the date. ERROR: \n" + e);
            return null;
        }
    }

    // Method for retrieve the custom properties stored in the node
    private Map<String, String> getNodeCustomProperties(Node node) {
        Map<String, String> propertiesMap = new HashMap<String, String>();

        try {
            PropertyIterator iterator = node.getProperties();
            while (iterator.hasNext()) {
                Property property = (Property) iterator.next();
                propertiesMap.put(property.getName(), property.getString());
            }

            return propertiesMap;
        } catch (RepositoryException e) {
            throw new SPException("There was an error getting the custom properties from the node", e);
        }
    }

    // Method for retrieve the binary data in the node
    private byte[] getNodeData(Node node) {
        Node dataNode = null;
        try {
            // Gets the file node
            NodeIterator iterator = node.getNodes();
            while (iterator.hasNext()) {
                Node currentNode = (Node) iterator.next();
                if (currentNode.getPrimaryNodeType().getName().equals("nt:file")) {
                    dataNode = currentNode;
                    break;
                }
            }

            // If the document has no data
            if (dataNode == null) {
                return null;
            }

            // Gets the data of the content node
            Node contentNode = dataNode.getNode("jcr:content");
            Binary data = contentNode.getProperty("jcr:data").getBinary();

            // Transforms it in byte array
            return IOUtils.toByteArray(data.getStream());
        } catch (RepositoryException e) {
            throw new SPException("There was an error trying to get the data from the node", e);
        } catch (IOException e) {
            throw new SPException("There was an error when converting Binary information to bytearray", e);
        }
    }

    /**
     * Method to prepare the folder before inserting an object. If the path doesn't exists, is created
     *
     * @param path - path to create
     * @param session - JCR session
     */
    private void prepareFolder(String path, Session session) {
        // This variable is used to get the name of each folder in the path
        String[] paths = path.split(SEPARATOR);
        // This variable stores the current path during the process
        StringBuffer stringPath = new StringBuffer();
        stringPath.append(SEPARATOR);

        try {
            // Loops the path array and creates it in each iteration
            for (int i = 1; i < paths.length; i++) {
                String currentPath = paths[i];
                Node currentNode = session.getNode(stringPath.toString());
                // This method checks if the current folder exists in the path. If not, is created.
                getOrAddNode(currentNode, currentPath, NT_UNSTRUCTURED);
                if (stringPath.toString().equals(SEPARATOR)) {
                    stringPath.append(currentPath);
                } else {
                    stringPath.append(SEPARATOR);
                    stringPath.append(currentPath);
                }
            }
        } catch (RepositoryException e) {
            throw new SPException("There was an error trying to create the path in the repository.", e);
        } finally {
            try {
                // We need to persist the changes the moment we finish with the path in order to have it
                // available for the next operations
                session.save();
            } catch (RepositoryException e) {
				LOGGER.debug("There was an error saving the session when preparing the folder.", e);
            }
        }
    }


    /**
     * Returns the named child of the given node, creating the child if
     * it does not already exist. If the child node gets added, then it
     * is created with the given node type. The caller is expected to take
     * care of saving or discarding any transient changes.
     *
     * @param parent parent node
     * @param name   name of the child node
     * @param type   type of the child node or {@code null},
     *               ignored if the child already exists
     * @return the child node
     * @throws RepositoryException if the child node can not be accessed
     *                             or created
     * @see Node#getNode(String)
     * @see Node#addNode(String, String)
     * @see Node#isNodeType(String)
     */
    private Node getOrAddNode(Node parent, String name, String type)
            throws RepositoryException {
        if (parent.hasNode(name)) {
            return parent.getNode(name);
        } else if (type != null) {
            return parent.addNode(name, type);
        } else {
            return parent.addNode(name);
        }
    }

	/**
	 * Sets the value of the module to be used when triggering drools
	 * @param document the document information
	 */
	private void setDocumentModule(Document document){
		String applicationType = document.getCustomProperties().get("applicationType");
		if(applicationType.equals("TM") || applicationType.equals("GI_EFILING")){
			document.setModule("tmefiling");
		} else if(applicationType.equals("DS")){
			document.setModule("dsefiling");
		} else if(applicationType.equals("PT_EFILING") || applicationType.equals("UM_EFILING") || applicationType.equals("EP_EFILING") || applicationType.equals("SV_EFILING") || applicationType.equals("SPC_EFILING")|| applicationType.equals("IS_EFILING")){
            document.setModule("ptefiling");
        } else {
			document.setModule("eservices");
		}
	}
}
