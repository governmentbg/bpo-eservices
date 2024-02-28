/*
 *  FspDomain:: PriorityConverterTest 03/09/13 16:46 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.filing.domain.tm.ClassDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.ClassNumber;
import eu.ohim.sp.filing.domain.tm.ClassificationTermDetails;
import eu.ohim.sp.filing.domain.tm.ClassificationTermType;
import eu.ohim.sp.filing.domain.tm.DocumentKind;
import eu.ohim.sp.filing.domain.tm.ExtendedWIPOST3Code;
import eu.ohim.sp.filing.domain.tm.GoodsServices;
import eu.ohim.sp.filing.domain.tm.ISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.Text;

public class PriorityConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		
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
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.tm.Priority dest = 
			    dozerBeanMapper.map(priority, eu.ohim.sp.filing.domain.tm.Priority.class);

		assertEquals(dest.getFilingDate(), priority.getFilingDate());
		assertEquals(dest.getFilingNumber(), priority.getFilingNumber());
		assertEquals(dest.getFilingOffice().getValue(), priority.getFilingOffice());		

		assertEquals(dest.isPartialIndicator(), priority.getPartialIndicator());
		assertEquals(dest.getDocuments().get(0).getComment().getValue(), priority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getDocuments().get(0).getFileName(), priority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getDocuments().get(0).getKind().value(), priority.getAttachedDocuments().get(0).getDocumentKind());
		assertEquals(dest.getDocuments().get(0).getLanguage().value(), priority.getAttachedDocuments().get(0).getDocument().getLanguage());
		assertEquals(dest.getDocuments().get(0).getName(), priority.getAttachedDocuments().get(0).getDocument().getName());

		assertEquals(priority.getPartialGoodsServices().get(0).getClassNumber(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().getValue());
		assertEquals(priority.getPartialGoodsServices().get(0).getLanguage(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getLanguage().value());
		assertEquals(priority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().getValue());

	}


	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.Priority priority = new eu.ohim.sp.filing.domain.tm.Priority();
		priority.setFilingNumber("W23423423");
		priority.setFilingOffice(new ExtendedWIPOST3Code("EM"));
		priority.setFilingDate(new Date());

		eu.ohim.sp.filing.domain.tm.Document document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new Text());
		document.getComment().setValue("comment");
		document.setFileName("filename");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.PRIORITY_CERTIFICATE_TRANSLATION);
		document.setName("name");
		document.setLanguage(ISOLanguageCode.EN);
		
		priority.getDocuments().add(document);
		priority.setPartialIndicator(true);
		priority.setPartialGoodsServices(new GoodsServices());
		priority.getPartialGoodsServices().setClassDescriptionDetails(new ClassDescriptionDetails());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().add(new eu.ohim.sp.filing.domain.tm.ClassDescription());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).setClassNumber(new ClassNumber());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().setValue("44");

		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().add(new ClassificationTermDetails());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).setLanguage(ISOLanguageCode.EN);
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().add(new ClassificationTermType());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).setClassificationTermText(new Text());
		priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().setValue("test");

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Priority dest = 
			    dozerBeanMapper.map(priority, Priority.class);
		
		assertEquals(dest.getFilingDate(), priority.getFilingDate());
		assertEquals(dest.getFilingNumber(), priority.getFilingNumber());
		assertEquals(dest.getFilingOffice(), priority.getFilingOffice().getValue());
		assertEquals(dest.getPartialIndicator(), priority.isPartialIndicator());
		
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), priority.getDocuments().get(0).getKind().value());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), priority.getDocuments().get(0).getComment().getValue());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), priority.getDocuments().get(0).getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), priority.getDocuments().get(0).getLanguage().value());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), priority.getDocuments().get(0).getName());

		assertEquals(dest.getPartialGoodsServices().get(0).getClassNumber(), priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().getValue());
		assertEquals(dest.getPartialGoodsServices().get(0).getLanguage(), priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getLanguage().value());
		assertEquals(dest.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), 
				priority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().getValue());
		
	}

}
