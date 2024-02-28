/*******************************************************************************
 * * $Id:: FileServiceInterface.java 50771 2012-11-14 15:10:27Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import eu.ohim.sp.common.ui.form.resources.FileContent;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;

/**
 * Interface that has the propose of managing the addition and the removal of
 * files
 * 
 * @author karalch
 */
public interface FileServiceInterface {

    /**
     * Adds a file to the working workspace of the content repository
     * 
     * @param provisionalId the provisional id of the application
     * @param file the file to be added
     * @return String result of the file added
     */
    StoredFile addFile(String provisionalId, MultipartFile file, FileWrapper wrapper) throws IOException;

    StoredFile addFile(String provisionalId, String originalFileName, String contentType, Long fileSize, byte[] data, FileWrapper wrapper) throws IOException;

    /**
     * Adds a file to the working workspace of the content repository
     * 
     * @param provisionalId the provisional id of the application
     * @param filename the file name
     * @param fileFormat the file format
     * @param data the file data
     * @param generateThumbnail if you want to generate a thumbnail
     * @return Stored file.
     */
    StoredFile addFile(String provisionalId, String filename, String fileFormat, byte[] data, boolean generateThumbnail)
            throws IOException;

    /**
     * Returns a file that has the given file name
     * 
     * @param provisionalId the provisional id of the application
     * @param fileName the file name of the file that is requested
     * @param cached if false reads always from back-end
     * @return the input stream of the file that is requested
     */
    FileContent getFileStream(String documentId);

    /**
     * Removes this file from the file repository
     * 
     * @param file The file to remove.
     */
    void removeFile(StoredFile file);
}
