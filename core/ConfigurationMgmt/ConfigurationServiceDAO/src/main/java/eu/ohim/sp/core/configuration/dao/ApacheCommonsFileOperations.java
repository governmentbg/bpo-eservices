package eu.ohim.sp.core.configuration.dao;

import eu.ohim.sp.common.path.PathResolutionStrategy;
import eu.ohim.sp.common.path.SpConfigDirPathResolutionStrategy;
import eu.ohim.sp.common.service.Component;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Apache Commons based implementation of {@link FileOperations}
 *
 * @author Maciej Walkowiak
 */
@Component
public class ApacheCommonsFileOperations implements FileOperations {
	private static final Logger LOG = LoggerFactory.getLogger(ApacheCommonsFileOperations.class);

	private static final String UTF_8 = "UTF-8";

	private PathResolutionStrategy pathResolutionStrategy;
	
	public ApacheCommonsFileOperations() {
		this.pathResolutionStrategy = new SpConfigDirPathResolutionStrategy();
	}
	
	public ApacheCommonsFileOperations(PathResolutionStrategy pathResolutionStrategy) {
		this.pathResolutionStrategy = pathResolutionStrategy;
	}

	@Override
	public String loadFileContent(String path) throws IOException {
		Validate.notEmpty(path, "path cannot be null");

		LOG.debug("Loading file from path: " + path);

		return FileUtils.readFileToString(new File(pathResolutionStrategy.resolvePath(path)), UTF_8);
	}

	@Override
	public void saveFileContent(String path, String content) throws IOException {
		Validate.notEmpty(path, "path cannot be null");

		FileUtils.writeStringToFile(new File(pathResolutionStrategy.resolvePath(path)), content);
	}
}
