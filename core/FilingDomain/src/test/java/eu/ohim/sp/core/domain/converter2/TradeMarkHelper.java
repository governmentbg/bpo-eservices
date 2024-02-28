/*
 *  FspDomain:: TradeMarkHelper 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter2;

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

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 04/11/13
 * Time: 09:45
 * To change this template use File | Settings | File Templates.
 */
public class TradeMarkHelper {
    public static <T extends TradeMark> T fillCoreTradeMark(Class<T> clazz) {

        T t = null;
        try {
            t = clazz.newInstance();
            TradeMark tradeMark = (TradeMark) t;
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
                    .setEmailAddress("test@oami.gr");
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
                    .setPostalName("Mr Christos Papas");

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

            applicant.getName().setFirstName("George");
            applicant.getName().setMiddleName("T");
            applicant.getName().setLastName("Papadopoulos");

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

            signatory.setCapacityText("text");
            signatory.setAssociatedText("associatedText");
            signatory.setEmail("email@sth.com");
            signatory.setAddress(new Address());

            signatory.getAddress().setCity("London Address");
            signatory.getAddress().setStreet("Agias Lauras 36 Address");
            signatory.getAddress().setState("Attica Address");
            signatory.getAddress().setCountry("GR");
            signatory.getAddress().setStreetNumber("36 B");

            signatory.setConfirmation(Boolean.TRUE);

            tradeMark.setSignatures(new ArrayList<Signatory>());
            tradeMark.getSignatures().add(signatory);

            // ImageSpecification
            ImageSpecification imageSpecification = new ImageSpecification();
            Image representation = new Image();
            representation.setFileName("filename");
            representation.setFileFormat("JPEG");
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
                    .setEmailAddress("test@oami.gr");
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
                    .setPostalName("Mr Christos Papas");

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

            representative.getName().setFirstName("George");
            representative.getName().setMiddleName("T");
            representative.getName().setLastName("Papadopoulos");

            tradeMark.setRepresentatives(new ArrayList<Representative>());
            tradeMark.getRepresentatives().add(representative);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return t;
    }

}
