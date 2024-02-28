/*
 *  FspDomain:: DesignConverterTest 02/09/13 12:01 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.claim.*;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.resources.*;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import eu.ohim.sp.filing.domain.ds.*;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesignConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvert() {
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

		//DozerMapping
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/ds/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/ds/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.ds.Design design2 =
			    dozerBeanMapper.map(design, eu.ohim.sp.filing.domain.ds.Design.class);

        javax.xml.bind.Marshaller marshaller;
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Transaction.class);
            marshaller = jc.createMarshaller();
            marshaller.setProperty(
                    javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);

            Transaction transaction = new Transaction();
            transaction.getDesignTransactionBody()
                    .add(new TransactionBody());
            transaction
                    .getDesignTransactionBody()
                    .get(0)
                    .setTransactionContentDetails(
                            new TransactionContentDetails());
            transaction.getDesignTransactionBody().get(0)
                    .getTransactionContentDetails()
                    .setTransactionData(new TransactionData());
            transaction.getDesignTransactionBody().get(0)
                    .getTransactionContentDetails().getTransactionData()
                    .setDesignApplication(new DesignApplication());
            transaction.getDesignTransactionBody().get(0)
                    .getTransactionContentDetails().getTransactionData()
                    .getDesignApplication()
                    .setDesignDetails(new DesignDetails());
            transaction.getDesignTransactionBody().get(0)
                    .getTransactionContentDetails().getTransactionData()
                    .getDesignApplication().getDesignDetails()
                    .getDesign().add(design2);

            FileOutputStream fos = new FileOutputStream(new File(
                    "target/applicationDesign.xml"));

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
