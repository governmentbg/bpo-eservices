/*******************************************************************************
 * * $Id:: CustomCommonsMultipartResolver.java 113489 2013-04-22 14:59:26Z karal#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload;

import eu.ohim.sp.common.ui.exception.SPMultipartException;
import eu.ohim.sp.common.ui.fileupload.exception.ValidationException;
import eu.ohim.sp.common.ui.fileupload.validation.FileMapValidator;
import eu.ohim.sp.common.ui.fileupload.validation.FileMapValidatorImpl;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

public class CustomCommonsMultipartResolver extends CommonsMultipartResolver {

	private final Logger log = Logger.getLogger(CustomCommonsMultipartResolver.class);

	private String tempRepository;

	private Map<String, FileUploadType> fileUploadConfigurationMap;
	private int maximumFilenameSize = -1;

	/**
	 * Initialize the underlying
	 * <code>org.apache.commons.fileupload.servlet.ServletFileUpload</code>
	 * instance. Can be overridden to use a custom subclass, e.g. for testing
	 * purposes.
	 * 
	 * @param fileItemFactory
	 *            the Commons FileItemFactory to use
	 * @return the new ServletFileUpload instance
	 */
	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		return new ServletFileUpload(new BoundedDiskFileItemFactory(fileUploadConfigurationMap, 
				(tempRepository != null?new File(tempRepository):null), maximumFilenameSize));
	}

	/**
	 * It specifies where the commons fileupload will store temporary files
	 * 
	 * @return the location of temporary repository
	 */
	public String getTempRepository() {
		return tempRepository;
	}

	/**
	 * It sets where the commons fileupload will store temporary files. If it is
	 * not specified the folder where the files will be stored is defined in
	 * system property <code>"java.io.tmpdir"</code>
	 */
	public void setTempRepository(String tempRepository) {
		this.tempRepository = tempRepository;
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws SPMultipartException {
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = super.resolveMultipart(request);
			multipartHttpServletRequest.getFileNames();

			FileMapValidator validator = new FileMapValidatorImpl(true);
			validator.validateMultipartFiles(multipartHttpServletRequest.getFileMap(), fileUploadConfigurationMap);

			return multipartHttpServletRequest;
		} catch (MultipartException e) {
			SPMultipartException exception = new SPMultipartException(e);
			exception.setErrorCode(e.getMessage());
			throw exception;
		}
        catch (ValidationException e)
        {
            throw new SPMultipartException(e.getMessage(), e, e.getErrorCode());
        }
	}

	public Map<String, FileUploadType> getFileUploadConfigurationMap() {
		return fileUploadConfigurationMap;
	}

	public void setFileUploadConfiguration(Map<String, FileUploadType> fileUploadConfigurationMap) {
		this.fileUploadConfigurationMap = fileUploadConfigurationMap;
	}

	public int getMaximumFilenameSize() {
		return maximumFilenameSize;
	}

	public void setMaximumFilenameSize(int maximumFilenameSize) {
		this.maximumFilenameSize = maximumFilenameSize;
	}

}
