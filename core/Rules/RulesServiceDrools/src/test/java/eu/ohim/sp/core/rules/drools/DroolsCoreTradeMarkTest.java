/*
 * BusinessRulesDrools:: DroolsCoreTradeMarkTest 03/09/13 16:46 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.rules.drools;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
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
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.resources.FileFormat;
import eu.ohim.sp.core.domain.resources.Image;
import eu.ohim.sp.core.domain.resources.Sound;
import eu.ohim.sp.core.domain.resources.SoundFileFormat;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.MarkDescription;
import eu.ohim.sp.core.domain.trademark.MarkDisclaimer;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.SoundSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import eu.ohim.sp.core.domain.validation.ErrorList;

public class DroolsCoreTradeMarkTest extends DroolsCore {

    @Mock
    ConfigurationService configurationService;

    private Sections sections;

    private TradeMark tradeMark;

    private final String _PACKAGE_ = "eu.ohim.sp.core.rules";

    private String readFileAsString(String path) {
        String info = null;
        try {
            info = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");
        } catch (IOException e) {
            throw new SPException(e.getMessage());
        }
        return info;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        sections = convertXML2Object(readFileAsString("/xml/oneform-conf.xml"), Sections.class);

        initConfiguration();
        fillBaseTradeMark();
    }

    private void initConfiguration() {

        Collection<String> drlFiles = new ArrayList<String>();
        drlFiles.add("trademark.drl");

        List<String> rulesMapList = new ArrayList<String>();
        rulesMapList.add("EF_rules");

        List<String> tmRulesList = new ArrayList<String>();
        tmRulesList.add("EF_trademark_list");

        List<String> tmRuleListDrls = new ArrayList<String>();
        tmRuleListDrls = new ArrayList<String>();
        tmRuleListDrls.add("trademark.drl");

        when(configurationService.getXml(eq("trademark.drl"), eq("eu.ohim.sp.core.rules"))).thenReturn(
                readFileAsString("/trademark.drl"));

        when(configurationService.getValueList(eq("rules_map"), eq(_PACKAGE_))).thenReturn(rulesMapList);
        when(configurationService.getValueList(eq("EF_rules"), eq(_PACKAGE_))).thenReturn(tmRulesList);

        when(configurationService.getValueList(eq("EF_trademark_list"), eq(_PACKAGE_))).thenReturn(tmRuleListDrls);

        initialize();
    }

    private void fillBaseTradeMark() {
        tradeMark = new TradeMark();

        tradeMark.setApplicationLanguage("en");
        tradeMark.setSecondLanguage("el");
        tradeMark.setApplicationReference("test 123");
        Date date = new Date();

        tradeMark.setExpirationDate(date);
        tradeMark.setFilingDate(date);
        tradeMark.setFilingNumber("test");
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
        priority.setFilingNumber("fefef");
        priority.setFilingOffice("EM");

        Document document = new Document();
        document.setComment("comment");
        document.setFileName("filename");
        document.setName("name");
        document.setLanguage("en");

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
        seniority.setOffice("EM");
        seniority.setFilingDate(new Date());
        seniority.setRegistrationDate(new Date());
        seniority.setRegistrationNumber("R21231");
        seniority.setKind(SeniorityKind.INTERNATIONAL_TRADE_MARK);
        seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

        document = new Document();
        document.setComment("comment");
        document.setFileName("filename");
        document.setName("name");
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
        document.setComment("comment");
        document.setFileName("filename");
        document.setName("name");
        document.setLanguage("en");

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
        document.setFileName("filename");
        document.setName("name");
        document.setLanguage("en");

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

        // TradeMarkDocuments
        tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
        attachedDocument = new AttachedDocument();
        attachedDocument.setDocument(document);
        attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE.value());
        tradeMark.getTradeMarkDocuments().add(attachedDocument);
    }

//	@Test
    public void testTradeMark_Ok() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 0);
    }

//	@Test
    public void testTradeMark_BR54Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setMarkKind(MarkFeature.UNDEFINED);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR57Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setMarkDisclaimers(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_423aEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "soundmark");

        tradeMark.setTradeMarkDocuments(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR63aEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setWordSpecifications(new ArrayList<WordSpecification>());

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR65_BR75_BR92_BR104a_Image_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "soundmark");

        tradeMark.setImageSpecifications(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR100aEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "soundmark");

        tradeMark.setSoundRepresentations(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR69_BR97_BR102_BR107aEmpty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setMarkDescriptions(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_ClassDescriptions_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_Priorities_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setPriorities(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_ExhibitionPriorities_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_TransformationPriorities_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setTransformationPriorities(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_Seniorities_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setSeniorities(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_Applicants_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setApplicants(new ArrayList<Applicant>());

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_Representatives_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setRepresentatives(new ArrayList<Representative>());

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

//	@Test
    public void testTradeMark_BR_Signatures_Empty() {
        Collection<Object> objects = new ArrayList<Object>();

        Section section = getSectionById(sections, "wordmark");

        tradeMark.setSignatures(null);

        objects.add(tradeMark);
        objects.add(section);

        ErrorList errors = fireRulesValidate("EF_rules", "EF_trademark_list", objects);
        Assert.assertEquals(errors.getErrorList().size(), 1);
    }

    @SuppressWarnings("unchecked")
    private <T> T convertXML2Object(String xml, Class<T> clazz) {
        try {

            StringReader sr = new StringReader(xml);
            StreamSource sc = new StreamSource(sr);
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (T) unmarshaller.unmarshal(sc);
        } catch (JAXBException jaxbException) {
            throw new SPException("Failed to convert xml", jaxbException, "error.generic");
        }
    }

    private Section getSectionById(Sections sections, String id) {

        if (sections != null) {
            for (Section sectionIterator : sections.getSection()) {
                if (sectionIterator.getId().value().equals(id)) {
                    return sectionIterator;
                }
                if (sectionIterator.getSubsection() != null) {
                    Section section = getSubSectionById(sectionIterator, id);
                    if (section != null) {
                        return section;
                    }
                }
            }
        }

        return null;
    }

    private Section getSubSectionById(Section section, String id) {
        for (Section sectionIterator : section.getSubsection()) {
            if (sectionIterator.getId().value().equals(id)) {
                return sectionIterator;
            }
            if (sectionIterator.getSubsection() != null) {
                Section sectionAux = getSubSectionById(sectionIterator, id);
                if (sectionAux != null) {
                    return sectionAux;
                }
            }
        }
        return null;
    }

}
