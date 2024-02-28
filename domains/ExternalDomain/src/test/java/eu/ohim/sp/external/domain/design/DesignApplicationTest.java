package eu.ohim.sp.external.domain.design;

import eu.ohim.sp.external.domain.application.Signatory;
import eu.ohim.sp.external.domain.claim.DesignExhibitionPriority;
import eu.ohim.sp.external.domain.claim.DesignPriority;
import eu.ohim.sp.external.domain.claim.DivisionalApplicationDetails;
import eu.ohim.sp.external.domain.claim.Exhibition;
import eu.ohim.sp.external.domain.contact.*;
import eu.ohim.sp.external.domain.epayment.Fee;
import eu.ohim.sp.external.domain.epayment.PaymentFee;
import eu.ohim.sp.external.domain.person.*;
import eu.ohim.sp.external.domain.resource.AttachedDocument;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Χρήστος
 * Date: 27/1/2014
 * Time: 9:00 μμ
 * To change this template use File | Settings | File Templates.
 */
public class DesignApplicationTest {

    @Test
    public void testDesignApplicationDummyConstructor() {
        DesignApplication designApplication = new DesignApplication("applicationFilingNumber", new Date(), new ArrayList<Fee>(),
                new ArrayList<PaymentFee>(), new Date(), "receivingOffice", "receiptNumber", new Date(), "applicationNumber", "user",
                new Date(), "applicationLanguage", "secondLanguage", Boolean.TRUE, Boolean.TRUE, new ArrayList<Design>(), new ArrayList<Applicant>(),
                new ArrayList<Representative>(), new ArrayList<AttachedDocument>(), new ArrayList<Signatory>(), new ArrayList<Address>(),
                "reference", "correspondenceLanguage", CorrespondenceKind.EMAIL, new ArrayList<Designer>(), new DivisionalApplicationDetails(),
                new ArrayList<DesignPriority>(), new ArrayList<DesignExhibitionPriority>(), "applicantURI", "representativeURI");
    }

    @Test
    public void testDesignApplication() {
        DesignApplication designApplication = new DesignApplication();
        designApplication.setDesignDetails(new ArrayList<Design>());
        //Tests are done on level of design
        designApplication.getDesignDetails().add(new Design());

        designApplication.setApplicationLanguage("en");
        designApplication.setDivisionalApplicationDetails(new DivisionalApplicationDetails());
        designApplication.setApplicationDate(new Date());
        designApplication.setCorrespondenceKind(CorrespondenceKind.EMAIL);

        designApplication.setApplicationNumber("ApplicationNumber");
        designApplication.setApplicationFilingDate(new Date());
        designApplication.setApplicationFilingNumber("FilingNumber");
        designApplication.setPublicationDate(new Date());
        designApplication.setReceiptNumber("Receipt");
        designApplication.setReceivingOffice("EM");
        designApplication.setReceivingOfficeDate(new Date());
        designApplication.setReference("reference");
        designApplication.setSecondLanguage("es");
        designApplication.setUser("username");
        designApplication.setPriorityClaimLaterIndicator(true);
        designApplication.setExhibitionPriorityClaimLaterIndicator(true);

        //Other tests contain applicant
        Applicant applicant = new Applicant();
        designApplication.setApplicants(new ArrayList<Applicant>());
        designApplication.getApplicants().add(applicant);

        //Other tests contain representative
        designApplication.setRepresentatives(new ArrayList<Representative>());
        designApplication.getRepresentatives().add(new Representative());

        //Other tests contain signatory
        designApplication.setSignatures(new ArrayList<Signatory>());
        designApplication.getSignatures().add(new Signatory());

        Designer designer = new Designer();
        designer.setLegalForm("screw");
        designer.setNationality("EL");
        designer.setDomicileCountry("GR");
        designer.setWaiver(true);
        designer.setDesignsAssociatedIndicator(true);
        designer.setGroupName("group");
        designer.setBelongsToAGroup(true);

        designer.setDomicileLocality("test");
        designer.setIncorporationCountry("GR");
        designer.setIncorporationState("Attica");

        designApplication.setDesigners(new ArrayList<eu.ohim.sp.external.domain.person.Designer>());
        designApplication.getDesigners().add(designer);


        DesignPriority priority = new DesignPriority();
        priority.setFilingDate(new Date());
        priority.setFilingOffice("EM");
        priority.setFilingNumber("filingNumber");
        priority.setSequenceNumber(BigInteger.ONE);
        priority.setApplicantName("applicant");
        priority.setCertificateAttachedIndicator(false);
        priority.setDesignsAssociatedIndicator(false);
        priority.setTranslationAttachedIndicator(false);
        designApplication.setPriorities(new ArrayList<DesignPriority>());
        designApplication.getPriorities().add(priority);

        DesignExhibitionPriority exhibitionPriority = new DesignExhibitionPriority();
        exhibitionPriority.setSequenceNumber(BigInteger.ONE);
        exhibitionPriority.setDesignsAssociatedIndicator(false);
        exhibitionPriority.setExhibition(new Exhibition());
        exhibitionPriority.getExhibition().setCity("city");
        exhibitionPriority.getExhibition().setCountry("country");
        exhibitionPriority.getExhibition().setName("name");
        exhibitionPriority.getExhibition().setOpeningDate(new Date());
        exhibitionPriority.setDate(new Date());
        exhibitionPriority.setStatus("active");
        exhibitionPriority.setStatusDate(new Date());
        designApplication.getExhibitionPriorities().add(exhibitionPriority);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        eu.ohim.sp.core.domain.design.DesignApplication designApplicationCore =
                dozerBeanMapper.map(designApplication, eu.ohim.sp.core.domain.design.DesignApplication.class);


        assertEquals(designApplication.getApplicationLanguage(), designApplicationCore.getApplicationLanguage());


        assertEquals(designApplication.getApplicationDate(), designApplicationCore.getApplicationDate());
        assertEquals(designApplication.getCorrespondenceKind().value().toLowerCase(), designApplicationCore.getCorrespondenceKind().value().toLowerCase());

        assertEquals(designApplication.getApplicationNumber(), designApplicationCore.getApplicationNumber());
        assertEquals(designApplication.getApplicationFilingDate(), designApplicationCore.getApplicationFilingDate());
        assertEquals(designApplication.getApplicationFilingNumber(), designApplicationCore.getApplicationFilingNumber());
        assertEquals(designApplication.getPublicationDate(), designApplicationCore.getPublicationDate());
        assertEquals(designApplication.getReceiptNumber(), designApplicationCore.getReceiptNumber());
        assertEquals(designApplication.getReceivingOffice(), designApplicationCore.getReceivingOffice());
        assertEquals(designApplication.getReceivingOfficeDate(), designApplicationCore.getReceivingOfficeDate());
        assertEquals(designApplication.getReference(), designApplicationCore.getReference());
        assertEquals(designApplication.getSecondLanguage(), designApplicationCore.getSecondLanguage());
        assertEquals(designApplication.getUser(), designApplicationCore.getUser());
        assertEquals(designApplication.getPriorityClaimLaterIndicator(), designApplicationCore.getPriorityClaimLaterIndicator());
        assertEquals(designApplication.getExhibitionPriorityClaimLaterIndicator(), designApplicationCore.getExhibitionPriorityClaimLaterIndicator());

        assertEquals(designApplication.getDesigners().get(0).getLegalForm(), designApplicationCore.getDesigners().get(0).getLegalForm());
        assertEquals(designApplication.getDesigners().get(0).getNationality(), designApplicationCore.getDesigners().get(0).getNationality());
        assertEquals(designApplication.getDesigners().get(0).getDomicileCountry(), designApplicationCore.getDesigners().get(0).getDomicileCountry());
        assertEquals(designApplication.getDesigners().get(0).getDomicileLocality(), designApplicationCore.getDesigners().get(0).getDomicileLocality());
        assertEquals(designApplication.getDesigners().get(0).isWaiver(), designApplicationCore.getDesigners().get(0).isWaiver());
        assertEquals(designApplication.getDesigners().get(0).isBelongsToAGroup(), designApplicationCore.getDesigners().get(0).isBelongsToAGroup());
        assertEquals(designApplication.getDesigners().get(0).isDesignsAssociatedIndicator(), designApplicationCore.getDesigners().get(0).getDesignsAssociatedIndicator());
        assertEquals(designApplication.getDesigners().get(0).getGroupName(), designApplicationCore.getDesigners().get(0).getGroupName());

        assertEquals(designApplication.getPriorities().get(0).getApplicantName(), designApplicationCore.getPriorities().get(0).getApplicantName());
        assertEquals(designApplication.getPriorities().get(0).getFilingDate(), designApplicationCore.getPriorities().get(0).getFilingDate());
        assertEquals(designApplication.getPriorities().get(0).getFilingNumber(), designApplicationCore.getPriorities().get(0).getFilingNumber());
        assertEquals(designApplication.getPriorities().get(0).getFilingOffice(), designApplicationCore.getPriorities().get(0).getFilingOffice());
        assertEquals(designApplication.getPriorities().get(0).getSequenceNumber().intValue(), designApplicationCore.getPriorities().get(0).getSequenceNumber());
        assertEquals(designApplication.getPriorities().get(0).isCertificateAttachedIndicator(), designApplicationCore.getPriorities().get(0).isCerticateAttachedIndicator());
        assertEquals(designApplication.getPriorities().get(0).isTranslationAttachedIndicator(), designApplicationCore.getPriorities().get(0).isTranslationAttachedIndicator());

        assertEquals(designApplication.getExhibitionPriorities().get(0).getExhibition().getCity(), designApplicationCore.getExhibitionPriorities().get(0).getExhibition().getCity());
        assertEquals(designApplication.getExhibitionPriorities().get(0).getExhibition().getCountry(), designApplicationCore.getExhibitionPriorities().get(0).getExhibition().getCountry());
        assertEquals(designApplication.getExhibitionPriorities().get(0).getExhibition().getOpeningDate(), designApplicationCore.getExhibitionPriorities().get(0).getExhibition().getOpeningDate());
        assertEquals(designApplication.getExhibitionPriorities().get(0).getExhibition().getName(), designApplicationCore.getExhibitionPriorities().get(0).getExhibition().getName());

        assertEquals(designApplication.getExhibitionPriorities().get(0).getDate(), designApplicationCore.getExhibitionPriorities().get(0).getDate());
        assertEquals(designApplication.getExhibitionPriorities().get(0).getStatus(), designApplicationCore.getExhibitionPriorities().get(0).getStatus());
        assertEquals(designApplication.getExhibitionPriorities().get(0).getStatusDate(), designApplicationCore.getExhibitionPriorities().get(0).getStatusDate());

    }

}
