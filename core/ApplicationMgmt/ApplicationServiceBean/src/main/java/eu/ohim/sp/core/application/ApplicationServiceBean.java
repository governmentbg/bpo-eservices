/*
 * ApplicationServiceBean:: ApplicationServiceBean 14/11/13 15:48 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.application;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.resources.FODocumentAdditionalConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.xml.RemoveEmptyElementsUtil;
import eu.ohim.sp.core.application.dao.ApplicationDAO;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.ApplicationStatus;
import eu.ohim.sp.core.domain.application.DraftStatus;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.domain.validation.ApplicationIdentifier;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.person.ApplicantService;
import eu.ohim.sp.core.person.RepresentativeService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.core.util.SectionUtil;
import eu.ohim.sp.external.application.ApplicationClient;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.filing.domain.utils.ReferenceUtil;

public abstract class ApplicationServiceBean implements ApplicationService {

    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class);

    private static final String APPLICATION_IDENTIFIER_LIST = "Application_Identifier_List";
    private static final String APPLICATION_SET = "application_set";

    private static final String ATTACHMENTS = "ATTACHMENTS";
    private static final String FOLDER_SEPARATOR = "/";
    public static final String CONTAINER_XML = "container.xml";
    public static final String APPLICATION_XML = "application.xml";
    public static final String FILING_NUMBER_INFO = "filingNumber : ";
    public static final String DELETE_FLAG = "#DELETE.@ROOT#";

    @Override
    public eu.ohim.sp.core.domain.application.DraftApplication initDraftApplication(final String office,
            final String module, String applicationType) {
        LOGGER.debug(" >>> initDraftApplication START");
        Date dtCreated = new Date();

        eu.ohim.sp.core.domain.application.DraftApplication draft = new eu.ohim.sp.core.domain.application.DraftApplication();
        draft.setOffice(office);
        draft.setCurrentStatus(new DraftStatus());
        draft.getCurrentStatus().setMessage("");
        draft.getCurrentStatus().setModifiedDate(dtCreated);
        draft.getCurrentStatus().setStatus(ApplicationStatus.STATUS_INITIALIZED);
        draft.setTyApplication(applicationType);

        DraftApplication draftApplication = getApplicationDAO().updateStatus(draft);
        LOGGER.debug("		- Draft Application updated.");

        if (draftApplication == null) {
            LOGGER.debug("		- Draft Application updated returns null.");
            return null;
        }

        // Gets the id from applicationDAO
        Long draftApplicationId = draftApplication.getId();

        LOGGER.debug("		- DraftpApplication id: " + draftApplicationId);

        // Creates the rules information object to get the application id
        RulesInformation rulesInformation = new RulesInformation();
        ApplicationIdentifier applicationIdentifier = new ApplicationIdentifier(office,
                new SimpleDateFormat("yyyy").format(dtCreated), draftApplicationId + "", null, applicationType);

        // Creates the objects list to calculate the id
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(rulesInformation);
        objectList.add(applicationIdentifier);

        // Gets the id
        Map<String, Object> resultList;
        String provisionalId = null;
        resultList = getBusinessRulesService().calculate(module, APPLICATION_IDENTIFIER_LIST, objectList);
        if (resultList != null && resultList.containsKey("provisionalId")) {
            provisionalId = (String) resultList.get("provisionalId");
            LOGGER.debug("		- Found the id: " + provisionalId);
        } else {
            LOGGER.debug("		> Error: the key 'provisionalId' couldn't be found in the result map.");
            return null;
        }

        // Update office code and provisionalId
        draftApplication.setProvisionalId(provisionalId);
        draftApplication.setOffice(office);
        draftApplication.setDtCreated(dtCreated);

        getApplicationDAO().saveDraftApplication(draftApplication);
        LOGGER.debug("		- Draft Application saved.");

        LOGGER.debug(" <<< initDraftApplication END");

        return getApplicationDAO().getDraftApplication(draftApplication);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public eu.ohim.sp.core.domain.application.DraftApplication updateDraftApplication(
            eu.ohim.sp.core.domain.application.DraftApplication draftApplication) {
        DraftApplication draftDAO = getApplicationDAO().updateStatus(draftApplication);
        return getApplicationDAO().getDraftApplication(draftDAO);
    }

    @Override
    public eu.ohim.sp.core.domain.application.DraftApplication getDraftApplication(String office, String module,
            String fillingNumber) {

        eu.ohim.sp.core.domain.application.DraftApplication draftApplication = new eu.ohim.sp.core.domain.application.DraftApplication();
        DraftApplication draftDAO = getApplicationDAO().findDraftApplicationByProvisionalId(fillingNumber);
        if (draftDAO != null) {
            draftApplication = getApplicationDAO().getDraftApplication(draftDAO);
        }
        return draftApplication;
    }

    @Override
    public List<eu.ohim.sp.core.domain.application.DraftApplication> searchDraftApplication(String office,
            String module, Map<String, String> searchCriteria, Integer pageIndex, Integer pageSize,
            Boolean sortDirection, String sortProperty) {
        return getApplicationDAO().getDraftApplications(office, module, searchCriteria, pageIndex, pageSize,
                sortDirection, sortProperty);
    }

    public long getApplicationsCount(String office, String module, Map<String, String> searchCriteria) {
        return getApplicationDAO().getApplicationsCount(office, module, searchCriteria);
    }

    public List<String> getTypeApplicationNames() {
        return getApplicationDAO().getTypeApplicationNames();
    }

    @Override
    public Result saveApplication(String office, String module, String applicationType, String user,
            String filingNumber, boolean finalDraft) {
        return getApplicationAdapter().saveApplication(office, user != null ? user : null, filingNumber, finalDraft);
    }

    @Override
    public Result saveApplication(String office, String module, String applicationType, String user, String filingNumber) {
        return getApplicationAdapter().saveApplication(office, user != null ? user : null, filingNumber);
    }

    /**
     * Check if the application exists with the given credentials
     * 
     * @param applicationType type of application
     * @param formName form name
     * @param applicationNumber the application number
     * @param registrationNumber the registration number
     * @return
     */
    @Override
    public Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber,
            String registrationNumber) {
        return getApplicationAdapter().checkExistingApplication(applicationType, formName, applicationNumber,
                registrationNumber);
    }

    @Override
    public byte[] getApplication(String office, String module, String applicationType, String filingNumber) {
        return exportApplication(office, module, FormatXML.APPLICATION_EPUB, filingNumber);
    }

    @Override
    public Result updateDraftApplicationStatus(String filingNumber, String statusCode, String statusDescription) {

        Result result = new Result();

        try {
            eu.ohim.sp.core.domain.application.DraftApplication draftApplication = getDraftApplication(null, null,
                    filingNumber);
            if (draftApplication.getProvisionalId() != null) {
                DraftStatus currentStatus = draftApplication.getCurrentStatus();
                currentStatus.setMessage(statusDescription);
                currentStatus.setModifiedDate(new Date());

                if (statusCode.equals("received")) {
                    currentStatus.setStatus(ApplicationStatus.TRANSFER_OK);
                    if("true".equals(getSystemConfigurationService().getValue("remove.filing.after.bo.retrieval","general"))) {
                        // remove filing from jackrabbit or whatever content document used.
                        deleteFiling(filingNumber);
                    }
                } else if (statusCode.equals("error")) {
                    currentStatus.setStatus(ApplicationStatus.TRANSFER_ERROR);
                }
                draftApplication.setCurrentStatus(currentStatus);
                updateDraftApplication(draftApplication);
                result.setResult(Result.SUCCESS);
            } else {
                result.setResult(Result.NOTFOUND);
            }
        } catch (SPException e) {
            result.setResult(Result.SERVERERROR);
        }

        return result;
    }

    public IPApplication getApplicationXMLAndPersistDocuments(String office, String module, String applicationType,
            String filingNumber, byte[] data) {
        IPApplication application = null;

        if (data != null) {
            FODocument foDocument = new FODocument();
            foDocument.setFileName("compressed.epub");
            foDocument.setApplicationType("compressed");
            foDocument.setAttachmentType(FormatXML.APPLICATION_EPUB.value());
            foDocument.setFileFormat("application/epub+zip");
            foDocument.setOffice(office);
            foDocument.setModule(module);
            foDocument.setData(data);

            LOGGER.debug("Unarchiving application : " + filingNumber);
            Map<String, Document> resultMap = getDocumentService().unarchiveDocuments(foDocument,
                    "application/epub+zip", false);

            if (resultMap != null && !resultMap.isEmpty()) {
                LOGGER.debug("Retrieved " + resultMap.size() + " documents");
                if (resultMap.get(APPLICATION_XML) != null) {
                    application = getTransactionUtil().generateIPApplication(resultMap.get(APPLICATION_XML).getData(),
                            applicationType, true);
                }

                for (Map.Entry<String, Document> entry : resultMap.entrySet()) {
                    if (!entry.getKey().equals("mimetype") && !entry.getKey().equals(APPLICATION_XML)
                            && !entry.getKey().contains(CONTAINER_XML)) {
                        Document persistedDocument = uploadAttachment(
                                filingNumber,
                                applicationType,
                                entry.getKey().contains(ATTACHMENTS + FOLDER_SEPARATOR) ? entry.getKey().replace(
                                        ATTACHMENTS + FOLDER_SEPARATOR, "") : entry.getKey(), entry.getValue()
                                        .getData(), entry.getValue().getFileFormat());
                        Set<Document> documents = ReferenceUtil.getReferencesOf(application, Document.class);
                        for (Document document : documents) {
                            if (StringUtils.equals(document.getUri(), entry.getKey())) {
                                document.setDocumentId(persistedDocument.getDocumentId());
                            }
                        }

                    }
                }
            }
            if (application != null) {
                deleteNotReferencedFiles(office, module, applicationType, application);
            }
        }

        return application;

    }

    @Override
    public IPApplication loadApplication(String office, String module, String user, String applicationType,
            String filingNumber) {
        LOGGER.debug("Loading the application with filing number : " + filingNumber);
        return getApplicationXMLAndPersistDocuments(office, module, applicationType, filingNumber,
                getApplicationAdapter().loadApplication(office, user, filingNumber));
    }

    @Override
    public ApplicationNumber getApplicationNumber(String office, String module, String filingNumber,
            String applicationType) {
        LOGGER.debug("Retrieving application number for application with provisional number : " + filingNumber);

        ApplicationNumber applicationNumber = null;
        if (getSystemConfigurationService().isServiceEnabled("Retrieve_Application_Number", applicationType)) {
            applicationNumber = new ApplicationNumber();

            // Check if the application number has already been given
            DraftApplication draftApplication = getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber);
            if (draftApplication != null) {
                applicationNumber.setNumber(draftApplication.getApplicationId());
                applicationNumber.setDate(draftApplication.getDtCreated());
            }

            // Retrieve the application number and persist it
            if (applicationNumber.getNumber() == null) {
                applicationNumber = getApplicationAdapter().getApplicationNumber(module, applicationType, filingNumber);

                // Check if there is a real response
                if (applicationNumber == null || applicationNumber.getNumber() == null) {
                    throw new SPException("Failed to retrieve an application number", "error.null.applicationNumber");
                }

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("	>>> GetApplicationNumber GET WITH ADAPTER: " + applicationNumber.getNumber());
                }

                // Persist the application number on the database
                draftApplication = getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber);
                draftApplication.setApplicationId(applicationNumber.getNumber());
                getApplicationDAO().saveDraftApplication(draftApplication);

            }

            LOGGER.debug("Retrieved application number : " + applicationNumber.getNumber()
                    + " Retrieved application Date : " + applicationNumber.getDate() + " Application Date : "
                    + applicationNumber.getDate());
        }

        return applicationNumber;
    }

    @Override
    public void notifyApplicationFiling(String office, String module,
            eu.ohim.sp.core.domain.application.DraftApplication draftApplication) {
        LOGGER.debug("Calling notifyApplicationFiling: module : " + module + FILING_NUMBER_INFO
                + (draftApplication != null ? draftApplication.getProvisionalId() : "null"));
        getApplicationAdapter().notifyApplicationFiling(office, module, draftApplication);
    }

    private void deleteNotReferencedFiles(String office, String module, String typeApplication,
            IPApplication application) {

        LOGGER.debug("deleting non reference files [module=" + module + "][typeApplication=" + typeApplication + "]");

        // Gets the bytes from the application
        if (application != null) {
            Set<Document> documents = ReferenceUtil.getReferencesOf(application, Document.class);
            FODocument attachmentTemplate = new FODocument(null, null, null, office, module,
                    application.getApplicationFilingNumber(), null, typeApplication, "drafts", null);

            List<Document> savedDocuments = getDocumentService().searchDocument(
                    getSearchFilter(attachmentTemplate.getCustomProperties()), true);
            if (LOGGER.isDebugEnabled()) {
                for (Document document : documents) {
                    LOGGER.debug("Existing document on Application : "
                            + document.getDocumentId()
                            + " - "
                            + (StringUtils.isBlank(document.getFileName()) ? document.getName() : document
                                    .getFileName()));
                }
            }
            for (Document savedDocument : savedDocuments) {
                LOGGER.debug("File to be checked : " + savedDocument.getDocumentId());
                boolean toBeDeleted = true;
                for (Document document : documents) {
                    if (StringUtils.equals(document.getDocumentId(), savedDocument.getDocumentId())) {
                        toBeDeleted = false;
                    }
                }
                if (toBeDeleted) {
                    getDocumentService().deleteDocument(savedDocument);
                    LOGGER.debug("Removed document : " + savedDocument.getDocumentId());
                }
            }
        }

    }

    private void setDocumentURIs(IPApplication application) {
        Set<Document> documents = ReferenceUtil.getReferencesOf(application, Document.class);
        for (Document document : documents) {
            if(StringUtils.isBlank(document.getUri())) {
                document.setUri(ATTACHMENTS + FOLDER_SEPARATOR + document.getFileName());
            }
        }
    }

    @Override
    public Document persistApplication(String office, String module, FormatXML format, IPApplication application) {
        LOGGER.debug("	>>> PersistApplication START");

        Document savedDocument = null;

        if (application != null) {
            String typeApplication = getTypeApplicationByFilingNumber(application.getApplicationFilingNumber());

            LOGGER.debug("	>>> PersistApplication typeApplication: " + typeApplication);
            deleteNotReferencedFiles(office, getApplicationModule(application), typeApplication, application);

            if (format == FormatXML.APPLICATION_EPUB) {
                setDocumentURIs(application);
            }

            // Specify if attachments should be mapped (globalConfigurationWithoutAttachmentsMapping).
            byte[] data = getTransactionUtil().generateByte(application, typeApplication,
                    true);

            // fix for issue DEVSPHARMO-176
            // https://jira.ohimqaportal.eu/browse/DEVSPHARMO-176
            data = RemoveEmptyElementsUtil.removeEmptyElements(data);

            if (data != null) {
                FODocument document = new FODocument("", "application.xml", "text/xml", office,
                        getApplicationModule(application), application.getApplicationFilingNumber(), format.value(),
                        typeApplication, "drafts", data);
                document.setName("application.xml");

                // Search if exists the document and call update
                // The fileName may have changed in the saveDocument. We don't put this field in the search
                Document existingDocument = getDocumentByCustomProperties(document.getCustomProperties());

                LOGGER.debug("Existing document = " + document);

                if (existingDocument != null) {
                    // Update the document
                    LOGGER.debug("	>>> PersistApplication: Document exists with id: "
                            + existingDocument.getDocumentId());
                    document.setDocumentId(existingDocument.getDocumentId());
                    document.setFileName(existingDocument.getFileName());
                    savedDocument = getDocumentService().updateDocument(document);
                } else {
                    // Create new document
                    LOGGER.debug("	>>> PersistApplication: Create new document");
                    document.setFileName(application.getApplicationFilingNumber());
                    document.setName(application.getApplicationFilingNumber());
                    savedDocument = getDocumentService().saveDocument(document);
                }
                if (savedDocument == null) {
                    LOGGER.debug("	>>> PersistApplication: Document saved is null");
                }

            } else {
                LOGGER.debug("	>>> PersistApplication: Data is null");
            }
        }

        LOGGER.debug("	>>> PersistApplication END");

        return savedDocument;
    }

    @Override
    public IPApplication retrieveApplication(String office, String module, FormatXML format, String filingNumber) {
        LOGGER.debug("	>>>	RetrieveApplication START. FilingNumber: " + filingNumber);

        byte[] data = null;
        IPApplication ipApplication = null;
        String typeApplication = getTypeApplicationByFilingNumber(filingNumber);

        LOGGER.debug("	>>> RetrieveApplication typeApplication: " + typeApplication);
        FODocument foDocument = new FODocument("", "", "", office, module, filingNumber, format.value(),
                typeApplication, "drafts", null);
        Document document = getDocumentByCustomProperties(foDocument.getCustomProperties());

        if (document != null && document.getData() != null) {
            data = document.getData();
        }
        if (data != null) {
            // Gets the tradeMarkApplication from the data
            if (format == FormatXML.APPLICATION_EPUB) {
                // Unarchives the document
                Map<String, Document> archivedDocuments = getDocumentService().unarchiveDocuments(document,
                        format.value(), true);
                if (archivedDocuments != null && !archivedDocuments.isEmpty()
                        && archivedDocuments.containsKey(APPLICATION_XML)) {
                    Document applicationDocument = archivedDocuments.get(APPLICATION_XML);
                    if (applicationDocument != null && applicationDocument.getData() != null) {
                        data = applicationDocument.getData();
                        LOGGER.debug("	>>> RetrieveApplication unarchiveDocuments successfully");
                    } else {
                        LOGGER.debug("	>>> RetrieveApplication unarchiveDocuments document or data null");
                    }
                }
            }

            ipApplication = getTransactionUtil().generateIPApplication(data, typeApplication, true);
        } else {
            LOGGER.debug("	>>> RetrieveApplication: No data found");
        }
        LOGGER.debug("	>>>	RetrieveApplication END");

        return ipApplication;
    }

    @Override
    public IPApplication importApplication(String office, String module, byte[] data, String typeApplication,
            FormatXML format) {
        IPApplication application = null;

        if (data != null) {
            try {
                // Call to initDraftApplication to create new DraftApplication with new filingNumber
                eu.ohim.sp.core.domain.application.DraftApplication draftApplication = initDraftApplication(office,
                        module, typeApplication);
                if (draftApplication != null) {
                    String newFilingNumber = draftApplication.getProvisionalId();

                    if (format == FormatXML.APPLICATION_EPUB) {
                        application = getApplicationXMLAndPersistDocuments(office, module, typeApplication,
                                newFilingNumber, data);
                    } else {
                        application = getTransactionUtil().generateIPApplication(data, typeApplication, true);
                        moveDocumentsToNewDraft(office, module, format, application, typeApplication, newFilingNumber);
                        application.setApplicationFilingNumber(newFilingNumber);
                    }

                    // Persist the application
                    if (application != null) {
                        persistApplication(office, module, format, application);
                    }
                }
            } catch (Exception e) {
                LOGGER.debug("Exception in importApplication: " + e);
                return null;
            }
        }

        return application;
    }

    // JIRA
    // http://f5dmzprodf03.oami.europa.eu/jira/browse/DEVSPHARMO-383
    private void moveDocumentsToNewDraft(
        String office,
        String module,
        FormatXML format,
        IPApplication application,
        String typeApplication,
        String newFilingNumber) {

        ReferenceUtil.getReferencesOf(application, Document.class).stream()
            .filter(d -> d.getDocumentId() != null)
            .forEach(d -> {
                Document content = getDocumentService().getContent(d.getDocumentId());
                Document copied = uploadAttachment(newFilingNumber, typeApplication, content.getFileName(), content.getData(), content.getFileFormat());
                d.getCustomProperties().put("documentId", copied.getCustomProperties().get("documentId"));
            });

    }

    @Override
    public byte[] exportApplication(String office, String module, FormatXML format, String filingNumber) {

        LOGGER.debug("	>>>	ExportApplication START. FilingNumber: " + filingNumber);
        byte[] data = null;
        String typeApplication = getTypeApplicationByFilingNumber(filingNumber);
        LOGGER.debug("	>>> type Application  : " + typeApplication);

        FODocument foDocument = new FODocument("", "", "", null, null, filingNumber, format.value(), typeApplication,
                "drafts", null);

        Document document = getDocumentByCustomProperties(foDocument.getCustomProperties());
        if (document == null) {
            LOGGER.debug("	>>> ExportApplicatino document is null");
        } else {
            if (document.getData() == null) {
                LOGGER.debug("	>>> ExportApplicatino document is null");
            }
        }

        if (document != null && document.getData() != null) {
            // If the format is APPLICATION_XML we only need the data of the document
            if (format == FormatXML.APPLICATION_XML) {
                data = document.getData();
            } else {
                // If the format is APPLICATION_EPUB we need the attachments
                if (format == FormatXML.APPLICATION_EPUB) {
                    // container
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("files/container.xml");
                    FODocument conDocument = null;
                    try {
                        byte[] containerData = new byte[inputStream.available()];
                        inputStream.read(containerData);

                        conDocument = new FODocument(null, CONTAINER_XML, FormatXML.APPLICATION_CONTAINER.value(),
                                office, module, filingNumber, FormatXML.APPLICATION_CONTAINER.value(), typeApplication,
                                "", containerData);
                    } catch (IOException e) {
                        throw new SPException("Failed to generate EPUB", e);
                    }

                    Map<String, Document> archivedFiles = new HashMap<String, Document>();
                    document.setFileName(APPLICATION_XML);

                    archivedFiles.put(CONTAINER_XML, conDocument);
                    archivedFiles.put(APPLICATION_XML, document);

                    // Add the attachments
                    Map<String, String> customProperties = new HashMap<String, String>();
                    customProperties.put(FODocument.FILING_NUMBER, filingNumber);
                    customProperties.put(FODocument.APPLICATION_TYPE, typeApplication);
                    customProperties.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_OTHER.value());
                    List<Document> attachments = getAttachmentsByCustomProperties(customProperties);

                    for (Document attachment : attachments) {
                        archivedFiles.put(ATTACHMENTS + FOLDER_SEPARATOR + attachment.getFileName(), attachment);
                    }

                    Document archiveDocument = getDocumentService().archiveDocuments(archivedFiles, format.value(),
                            false);
                    if (archiveDocument != null && archiveDocument.getData() != null) {
                        data = archiveDocument.getData();
                    } else {
                        throw new SPException("	>>> ExportApplication: ArchiveDocument is null");
                    }
                }
            }
        } else {
            throw new SPException("	>>> ExportApplication: Document or Document Data is null");
        }
        return data;
    }

    /**
     * Delete a filing from document repository (to be done once retrieved by BO).
     * @param filingNumber Efiling to be deleted.
     * @return efiling document
     */
    private void deleteFiling(String filingNumber) {
        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.FILING_NUMBER, filingNumber);
        List<Document> docs = getDocumentService().searchDocument(customProperties, false);
        if(docs != null && docs.size() > 0) {
            Document d = docs.get(0);
            d.setComment(DELETE_FLAG);
            getDocumentService().deleteDocument(d);
        }
    }

    /**
     * Searches the document using a Map
     * 
     * @param customProperties
     * @return the document searched
     */
    private Document getDocumentByCustomProperties(Map<String, String> customProperties) {

        LOGGER.debug("	>>> getDocumentBySearchDocument START");
        Document document = null;

        try {
            List<Document> documents = getDocumentService().searchDocument(getSearchFilter(customProperties), false);
            String documentId = "";

            if (documents != null && !documents.isEmpty()) {
                if (documents.size() == 1) {
                    documentId = documents.get(0).getDocumentId();
                } else  if(documents.size() == 2){
                    //Then we are dealing with a migrated eservice application
                    Document docOne = documents.get(0);
                    Document docTwo = documents.get(1);
                    if(docOne.getCustomProperties().containsKey(FODocumentAdditionalConstants.CUSTOM_PROPERTIES_XML_TYPE) &&
                            docOne.getCustomProperties().get(FODocumentAdditionalConstants.CUSTOM_PROPERTIES_XML_TYPE).equals(FODocumentAdditionalConstants.XML_TYPE_MIGRATED)){
                        documentId = docOne.getDocumentId();
                    } else if(docTwo.getCustomProperties().containsKey(FODocumentAdditionalConstants.CUSTOM_PROPERTIES_XML_TYPE) &&
                            docTwo.getCustomProperties().get(FODocumentAdditionalConstants.CUSTOM_PROPERTIES_XML_TYPE).equals(FODocumentAdditionalConstants.XML_TYPE_MIGRATED)){
                        documentId = docTwo.getDocumentId();
                    } else {
                        throw new SPException("getDocumentBytes: SearchDocument returns more than one document");
                    }
                } else {
                    throw new SPException("getDocumentBytes: SearchDocument returns more than one document");
                }
                if (StringUtils.isNotBlank(documentId)) {
                    LOGGER.debug("	>>> getDocumentBySearchDocument id: " + documentId);
                    document = getDocumentService().getContent(documentId);

                    if (document == null || document.getData() == null) {
                        LOGGER.debug("	>>> getDocumentBySearchDocument documentNull");
                    }
                }
            } else {
                LOGGER.debug("	>>> getDocumentBySearchDocument NULL");
            }
        } catch (SPException e) {
            throw new SPException(e);
        }

        LOGGER.debug("	>>> getDocumentBySearchDocument END");

        return document;
    }

    private Map<String, String> getSearchFilter(Map<String, String> customProperties) {
        Map<String, String> searchFilter = new HashMap<String, String>();

        if (customProperties.containsKey(FODocument.FILING_NUMBER)) {
            searchFilter.put(FODocument.FILING_NUMBER, customProperties.get(FODocument.FILING_NUMBER));
        }
        if (customProperties.containsKey(FODocument.MODULE)) {
            searchFilter.put(FODocument.MODULE, customProperties.get(FODocument.MODULE));
        }
        if (customProperties.containsKey("fileName")) {
            searchFilter.put("fileName", customProperties.get("fileName"));
        }
        if (customProperties.containsKey(FODocument.ATTACHMENT_TYPE)) {
            searchFilter.put(FODocument.ATTACHMENT_TYPE, customProperties.get(FODocument.ATTACHMENT_TYPE));
        }

        LOGGER.debug("Search filter: " + searchFilter);

        return searchFilter;
    }

    /**
     * Gets a list of attachments using a Map
     * 
     * @param customProperties
     * @return a list of documents
     */
    private List<Document> getAttachmentsByCustomProperties(Map<String, String> customProperties) {

        LOGGER.debug("	>>> getAttachmentsByCustomProperties START");

        List<Document> attachments = new ArrayList<Document>();

        List<Document> documents = getDocumentService().searchDocument(getSearchFilter(customProperties), false);
        if (documents != null && !documents.isEmpty()) {
            for (Document document : documents) {
                if (document.getCustomProperties().containsKey(FODocument.ATTACHMENT_TYPE)
                        && !StringUtils.equals(document.getCustomProperties().get(FODocument.ATTACHMENT_TYPE),
                                FormatXML.APPLICATION_EPUB.value())
                        && !StringUtils.equals(document.getCustomProperties().get(FODocument.ATTACHMENT_TYPE),
                                FormatXML.APPLICATION_XML.value())) {
                    attachments.add(document);
                }
            }
        }
        LOGGER.debug("	>>> getAttachmentsByCustomProperties END");

        return attachments;
    }

    /**
     * Gets the typeApplication by the filingNumber
     * 
     * @param filingNumber
     * @return the typeApplication
     */
    private String getTypeApplicationByFilingNumber(String filingNumber) {

        String typeApplication = "";

        if (StringUtils.isNotBlank(filingNumber)) {
            DraftApplication draftApplication = getApplicationDAO().findDraftApplicationByProvisionalId(filingNumber);
            if (draftApplication != null && draftApplication.getTyApplication() != null
                    && StringUtils.isNotBlank(draftApplication.getTyApplication().getTypeApplication())) {
                typeApplication = draftApplication.getTyApplication().getTypeApplication();
            }
        }
        return typeApplication;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ErrorList validateApplication(String module, IPApplication application, RulesInformation rulesInformation) {
        if (application != null) {
            LOGGER.debug(">>> Validate Application: " + application.getApplicationFilingNumber());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();

        // Prepares the objects to insert in the session
        if (rulesInformation.getCustomObjects().containsKey("sectionName")) {
            Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);
            objects.add(section);
        }
        if (rulesInformation.getCustomObjects().containsKey("sections")) {
            objects.add(rulesInformation.getCustomObjects().get("sections"));
        }

        objects.add(application);
        objects.add(rulesInformation);

        // Starts the check
        ErrorList errors = getBusinessRulesService().validate(module, APPLICATION_SET, objects);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("<<< Validate Application END");
        }

        return errors;

    }

    @Override
    public IPApplication fileApplication(String office, String module, IPApplication application) {
        if (application == null) {
            throw new SPException("Unexpected data on the application. Cannot be null");
        }
        LOGGER.debug("START     fileApplication: module : " + module + FILING_NUMBER_INFO
                + application.getApplicationFilingNumber());

        String applicationModule = getApplicationModule(application);

        if (getSystemConfigurationService().isServiceEnabled("Applicant_Save", applicationModule)) {
            Set<Applicant> applicants = ReferenceUtil.getReferencesOf(application, Applicant.class);
            for (Applicant applicant : applicants) {
                Applicant savedApplicant = getApplicantService().saveApplicant(module, office, null, applicant);
                applicant.setPersonNumber(savedApplicant.getPersonNumber());
            }
        }
        if (getSystemConfigurationService().isServiceEnabled("Representative_Save", applicationModule)) {
            Set<Representative> representatives = ReferenceUtil.getReferencesOf(application, Representative.class);
            for (Representative representative : representatives) {
                Representative savedRepresentative = getRepresentativeService().saveRepresentative(module, office,
                        null, representative);
                representative.setPersonNumber(savedRepresentative.getPersonNumber());
            }
        }

        // Setting up application number if it is not available, you should get a null application number
        DraftApplication draftApplication = getApplicationDAO().findDraftApplicationByProvisionalId(
                application.getApplicationFilingNumber());
        ApplicationNumber applicationNumber = null;
        if (draftApplication != null && draftApplication.getTyApplication() != null
                && draftApplication.getTyApplication().getTypeApplication() != null) {
            applicationNumber = getApplicationNumber(office, module, application.getApplicationFilingNumber(),
                    applicationModule);
            LOGGER.debug("CALLED getApplicationNumber: module : " + module + FILING_NUMBER_INFO
                    + application.getApplicationFilingNumber());
        } else {
            throw new SPException("Unexpected data on the application. Missing application type");
        }

        if (applicationNumber != null) {
            application.setApplicationNumber(applicationNumber.getNumber());
        }

        Date applicationDate = new Date();
        application.setApplicationFilingDate(applicationDate);

        Document savedDocument = persistApplication(office, module, FormatXML.APPLICATION_EPUB, application);

        // Set the status of application submitted to the documentService
        LOGGER.debug("calling generateReceipts: module : " + module + FILING_NUMBER_INFO
                + application.getApplicationFilingNumber());

        application = fillApplicationDocuments(application,true);

        application = generateReceipts(office, module, application, PrefixPdfKind.SUBMIT_QUEUE);

        updateApplication(application, savedDocument);

        notifyApplicationFiling(office, module,
                getDraftApplication(office, module, application.getApplicationFilingNumber()));

        eu.ohim.sp.core.domain.application.DraftApplication updatedDraftApplication = getApplicationDAO()
                .getDraftApplication(draftApplication);
        updatedDraftApplication.setCurrentStatus(new DraftStatus());
        updatedDraftApplication.getCurrentStatus().setModifiedDate(applicationDate);
        updatedDraftApplication.getCurrentStatus().setStatus(getFinalStatus(application.getApplicationType()));
        updatedDraftApplication.setUsername(application.getUser());
        updatedDraftApplication.seteCorrespondence(application.getElectronicCorrespondenceRequested());
        updatedDraftApplication.setTitleApplication(application.getTitleApplication());
        updatedDraftApplication.setAppSubtype(application.getAppSubtype());
        getApplicationDAO().updateStatus(updatedDraftApplication);

        return application;
    }

    private void updateApplication(IPApplication application, Document existingDocument) {
        LOGGER.debug("	>>> UpdateApplication START");

        if (application != null && existingDocument != null) {
            String typeApplication = getTypeApplicationByFilingNumber(application.getApplicationFilingNumber());

            // Specify if attachments should be mapped (globalConfigurationWithoutAttachmentsMapping).
            byte[] data = getTransactionUtil().generateByte(application, typeApplication, true);

            if (data != null) {
                existingDocument.setData(data);

                getDocumentService().updateDocument(existingDocument);
            } else {
                LOGGER.debug("	>>> UpdateApplication: Data is null");
            }
        }

        LOGGER.debug("	>>> UpdateApplication END");
    }

    public Document uploadAttachment(String filingNumber, String applicationType, String filename, byte[] data,
            String fileFormat) {
        FODocument foDocument = new FODocument();
        foDocument.setFilingNumber(filingNumber);
        foDocument.setApplicationType(applicationType);
        foDocument.setData(data);
        foDocument.setFileFormat(fileFormat);
        if(filename != null) {
            foDocument.setFileName(filename);
            foDocument.setName(filename);
            String patternFilename = "thumbnail-.*";
            Pattern pattern = Pattern.compile(patternFilename);
            Matcher matcher = pattern.matcher(filename);
            if (!matcher.matches()) {
                foDocument.setAttachmentType(FormatXML.APPLICATION_OTHER.value());
            }
        }
        return getDocumentService().saveDocument(foDocument);
    }

    private String getApplicationModule(IPApplication application) {
        if (application instanceof EServiceApplication) {
            return "eservices";
        } else if (application instanceof DesignApplication) {
            return "dsefiling";
        } else if (application instanceof TradeMarkApplication) {
            return "tmefiling";
        } else if (application instanceof PatentApplication) {
            return "ptefiling";
        } else {
            return "unknown";
        }
    }

    @Override
    public abstract IPApplication generateReceipts(String office, String module, IPApplication application);

    @Override
    public abstract IPApplication generateReceipts(String office, String module, IPApplication application,
            PrefixPdfKind prefixPdfKind);

    protected abstract ApplicationDAO getApplicationDAO();

    protected abstract DocumentService getDocumentService();

    protected abstract RulesService getBusinessRulesService();

    protected abstract UserSearchService getUserService();

    protected abstract ConfigurationService getSystemConfigurationService();

    protected abstract ApplicationClient getApplicationAdapter();

    protected abstract TransactionUtil getTransactionUtil();

    protected abstract ApplicantService getApplicantService();

    protected abstract RepresentativeService getRepresentativeService();

    protected abstract ApplicationStatus getFinalStatus(String applicationType);

}
