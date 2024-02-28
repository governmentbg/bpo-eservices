package eu.ohim.sp.external.domain.trademark;

import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.resources.SoundFileFormat;
import eu.ohim.sp.external.domain.application.Signatory;
import eu.ohim.sp.external.domain.claim.*;
import eu.ohim.sp.external.domain.claim.ObjectFactory;
import eu.ohim.sp.external.domain.classification.*;
import eu.ohim.sp.external.domain.common.Text;
import eu.ohim.sp.external.domain.contact.Address;
import eu.ohim.sp.external.domain.contact.Email;
import eu.ohim.sp.external.domain.contact.Phone;
import eu.ohim.sp.external.domain.contact.PhoneKind;
import eu.ohim.sp.external.domain.person.Applicant;
import eu.ohim.sp.external.domain.person.PersonRoleKind;
import eu.ohim.sp.external.domain.person.Representative;
import eu.ohim.sp.external.domain.resource.*;
import org.dozer.DozerBeanMapper;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 22/01/14
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class TradeMarkTest {

    @Test
    public void testDummyConstructor() {
        TradeMark tradeMark = new TradeMark("receivingOffice", new Date(), new Date(), "registrationNumber",
                "registrationOffice", "applicationNumber", new Date(), "filingNumber", new Date(), "filingUser", new Date(), "applicationReference",
                new Date(), new Date(), "applicationLanguage", "secondLanguage", "correspondenceLanguage", "currentStatus", new Date(), new Date(), new Date(),
                new ArrayList<ClassDescription>(), new ArrayList<MarkDescription>(), new ArrayList<MarkDisclaimer>(), new ArrayList<WordSpecification>(),
                new ArrayList<ImageSpecification>(), new ArrayList<SoundSpecification>(), MarkKind.INDIVIDUAL, false, 1, MarkFeature.HOLOGRAM, false,
                new ArrayList<MarkPriority>(), false, new ArrayList<ExhibitionPriority>(), new ArrayList<IRTransformationPriority>(), new ArrayList<MarkSeniority>(),
                new ArrayList<Applicant>(), new ArrayList<Representative>(), new ArrayList<AttachedDocument>(), false, false, false, new DivisionalApplicationDetails(),
                new ArrayList<Signatory>(), false, false, new ArrayList<Address>(), "officeSpecificId", "comment");
    }

    @Test
    public void testTradeMarkDomain() {
        TradeMark tradeMarkExternal = fillCoreTradeMark();

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.trademark.TradeMark tradeMarkCore =
            dozerBeanMapper.map(tradeMarkExternal, eu.ohim.sp.core.domain.trademark.TradeMark.class);

        assertEquals(tradeMarkExternal.getApplicationLanguage(), tradeMarkCore.getApplicationLanguage());
        assertEquals(tradeMarkExternal.getSecondLanguage(), tradeMarkCore.getSecondLanguage());
        assertEquals(tradeMarkExternal.getCorrespondenceLanguage(), tradeMarkCore.getCorrespondenceLanguage());
        assertEquals(tradeMarkExternal.getApplicationReference(), tradeMarkCore.getApplicationReference());
        assertEquals(tradeMarkExternal.getFilingUser(), tradeMarkCore.getFilingUser());

        assertEquals(tradeMarkExternal.getOppositionPeriodEnd(), tradeMarkCore.getOppositionPeriodEnd());
        assertEquals(tradeMarkExternal.getOppositionPeriodStart(), tradeMarkCore.getOppositionPeriodStart());
        assertEquals(tradeMarkExternal.getPublicationDate(), tradeMarkCore.getPublicationDate());
        assertEquals(tradeMarkExternal.getExpirationDate(), tradeMarkCore.getExpirationDate());
        assertEquals(tradeMarkExternal.getFilingDate(), tradeMarkCore.getFilingDate());
        assertEquals(tradeMarkExternal.getFilingNumber(), tradeMarkCore.getFilingNumber());
        assertEquals(tradeMarkExternal.getApplicationNumber(), tradeMarkCore.getApplicationNumber());
        assertEquals(tradeMarkExternal.getReceivingOffice(), tradeMarkCore.getReceivingOffice());
        assertEquals(tradeMarkExternal.getRegistrationOffice(), tradeMarkCore.getRegistrationOffice());
        assertEquals(tradeMarkExternal.getRegistrationDate(), tradeMarkCore.getRegistrationDate());
        assertEquals(tradeMarkExternal.getRegistrationNumber(), tradeMarkCore.getRegistrationNumber());
        assertEquals(tradeMarkExternal.getTerminationDate(), tradeMarkCore.getTerminationDate());
        assertEquals(tradeMarkExternal.getComment(), tradeMarkCore.getComment());
        assertEquals(tradeMarkExternal.getPriorityClaimLaterIndicator(), tradeMarkCore.getPriorityClaimLaterIndicator());
        assertEquals(tradeMarkExternal.getExhibitionPriorityClaimLaterIndicator(), tradeMarkCore.getExhibitionPriorityClaimLaterIndicator());
        assertEquals(tradeMarkExternal.getApplicationDate(), tradeMarkCore.getApplicationDate());
        // To Be checked
        assertEquals(tradeMarkExternal.getMarkKind().value().toLowerCase(), tradeMarkCore.getMarkKind().value().toLowerCase());

        tradeMarkExternal.getApplicants().get(0).getLegalForm();
        assertEquals(tradeMarkExternal.getApplicants().get(0).getNationality(), tradeMarkCore.getApplicants().get(0).getNationality());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getDomicileCountry(), tradeMarkCore.getApplicants().get(0).getDomicileCountry());

        assertEquals(tradeMarkExternal.getApplicants().get(0).getDomicileLocality(), tradeMarkCore.getApplicants().get(0).getDomicileLocality());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getIncorporationCountry(), tradeMarkCore.getApplicants().get(0).getIncorporationCountry());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getIncorporationState(), tradeMarkCore.getApplicants().get(0).getIncorporationState());


        assertEquals(tradeMarkExternal.getApplicants().get(0).getEmails().get(0).getEmailAddress(), tradeMarkCore.getApplicants().get(0).getEmails().get(0).getEmailAddress());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getEmails().get(1).getEmailAddress(), tradeMarkCore.getApplicants().get(0).getEmails().get(1).getEmailAddress());

        assertEquals(tradeMarkExternal.getApplicants().get(0).getPhones().get(0).getNumber(), tradeMarkCore.getApplicants().get(0).getPhones().get(0).getNumber());
        // To Be checked
        assertEquals(tradeMarkExternal.getApplicants().get(0).getPhones().get(0).getPhoneKind().value(), tradeMarkCore.getApplicants().get(0).getPhones().get(0).getPhoneKind().value());

        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getCity(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getCity());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getStreet(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getStreet());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getState(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getState());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getCountry(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getCountry());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getStreetNumber(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getStreetNumber());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getPostalName(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getPostalName());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getCorrespondenceAddresses().get(0).getPostCode(), tradeMarkCore.getApplicants().get(0).getCorrespondenceAddresses().get(0).getPostCode());

        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getCity(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getCity());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getStreet(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getStreet());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getState(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getState());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getCountry(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getCountry());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getStreetNumber(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getStreetNumber());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getAddresses().get(0).getPostalName(), tradeMarkCore.getApplicants().get(0).getAddresses().get(0).getPostalName());

        assertEquals(tradeMarkExternal.getApplicants().get(0).getUrls().get(0), tradeMarkCore.getApplicants().get(0).getUrls().get(0));

        assertEquals(tradeMarkExternal.getApplicants().get(0).getName().getFirstName(), tradeMarkCore.getApplicants().get(0).getName().getFirstName());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getName().getMiddleName(), tradeMarkCore.getApplicants().get(0).getName().getMiddleName());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getName().getLastName(), tradeMarkCore.getApplicants().get(0).getName().getLastName());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getName().getSecondLastName(), tradeMarkCore.getApplicants().get(0).getName().getSecondLastName());
        assertEquals(tradeMarkExternal.getApplicants().get(0).getName().getOrganizationName(), tradeMarkCore.getApplicants().get(0).getName().getOrganizationName());


        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getLegalForm(), tradeMarkCore.getRepresentatives().get(0).getLegalForm());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getNationality(), tradeMarkCore.getRepresentatives().get(0).getNationality());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getDomicileCountry(), tradeMarkCore.getRepresentatives().get(0).getDomicileCountry());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getDomicileLocality(), tradeMarkCore.getRepresentatives().get(0).getDomicileLocality());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getIncorporationCountry(), tradeMarkCore.getRepresentatives().get(0).getIncorporationCountry());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getIncorporationState(), tradeMarkCore.getRepresentatives().get(0).getIncorporationState());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getEmails().get(0).getEmailAddress(), tradeMarkCore.getRepresentatives().get(0).getEmails().get(0).getEmailAddress());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getEmails().get(1).getEmailAddress(), tradeMarkCore.getRepresentatives().get(0).getEmails().get(1).getEmailAddress());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getPhones().get(0).getNumber(), tradeMarkCore.getRepresentatives().get(0).getPhones().get(0).getNumber());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getPhones().get(0).getPhoneKind().value(), tradeMarkCore.getRepresentatives().get(0).getPhones().get(0).getPhoneKind().value());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getCity(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getCity());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getStreet(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getStreet());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getState(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getState());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getCountry(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getCountry());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getStreetNumber(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getStreetNumber());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getPostalName(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getPostalName());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getPostCode(), tradeMarkCore.getRepresentatives().get(0).getCorrespondenceAddresses().get(0).getPostCode());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getCity(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getCity());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getStreet(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getStreet());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getState(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getState());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getCountry(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getCountry());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getStreetNumber(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getStreetNumber());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getAddresses().get(0).getPostalName(), tradeMarkCore.getRepresentatives().get(0).getAddresses().get(0).getPostalName());

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getUrls().get(0), tradeMarkCore.getRepresentatives().get(0).getUrls().get(0));

        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getName().getFirstName(), tradeMarkCore.getRepresentatives().get(0).getName().getFirstName());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getName().getMiddleName(), tradeMarkCore.getRepresentatives().get(0).getName().getMiddleName());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getName().getLastName(), tradeMarkCore.getRepresentatives().get(0).getName().getLastName());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getName().getSecondLastName(), tradeMarkCore.getRepresentatives().get(0).getName().getSecondLastName());
        assertEquals(tradeMarkExternal.getRepresentatives().get(0).getName().getOrganizationName(), tradeMarkCore.getRepresentatives().get(0).getName().getOrganizationName());

        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getLanguage(), tradeMarkCore.getClassDescriptions().get(0).getLanguage());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassNumber(), tradeMarkCore.getClassDescriptions().get(0).getClassNumber());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getGoodsServicesDescription(), tradeMarkCore.getClassDescriptions().get(0).getGoodsServicesDescription());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermText(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermText());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermAssessment().getErrorEnum().value().toLowerCase(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermAssessment().getErrorEnum().value().toLowerCase());
//        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermAssessment().getVerificationAssessment().value(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getTermAssessment().getVerificationAssessment());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getAssessmentDate(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getAssessmentDate());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getExaminationStatus(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getExaminationStatus());
        assertEquals(tradeMarkExternal.getClassDescriptions().get(0).getClassificationTerms().get(0).getExaminationStatusDate(), tradeMarkCore.getClassDescriptions().get(0).getClassificationTerms().get(0).getExaminationStatusDate());

        assertEquals(tradeMarkExternal.getDivisionalApplicationDetails().getComment().getValue(), tradeMarkCore.getDivisionalApplicationDetails().getComment().getValue());
        assertEquals(tradeMarkExternal.getDivisionalApplicationDetails().getComment().getLanguage(), tradeMarkCore.getDivisionalApplicationDetails().getComment().getLanguage());
        assertEquals(tradeMarkExternal.getDivisionalApplicationDetails().getInitialApplicationDate(), tradeMarkCore.getDivisionalApplicationDetails().getInitialApplicationDate());
        assertEquals(tradeMarkExternal.getDivisionalApplicationDetails().getInitialApplicationNumber(), tradeMarkCore.getDivisionalApplicationDetails().getInitialApplicationNumber());

        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getExhibition().getCity(), tradeMarkCore.getExhibitionPriorities().get(0).getExhibition().getCity());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getExhibition().getCountry(), tradeMarkCore.getExhibitionPriorities().get(0).getExhibition().getCountry());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getExhibition().getName(), tradeMarkCore.getExhibitionPriorities().get(0).getExhibition().getName());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getExhibition().getOpeningDate(), tradeMarkCore.getExhibitionPriorities().get(0).getExhibition().getOpeningDate());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getDate(), tradeMarkCore.getExhibitionPriorities().get(0).getDate());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getStatusDate(), tradeMarkCore.getExhibitionPriorities().get(0).getStatusDate());
        assertEquals(tradeMarkExternal.getExhibitionPriorities().get(0).getStatus(), tradeMarkCore.getExhibitionPriorities().get(0).getStatus());

        assertEquals(tradeMarkExternal.getPriorities().get(0).getFilingDate(), tradeMarkCore.getPriorities().get(0).getFilingDate());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getFilingOffice(), tradeMarkCore.getPriorities().get(0).getFilingOffice());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getFilingNumber(), tradeMarkCore.getPriorities().get(0).getFilingNumber());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().size(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().size());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().get(0).getDocumentKind(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().get(0).getDocumentKind());

        assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getName(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getName());
        // To Be checked
        // To Be checked
        //assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getComment(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getComment());
        // To Be checked
        // To Be checked
        assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getFileName(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getFileName());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getLanguage(), tradeMarkCore.getPriorities().get(0).getAttachedDocuments().get(0).getDocument().getLanguage());

        assertEquals(tradeMarkExternal.getPriorities().get(0).getPartialGoodsServices().get(0).getClassNumber(), tradeMarkCore.getPriorities().get(0).getPartialGoodsServices().get(0).getClassNumber());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getPartialGoodsServices().get(0).getGoodsServicesDescription(), tradeMarkCore.getPriorities().get(0).getPartialGoodsServices().get(0).getGoodsServicesDescription());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getPartialGoodsServices().get(0).getLanguage(), tradeMarkCore.getPriorities().get(0).getPartialGoodsServices().get(0).getLanguage());
        assertEquals(tradeMarkExternal.getPriorities().get(0).getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), tradeMarkCore.getPriorities().get(0).getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText());

        assertEquals(tradeMarkExternal.getSeniorities().get(0).getFilingDate(), tradeMarkCore.getSeniorities().get(0).getFilingDate());
        assertEquals(tradeMarkExternal.getSeniorities().get(0).getFilingNumber(), tradeMarkCore.getSeniorities().get(0).getFilingNumber());
        assertEquals(tradeMarkExternal.getSeniorities().get(0).getRegistrationNumber(), tradeMarkCore.getSeniorities().get(0).getRegistrationNumber());
        assertEquals(tradeMarkExternal.getSeniorities().get(0).getRegistrationDate(), tradeMarkCore.getSeniorities().get(0).getRegistrationDate());
        assertEquals(tradeMarkExternal.getSeniorities().get(0).getKind().value(), tradeMarkCore.getSeniorities().get(0).getKind().value());
        assertEquals(tradeMarkExternal.getSeniorities().get(0).getInternationalTradeMarkCode().value(), tradeMarkCore.getSeniorities().get(0).getInternationalTradeMarkCode().value());

        assertEquals(tradeMarkExternal.getWordSpecifications().get(0).getWordElements(), tradeMarkCore.getWordSpecifications().get(0).getWordElements());
        assertEquals(tradeMarkExternal.getWordSpecifications().get(1).getWordElements(), tradeMarkCore.getWordSpecifications().get(1).getWordElements());
        assertEquals(tradeMarkExternal.getWordSpecifications().get(1).getCharacterSet(), tradeMarkCore.getWordSpecifications().get(1).getCharacterSet());
        assertEquals(tradeMarkExternal.getWordSpecifications().get(0).getTranscriptionDetails(), tradeMarkCore.getWordSpecifications().get(0).getTranscriptionDetails());
        assertEquals(tradeMarkExternal.getWordSpecifications().get(1).getTranscriptionDetails(), tradeMarkCore.getWordSpecifications().get(1).getTranscriptionDetails());

        assertEquals(tradeMarkExternal.getMarkDescriptions().get(0).getValue(), tradeMarkCore.getMarkDescriptions().get(0).getValue());
        assertEquals(tradeMarkExternal.getMarkDescriptions().get(0).getLanguage(), tradeMarkCore.getMarkDescriptions().get(0).getLanguage());
        assertEquals(tradeMarkExternal.getMarkDisclaimers().get(0).getValue(), tradeMarkCore.getMarkDisclaimers().get(0).getValue());
        assertEquals(tradeMarkExternal.getMarkDisclaimers().get(0).getLanguage(), tradeMarkCore.getMarkDisclaimers().get(0).getLanguage());


        assertEquals(tradeMarkExternal.getSignatures().get(0).getManner(), tradeMarkCore.getSignatures().get(0).getManner());
        assertEquals(tradeMarkExternal.getSignatures().get(0).getCapacityText(), tradeMarkCore.getSignatures().get(0).getCapacityText());
        assertEquals(tradeMarkExternal.getSignatures().get(0).getName(), tradeMarkCore.getSignatures().get(0).getName());
        assertEquals(tradeMarkExternal.getSignatures().get(0).getEmail(), tradeMarkCore.getSignatures().get(0).getEmail());
        assertEquals(tradeMarkExternal.getSignatures().get(0).getAssociatedText(), tradeMarkCore.getSignatures().get(0).getAssociatedText());
        assertEquals(tradeMarkExternal.getSignatures().get(0).getPlace(), tradeMarkCore.getSignatures().get(0).getPlace());

    }

    public static TradeMark fillCoreTradeMark() {
        Date date = new Date();

        eu.ohim.sp.external.domain.trademark.ObjectFactory trademarkObjectFactory = new eu.ohim.sp.external.domain.trademark.ObjectFactory();
        TradeMark tradeMark = trademarkObjectFactory.createTradeMark();
        tradeMark.setApplicationLanguage("en");
        tradeMark.setSecondLanguage("el");
        tradeMark.setCorrespondenceLanguage("el");
        tradeMark.setFilingUser("user");
        tradeMark.setApplicationReference("test 123");

        tradeMark.setReceivingDate(new Date());
        tradeMark.setPublicationDate(new Date());
        tradeMark.setOppositionPeriodStart(new Date());
        tradeMark.setOppositionPeriodEnd(new Date());
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
        tradeMark.setNationalSearchReportIndicator(Boolean.TRUE);

        // DivisionalApplicationDetails
        ObjectFactory claimObjectFactory = new ObjectFactory();
        DivisionalApplicationDetails divisionalApplicationDetails = claimObjectFactory.createDivisionalApplicationDetails();
        divisionalApplicationDetails.setComment(new Text());
        divisionalApplicationDetails.getComment().setValue("comment");
        divisionalApplicationDetails.getComment().setLanguage("en");
        divisionalApplicationDetails.setInitialApplicationDate(new Date());
        divisionalApplicationDetails.setInitialApplicationNumber("initialApp");
        tradeMark.setDivisionalApplicationDetails(divisionalApplicationDetails);

        // Priorities
        MarkPriority priority = claimObjectFactory.createMarkPriority();
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
        eu.ohim.sp.external.domain.classification.ObjectFactory classificationObjectFactory = new eu.ohim.sp.external.domain.classification.ObjectFactory();
        priority.getPartialGoodsServices().add(classificationObjectFactory.createClassDescription());
        priority.getPartialGoodsServices().get(0).setLanguage("en");
        priority.getPartialGoodsServices().get(0).setClassNumber("42");
        priority.getPartialGoodsServices().get(0)
                .setClassificationTerms(new ArrayList<ClassificationTerm>());
        priority.getPartialGoodsServices().get(0).getClassificationTerms()
                .add(classificationObjectFactory.createClassificationTerm());
        priority.getPartialGoodsServices().get(0).getClassificationTerms()
                .get(0).setTermText("whatever");

        tradeMark.setPriorities(new ArrayList<MarkPriority>());
        tradeMark.getPriorities().add(priority);

        // Seniorities
        MarkSeniority seniority = claimObjectFactory.createMarkSeniority();
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

        tradeMark.setSeniorities(new ArrayList<MarkSeniority>());
        tradeMark.getSeniorities().add(seniority);

        // ExhibitionPriorities
        ExhibitionPriority exhibitionPriority = claimObjectFactory.createExhibitionPriority();
        exhibitionPriority.setExhibition(new Exhibition());
        exhibitionPriority.getExhibition().setCity("city");
        exhibitionPriority.getExhibition().setCountry("FR");
        exhibitionPriority.setDate(new Date());
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
        IRTransformationPriority transformationPriority = claimObjectFactory.createIRTransformationPriority();
        transformationPriority.setCancellationDate(new Date());
        transformationPriority.setPriorityDate(new Date());
        transformationPriority.setRegistrationDate(new Date());
        transformationPriority.setRegistrationNumber("R21312313");

        tradeMark
                .setTransformationPriorities(new ArrayList<IRTransformationPriority>());
        tradeMark.getTransformationPriorities().add(transformationPriority);

        // WordSpecifications
        WordSpecification wordSpecification = trademarkObjectFactory.createWordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.setWordSpecifications(new ArrayList<WordSpecification>());
        tradeMark.getWordSpecifications().add(wordSpecification);

        wordSpecification = trademarkObjectFactory.createWordSpecification();
        wordSpecification.setCharacterSet("en");
        wordSpecification.setTranscriptionDetails("test whatever 2");
        wordSpecification.setTransliterationDetails("ere");
        wordSpecification.setWordElements("word element");

        tradeMark.getWordSpecifications().add(wordSpecification);

        // Applicants
        eu.ohim.sp.external.domain.person.ObjectFactory personObjectFactory = new eu.ohim.sp.external.domain.person.ObjectFactory();
        Applicant applicant = personObjectFactory.createApplicant();
        applicant.setLegalForm("screw");
        applicant.setNationality("EL");
        applicant.setDomicileCountry("GR");

        applicant.setDomicileLocality("test");
        applicant.setIncorporationCountry("GR");
        applicant.setIncorporationState("Attica");

        eu.ohim.sp.external.domain.contact.ObjectFactory contactObjectFactory = new eu.ohim.sp.external.domain.contact.ObjectFactory();
        applicant.setCorrespondenceAddresses(new ArrayList<Address>());
        applicant.getCorrespondenceAddresses().add(contactObjectFactory.createAddress());

        applicant.setEmails(new ArrayList<Email>());
        applicant.getEmails().add(contactObjectFactory.createEmail());
        applicant.getEmails().get(0)
                .setEmailAddress("test@oami.gr");
        applicant.getEmails().add(contactObjectFactory.createEmail());
        applicant.getEmails().get(1).setEmailAddress("kapelo@oami.gr");

        applicant.setPhones(new ArrayList<Phone>());
        applicant.getPhones().add(contactObjectFactory.createPhone());
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
        applicant.getCorrespondenceAddresses().get(0)
                .setPostCode("04003");

        applicant.setAddresses(new ArrayList<Address>());

        applicant.getAddresses().add(new Address());
        applicant.getAddresses().get(0).setCity("London Address");
        applicant.getAddresses().get(0).setStreet("Agias Lauras 36 Address");
        applicant.getAddresses().get(0).setState("Attica Address");
        applicant.getAddresses().get(0).setCountry("GR");
        applicant.getAddresses().get(0).setStreetNumber("36 B");

        applicant.setUrls(new ArrayList<String>());
        applicant.getUrls().add("some url");

        applicant.setName(personObjectFactory.createPersonName());

        applicant.getName().setFirstName("Georgios");
        applicant.getName().setMiddleName("P");
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
        signatory.setCapacity(PersonRoleKind.APPLICANT);

        signatory.setCapacityText("text");
        signatory.setAssociatedText("associatedText");
        signatory.setEmail("email@sth.com");
        signatory.setManner("manner");
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
        soundSpecification.setRepresentation(commonSound);

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

        classDescription.getClassificationTerms()
                .get(0).setTermAssessment(classificationObjectFactory.createClassificationErrorType());
        classDescription.getClassificationTerms()
                .get(0).getTermAssessment().setErrorEnum(ClassificationErrorEnum.NONE);
        classDescription.getClassificationTerms()
                .get(0).getTermAssessment().setVerificationAssessment(VerificationAssessmentType.HOMONYM.value());
        classDescription.getClassificationTerms()
                .get(0).setAssessmentDate(new Date());
        classDescription.getClassificationTerms()
                .get(0).setExaminationStatus("status");
        classDescription.getClassificationTerms()
                .get(0).setExaminationStatusDate(new Date());

        tradeMark.setClassDescriptions(new ArrayList<ClassDescription>());
        tradeMark.getClassDescriptions().add(classDescription);

        // MarkDescriptions
        tradeMark.setMarkDescriptions(new ArrayList<MarkDescription>());
        MarkDescription markDescription = trademarkObjectFactory.createMarkDescription();
        markDescription.setLanguage("en");
        markDescription.setValue("test papapra");
        tradeMark.getMarkDescriptions().add(markDescription);

        markDescription = trademarkObjectFactory.createMarkDescription();
        markDescription.setLanguage("ES");
        markDescription.setValue("test papapra");

        tradeMark.getMarkDescriptions().add(markDescription);

        // MarkDisclaimers
        tradeMark.setMarkDisclaimers(new ArrayList<MarkDisclaimer>());
        MarkDisclaimer markDisclaimer = trademarkObjectFactory.createMarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("en");
        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        markDisclaimer = trademarkObjectFactory.createMarkDisclaimer();
        markDisclaimer.setValue("value");
        markDisclaimer.setLanguage("es");

        tradeMark.getMarkDisclaimers().add(markDisclaimer);

        // Representatives
        Representative representative = personObjectFactory.createRepresentative();
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
        representative.getPhones().add(contactObjectFactory.createPhone());
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

        representative.getAddresses().add(contactObjectFactory.createAddress());
        representative.getAddresses().get(0).setCity("London Address");
        representative.getAddresses().get(0)
                .setStreet("Agias Lauras 36 Address");
        representative.getAddresses().get(0).setState("Attica Address");
        representative.getAddresses().get(0).setCountry("GR");
        representative.getAddresses().get(0).setStreetNumber("36 B");

        representative.setUrls(new ArrayList<String>());
        representative.getUrls().add("some url");
        representative.getUrls().add("some url 2");

        representative.setName(personObjectFactory.createPersonName());

        representative.getName().setFirstName("Georgios");
        representative.getName().setMiddleName("P");
        representative.getName().setLastName("Papadopoulos");

        tradeMark.setRepresentatives(new ArrayList<Representative>());
        tradeMark.getRepresentatives().add(representative);
        return tradeMark;
    }

}
