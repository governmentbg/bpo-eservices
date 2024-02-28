/*******************************************************************************
 * * $Id:: FileUtil.java 54481 2013-01-15 15:55:49Z blanean                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.util;

import eu.ohim.sp.common.SPException;
import org.apache.log4j.Logger;

import java.io.*;

@Deprecated
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * Returns the file as a string
	 *
     * use {@link org.apache.commons.io.FileUtils.readFileToString} instead
	 * @param filePath
	 *            the file path where the file will be loaded
	 * @param classpath
	 *            true of it is loaded from the classpath
	 * @return the file as a string
	 */
	public static String readFileAsString(String filePath, boolean classpath) {
		try {
			StringBuffer fileData = new StringBuffer(1000);
			InputStream inputStream;
			if (classpath) {
				inputStream = new BufferedInputStream(
						FileUtil.class.getResourceAsStream(filePath));
			} else {
				inputStream = new BufferedInputStream(new FileInputStream(filePath));
			}

			byte[] buf = new byte[1024];
			int numRead;
			try {
				while ((numRead = inputStream.read(buf)) != -1) {
					String readData = new String(buf, "UTF-8");
					fileData.append(readData);
					buf = new byte[1024];
				}
			} catch (IOException e) {
				logger.debug("ERROR. readFileasString. File not found. File: "
						+ filePath);
				throw new SPException(e);
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
			return fileData.toString().trim();
		} catch (FileNotFoundException e) {
			logger.debug("ERROR. ConfigParamDAO. readFileasString. File not found. File: "
					+ filePath);
			throw new SPException(e);
		} catch (IOException e) {
			logger.debug("ERROR. ConfigParamDAO. readFileasString. I/O file error. File: "
					+ filePath);
			throw new SPException(e);
		}
	}
	
	
	
	
	public static boolean writeStringAsFile(String filePath, String object) {
		try {
			PrintStream out = null;
			try {
			    out = new PrintStream(new FileOutputStream(filePath));
			    out.print(object);
			}
			finally {
			    if (out != null) out.close();
			}
			return true;
		} catch (FileNotFoundException e) {
			logger.debug("ERROR. ConfigParamDAO. writeFileasString. File not found. File: "
					+ filePath);
			throw new SPException(e);
		} catch (IOException e) {
			logger.debug("ERROR. ConfigParamDAO. writeFileasString. I/O file error. File: "
					+ filePath);
			throw new SPException(e);
		}
	}
	
	
	

	
	
	
	
	
	
	
	

}
