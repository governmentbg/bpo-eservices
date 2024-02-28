/*
 *  DocumentService:: ZipUtil 04/10/13 19:33 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.document.utils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.external.document.DocumentClient;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.*;

/**
 * Utility method that generates a ZIP used on the documentService that could look on the document
 * service to retrieve the data. The input will be a map of files that the key will be the path where each file
 * will be saved and the value will be document of core domain
 */
public class ZipUtil {

    private static final Logger LOGGER = Logger.getLogger(ZipUtil.class);

    protected static final String UNCOMPRESSED_LOW = "uncompressed";
    protected static final String UNCOMPRESSED_CAPS = "UNCOMPRESSED";
    protected static final String META_INF = "META-INF";
    protected static final String FOLDER_SEPARATOR = "/";

    private static final String COMPRESSED_LOW = "compressed";
    private static final String COMPRESSED_CAPS = "COMPRESSED";

    private static final int BUFFER_SIZE = 2048;

    private DocumentClient documentClient;

    private ZipOutputStream zip;
    private ByteArrayOutputStream bos;
    private ZipInputStream inZip;
    private long dateForUncompressed;

    public ZipUtil(DocumentClient documentClient) {
        this.documentClient = documentClient;
        bos = new ByteArrayOutputStream();
        zip = new ZipOutputStream(bos);
    }

    protected DocumentClient getDocumentClient() {
        return documentClient;
    }

    /**
     * Adds a file to the compressed file to the final artifact
     * Internal method, it shouldn't be used
     * @param path the path of the file, including the filename
     * @param data the actual content of the file
     * @param compress to compress the included data or not
     */
    protected void addFile(String path, byte[] data, boolean compress) {
        ZipEntry entry = null;
        LOGGER.debug(" >>> addFile START");

        // Checks if all the information is available
        if (StringUtils.isEmpty(path) || data == null) {
            throw new SPException("There was missing information when adding a file to the compressed archive.");
        }

        // Prepares the new zip entry with the mandatory information
        entry = new ZipEntry(path);
        entry.setSize(data.length);
        LOGGER.debug("	- Zip entry created");

        // Checks if it has to compress the file
        if (!compress) {
            entry.setMethod(ZipEntry.STORED);
            entry.setCrc(getCRC(data));
            LOGGER.debug("	- Not compressed");
        } else {
            entry.setMethod(ZipEntry.DEFLATED);
            LOGGER.debug("	- Compressed");
        }

        try {
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
        } catch (IOException e) {
            throw new SPException("There was an error when trying to add the file: " + path, e);
        }
        LOGGER.debug("		- File added: " + path);
        LOGGER.debug(" <<< addFile END");
    }

    /**
     * Gets the CRC code of a byte array
     * @param content the content of the file
     * @return the checksum
     */
    protected Long getCRC(byte[] content) {
        Checksum checksum = new CRC32();
        checksum.update(content, 0, content.length);
        return checksum.getValue();
    }

    /**
     * Closes the compressed file. No more files can be added unless it's opened again
     */
    protected void closeCompressedFile() {
        try {
            zip.finish();
            zip.close();
        } catch (IOException e) {
            throw new SPException("There was an error closing the compressed file", e);
        }
    }

    /**
     * Creates a compressed file in memory. If persisted is true, the file will be presisted in the
     * repository and only the metadata and the document ID will be returned. Otherwise the data
     * will be returned with the metadata, the document id will be null.
     *
     * @param archivedFiles the list of document to include in the compressed file
     * @param persisted the flag to know if the result compressed file must be persisted or not
     * @return the document file created. If persisted is true, with the document Id. Otherwise with the data.
     */
    public Document createArchiveDocument(Map<String, Document> archivedFiles, boolean persisted) {
        LOGGER.debug(" >>> createArchiveDocument START");
        Document finalDocument = null;

        // Fills the compressed file with the files in the map
        fillZip(archivedFiles);
        // Finish and close the compressed file
        closeCompressedFile();
        if (persisted) {
            // Stores the document in the repository
            finalDocument = getDocumentClient().addDocument(createRepositoryDocument());
        } else {
            finalDocument = createRepositoryDocument();
        }

        LOGGER.debug(" >>> createArchiveDocument END");
        return finalDocument;
    }

    /**
     * Introduces all the files in the compressed document
     * @param archivedFiles
     */
    protected void fillZip(Map<String, Document> archivedFiles) {
        // Adds every document to the archive file
        for (Map.Entry<String, Document> entry : archivedFiles.entrySet()) {
            // Gets the data of the document
            Document document = getDocumentClient().getDocument(entry.getValue());
            if (document.getDocumentId() == null) {
                throw new SPException("Document not found: " + entry.getValue().getFileName());
            }
            addFile(entry.getKey(), document.getData(), true);
        }
    }


    /**
     * Unarchives the documents compressed in the document included as parameter. If persisted is true the
     * extracted documents are uncompressed and persisted in the repository. Then the map will include only
     * the ids of this documents to refer them in the repository. If persisted is false, the documents will
     * include the data when included in the map
     *
     * @param document the document compressed
     * @param persisted the flag to know if the uncompressed files must be persisted in the repository
     * @return the map with the uncompressed files. If persisted true, with the document id. Otherwise, with the data.
     */
    public Map<String, Document> unarchiveDocuments(Document document, boolean persisted) {
        // Checks if the document has the information or we need to retrieve it from the SVN
        Document artifact = null;
        // Checks if we have the needed information
        if (document != null && document.getDocumentId() != null) {
            artifact = getDocumentClient().getDocument(document);
        } else if (document != null && document.getData() != null) {
            artifact = document;
        } else {
            throw new SPException("Empty data. Not available file");
        }

        // Opens de compressed file
        openArchiveFile(artifact);

        // Gets all the documents
        Map<String, Document> resultList =  getAllDocuments(persisted);

        return resultList;
    }

    /**
     * Opens the compressed file to insert documents
     * @param document
     */
    private void openArchiveFile(Document document) {
        byte[] dataClone = document.getData().clone();
        ByteArrayInputStream bis = new ByteArrayInputStream(dataClone);
        inZip = new ZipInputStream(bis);
    }

    /**
     * Gets all the documents from a compressed file
     * @param persisted to persist or not the archived artifacts
     * @return a map of files that has the located path on the original artifact as key and as value the actual document
     */
    private Map<String, Document> getAllDocuments(boolean persisted) {
        Map<String, Document> documents = new HashMap<String, Document>();
        ZipEntry entry = null;
		dateForUncompressed = new Date().getTime();

        try {
            while ((entry = inZip.getNextEntry()) != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int count;
                byte data[] = new byte[BUFFER_SIZE];
                if (!entry.isDirectory()) {
                    BufferedOutputStream out = new BufferedOutputStream(outputStream, BUFFER_SIZE);
                    while ((count = inZip.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.flush();
                    out.close();

                    // Checks if the filename has separators
                    String fileName = entry.getName();
                    if(fileName.indexOf('/') > 0){
                        fileName = fileName.substring(fileName.indexOf('/')+1);
                    }

                    // Checks if it's an attachment file
                    boolean attachmentFile = false;
                    if(entry.getName().contains("ATTACHMENTS")){
                        attachmentFile = true;
                    }

                    //Adds the file
                    if(persisted){
                        Document persistedDocument =
                                getDocumentClient().addDocument(createNewDocument(outputStream.toByteArray(), fileName, attachmentFile));
                        documents.put(entry.getName(), persistedDocument);
                    } else {
                        documents.put(entry.getName(), createNewDocument(outputStream.toByteArray(), fileName, attachmentFile));
                    }
                }
            }

            //Close the buffer connection
            inZip.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return documents;
    }

    /**
     * Creates a new document with one of the documents uncompressed
     * @param data
     * @param name
     * @param attachment
     * @return
     */
    private Document createNewDocument(byte[] data, String name, boolean attachment) {
        Document newDocument = new Document();
        newDocument.setData(data.clone());
        newDocument.setFileName(name);
		newDocument.setName(name);
        newDocument.getCustomProperties().put(FODocument.APPLICATION_TYPE, UNCOMPRESSED_CAPS);
        newDocument.getCustomProperties().put(FODocument.APPLICATION_STATUS, UNCOMPRESSED_LOW);
        newDocument.getCustomProperties().put(FODocument.MODULE, UNCOMPRESSED_LOW + "-" + dateForUncompressed);
		newDocument.getCustomProperties().put("customPath", UNCOMPRESSED_LOW + "-" + dateForUncompressed);

        // Sets the correct document type
        if("mimetype".equals(name) && !attachment){
            newDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_MIMETYPE.value());
        } else if ("application.xml".equals(name) && !attachment){
            newDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_XML.value());
        } else if ("container.xml".equals(name) && !attachment){
            newDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_CONTAINER.value());
        } else {
            newDocument.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_OTHER.value());
        }
		String mimetype = URLConnection.guessContentTypeFromName(newDocument.getFileName());
        newDocument.setFileFormat(mimetype == null ? "text" : mimetype);

        return newDocument;
    }

    /**
     * Creates the compressed file document to be included in the repository
     * @return
     */
    protected Document createRepositoryDocument() {
        Document document = new Document();
		Date date = new Date();

        document.setCustomProperties(new HashMap<String, String>());
		document.setName("compressed-" + date.getTime());
        document.getCustomProperties().put(FODocument.APPLICATION_TYPE, COMPRESSED_CAPS);
        document.getCustomProperties().put(FODocument.APPLICATION_STATUS, COMPRESSED_LOW);
        document.getCustomProperties().put(FODocument.MODULE, "compressed");
		document.getCustomProperties().put("customPath", "compressed");
        document.getCustomProperties().put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_EPUB.value());
        document.setFileName("compressed.epub");
        document.setFileFormat("application/epub+zip");
        document.setData(bos.toByteArray());

        return document;
    }




}
