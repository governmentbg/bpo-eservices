/*
 *  FspDomain:: DesignApplicationConverterTest 17/12/13 13:37 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.contact.Email;
import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.filing.domain.ds.Transaction;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DesignApplicationConverterTest {

	public TradeMarkApplication fspTradeMarkApplicationToCoreTradeMarkApplication(TradeMarkApplication designApplication) {
		return new TradeMarkApplication();
	}

	@Before
	public void setUp() throws Exception {
	}


    private Design getDesign() {
        Design design = new Design();
        design.setApplicationLanguage("en");
        design.setSecondLanguage("el");
        Date date = new Date();

        design.setExpiryDate(date);
        design.setRegistrationDate(new Date());
        design.setRegistrationNumber("R2342334567567");

        //Priorities
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

        design.setPriorities(new ArrayList<Priority>());
        design.getPriorities().add(priority);

        System.out.println(design.getPriorities().size());

        //ExhibitionPriorities
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

        design.setExhibitionPriorities(new ArrayList<ExhibitionPriority>());
        design.getExhibitionPriorities().add(exhibitionPriority);

        return design;
    }

	@Test
	public void testConvert() {
        Design design = getDesign();

        eu.ohim.sp.core.domain.design.DesignApplication designApplication = new eu.ohim.sp.core.domain.design.DesignApplication();
        designApplication.setDesignDetails(new ArrayList<eu.ohim.sp.core.domain.design.Design>());
        designApplication.getDesignDetails().add(design);

        designApplication.setApplicationLanguage("en");
        designApplication.setSecondLanguage("es");
        designApplication.setApplicationDate(new Date());
        designApplication.setApplicationNumber("ApplicationNumber");
        designApplication.setApplicationFilingDate(new Date());
        designApplication.setApplicationFilingNumber("FilingNumber");
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
        applicant.getEmails().get(0).setEmailAddress("test@oami.gr");
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
        applicant.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");

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

        applicant.getName().setFirstName("Nikolaos");
        applicant.getName().setMiddleName("B");
        applicant.getName().setLastName("Papadopoulos");

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
        designer.getEmails().get(0).setEmailAddress("test@oami.gr");
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
        designer.getCorrespondenceAddresses().get(0).setPostalName("Mr Christos Papas");

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

        designer.getName().setFirstName("Georgios");
        designer.getName().setMiddleName("H");
        designer.getName().setLastName("Papadopoulos");

        designer.getName().setSecondLastName("secondLastName");
        designer.getName().setOrganizationName("organizationName");


        designApplication.setDesigners(new ArrayList<Designer>());
        designApplication.getDesigners().add(designer);

        //DozerMapping
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/ds/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/ds/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Transaction transaction =
			    dozerBeanMapper.map(designApplication, Transaction.class);

        javax.xml.bind.Marshaller marshaller;
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Transaction.class);
            marshaller = jc.createMarshaller();
            marshaller.setProperty(
                    javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);

            FileOutputStream fos = new FileOutputStream(new File(
                    "target/applicationDesignApplication.xml"));

            marshaller.marshal(transaction, fos);
        } catch (JAXBException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
