package eu.ohim.sp.core.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Priority;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;

public class DSApplicationServiceTest {

    ApplicationServiceRemote applicationService = null;

    DocumentServiceRemote documentService = null;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Before
    public void setup() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            applicationService = (ApplicationServiceRemote) context
                    .lookup("ejb:core-application-management/ApplicationService//DSApplicationService!eu.ohim.sp.core.application.ApplicationServiceRemote");
            documentService = (DocumentServiceRemote) context
                    .lookup("ejb:core-document-management/DocumentService//DocumentService!eu.ohim.sp.core.document.DocumentServiceRemote");
        } catch (NamingException e) {

        }
    }

    // TESTS WITH CALLS TO DOCUMENTSERVICE

    @Test
    public void test_persistApplication() {
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "EFEM201200000000008";

        DesignApplication application = getDesignApplication(filingNumber, true);
        applicationService.persistApplication(office, module, format, application);
    }

    @Test
    public void test_retrieveApplication() {

        String filingNumber = "EFEM201200000000008";
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_EPUB;

        DesignApplication expected = getDesignApplication(filingNumber, false);
        DesignApplication application = new DesignApplication();

        application = (DesignApplication) applicationService.retrieveApplication(office, module, format, filingNumber);

        assertEquals(expected.getApplicationLanguage(), application.getApplicationLanguage());
        assertEquals(expected.getSecondLanguage(), application.getSecondLanguage());
        assertEquals(expected.getDesigners().get(0).getName().getFirstName(), application.getDesigners().get(0)
                .getName().getFirstName());
        assertEquals(expected.getApplicants().get(0).getName().getFirstName(), application.getApplicants().get(0)
                .getName().getFirstName());
        assertEquals(expected.getDesignDetails().get(0).getPriorities().get(0).getFilingNumber(), application
                .getDesignDetails().get(0).getPriorities().get(0).getFilingNumber());
        assertEquals(expected.getDesignDetails().get(0).getExhibitionPriorities().get(0).getExhibition().getCity(),
                application.getDesignDetails().get(0).getExhibitionPriorities().get(0).getExhibition().getCity());
        assertEquals(expected.getDesignDetails().get(0).getExhibitionPriorities().get(0).getAttachedDocuments().get(0)
                .getDocument().getComment(), "exhibition comment");
    }

    // @Test
    public void test_exportApplication() {

        String filingNumber = "EFEM201200000000008";
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_EPUB;

        System.out.println("Test exportApplication START");

        byte[] data = applicationService.exportApplication(office, module, format, filingNumber);

        if (data != null) {
            System.out.println("Data not null");
        } else {
            System.out.println("Data null");
            fail();
        }

        System.out.println("Test exportApplication END");
    }

    // @Test
    public void test_persistApplication_withoutAttachments() {
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "EFEM201200000000009";

        DesignApplication application = getDesignApplication(filingNumber, false);
        applicationService.persistApplication(office, module, format, application);
    }

    // @Test
    public void test_retrieveApplication_withoutAttachments() {

        String filingNumber = "EFEM201200000000009";
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_XML;

        DesignApplication expected = getDesignApplication(filingNumber, false);
        DesignApplication application = new DesignApplication();

        application = (DesignApplication) applicationService.retrieveApplication(office, module, format, filingNumber);

        assertEquals(expected.getApplicationLanguage(), application.getApplicationLanguage());
        assertEquals(expected.getSecondLanguage(), application.getSecondLanguage());
        assertEquals(expected.getDesigners().get(0).getName().getFirstName(), application.getDesigners().get(0)
                .getName().getFirstName());
        assertEquals(expected.getApplicants().get(0).getName().getFirstName(), application.getApplicants().get(0)
                .getName().getFirstName());
        assertEquals(expected.getDesignDetails().get(0).getPriorities().get(0).getFilingNumber(), application
                .getDesignDetails().get(0).getPriorities().get(0).getFilingNumber());
        assertEquals(expected.getDesignDetails().get(0).getExhibitionPriorities().get(0).getExhibition().getCity(),
                application.getDesignDetails().get(0).getExhibitionPriorities().get(0).getExhibition().getCity());
    }

    // @Test
    public void test_exportApplicationWithoutAttachments() {

        String filingNumber = "EFEM201200000000009";
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_XML;

        System.out.println("Test exportApplication without attachments START");

        byte[] data = applicationService.exportApplication(office, module, format, filingNumber);

        if (data != null) {
            System.out.println("Data not null");
        } else {
            System.out.println("Data null");
            fail();
        }

        System.out.println("Test exportApplication without attachments END");
    }

    // @Test
    public void test_retrieveApplicationNotFound() {

        String filingNumber = "wrong filingNumber";
        String office = "EM";
        String module = "design";
        FormatXML format = FormatXML.APPLICATION_XML;
        DesignApplication application = new DesignApplication();

        application = (DesignApplication) applicationService.retrieveApplication(office, module, format, filingNumber);
        assertEquals(application, null);
    }

    // @Test (expected = EJBException.class)
    public void test_exportApplication_null() {

        applicationService.exportApplication("", "", null, "");
    }

    // @Test
    public void test_importApplication_xml() {

        FormatXML format = FormatXML.APPLICATION_XML;
        String typeApplication = "TM";
        byte[] data = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/backApplicant.xml");
            data = IOUtils.toByteArray(is);

            IPApplication application = applicationService.importApplication("EM", "design", data, typeApplication,
                    format);
            System.out.println("	>>> Test ImportApplication XML: " + application.getApplicationFilingNumber());
        } catch (FileNotFoundException e) {
            throw new SPException("File not found");
        } catch (IOException e) {
            throw new SPException("IOException while reading the resource");
        }
    }

    // @Test
    public void test_importApplication_epub() {

        FormatXML format = FormatXML.APPLICATION_EPUB;
        String typeApplication = "TM";
        byte[] data = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/compressed.epub");
            data = IOUtils.toByteArray(is);

            IPApplication application = applicationService.importApplication("EM", "design", data, typeApplication,
                    format);
            System.out.println("	>>> Test ImportApplication EPUB: " + application.getApplicationFilingNumber());
        } catch (FileNotFoundException e) {
            throw new SPException("File not found");
        } catch (IOException e) {
            throw new SPException("IOException while reading the resource");
        }
    }

    private DesignApplication getDesignApplication(String filingNumber, boolean saveDocuments) {
        Design design = getDesign(filingNumber, saveDocuments);

        eu.ohim.sp.core.domain.design.DesignApplication designApplication = new eu.ohim.sp.core.domain.design.DesignApplication();
        designApplication.setDesignDetails(new ArrayList<eu.ohim.sp.core.domain.design.Design>());
        designApplication.getDesignDetails().add(design);

        designApplication.setApplicationLanguage("en");
        designApplication.setSecondLanguage("es");
        designApplication.setApplicationDate(new Date());
        designApplication.setApplicationNumber("ApplicationNumber");
        designApplication.setApplicationFilingDate(new Date());
        designApplication.setApplicationFilingNumber(filingNumber);
        designApplication.setReceiptNumber("Receipt");
        designApplication.setReceivingOffice("EM");
        designApplication.setReceivingOfficeDate(new Date());

        Applicant applicant = new Applicant();
        applicant.setLegalForm("screw");
        applicant.setNationality("EL");
        applicant.setDomicileCountry("GR");

        applicant.setDomicileLocality("test");
        applicant.setIncorporationCountry("GR");
        applicant.setIncorporationState("Attica");

        applicant.setCorrespondenceAddresses(new ArrayList<Address>());
        applicant.getCorrespondenceAddresses().add(new Address());

        applicant.setEmails(new ArrayList<Email>());
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
        applicant.getEmails().add(new Email());
        applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        applicant.setPhones(new ArrayList<Phone>());
        applicant.getPhones().add(new Phone());
        applicant.getPhones().get(0).setNumber("2133542352");
        applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        applicant.getCorrespondenceAddresses().get(0).setCity("London");
        applicant.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        applicant.getCorrespondenceAddresses().get(0).setState("Attica");
        applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
        applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        applicant.setAddresses(new ArrayList<Address>());

        applicant.getAddresses().add(new Address());
        applicant.getAddresses().get(0).setCity("London Address");
        applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        applicant.getAddresses().get(0).setState("Attica Address");
        applicant.getAddresses().get(0).setCountry("GR");
        applicant.getAddresses().get(0).setStreetNumber("36 B");

        applicant.setUrls(new ArrayList<String>());
        applicant.getUrls().add("some url");

        applicant.setName(new PersonName());

        applicant.getName().setFirstName("Christos");
        applicant.getName().setMiddleName("Paparas");
        applicant.getName().setLastName("Karalis");

        applicant.getName().setSecondLastName("secondLastName");
        applicant.getName().setOrganizationName("organizationName");

        designApplication.setApplicants(new ArrayList<Applicant>());
        designApplication.getApplicants().add(applicant);

        Designer designer = new Designer();
        designer.setLegalForm("screw");
        designer.setNationality("EL");
        designer.setDomicileCountry("GR");

        designer.setDomicileLocality("test");
        designer.setIncorporationCountry("GR");
        designer.setIncorporationState("Attica");

        designer.setCorrespondenceAddresses(new ArrayList<Address>());
        designer.getCorrespondenceAddresses().add(new Address());

        designer.setEmails(new ArrayList<Email>());
        designer.getEmails().add(new Email());
        designer.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
        designer.getEmails().add(new Email());
        designer.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        designer.setPhones(new ArrayList<Phone>());
        designer.getPhones().add(new Phone());
        designer.getPhones().get(0).setNumber("2133542352");
        designer.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        designer.getCorrespondenceAddresses().get(0).setCity("London");
        designer.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        designer.getCorrespondenceAddresses().get(0).setState("Attica");
        designer.getCorrespondenceAddresses().get(0).setCountry("GR");
        designer.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        designer.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        designer.setAddresses(new ArrayList<Address>());

        designer.getAddresses().add(new Address());
        designer.getAddresses().get(0).setCity("London Address");
        designer.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        designer.getAddresses().get(0).setState("Attica Address");
        designer.getAddresses().get(0).setCountry("GR");
        designer.getAddresses().get(0).setStreetNumber("36 B");

        designer.setUrls(new ArrayList<String>());
        designer.getUrls().add("some url");

        designer.setName(new PersonName());

        designer.getName().setFirstName("Christos");
        designer.getName().setMiddleName("Paparas");
        designer.getName().setLastName("Karalis");

        designer.getName().setSecondLastName("secondLastName");
        designer.getName().setOrganizationName("organizationName");

        designApplication.setDesigners(new ArrayList<Designer>());
        designApplication.getDesigners().add(designer);

        return designApplication;
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

    private Design getDesign(String filingNumber, boolean saveDocuments) {
        Design design = new Design();
        design.setApplicationLanguage("en");
        design.setSecondLanguage("el");
        Date date = new Date();

        design.setExpiryDate(date);
        design.setRegistrationDate(new Date());
        design.setRegistrationNumber("R2342334567567");

        // Priorities
        Priority priority = new Priority();
        priority.setFilingDate(new Date());
        priority.setFilingNumber(filingNumber);
        priority.setFilingOffice("EM");

        FODocument document = new FODocument();
        document.setComment("priorities comment");
        document.setFileName("filename_priorities.doc");
        document.setFileFormat(FileFormat.DOC.value());
        document.setFilingNumber(filingNumber);
        document.setApplicationType("TM");
        document.setName("name");
        document.setLanguage("en");
        document.setAttachmentType("attachment");

        AttachedDocument attachedDocument = new AttachedDocument();
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        String dataString = readFileAsString("/files/Service.docx");
        document.setData(dataString.getBytes());

        if (saveDocuments) {
            Document documentPersisted = documentService.saveDocument(document);
            attachedDocument.setDocument(documentPersisted);
        } else {
            attachedDocument.setDocument(document);
        }

        priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        priority.getAttachedDocuments().add(attachedDocument);

        priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        priority.getAttachedDocuments().add(attachedDocument);

        design.setPriorities(new ArrayList<Priority>());
        design.getPriorities().add(priority);

        System.out.println(design.getPriorities().size());

        // ExhibitionPriorities
        ExhibitionPriority exhibitionPriority = new ExhibitionPriority();
        exhibitionPriority.setExhibition(new Exhibition());
        exhibitionPriority.getExhibition().setCity("city");
        exhibitionPriority.getExhibition().setCountry("FR");
        exhibitionPriority.setDate(new Date());
        exhibitionPriority.setFirstDisplayDate(new Date());
        exhibitionPriority.getExhibition().setName("name");

        document = new FODocument();
        document.setComment("exhibition comment");
        document.setFileName("filename_exhibition.jpg");
        document.setFileFormat(FileFormat.JPEG.value());
        document.setFilingNumber(filingNumber);
        document.setApplicationType("TM");
        document.setName("name");
        document.setLanguage("en");
        document.setAttachmentType("attachment");

        attachedDocument = new AttachedDocument();

        dataString = readFileAsString("/files/Service.docx");
        document.setData(dataString.getBytes());

        if (saveDocuments) {
            Document documentPersisted = documentService.saveDocument(document);
            attachedDocument.setDocument(documentPersisted);
        } else {
            attachedDocument.setDocument(document);
        }

        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        exhibitionPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        exhibitionPriority.getAttachedDocuments().add(attachedDocument);

        design.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        design.getExhibitionPriorities().add(exhibitionPriority);

        return design;
    }
}
