package eu.ohim.sp.external.domain.claim;

import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.SeniorityKind;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.resources.*;
import eu.ohim.sp.core.domain.trademark.*;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.MarkDescription;
import eu.ohim.sp.core.domain.trademark.MarkDisclaimer;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TradeMarkConverterTest {

	@Test
	public void test() {
		eu.ohim.sp.core.domain.trademark.TradeMark tradeMark = new eu.ohim.sp.core.domain.trademark.TradeMark();
		tradeMark.setApplicationLanguage("en");
		tradeMark.setSecondLanguage("el");
        tradeMark.setCorrespondenceLanguage("en");
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
		
		Priority priority = new Priority();
		priority.setFilingNumber("fefef");
		priority.setFilingOffice("EM");
		priority.setFilingDate(new Date());
		
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

		tradeMark.setPriorities(new ArrayList<Priority>());
		tradeMark.getPriorities().add(priority);
		
		Seniority seniority = new Seniority();
		seniority.setFilingNumber("1231241");
		seniority.setOffice("EM");
		seniority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		seniority.setFilingDate(new Date());
		seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);
		seniority.setKind(SeniorityKind.INTERNATIONAL_TRADE_MARK);
		seniority.setRegistrationNumber("R23324234");
		seniority.setRegistrationDate(new Date());
		
		document = new Document();
		document.setComment("seniority Comment");
		document.setFileName("seniority filename");
		document.setName("seniority name");
		document.setFileFormat(FileFormat.JPEG.value());
		
		attachedDocument.setDocument(document);
		attachedDocument.setDocumentKind(DocumentKind.SENIORITY_CERTIFICATE.value());
		seniority.getAttachedDocuments().add(attachedDocument);
		seniority.setCerticateAttachedIndicator(true);

		tradeMark.setSeniorities(new ArrayList<Seniority>());
		tradeMark.getSeniorities().add(seniority);
		
		ExhibitionPriority exhibitionPriority = new ExhibitionPriority();
		exhibitionPriority.setExhibition(new Exhibition());
		exhibitionPriority.getExhibition().setCity("London");
		exhibitionPriority.setDate(new Date());
		exhibitionPriority.getExhibition().setName("LONDON - Expo");
		exhibitionPriority.getExhibition().setCountry("GR");
		exhibitionPriority.setFirstDisplayDate(new Date());
//		
//		document = new Document();
//		document.setComment("exhibition Comment");
//		document.setFilename("exhibition filename");
//		document.setName("exhibition name");
//		document.setKind(DocumentKind.EXHIBITION_PRIORITY_CERTIFICATE);
//		document.setFileFormat(FileFormat.JPEG);
//		document.setAttached(true);
//		
//		exhibitionPriority.setDocuments(new ArrayList<Document>());
//		exhibitionPriority.getDocuments().add(document);
//		
		tradeMark.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
		tradeMark.getExhibitionPriorities().add(exhibitionPriority);

		eu.ohim.sp.core.domain.claim.TransformationPriority transformationPriority = new eu.ohim.sp.core.domain.claim.TransformationPriority();
		transformationPriority.setCancellationDate(new Date());
		transformationPriority.setPriorityDate(new Date());
		transformationPriority.setRegistrationDate(new Date());
		transformationPriority.setRegistrationNumber("test 2313");
		
		tradeMark.setTransformationPriorities(new ArrayList<eu.ohim.sp.core.domain.claim.TransformationPriority>());
		tradeMark.getTransformationPriorities().add(transformationPriority);
		
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
		
		Applicant applicant = new Applicant();
		applicant.setLegalForm("screw");
		applicant.setNationality("EL");
		applicant.setDomicileCountry("GR");
		
		applicant.setDomicileLocality("test");
		applicant.setIncorporationCountry("GR");
		applicant.setIncorporationState("Attica");
		
		applicant.setCorrespondenceAddresses(new ArrayList<Address>());
		applicant.getCorrespondenceAddresses().add(new Address());
		
		applicant.setAddresses(new ArrayList<Address>());
		
		applicant.getAddresses().add(new Address());
		applicant.getAddresses().get(0).setCity("Limnoupoli");
		applicant.getAddresses().get(0).setStreet("Attica 36 Address");
		applicant.getAddresses().get(0).setState("Attica State");
		applicant.getAddresses().get(0).setCountry("FR");
		applicant.getAddresses().get(0).setStreetNumber("36 B");
		
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
		
		applicant.setName(new PersonName());

		applicant.getName().setFirstName("Christos");
		applicant.getName().setMiddleName("Paparas");
		applicant.getName().setLastName("Karalis");
		
		tradeMark.setSignatures(new ArrayList<Signatory>());
		Signatory signatory = new Signatory();
		signatory.setDate(new Date());
		signatory.setName("Christos");
		signatory.setPlace("here");
		
		
		tradeMark.setImageSpecifications(new ArrayList<ImageSpecification>());
		
		ImageSpecification imageSpecification = new ImageSpecification();
		tradeMark.getImageSpecifications().add(imageSpecification);
		
		Document image = new Document();
		imageSpecification.setRepresentation(image);
		imageSpecification.setColourClaimedIndicator(true);
		imageSpecification.getRepresentation().setFileName("filename");
		imageSpecification.getRepresentation().setFileFormat(FileFormat.JPEG.value());
		imageSpecification.getRepresentation().setUri("test URI");

		Colour colour = new Colour();
		colour.setFormat("HEX");
		colour.setValue("D213");
		
		imageSpecification.setColours(new ArrayList<Colour>());
		imageSpecification.getColours().add(colour);
		
		colour = new Colour();
		colour.setValue("whateber");
		colour.setValue("Black");
		imageSpecification.getColours().add(colour);

		colour = new Colour();
		colour.setValue("Black");
		imageSpecification.getColours().add(colour);

		imageSpecification = new ImageSpecification();
		tradeMark.getImageSpecifications().add(imageSpecification);
		
//		image = new Image();
//		imageSpecification.setRepresentation(image);
//		imageSpecification.getRepresentation().setMarkImageColourIndicator(true);
//		imageSpecification.getRepresentation().setMarkImageFilename("filename");
//		imageSpecification.getRepresentation().setMarkImageFileFormat(FileFormat.JPEG);
//		imageSpecification.getRepresentation().setMarkImageURI("test URI");
//
//		colour = new Colour();
//		colour.setFormat("HEX");
//		colour.setValue("D2131");
//		
//		imageSpecification.setColours(new ArrayList<Colour>());
//		imageSpecification.getColours().add(colour);
		
//		colour = new Colour();
//		colour.setValue("whateber");
//		colour.setValue("White");
//		imageSpecification.getColours().add(colour);
//
//		colour = new Colour();
//		colour.setValue("White");
//		imageSpecification.getColours().add(colour);

		
//		SoundSpecification soundSpecification = new SoundSpecification();
//		soundSpecification.setFileFormat(SoundFileFormat.MP3);
//		soundSpecification.setFilename("filename");
//		soundSpecification.setURI("uri");
//		
//		tradeMark.setSoundRepresentations(new ArrayList<SoundSpecification>());
//		tradeMark.getSoundRepresentations().add(soundSpecification);
		
		ClassDescription classDescription = new ClassDescription();
		classDescription.setFullClassCoverageIndicator(Boolean.FALSE);
		classDescription.setLanguage("en");
		classDescription.setClassNumber("32");
		classDescription.setClassificationTerms(new ArrayList<ClassificationTerm>());
		
		ClassificationTerm classificationTerm = new ClassificationTerm();
		classificationTerm.setTermText("Test");
		classDescription.getClassificationTerms().add(classificationTerm);
		
		tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());
		tradeMark.getClassDescriptions().add(classDescription);
		
		tradeMark.setMarkDescriptions(new ArrayList<MarkDescription>());
		
		MarkDescription markDescription = new MarkDescription();
		markDescription.setLanguage("en");
		markDescription.setValue("test markDescription 1");
		tradeMark.getMarkDescriptions().add(markDescription);
		
		markDescription = new MarkDescription();
		markDescription.setLanguage("ES");
		markDescription.setValue("test markDescription 2");
		tradeMark.getMarkDescriptions().add(markDescription);
		
		tradeMark.setMarkDisclaimers(new ArrayList<MarkDisclaimer>());
		
		MarkDisclaimer markDisclaimer = new MarkDisclaimer();
		markDisclaimer.setValue("value");
		markDisclaimer.setLanguage("en");
		tradeMark.getMarkDisclaimers().add(markDisclaimer);

		markDisclaimer = new MarkDisclaimer();
		markDisclaimer.setValue("value");
		markDisclaimer.setLanguage("ES");
		tradeMark.getMarkDisclaimers().add(markDisclaimer);
		
		tradeMark.setApplicants(new ArrayList<Applicant>());
		tradeMark.getApplicants().add(applicant);
		
		eu.ohim.sp.core.domain.person.Representative representative = new eu.ohim.sp.core.domain.person.Representative();
		representative.setLegalForm("test");
		representative.setNationality("gf");
		representative.setDomicileCountry("ES");
		
		representative.setName(new PersonName());
		
		representative.getName().setFirstName("Christos");
		representative.getName().setMiddleName("Paparas");
		representative.getName().setLastName("Karalis");
		
		representative.setLegalForm("screw");
		representative.setNationality("EL");
		representative.setDomicileCountry("GR");
		
		representative.setDomicileLocality("test");
		representative.setIncorporationCountry("GR");
		representative.setIncorporationState("Attica");
		
		representative.setCorrespondenceAddresses(new ArrayList<Address>());
		representative.getCorrespondenceAddresses().add(new Address());
		
		representative.setAddresses(new ArrayList<Address>());
		
		representative.getAddresses().add(new Address());
		representative.getAddresses().get(0).setCity("Limnoupoli");
		representative.getAddresses().get(0).setStreet("Attica 36 Address");
		representative.getAddresses().get(0).setState("Attica State");
		representative.getAddresses().get(0).setCountry("FR");
		representative.getAddresses().get(0).setStreetNumber("36 B");
		
		representative.getCorrespondenceAddresses().get(0).setCity("London");
		representative.getCorrespondenceAddresses().get(0).setStreet("Agias Lauras 36");
		representative.getCorrespondenceAddresses().get(0).setState("Attica");
		representative.getCorrespondenceAddresses().get(0).setCountry("GR");
		representative.getCorrespondenceAddresses().get(0).setStreetNumber("36");
		representative.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Paparis");		
		
		representative.setEmails(new ArrayList<Email>());
		representative.getEmails().add(new Email());
		representative.getEmails().get(0).setEmailAddress("mouta.sta.skatra.mou@oami.gr");
		representative.getEmails().add(new Email());
		representative.getEmails().get(1).setEmailAddress("kapelo@oami.gr");
		
		tradeMark.setRepresentatives(new ArrayList<eu.ohim.sp.core.domain.person.Representative>());
		tradeMark.getRepresentatives().add(representative);

		DozerBeanMapper mapper = new DozerBeanMapper();
		eu.ohim.sp.external.domain.trademark.TradeMark tradeMark2 =
			    mapper.map(tradeMark, eu.ohim.sp.external.domain.trademark.TradeMark.class);


		assertEquals(tradeMark.getApplicationNumber(), tradeMark2.getApplicationNumber());
		assertEquals(tradeMark.getTerminationDate(), tradeMark2.getTerminationDate());
		assertEquals(tradeMark.getExhibitionPriorityClaimLaterIndicator(), tradeMark2.getExhibitionPriorityClaimLaterIndicator());
		assertEquals(tradeMark.getPriorityClaimLaterIndicator(), tradeMark2.getPriorityClaimLaterIndicator());

		assertEquals(tradeMark.getMarkDescriptions().get(0).getLanguage(), tradeMark2.getMarkDescriptions().get(0).getLanguage());
		assertEquals(tradeMark.getMarkDescriptions().get(0).getValue(), tradeMark2.getMarkDescriptions().get(0).getValue());
		assertEquals(tradeMark.getMarkDescriptions().get(1).getLanguage(), tradeMark2.getMarkDescriptions().get(1).getLanguage());
		assertEquals(tradeMark.getMarkDescriptions().get(1).getValue(), tradeMark2.getMarkDescriptions().get(1).getValue());

		assertEquals(tradeMark.getMarkDisclaimers().get(0).getLanguage(), tradeMark2.getMarkDisclaimers().get(0).getLanguage());
		assertEquals(tradeMark.getMarkDisclaimers().get(0).getValue(), tradeMark2.getMarkDisclaimers().get(0).getValue());
		assertEquals(tradeMark.getMarkDisclaimers().get(1).getLanguage(), tradeMark2.getMarkDisclaimers().get(1).getLanguage());
		assertEquals(tradeMark.getMarkDisclaimers().get(1).getValue(), tradeMark2.getMarkDisclaimers().get(1).getValue());

		assertEquals(tradeMark.getPriorities().size(), tradeMark2.getPriorities().size());
		assertEquals(tradeMark.getPriorities().get(0).getFilingNumber(), tradeMark2.getPriorities().get(0).getFilingNumber());
		assertEquals(tradeMark.getSeniorities().size(), tradeMark2.getSeniorities().size());
		assertEquals(tradeMark.getSeniorities().get(0).getFilingNumber(), tradeMark2.getSeniorities().get(0).getFilingNumber());
		assertEquals(tradeMark.getExhibitionPriorities().size(), tradeMark2.getExhibitionPriorities().size());
		assertEquals(tradeMark.getExhibitionPriorities().get(0).getExhibition().getName(), tradeMark2.getExhibitionPriorities().get(0).getExhibition().getName());
		assertEquals(tradeMark.getTransformationPriorities().size(), tradeMark2.getTransformationPriorities().size());
		assertEquals(tradeMark.getTransformationPriorities().get(0).getPriorityDate(), tradeMark2.getTransformationPriorities().get(0).getPriorityDate());

		assertEquals(tradeMark.getApplicants().size(), tradeMark2.getApplicants().size());
		assertEquals(tradeMark.getApplicants().get(0).getNationality(), tradeMark2.getApplicants().get(0).getNationality());
		assertEquals(tradeMark.getRepresentatives().size(), tradeMark2.getRepresentatives().size());
		assertEquals(tradeMark.getRepresentatives().get(0).getNationality(), tradeMark2.getRepresentatives().get(0).getNationality());

	}

}
