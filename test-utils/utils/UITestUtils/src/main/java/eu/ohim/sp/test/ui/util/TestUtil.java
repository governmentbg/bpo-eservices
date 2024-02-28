package eu.ohim.sp.test.ui.util;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.flow.CommonSpFlowBean;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.payment.FeesForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.*;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.*;
import eu.ohim.sp.core.domain.trademark.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by marcoantonioalberoalbero on 1/3/17.
 */
public class TestUtil {

    private static final Logger logger = Logger.getLogger(TestUtil.class);

    protected TestUtil() {
    }

    public static class CoreTMGenerator {

        protected CoreTMGenerator() {
        }

        public static final String OHIM_OFFICE = "EM";

        public static TradeMarkApplication fillTMApplication() {
            TradeMarkApplication application = new TradeMarkApplication();
            application.setApplicationFilingDate(new Date());
            application.setApplicationFilingNumber("some filing number");
            application.setTradeMark(TestUtil.CoreTMGenerator.fillCoreTradeMark(TradeMark.class));
            ArrayList<AttachedDocument> documents = new ArrayList<>();
            AttachedDocument d1 = new AttachedDocument();
            d1.setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
            d1.setId(1);
            AttachedDocument d2 = new AttachedDocument();
            d2.setDocumentKind(DocumentKindDefault.APPLICATION_RECEIPT);
            d2.setId(2);
            Document doc = new Document();
            doc.setId(10);
            doc.setDocumentId("test");
            Document doc2 = new Document();
            doc2.setId(11);
            doc2.setDocumentId("");
            d1.setDocument(doc);
            d2.setDocument(doc2);
            documents.add(d1);
            documents.add(d2);
            application.setDocuments(documents);
            return application;
        }

        public static <T extends TradeMark> T fillCoreTradeMark(Class<T> clazz) {

            T tradeMark;
            try {
                tradeMark = clazz.newInstance();
            } catch (InstantiationException e) {
                TestUtil.logger.info(e.getMessage());
                throw new SPException(e);
            } catch (IllegalAccessException e) {
                TestUtil.logger.info(e.getMessage());
                throw new SPException(e);
            }

            tradeMark.setApplicationLanguage("en");
            tradeMark.setSecondLanguage("el");
            tradeMark.setApplicationReference("test 123");
            Date date = new Date();

            tradeMark.setExpirationDate(date);
            tradeMark.setFilingDate(date);
            tradeMark.setFilingNumber("test");
            tradeMark.setApplicationNumber("test");
            tradeMark.setReceivingOffice(OHIM_OFFICE);
            tradeMark.setRegistrationOffice(OHIM_OFFICE);
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
            priority.setFilingNumber("111324234");
            priority.setFilingOffice(OHIM_OFFICE);

            Document document = new Document();
            document.setComment("comment1");
            document.setFileName("filename priorities");
            document.setFileFormat(FileFormat.PDF.value());
            document.setName("name");
            document.setLanguage("en");
            document.setDocumentId("0000000000001");
            document.setUri("priority uri");

            AttachedDocument attachedDocument = new AttachedDocument();
            attachedDocument.setDocument(document);
            attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

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

            tradeMark.setPriorities(new ArrayList<Priority>());
            tradeMark.getPriorities().add(priority);

            // Seniorities
            Seniority seniority = new Seniority();
            seniority.setFilingNumber("application number");
            seniority.setOffice(OHIM_OFFICE);
            seniority.setFilingDate(new Date());
            seniority.setRegistrationDate(new Date());
            seniority.setRegistrationNumber("R21231");
            seniority.setKind(SeniorityKind.INTERNATIONAL_TRADE_MARK);
            seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

            document = new Document();
            document.setComment("comment2");
            document.setFileName("filename seniority");
            document.setFileFormat(FileFormat.PDF.value());
            document.setName("name");
            document.setDocumentId("0000000000002");
            document.setLanguage("en");

            attachedDocument = new AttachedDocument();
            attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE.value());
            attachedDocument.setDocument(document);
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

            document = new Document();
            document.setComment("comment3");
            document.setFileName("filename exhibition");
            document.setFileFormat(FileFormat.PDF.value());
            document.setName("name");
            document.setLanguage("en");
            document.setDocumentId("0000000000003");
            document.setUri("exhibition uri");

            attachedDocument = new AttachedDocument();
            attachedDocument.setDocument(document);
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

            document = new Document();
            document.setComment("comment");
            document.setFileName("filename transformation");
            document.setFileFormat(FileFormat.PDF.value());
            document.setName("name");
            document.setLanguage("en");
            document.setDocumentId("0000000000006");
            document.setUri("transformation uri");

            attachedDocument = new AttachedDocument();
            attachedDocument.setDocument(document);
            attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

            transformationPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
            transformationPriority.getAttachedDocuments().add(attachedDocument);

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
            applicant.setIncorporationState("Attica1");

            applicant.setCorrespondenceAddresses(new ArrayList<Address>());
            applicant.getCorrespondenceAddresses().add(new Address());

            applicant.setEmails(new ArrayList<Email>());
            applicant.getEmails().add(new Email());
            applicant.getEmails().get(0).setEmailAddress("test@oami.gr");
            applicant.getEmails().add(new Email());
            applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

            applicant.setPhones(new ArrayList<Phone>());
            applicant.getPhones().add(new Phone());
            applicant.getPhones().get(0).setNumber("2133542352");
            applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

            applicant.getCorrespondenceAddresses().get(0).setCity("London");
            applicant.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
            applicant.getCorrespondenceAddresses().get(0).setState("Attica2");
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

            applicant.getName().setFirstName("Christos1");
            applicant.getName().setMiddleName("Paparas");
            applicant.getName().setLastName("Karalis");

            applicant.getName().setSecondLastName("secondLastName");
            applicant.getName().setOrganizationName("organizationName");

            tradeMark.setApplicants(new ArrayList<Applicant>());
            tradeMark.getApplicants().add(applicant);

            // Signatures
            Signatory signatory = new Signatory();
            signatory.setDate(new Date());
            signatory.setName("Christos2");
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
            representation.setUri("ATTACHMENTS/test.jpg");
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
            representative.getEmails().get(0).setEmailAddress("test@oami.gr");
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

            document = new Document();
            document.setComment("comment");
            document.setFileName("filename tradeMarkdocuments");
            document.setFileFormat(FileFormat.PDF.value());
            document.setName("name");
            document.setLanguage("en");
            document.setDocumentId("0000000000007");
            document.setUri("tradeMarkDocuments uri");

            attachedDocument = new AttachedDocument();
            attachedDocument.setDocument(document);
            attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());

            tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
            tradeMark.getTradeMarkDocuments().add(attachedDocument);

            return tradeMark;
        }
    }

    public static class FlowBeanGenerator {
        protected FlowBeanGenerator() {
        }

        public static <T extends CommonSpFlowBean>T fillFlowBean(T fb) {
            fb.setIdreserventity("10");
            fb.setId("1");
            fb.setCheckBdBlocking(false);
            fb.setFeesForm(new FeesForm());
            fb.setFirstLang("en");
            fb.setImported(false);
            fb.setInitializationErrorCode("1003");
            fb.setPaymentForm(new PaymentForm());
            fb.setSecLang("es");
            fb.setValidateImported(true);
            fb.setWarningValidated(true);
            return fb;
        }
    }
}
