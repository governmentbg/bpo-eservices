/*******************************************************************************
 * * $Id:: FileService.java 50771 2012-11-14 15:10:27Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.name.Rename;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.FileContent;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;

/**
 * Implementation of the FileServiceInterface that uses JCR and
 * calls the CORE module to notify about the addition and removal
 * of files
 * 
 * @author karalch
 * 
 */

public abstract class AbstractFileService implements FileServiceInterface {

    Logger logger = Logger.getLogger(AbstractFileService.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentFactory documentFactory;

    @Override
    public StoredFile addFile(String provisionalId, MultipartFile file, FileWrapper wrapper) throws IOException {
       return addFile(provisionalId, file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getBytes(), wrapper);
    }

    public StoredFile addFile(String provisionalId, String originalFileName, String contentType, Long fileSize, byte[] data, FileWrapper wrapper) throws IOException {
        String filename = generateUniqueName(originalFileName, wrapper);
        FODocument document = new FODocument();



        document.setName(clearBadCharactersFromFileName(filename));
        document.setDateCreated(new Date());
        document.setLanguage("en");
        document.setFileName(clearBadCharactersFromFileName(filename));
        document.setApplicationType(getModule());
        document.setFilingNumber(provisionalId);
        document.setApplicationStatus("draft");
        // document.getCustomProperties().put("applicationType", application);
        // document.getCustomProperties().put("filingNumber", provisionalId);
        // document.getCustomProperties().put("status", "draft");
        document.setFileFormat(contentType);
        document.setAttachmentType(FormatXML.APPLICATION_OTHER.value());

        if (wrapper != null) {
            document.setComment(wrapper.getDescription());
            if (StringUtils.hasText(wrapper.getDocType())) {
                document.getCustomProperties().put("documentType", wrapper.getDocType());
            } else {
                document.getCustomProperties().put("documentType", AttachmentDocumentType.OTHER_DOCUMENT.toString());
            }
        }

            document.setData(data);
            document.setFileSize(fileSize);
            document.setFileDate(new Date());


        Document documentReceived = documentService.saveDocument(document);
        StoredFile storedFile = documentFactory.convertFrom(documentReceived);

        if (wrapper != null && BooleanUtils.isTrue(wrapper.getThumbnail())) {
            try {
                storedFile.setThumbnail(generateThumbnail(provisionalId, filename, contentType,
                        data));
            } catch (SPException e) {
                // Possible exception if thumbnail creation fails
                // should log and set stored file's thumbnail to null
                logger.error(e.getMessage());
                logger.error(e.getErrorCode());
                storedFile.setThumbnail(null);
            }
        }
        return storedFile;
    }
    @Override
    public FileContent getFileStream(String documentId) {
        // Check if there is a file locally, otherwise call the service

        Document doc = documentService.getContent(documentId);
        // JcrUtil.getFileStream(engine, provisionalId, fileName);

        FileContent wrapper = null;
        if (doc != null) {
            StoredFile storedFile = documentFactory.convertFrom(doc);
            wrapper = new FileContent();
            wrapper.setContent(doc.getData());
            wrapper.setContentType(storedFile.getContentType());
            wrapper.setName(storedFile.getOriginalFilename());
        }

        return wrapper;
    }

    @Override
    public void removeFile(StoredFile file) {
        FODocument document = new FODocument();
        document.setDocumentId(file.getDocumentId());
        documentService.deleteDocument(document);

        if (file.getThumbnail() != null
                && org.apache.commons.lang.StringUtils.isNotEmpty(file.getThumbnail().getDocumentId())) {
            document.setDocumentId(file.getThumbnail().getDocumentId());
            documentService.deleteDocument(document);
        }
    }

    @Override
    public StoredFile addFile(String provisionalId, String filename, String fileFormat, byte[] data,
            boolean generateThumbnail) throws IOException {
        FODocument document = new FODocument();

        document.setFilingNumber(provisionalId);
        document.setName(filename);
        document.setFileName(filename);
        document.setApplicationType(getModule());
        document.setFileFormat(fileFormat);
        document.setDateCreated(new Date());
        document.setData(data);
        document.setFileSize((long)data.length);

        Document documentReceived = documentService.saveDocument(document);
        StoredFile storedFile = documentFactory.convertFrom(documentReceived);
        if (generateThumbnail) {
            try {
                storedFile.setThumbnail(generateThumbnail(provisionalId, filename, fileFormat, data));
            } catch (SPException e) {
                // Possible exception if thumbnail creation fails
                // should log and set stored file's thumbnail to null
                logger.error(e.getMessage());
                logger.error(e.getErrorCode());
                storedFile.setThumbnail(null);
            }
        }
        return storedFile;
    }

    public abstract String getModule();

    /**
     * TODO Make configurable...
     * 
     * @param originalFilename
     * @param data
     * @return
     * @throws IOException
     */
    private StoredFile generateThumbnail(String provisionalId, String originalFilename, String fileFormat, byte[] data)
            throws IOException {
        FODocument document = new FODocument();
        String thumbnailName = Rename.PREFIX_HYPHEN_THUMBNAIL.apply(originalFilename, null);

        document.setFilingNumber(provisionalId);
        document.setName(thumbnailName);
        document.setFileName(thumbnailName);
        document.setApplicationType(getModule());
        document.setFileFormat(fileFormat);
        document.setDateCreated(new Date());

        Builder<?> b = Thumbnails.of(new ByteArrayInputStream(data));
        b.size(640, 480);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.toOutputStream(baos);
            document.setData(baos.toByteArray());
            document.setFileSize(null);
            Document documentReceivedThumbnail = documentService.saveDocument(document);
            StoredFile storedFileThumbnail = documentFactory.convertFrom(documentReceivedThumbnail);
            return storedFileThumbnail;
        } catch (IOException e) {
            throw new SPException("Error while creating thumbnail", e, "error.thumbnail.creation");
        }
    }

    private String generateUniqueName(String filename, FileWrapper wrapper) {
        int count = 0;
        boolean exists = false;
        String generatedName = filename;
        do {
            exists = false;
            if (count > 0) {
                String[] filenameArray = filename.split("\\.", 2);
                generatedName = filenameArray[0] + "(" + count + ")";
                if (filenameArray.length == 2) {
                    generatedName += "." + filenameArray[1];
                }
            }
            for (StoredFile storedFile : wrapper.getStoredFiles()) {
                if (storedFile.getOriginalFilename().equalsIgnoreCase(generatedName)) {
                    exists = true;
                }
            }
            count++;
        } while (exists);
        return generatedName;
    }

    public String clearBadCharactersFromFileName(String fileName){
        if(fileName == null){
            return null;
        }
        fileName = fileName.replaceAll("[^\\p{L}\\p{N}\\.\\-_+=\\(\\)]", "");
        fileName = fileName.replaceAll("\\s", "_");
        return fileName;
    }

}
