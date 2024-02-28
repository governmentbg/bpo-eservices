/*
 * CoreDomain:: FODocument 04/11/13 12:30 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.domain.resources;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.SPException;

public class FODocument extends Document {

    private static final long serialVersionUID = 1L;

    public static final String FILING_NUMBER = "filingNumber";
    public static final String ATTACHMENT_TYPE = "attachmentType";
    public static final String OFFICE = "office";
    public static final String MODULE = "module";
    public static final String APPLICATION_STATUS = "applicationStatus";
    public static final String APPLICATION_TYPE = "applicationType";

    public FODocument() {
    };

    public FODocument(String path, String fileName, String fileFormat, String office, String module,
            String filingNumber, String attachmentType, String applicationType, String applicationStatus, byte[] data) {
        if (StringUtils.isNotBlank(path)) {
            String dataString = readFileAsString(path);
            setData(dataString.getBytes());
        } else {
            if (data != null) {
                setData(data);
            }
        }
        if (getCustomProperties() == null) {
            setCustomProperties(new HashMap<String, String>());
        }
        if (StringUtils.isNotBlank(fileName)) {
            setFileName(fileName);
        }
        if (StringUtils.isNotBlank(fileFormat)) {
            setFileFormat(fileFormat);
        }
        if (StringUtils.isNotBlank(office)) {
            getCustomProperties().put(OFFICE, office);
        }
        if (StringUtils.isNotBlank(module)) {
            getCustomProperties().put(MODULE, module);
        }
        if (StringUtils.isNotBlank(filingNumber)) {
            getCustomProperties().put(FILING_NUMBER, filingNumber);
        }
        if (StringUtils.isNotBlank(attachmentType)) {
            getCustomProperties().put(ATTACHMENT_TYPE, attachmentType);
        }
        if (StringUtils.isNotBlank(applicationType)) {
            getCustomProperties().put(APPLICATION_TYPE, applicationType);
        }
        if (StringUtils.isNotBlank(applicationStatus)) {
            getCustomProperties().put(APPLICATION_STATUS, applicationStatus);
        }
    }

    public String getFilingNumber() {
        return getCustomProperties().get(FILING_NUMBER);
    }

    public void setFilingNumber(String filingNumber) {
        getCustomProperties().put(FILING_NUMBER, filingNumber);
    }

    public String getAttachmentType() {
        return getCustomProperties().get(ATTACHMENT_TYPE);
    }

    public void setAttachmentType(String attachmentType) {
        getCustomProperties().put(ATTACHMENT_TYPE, attachmentType);
    }

    public String getOffice() {
        return getCustomProperties().get(OFFICE);
    }

    public void setOffice(String office) {
        getCustomProperties().put(OFFICE, office);
    }

    @Override
    public String getModule() {
        return getCustomProperties().get(MODULE);
    }

    @Override
    public void setModule(String module) {
        getCustomProperties().put(MODULE, module);
    }

    public String getApplicationStatus() {
        return getCustomProperties().get(APPLICATION_STATUS);
    }

    public void setApplicationStatus(String applicationStatus) {
        getCustomProperties().put(APPLICATION_STATUS, applicationStatus);
    }

    public String getApplicationType() {
        return getCustomProperties().get(APPLICATION_TYPE);
    }

    public void setApplicationType(String applicationType) {
        getCustomProperties().put(APPLICATION_TYPE, applicationType);
    }

    private String readFileAsString(String path) {
        String info = null;
        try {
            info = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");
        } catch (IOException e) {
            throw new SPException(e.getMessage());
        }
        return info;
    }

}
