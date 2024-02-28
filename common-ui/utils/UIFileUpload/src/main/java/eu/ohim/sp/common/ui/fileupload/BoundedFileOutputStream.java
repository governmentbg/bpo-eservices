/*******************************************************************************
 * * $Id:: BoundedFileOutputStream.java 113489 2013-04-22 14:59:26Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import eu.ohim.sp.common.ui.exception.SPMultipartException;
import eu.ohim.sp.common.ui.fileupload.util.FileUtil;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.apache.log4j.Logger;


public class BoundedFileOutputStream extends ThresholdingOutputStream {

	private final Logger log = Logger.getLogger(BoundedFileOutputStream.class);


	/**
     * The file to which output will be directed if the threshold is exceeded.
     */
    private File outputFile;
    private long threshold;

    /**
     * The output stream to which data will be written at any given time. This
     * will always be <code>diskOutputStream</code>.
     */
    private OutputStream currentOutputStream;

    public BoundedFileOutputStream(int threshold, File outputFile) {
        super(threshold);

        this.threshold = threshold;
        this.outputFile = outputFile;
	}

    
    public File getFile() {
		return outputFile;
	}
	
    public boolean isInMemory()
    {
        return false;
    }

	public void setFile(File outputFile) {
		this.outputFile = outputFile;
	}

	@Override
	protected void thresholdReached() throws IOException {
		SPMultipartException spMultipartException = new SPMultipartException("File maximum file reached", null, "fileupload.error.file.limit");
		spMultipartException.setArgs(FileUtil.getCanonicalFileSize(threshold));
		throw spMultipartException;
	}
	
	@Override
	protected OutputStream getStream() throws IOException {
    	if (currentOutputStream==null) {
			currentOutputStream = new FileOutputStream(outputFile);
    	}
    	log.debug("Can write : " + outputFile.canWrite());
    	log.debug("Name : " + outputFile.getName());
    	log.debug("Path : " + outputFile.getPath());

    	return currentOutputStream;
	}

}
