/*******************************************************************************
 * * $Id:: BoundedDiskFileItemFactory.java 113489 2013-04-22 14:59:26Z karalch   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.exception.SPMultipartException;
import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Map;

public class BoundedDiskFileItemFactory implements FileItemFactory {
    // ----------------------------------------------------- Manifest constants


    /**
     * The default threshold above which uploads will be stored on disk.
     */
    public static final int DEFAULT_SIZE_THRESHOLD = 10240;


    // ----------------------------------------------------- Instance Variables


    /**
     * The directory in which uploaded files will be stored, if stored on disk.
     */
    private File repository;


    /**
     * The threshold above which uploads will be stored on disk.
     */
    private int sizeThreshold = DEFAULT_SIZE_THRESHOLD;
    
    
    /**
     * The threshold , in bytes, in map depending on the 
     *                      content type of the file
     */
    private Map<String, FileUploadType> mapSizeThreshold = null;
    
    private int maximumFilenameSize = -1;
    
    /**
     * Default if nothing else is defined
     */
    private int DEFAULT_MAXIMUM_SIZE = 52428800;

    /**
     * <p>The instance of {@link FileCleaningTracker}, which is responsible
     * for deleting temporary files.</p>
     * <p>May be null, if tracking files is not required.</p>
     */
    private FileCleaningTracker fileCleaningTracker;

    // ----------------------------------------------------------- Constructors


    /**
     * Constructs a preconfigured instance of this class.
     *
     * @param sizeThreshold The threshold, in bytes, below which items will be
     *                      stored as a file and above which they will be
     *                      rejected.
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    public BoundedDiskFileItemFactory(int sizeThreshold, File repository) {
        this.sizeThreshold = sizeThreshold;
        this.repository = repository;
    }

    
    /**
     * Constructs a preconfigured instance of this class.
     *
     * @param mapSizeThreshold The threshold , in bytes, in map depending on the 
     *                      content type of the file 
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    public BoundedDiskFileItemFactory(Map<String, FileUploadType> mapSizeThreshold, File repository) {
        this.mapSizeThreshold = mapSizeThreshold;
        this.repository = repository;
    }


    /**
     * Constructs a preconfigured instance of this class.
     *
     * @param mapSizeThreshold The threshold , in bytes, in map depending on the 
     *                      content type of the file 
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    public BoundedDiskFileItemFactory(Map<String, FileUploadType> mapSizeThreshold, File repository, int maximumFilenameSize) {
        this.mapSizeThreshold = mapSizeThreshold;
        this.repository = repository;
        this.maximumFilenameSize = maximumFilenameSize;
    }

// ------------------------------------------------------------- Properties


    /**
     * Returns the directory used to temporarily store files that are larger
     * than the configured size threshold.
     *
     * @return The directory in which temporary files will be located.
     *
     * @see #setRepository(java.io.File)
     *
     */
    public File getRepository() {
        return repository;
    }


    /**
     * Sets the directory used to temporarily store files that are larger
     * than the configured size threshold.
     *
     * @param repository The directory in which temporary files will be located.
     *
     * @see #getRepository()
     *
     */
    public void setRepository(File repository) {
        this.repository = repository;
    }


    /**
     * Returns the size threshold beyond which files are written directly to
     * disk. The default value is 10240 bytes.
     *
     * @return The size threshold, in bytes.
     *
     * @see #setSizeThreshold(int)
     */
    public int getSizeThreshold() {
        return sizeThreshold;
    }


    /**
     * Sets the size threshold beyond which files are written directly to disk.
     *
     * @param sizeThreshold The size threshold, in bytes.
     *
     * @see #getSizeThreshold()
     *
     */
    public void setSizeThreshold(int sizeThreshold) {
        this.sizeThreshold = sizeThreshold;
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Create a new {@link org.apache.commons.fileupload.disk.DiskFileItem}
     * instance from the supplied parameters and the local factory
     * configuration.
     *
     * @param fieldName   The name of the form field.
     * @param contentType The content type of the form field.
     * @param isFormField <code>true</code> if this is a plain form field;
     *                    <code>false</code> otherwise.
     * @param fileName    The name of the uploaded file, if any, as supplied
     *                    by the browser or other client.
     *
     * @return The newly created file item.
     */
    public FileItem createItem(String fieldName, String contentType,
            boolean isFormField, String fileName) {
    	BoundedDiskFileItem result = null;
    	if (mapSizeThreshold==null || isFormField) {
    		result = new BoundedDiskFileItem(fieldName, contentType,
                isFormField, fileName, sizeThreshold, repository);
    	} else {
    		FileUploadType fileUploadType = mapSizeThreshold.get(fieldName);
    		if (fileName!=null 
    				&& maximumFilenameSize>-1) {
    			String baseFileName = FilenameUtils.getBaseName(fileName);
    			if (baseFileName.length()>maximumFilenameSize) {
	    			SPMultipartException spMultipartException = new SPMultipartException("Too big filename", null, "fileupload.error.filename.big");
	    			spMultipartException.setArgs(Integer.toString(maximumFilenameSize));
	    			throw spMultipartException;
    			}
    		}
    		if (fileUploadType!=null) {
    			if (fileUploadType.isAvailableAllContentTypes()) {
		    		result = new BoundedDiskFileItem(fieldName, contentType,
		                    isFormField, fileName, DEFAULT_MAXIMUM_SIZE, repository);
    			}
    			for (AllowedFileType allowedFileType : fileUploadType.getFileUploadInfo().getAllowedTypes().getAllowedFileType()) {
    				if (allowedFileType.getValue().equals(contentType)) {
    		    		result = new BoundedDiskFileItem(fieldName, contentType,
    		                    isFormField, fileName, allowedFileType.getAllowedSize(), repository);
    				}
    			}
    		} else {
    			throw new SPMultipartException("Not Supported FileType", null, "fileupload.error.not.supported");
    		}
    		if (result == null) {
    			SPMultipartException spMultipartException = new SPMultipartException("Not Supported FileType", null, "fileupload.error.not.supported");
    			String availableFormats = FileUploadUtils.getAvailableFormats(fileUploadType);
    			if (StringUtils.hasText(availableFormats)) {
    				spMultipartException = new SPMultipartException("Not Supported FileType", null, "fileupload.error.supported.filetypes");
        			spMultipartException.setArgs(availableFormats);
    			}
    			throw spMultipartException;
    		}
    	}
        FileCleaningTracker tracker = getFileCleaningTracker();
        if (tracker != null) {
            tracker.track(result.getTempFile(), this);
        }
        return result;
    }


    /**
     * Returns the tracker, which is responsible for deleting temporary
     * files.
     * @return An instance of {@link FileCleaningTracker}, or null
     *   (default), if temporary files aren't tracked.
     */
    public FileCleaningTracker getFileCleaningTracker() {
        return fileCleaningTracker;
    }

    /**
     * Sets the tracker, which is responsible for deleting temporary
     * files.
     * @param pTracker An instance of {@link FileCleaningTracker},
     *   which will from now on track the created files, or null
     *   (default), to disable tracking.
     */
    public void setFileCleaningTracker(FileCleaningTracker pTracker) {
        fileCleaningTracker = pTracker;
    }

}
