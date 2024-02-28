/*
 *  PaymentService:: PaymentServiceTest 03/09/13 16:46 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.epayment;

import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.*;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequest;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentRequestParam;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentResult;
import eu.ohim.sp.core.domain.payment.wrapper.PaymentStatus;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.person.PersonRoleKind;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.*;
import eu.ohim.sp.core.domain.trademark.*;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.external.epayment.EPaymentClient;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

public class EPaymentServiceBeanTest {

	@InjectMocks
	EPaymentServiceBean paymentService;
	
	@Mock
	private ApplicationService applicationService;	
	
	@Mock
	private ApplicationService tradeMarkApplicationService;
	
	@Mock
	private EPaymentClient paymentServiceAdapter;
	
	private TradeMarkApplication tradeMarkApplication;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test_requestPayment() {

		tradeMarkApplication = fillCoreTradeMarkApplication();
		
		String office = "";
		String module = "";
		String filingNumber = "some filingNumber";
		
		Map<PaymentRequestParam, String> parameters = null;
		User user = new User();
		user.setFullName("fullName");
		user.setId(1);
		user.setRole(new ArrayList<String>());
		user.getRole().add("some role");
		user.setStatus("some status");
		user.setUserName("userName");
	
		Map<String, String> redirectionParams = new HashMap<String, String>();
		
		PaymentRequest paymentRequestExpected = new PaymentRequest();
		paymentRequestExpected.setId(1);
		paymentRequestExpected.setRedirectionParams(redirectionParams);
		paymentRequestExpected.setRedirectionURL("some url");
		paymentRequestExpected.setPaymentRequestId("some paymentId");
		
		DraftApplication draftApplication = new DraftApplication();
		draftApplication.setPaymentId("some paymentId");
		
		
		//applicationService.getDraftApplication(office, module, provisionalId);
		when(tradeMarkApplicationService.getDraftApplication(office, module, filingNumber)).thenReturn(draftApplication);
		when(paymentServiceAdapter.requestPayment(tradeMarkApplication, draftApplication.getPaymentId(), user, parameters)).thenReturn(paymentRequestExpected);
		
		PaymentRequest paymentRequest = paymentService.requestPayment(office, module, tradeMarkApplication, user, parameters);
		
		System.out.println("PaymentRequest Id: " + paymentRequest.getPaymentRequestId());
		System.out.println("PaymentRequest Id: " + paymentRequest.getRedirectionURL());
		if (paymentRequest.getRedirectionParams() != null && !paymentRequest.getRedirectionParams().isEmpty()) {
			Iterator iterator = paymentRequest.getRedirectionParams().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				System.out.println("Payment Params Key: " + entry.getKey() + ", Value: " + entry.getValue());
			}
		}
		
		assertEquals(paymentRequest.getPaymentRequestId(), "some paymentId");
	}
	
	@Test
	public void test_requestPayment_null() {
		
		PaymentRequest paymentRequest = paymentService.requestPayment("", "", null, null, null);
		assertEquals(paymentRequest, null);
	}
	
	@Test
	public void test_notifyPaymentResult_statusOK() {
		
		tradeMarkApplication = fillCoreTradeMarkApplication();
		
		String office = "";
		String module = "";
		String applicationFilingNumber = "some filingNumber";
		tradeMarkApplication.setApplicationFilingNumber(applicationFilingNumber);
		Map<String, String> paymentPlatformData = new HashMap<String, String>();
		
		PaymentResult paymentResultExpected = new PaymentResult();
		paymentResultExpected.setPaymentStatus(PaymentStatus.PAYMENT_OK);
		when(paymentServiceAdapter.notifyPaymentResult(anyMap())).thenReturn(paymentResultExpected);
		
		DraftApplication draftApplication = new DraftApplication();
		when(tradeMarkApplicationService.getDraftApplication(office, module, tradeMarkApplication.getApplicationFilingNumber())).thenReturn(draftApplication);
		
		PaymentResult paymentResult = paymentService.notifyPaymentResult(office, module, tradeMarkApplication, paymentPlatformData);
		
		assertEquals(paymentResult.getPaymentStatus().toString(), paymentResultExpected.getPaymentStatus().toString());
	}
	
	@Test
	public void test_notifyPaymentResult_null() {
		tradeMarkApplication = fillCoreTradeMarkApplication();
		
		DraftApplication draftApplication = new DraftApplication();
		when(tradeMarkApplicationService.getDraftApplication("", "", tradeMarkApplication.getApplicationFilingNumber())).thenReturn(draftApplication);
		
		PaymentResult paymentResult = paymentService.notifyPaymentResult("", "", tradeMarkApplication, null);
		assertEquals(paymentResult, null);
	}

	@Test
	public void requestPayment_applicationNotAlreadyUnderPayment_normalBehaviour() {
		//ARRANGE
//		when(applicationService.checkStatus(anyString())).thenReturn(ApplicationStatus.STATUS_CC_PAYMENT_UNKNOWN);
//		
//		PaymentRequest paymentRequestFromAdapter = new PaymentRequest();		
		//when(paymentServiceAdapter.requestPayment("", "", new ArrayList<PaymentFee>())).thenReturn(paymentRequestFromAdapter);
		
		//ACT
		//PaymentRequest paymentRequest = paymentService.requestPayment("", "", new ArrayList<PaymentFee>());
		
		//ASSERT
		//assertEquals(paymentRequestFromAdapter, paymentRequest);
		
	}
	
	@Test
	public void requestPayment_applicationNotAlreadyUnderPayment_applicationStatusUpdated()
	{
		//ARRANGE
//		when(applicationService.checkStatus(anyString())).thenReturn(ApplicationStatus.STATUS_CC_PAYMENT_UNKNOWN);
		
		//ACT
		//paymentService.requestPayment("something", "app id", new ArrayList<PaymentFee>());
		
		//ASSERT
//		verify(applicationService, times(1)).updateStatus("app id", ApplicationStatus.STATUS_CC_PAYMENT_PENDING, null);
	}
	
	//@Test(expected=RuntimeException.class)
	public void requestPayment_applicationUnderPayment_throwsRuntimeException() {
		//ARRANGE
//		when(applicationService.checkStatus(anyString())).thenReturn(ApplicationStatus.STATUS_CC_PAYMENT_PENDING);
		
		//ACT
		//paymentService.requestPayment("", "", new ArrayList<PaymentFee>());
		
		//ASSERT
	}
	
	private TradeMarkApplication fillCoreTradeMarkApplication() {
		
		TradeMarkApplication application = new TradeMarkApplication();
		
		TradeMark tradeMark = new TradeMark();
		tradeMark.setApplicationLanguage("es");
		tradeMark.setSecondLanguage("ja");
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
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		priority.getAttachedDocuments().add(attachedDocument);
		priority.setPartialIndicator(true);

		priority.setPartialGoodsServices(new ArrayList<ClassDescription>());
		priority.getPartialGoodsServices().add(new ClassDescription());
		priority.getPartialGoodsServices().get(0).setLanguage("en");
		priority.getPartialGoodsServices().get(0).setClassNumber("42");
		priority.getPartialGoodsServices().get(0)
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
		priority.getPartialGoodsServices().get(0).getClassificationTerms()
				.add(new ClassificationTerm());
		priority.getPartialGoodsServices().get(0).getClassificationTerms()
				.get(0).setTermText("whatever");

		tradeMark.setPriorities(new ArrayList<Priority>());
		tradeMark.getPriorities().add(priority);

		// Seniorities
		Seniority seniority = new Seniority();
		seniority.setFilingNumber("application number");
		seniority.setOffice("EM");
		seniority.setFilingDate(new Date());
		seniority.setRegistrationDate(new Date());
		seniority.setRegistrationNumber("R21231");
		seniority
				.setKind(SeniorityKind.INTERNATIONAL_TRADE_MARK);
		seniority
				.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);

		document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		attachedDocument = new AttachedDocument();
		attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE
				.value());
		attachedDocument.setDocument(document);
		seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		seniority.getAttachedDocuments().add(attachedDocument);

		seniority.setPartialIndicator(true);
		seniority.setPartialGoodsServices(new ArrayList<ClassDescription>());
		seniority.getPartialGoodsServices().add(new ClassDescription());
		seniority.getPartialGoodsServices().get(0).setLanguage("en");
		seniority.getPartialGoodsServices().get(0).setClassNumber("42");
		seniority.getPartialGoodsServices().get(0)
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
		seniority.getPartialGoodsServices().get(0).getClassificationTerms()
				.add(new ClassificationTerm());
		seniority.getPartialGoodsServices().get(0).getClassificationTerms()
				.get(0).setTermText("whatever");

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
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		exhibitionPriority
				.setAttachedDocuments(new ArrayList<AttachedDocument>());
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
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		transformationPriority
				.setAttachedDocuments(new ArrayList<AttachedDocument>());
		transformationPriority.getAttachedDocuments().add(attachedDocument);

		tradeMark
				.setTransformationPriorities(new ArrayList<TransformationPriority>());
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
		applicant.getEmails().get(0)
				.setEmailAddress("mouta.sta.skatra.mou@oami.gr");
		applicant.getEmails().add(new Email());
		applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		applicant.setPhones(new ArrayList<Phone>());
		applicant.getPhones().add(new Phone());
		applicant.getPhones().get(0).setNumber("2133542352");
		applicant.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		applicant.getCorrespondenceAddresses().get(0).setCity("London");
		applicant.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		applicant.getCorrespondenceAddresses().get(0).setState("Attica");
		applicant.getCorrespondenceAddresses().get(0).setCountry("GR");
		applicant.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		applicant.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Paparis");

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
		classDescription
				.setClassificationTerms(new ArrayList<ClassificationTerm>());
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
		representative.getEmails().get(0)
				.setEmailAddress("mouta.sta.skatra.mou@oami.gr");
		representative.getEmails().add(new Email());
		representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

		representative.setPhones(new ArrayList<Phone>());
		representative.getPhones().add(new Phone());
		representative.getPhones().get(0).setNumber("2133542352");
		representative.getPhones().get(0).setPhoneKind(PhoneKind.FIXED);

		representative.getCorrespondenceAddresses().get(0).setCity("London");
		representative.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).setState("Attica");
		representative.getCorrespondenceAddresses().get(0).setCountry("GR");
		representative.getCorrespondenceAddresses().get(0)
				.setStreetNumber("36");
		representative.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Paparis");

		representative.setAddresses(new ArrayList<Address>());

		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("London Address");
		representative.getAddresses().get(0)
				.setStreet("Agias Lauras 36 Address");
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
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");
		
		attachedDocument = new AttachedDocument();
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.PRIORITY_CERTIFICATE
				.value());

		tradeMark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
		tradeMark.getTradeMarkDocuments().add(attachedDocument);
/*
		tradeMark.setCorrespondenceAddresses(new ArrayList<Address>());
		tradeMark.getCorrespondenceAddresses().add(new Address());
		tradeMark.getCorrespondenceAddresses().get(0).setCity("London");
		tradeMark.getCorrespondenceAddresses().get(0)
				.setStreet("Agias Lauras 36");
		tradeMark.getCorrespondenceAddresses().get(0).setState("Attica");
		tradeMark.getCorrespondenceAddresses().get(0).setCountry("GR");
		tradeMark.getCorrespondenceAddresses().get(0)
				.setStreetNumber("36");
		tradeMark.getCorrespondenceAddresses().get(0)
				.setPostalName("Mr Christos Paparis");
*/
    	application.setApplicationFilingDate(new Date());
    	application.setApplicationFilingNumber("some filingNumber");
    	application.setTradeMark(tradeMark);
		
		return application;
	}

}
