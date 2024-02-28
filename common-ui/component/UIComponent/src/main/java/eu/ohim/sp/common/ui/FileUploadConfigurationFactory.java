/*******************************************************************************
 * * $Id:: FileUploadConfigurationFactory.java 50771 2012-11-14 15:10:27Z karalc#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui;

import eu.ohim.sp.common.ui.fileupload.FileUploadUtils;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.FileUploadType;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import static eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface.FILE_UPLOAD_CONFIGURATION;
import static eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT;

public class FileUploadConfigurationFactory {

	private ConfigurationServiceDelegatorInterface configurationService;

    private String component;

	public ConfigurationServiceDelegatorInterface getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationServiceDelegatorInterface configurationService) {
		this.configurationService = configurationService;
	}

	public Map<String, FileUploadType> getFileuploadConfiguration() {
        if(component!=null) {
            return getFileuploadConfiguration(component);
        } else {
            return getFileuploadConfiguration(GENERAL_COMPONENT);
        }
    }

    public Map<String, FileUploadType> getFileuploadConfiguration(String sp_component) {
        FileUploadConfiguration fileUploadConfiguration = configurationService
                .getObjectFromComponent(FILE_UPLOAD_CONFIGURATION, sp_component, FileUploadConfiguration.class);

        Map<String, FileUploadType> fileUploadConfigurationMap = new HashMap<String, FileUploadType>();

        if (fileUploadConfiguration != null) {
			for (FileUploadType fileUploadType : fileUploadConfiguration.getFileUpload()) {
				fileUploadConfigurationMap.put(fileUploadType.getFieldName(), fileUploadType);
			}
        }

        return fileUploadConfigurationMap;
    }
	
	public String getAvailableFormats(String fieldName) {
        if(component!=null) {
            return getAvailableFormats(fieldName, component);
        } else {
            return getAvailableFormats(fieldName, GENERAL_COMPONENT);
        }
	}

    public String getAvailableFormats(String fieldName, String sp_component) {
        FileUploadConfiguration fileUploadConfiguration = configurationService
                .getObjectFromComponent(FILE_UPLOAD_CONFIGURATION, sp_component, FileUploadConfiguration.class);

        for (FileUploadType fileUploadType : fileUploadConfiguration.getFileUpload()) {
            if (StringUtils.equals(fileUploadType.getFieldName(), fieldName)) {
                return FileUploadUtils.getAvailableFormats(fileUploadType);
            }

        }
        return null;
    }

	public boolean acceptAll(String fieldName) {
        if(component!=null) {
            return acceptAll(fieldName, component);
        } else {
            return acceptAll(fieldName, GENERAL_COMPONENT);
        }
	}

    public boolean acceptAll(String fieldName, String sp_component) {
        FileUploadConfiguration fileUploadConfiguration = configurationService
                .getObjectFromComponent(FILE_UPLOAD_CONFIGURATION, sp_component, FileUploadConfiguration.class);

		// TODO: this for loop looks suspicious - what does this method should actually do?
        for (FileUploadType fileUploadType : fileUploadConfiguration.getFileUpload()) {
            return fileUploadType.isAvailableAllContentTypes();
        }
        return false;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
