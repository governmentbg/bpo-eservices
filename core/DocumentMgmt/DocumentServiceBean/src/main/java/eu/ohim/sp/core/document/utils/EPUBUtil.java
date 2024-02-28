/*
 *  DocumentService:: EPUBUtil 04/11/13 12:30 karalch $
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
import eu.ohim.sp.core.domain.resources.Document;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Utility method that generates an EPUB that generates the default mimetype file
 * and expects a container.xml to be provided on the list of expected files, otherwise
 * it will fail
 */
public class EPUBUtil extends ZipUtil {

    private static final Logger LOGGER = Logger.getLogger(EPUBUtil.class);

    /** Internal placeholder for mimetype file */
    private static final String MIMETYPE = "mimetype";
    /** Expected placeholder for container.xml file*/
    private static final String CONTAINER_XML = "container.xml";

    /** Contents of mimetype file */
    private static final String EPUB_MIMETYPE = "application/epub+zip";

    /**
     * Instantiates a new Compression util.
     *
     * @param documentClient the document client
     */
    public EPUBUtil(DocumentClient documentClient) {
        super(documentClient);
    }

    /**
     * Adds the file mimetype on the epub and places container.xml on the META_INF folder
     * and introduces all the files in the compressed document
     * @param archivedFiles a map that contains the path as key and the document as value
     */
    protected void fillZip(Map<String, Document> archivedFiles) {
        // Adds the special files
        // mimetype
        LOGGER.debug("Filling epub ");
        try {
            addFile(MIMETYPE, EPUB_MIMETYPE.getBytes("UTF-8"), false);
        } catch (UnsupportedEncodingException e) {
            throw new SPException("Unexpected failure. Not supported UTF-8 encoding", e);
        }
        // container
        addFile(META_INF + FOLDER_SEPARATOR + archivedFiles.get(CONTAINER_XML).getFileName(), archivedFiles.get(CONTAINER_XML).getData(), true);
        LOGGER.debug("Added container.xml");
        // application.xml it should be checked container.xml

        // Adds every document to the archive file
        for (Map.Entry<String, Document> entry : archivedFiles.entrySet()) {
            // Gets the data of the document
            if (!entry.getKey().equals(CONTAINER_XML) && !entry.getKey().equals(MIMETYPE)) {
                Document document = entry.getValue();
                if (entry.getValue().getData()==null) {
                    document = getDocumentClient().getDocument(entry.getValue());
                    if (document.getDocumentId() == null) {
                        throw new SPException("Document not found: " + entry.getValue().getFileName());
                    }
                }
                addFile(entry.getKey(), document.getData(), true);
                LOGGER.debug("Added file on path : " + entry.getKey());
            }
        }
        LOGGER.debug("Finished filling EPUB");
    }
}
