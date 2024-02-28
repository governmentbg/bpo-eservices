package eu.ohim.sp.core.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Image;
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
import eu.ohim.sp.core.configuration.domain.colour.xsd.Colour;

@SuppressWarnings("deprecation")
public class EServiceApplicationServiceTest {

    ApplicationServiceRemote applicationService = null;

    DocumentServiceRemote documentService = null;

    public static final String MODULE = "eservices";
    public static final String OFFICE = "EM";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Before
    public void setup() {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            final Context context = new InitialContext(jndiProperties);
            applicationService = (ApplicationServiceRemote) context
                    .lookup("ejb:core-application-management/ApplicationService//EServiceApplicationService!eu.ohim.sp.core.application.ApplicationServiceRemote");
            documentService = (DocumentServiceRemote) context
                    .lookup("ejb:core-document-management/DocumentService//DocumentService!eu.ohim.sp.core.document.DocumentServiceRemote");
        } catch (NamingException e) {

        }
    }

    // TESTS WITH CALLS TO DOCUMENTSERVICE

    @Test
    public void test_persistApplication() {
        String office = "EM";
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        String filingNumber = "EFEM201200000000002";

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber(filingNumber);
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        application.getTradeMarks().add(fillCoreTradeMark(filingNumber));

        applicationService.persistApplication(office, module, format, application);
    }

    @Test
    public void test_retrieveApplication() {

        String filingNumber = "EFEM201200000000002";
        String office = "EM";
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_EPUB;
        TMeServiceApplication application = new TMeServiceApplication();

        application = (TMeServiceApplication) applicationService.retrieveApplication(office, module, format,
                filingNumber);
        assertEquals(application.getTradeMarks().get(0).getApplicationLanguage(), "en");
        assertEquals(application.getTradeMarks().get(0).getSecondLanguage(), "el");
        assertEquals(application.getTradeMarks().get(0).getApplicationReference(), "test 123");
        assertEquals(application.getTradeMarks().get(0).getApplicationNumber(), "test");
        assertEquals(application.getTradeMarks().get(0).getReceivingOffice(), "EM");
        assertEquals(application.getTradeMarks().get(0).getRegistrationOffice(), "EM");
        assertEquals(application.getTradeMarks().get(0).getRegistrationNumber(), "R2342334567567");
        assertEquals(application.getTradeMarks().get(0).getComment(), "comment FSP");
        assertEquals(application.getTradeMarks().get(0).getMarkKind(), MarkFeature.WORD);
    }

    // @Test
    public void test_exportApplication() {

        String filingNumber = "EFEM201200000000002";
        String office = "EM";
        String module = "eservices";
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
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_XML;
        String filingNumber = "EFEM201200000000007";

        TMeServiceApplication application = new TMeServiceApplication();
        application.setApplicationFilingDate(new Date());
        application.setApplicationFilingNumber(filingNumber);
        application.setTradeMarks(new ArrayList<LimitedTradeMark>());
        application.getTradeMarks().add(fillCoreTradeMark(filingNumber));
        applicationService.persistApplication(office, module, format, application);
    }

    // @Test
    public void test_retrieveApplication_withoutAttachments() {

        String filingNumber = "EFEM201200000000007";
        String office = "EM";
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_XML;
        TMeServiceApplication application = new TMeServiceApplication();

        application = (TMeServiceApplication) applicationService.retrieveApplication(office, module, format,
                filingNumber);
        assertEquals(application.getTradeMarks().get(0).getApplicationLanguage(), "en");
        assertEquals(application.getTradeMarks().get(0).getSecondLanguage(), "el");
        assertEquals(application.getTradeMarks().get(0).getApplicationReference(), "test 123");
        assertEquals(application.getTradeMarks().get(0).getApplicationNumber(), "test");
        assertEquals(application.getTradeMarks().get(0).getReceivingOffice(), "EM");
        assertEquals(application.getTradeMarks().get(0).getRegistrationOffice(), "EM");
        assertEquals(application.getTradeMarks().get(0).getRegistrationNumber(), "R2342334567567");
        assertEquals(application.getTradeMarks().get(0).getComment(), "comment FSP");
        assertEquals(application.getTradeMarks().get(0).getMarkKind(), MarkFeature.WORD);

    }

    // @Test
    public void test_exportApplicationWithoutAttachments() {

        String filingNumber = "EFEM201200000000007";
        String office = "EM";
        String module = "eservices";
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
        String module = "eservices";
        FormatXML format = FormatXML.APPLICATION_XML;
        TradeMarkApplication tradeMarkApplication = new TradeMarkApplication();

        tradeMarkApplication = (TradeMarkApplication) applicationService.retrieveApplication(office, module, format,
                filingNumber);
        assertEquals(tradeMarkApplication, null);
    }

    // @Test
    public void test_importApplication_xml() {

        FormatXML format = FormatXML.APPLICATION_XML;
        String typeApplication = "TM";
        byte[] data = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/backApplicant.xml");
            data = IOUtils.toByteArray(is);

            IPApplication application = applicationService.importApplication(OFFICE, MODULE, data, typeApplication,
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

            IPApplication application = applicationService.importApplication(OFFICE, MODULE, data, typeApplication,
                    format);
            System.out.println("	>>> Test ImportApplication EPUB: " + application.getApplicationFilingNumber());
        } catch (FileNotFoundException e) {
            throw new SPException("File not found");
        } catch (IOException e) {
            throw new SPException("IOException while reading the resource");
        }
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

    private LimitedTradeMark fillCoreTradeMark(String filingNumber) {
        LimitedTradeMark tradeMark = new LimitedTradeMark();
        tradeMark.setApplicationLanguage("en");
        tradeMark.setSecondLanguage("el");
        tradeMark.setApplicationReference("test 123");
        Date date = new Date();

        tradeMark.setExpirationDate(date);
        tradeMark.setFilingDate(date);
        tradeMark.setFilingNumber(filingNumber);
        tradeMark.setApplicationNumber("test");
        tradeMark.setReceivingOffice("EM");
        tradeMark.setRegistrationOffice("EM");
        tradeMark.setRegistrationDate(new Date());
        tradeMark.setRegistrationNumber("R2342334567567");
        tradeMark.setTerminationDate(new Date());
        tradeMark.setComment("comment FSP");
        tradeMark.setPriorityClaimLaterIndicator(true);
        tradeMark.setExhibitionPriorityClaimLaterIndicator(true);
        tradeMark.setApplicationDate(new Date());
        tradeMark.setMarkKind(MarkFeature.WORD);

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

        Document documentPersisted = documentService.saveDocument(document);
        attachedDocument.setDocument(documentPersisted);

        priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        priority.getAttachedDocuments().add(attachedDocument);
        priority.setPartialIndicator(true);

        priority.setPartialGoodsServices(new ArrayList<ClassDescription>());
        priority.getPartialGoodsServices().add(new ClassDescription());
        priority.getPartialGoodsServices().get(0).setLanguage("en");
        priority.getPartialGoodsServices().get(0).setClassNumber("42");
        priority.getPartialGoodsServices().get(0).setClassificationTerms(new ArrayList<ClassificationTerm>());
        priority.getPartialGoodsServices().get(0).getClassificationTerms().add(new ClassificationTerm());
        priority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).setTermText("whatever");

//		tradeMark.setPriorities(new ArrayList<Priority>());
//		tradeMark.getPriorities().add(priority);

        // Seniorities
        Seniority seniority = new Seniority();
        seniority.setFilingNumber(filingNumber);
        seniority.setOffice("EM");
        seniority.setFilingDate(new Date());
        seniority.setRegistrationDate(new Date());
        seniority.setRegistrationNumber("R21231");
        seniority.setKind(eu.ohim.sp.core.domain.claim.SeniorityKind.INTERNATIONAL_TRADE_MARK);
        seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

        document = new FODocument();
        document.setComment("seniorities comment");
        document.setFileName("filename_seniorities.pdf");
        document.setFileFormat(FileFormat.PDF.value());
        document.setFilingNumber(filingNumber);
        document.setApplicationType("TM");
        document.setName("name");
        document.setLanguage("en");
        document.setAttachmentType("attachment");

        attachedDocument = new AttachedDocument();
        attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE.value());

        dataString = readFileAsString("/files/Service.docx");
        document.setData(dataString.getBytes());

        documentPersisted = documentService.saveDocument(document);
        attachedDocument.setDocument(documentPersisted);

        seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        seniority.getAttachedDocuments().add(attachedDocument);

        seniority.setPartialIndicator(true);
        seniority.setPartialGoodsServices(new ArrayList<ClassDescription>());
        seniority.getPartialGoodsServices().add(new ClassDescription());
        seniority.getPartialGoodsServices().get(0).setLanguage("en");
        seniority.getPartialGoodsServices().get(0).setClassNumber("42");
        seniority.getPartialGoodsServices().get(0).setClassificationTerms(new ArrayList<ClassificationTerm>());
        seniority.getPartialGoodsServices().get(0).getClassificationTerms().add(new ClassificationTerm());
        seniority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).setTermText("whatever");

        tradeMark.setSeniorities(new ArrayList<Seniority>());
        tradeMark.getSeniorities().add(seniority);

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

        documentPersisted = documentService.saveDocument(document);
        attachedDocument.setDocument(documentPersisted);

        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        exhibitionPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        exhibitionPriority.getAttachedDocuments().add(attachedDocument);

        tradeMark.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        tradeMark.getExhibitionPriorities().add(exhibitionPriority);

        // TransformationPriorities
        TransformationPriority transformationPriority = new TransformationPriority();
        transformationPriority.setCancellationDate(new Date());
        transformationPriority.setPriorityDate(new Date());
        transformationPriority.setRegistrationDate(new Date());
        transformationPriority.setRegistrationNumber("R21312313");

        tradeMark.setTransformationPriorities(new ArrayList<TransformationPriority>());
        tradeMark.getTransformationPriorities().add(transformationPriority);

        // WordSpecifications
        WordSpecification wordSpecification = new WordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.setWordSpecifications(new ArrayList<WordSpecification>());
        tradeMark.getWordSpecifications().add(wordSpecification);

        wordSpecification = new WordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever 2");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.getWordSpecifications().add(wordSpecification);

        // Applicants
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

        tradeMark.setApplicants(new ArrayList<Applicant>());
        tradeMark.getApplicants().add(applicant);

        // Signatures
        Signatory signatory = new Signatory();
        signatory.setDate(new Date());
        signatory.setName("Christos");
        signatory.setPlace("here");
        signatory.setId(1231);
        signatory.setCapacity(PersonRoleKind.APPLICANT);

        tradeMark.setSignatures(new ArrayList<Signatory>());
        tradeMark.getSignatures().add(signatory);

        // ImageSpecification
        ImageSpecification imageSpecification = new ImageSpecification();
        Image representation = new Image();
        representation.setFileName("filename");
        representation.setFileFormat(FileFormat.JPEG.value());
        representation.setUri("test URI");
        representation.setDocumentId("image id");
        imageSpecification.setRepresentation(representation);

        Colour colour = new Colour();
        colour.setFormat("HEX");
        colour.setValue("D213");
        imageSpecification.setColours(new ArrayList<Colour>());
        imageSpecification.getColours().add(colour);

        colour = new Colour();
        colour.setFormat("RGB");
        colour.setValue("Black");
        imageSpecification.getColours().add(colour);

        colour = new Colour();
        colour.setValue("Black");
        colour.setFormat("PANTONE");
        imageSpecification.getColours().add(colour);

        Text colourText = new Text();
        colourText.setLanguage("en");
        colourText.setValue("colour 1");
        imageSpecification.setColourClaimedText(new ArrayList<Text>());
        imageSpecification.getColourClaimedText().add(colourText);

        tradeMark.setImageSpecifications(new ArrayList<ImageSpecification>());
        tradeMark.getImageSpecifications().add(imageSpecification);

        // SoundSpecification
        SoundSpecification soundSpecification = new SoundSpecification();
        Sound commonSound = new Sound();
        commonSound.setFileFormat(SoundFileFormat.MP3.value());
        commonSound.setFileName("filename");
        commonSound.setUri("uri");
        commonSound.setDocumentId("sound id");
        soundSpecification.setDocument(commonSound);

        tradeMark.setSoundRepresentations(new ArrayList<SoundSpecification>());
        tradeMark.getSoundRepresentations().add(soundSpecification);

        // ClassDescription
        ClassDescription classDescription = new ClassDescription();
        classDescription.setFullClassCoverageIndicator(Boolean.FALSE);
        classDescription.setLanguage("en");
        classDescription.setClassNumber("32");
        classDescription.setClassificationTerms(new ArrayList<ClassificationTerm>());
        classDescription.setGoodsServicesDescription("descriptions");

        ClassificationTerm classificationTerm = new ClassificationTerm();
        classificationTerm.setTermText("Test");
        classDescription.getClassificationTerms().add(classificationTerm);

        tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());
        tradeMark.getClassDescriptions().add(classDescription);

        // MarkDescriptions
        tradeMark.setMarkDescriptions(new ArrayList<MarkDescription>());
        MarkDescription markDescription = new MarkDescription();
        markDescription.setLanguage("en");
        markDescription.setValue("test papapra");
        tradeMark.getMarkDescriptions().add(markDescription);

        markDescription = new MarkDescription();
        markDescription.setLanguage("ES");
        markDescription.setValue("test papapra");

        tradeMark.getMarkDescriptions().add(markDescription);

        // MarkDisclaimers
        tradeMark.setMarkDisclaimers(new ArrayList<MarkDisclaimer>());
        MarkDisclaimer markDisclaimer = new MarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("en");
        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        markDisclaimer = new MarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("es");

        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        // Representatives
        Representative representative = new Representative();
        representative.setLegalForm("screw");
        representative.setNationality("EL");
        representative.setDomicileCountry("GR");

        representative.setDomicileLocality("test");
        representative.setIncorporationCountry("GR");
        representative.setIncorporationState("Attica");

        representative.setCorrespondenceAddresses(new ArrayList<Address>());
        representative.getCorrespondenceAddresses().add(new Address());

        representative.setEmails(new ArrayList<Email>());
        representative.getEmails().add(new Email());
        representative.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
        representative.getEmails().add(new Email());
        representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        representative.setPhones(new ArrayList<Phone>());
        representative.getPhones().add(new Phone());
        representative.getPhones().get(0).setNumber("2133542352");
        representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

        representative.getCorrespondenceAddresses().get(0).setCity("London");
        representative.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        representative.getCorrespondenceAddresses().get(0).setState("Attica");
        representative.getCorrespondenceAddresses().get(0).setCountry("GR");
        representative.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        representative.setAddresses(new ArrayList<Address>());

        representative.getAddresses().add(new Address());
        representative.getAddresses().get(0).setCity("London Address");
        representative.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        representative.getAddresses().get(0).setState("Attica Address");
        representative.getAddresses().get(0).setCountry("GR");
        representative.getAddresses().get(0).setStreetNumber("36 B");

        representative.setUrls(new ArrayList<String>());
        representative.getUrls().add("some url");
        representative.getUrls().add("some url 2");

        representative.setName(new PersonName());

        representative.getName().setFirstName("Christos");
        representative.getName().setMiddleName("Paparas");
        representative.getName().setLastName("Karalis");

        tradeMark.setRepresentatives(new ArrayList<Representative>());
        tradeMark.getRepresentatives().add(representative);

        tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());

        document = new FODocument();
        document.setComment("tradeMarkDocuments comment");
        document.setFileName("filename_trademarkdocuments.doc");
        document.setFileFormat(FileFormat.DOC.value());
        document.setFilingNumber(filingNumber);
        document.setApplicationType("TM");
        document.setName("name");
        document.setLanguage("en");
        document.setAttachmentType("attachment");

        attachedDocument = new AttachedDocument();

        dataString = readFileAsString("/files/Service.docx");
        document.setData(dataString.getBytes());

        documentPersisted = documentService.saveDocument(document);
        attachedDocument.setDocument(documentPersisted);

        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

        tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
        tradeMark.getTradeMarkDocuments().add(attachedDocument);

        tradeMark.setCorrespondenceAddresses(new ArrayList<Address>());
        tradeMark.getCorrespondenceAddresses().add(new Address());
        tradeMark.getCorrespondenceAddresses().get(0).setCity("London");
        tradeMark.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
        tradeMark.getCorrespondenceAddresses().get(0).setState("Attica");
        tradeMark.getCorrespondenceAddresses().get(0).setCountry("GR");
        tradeMark.getCorrespondenceAddresses().get(0).setStreetNumber("36");
        tradeMark.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");

        return tradeMark;
    }

}
