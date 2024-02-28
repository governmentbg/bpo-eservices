/*******************************************************************************
 * * $Id:: FileUploadUtils.java 113489 2013-04-22 14:59:26Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.StringUtils;

import eu.ohim.sp.core.configuration.domain.xsd.AllowedFileType;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;

public class FileUploadUtils {

	public static String getAvailableFormats(FileUploadType fileUploadType) {
		
		StringBuffer allowedFileTypes = new StringBuffer();
		if (fileUploadType!=null && fileUploadType.getFileUploadInfo()!=null
				&& fileUploadType.getFileUploadInfo().getAllowedTypes()!=null 
				&& fileUploadType.getFileUploadInfo().getAllowedTypes().getAllowedFileType()!=null) {
			Iterator<AllowedFileType> iterator = fileUploadType.getFileUploadInfo().getAllowedTypes().getAllowedFileType().iterator();
			Collection<String> availableFileTypes = new ArrayList<String>();
			
			while (iterator.hasNext()) {
				AllowedFileType allowedFileType = iterator.next();
				if (StringUtils.hasText(allowedFileType.getDescription())) {
					availableFileTypes.add(allowedFileType.getDescription());
				}
			}
			
			Iterator<String> filetypesIterator = availableFileTypes.iterator();
			
			while (filetypesIterator.hasNext()) {
				String availableFileType = filetypesIterator.next();
				allowedFileTypes.append(availableFileType);
				if (filetypesIterator.hasNext()) {
					allowedFileTypes.append(", ");
				}
			}
			
		}
		return allowedFileTypes.toString();
	}
}
