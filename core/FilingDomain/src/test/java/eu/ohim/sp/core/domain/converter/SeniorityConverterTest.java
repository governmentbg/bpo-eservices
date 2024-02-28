/*
 *  FspDomain:: SeniorityConverterTest 02/09/13 12:01 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.filing.domain.tm.ClassDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.ClassNumber;
import eu.ohim.sp.filing.domain.tm.ClassificationTermDetails;
import eu.ohim.sp.filing.domain.tm.ClassificationTermType;
import eu.ohim.sp.filing.domain.tm.DocumentKind;
import eu.ohim.sp.filing.domain.tm.GoodsServices;
import eu.ohim.sp.filing.domain.tm.ISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.SeniorityKind;
import eu.ohim.sp.filing.domain.tm.Text;
import eu.ohim.sp.filing.domain.tm.WIPOST3Code;

public class SeniorityConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		
		Seniority seniority = new Seniority();
		seniority.setFilingNumber("application number");
		seniority.setOffice("EM");
		seniority.setFilingDate(new Date());
		seniority.setRegistrationDate(new Date());
		seniority.setRegistrationNumber("R21231");
		seniority.setKind(eu.ohim.sp.core.domain.claim.SeniorityKind.INTERNATIONAL_TRADE_MARK);
		seniority.setInternationalTradeMarkCode(InternationalTradeMarkCode.MADRID);
		
		Document document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");

		AttachedDocument attachedDocument = new AttachedDocument();
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
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.tm.Seniority dest = 
			    dozerBeanMapper.map(seniority, eu.ohim.sp.filing.domain.tm.Seniority.class);

		assertEquals(dest.getFilingDate(), seniority.getFilingDate());
		assertEquals(dest.getApplicationNumber(), seniority.getFilingNumber());
		assertEquals(dest.getRegistrationDate(), seniority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), seniority.getRegistrationNumber());
		
		assertEquals(dest.getOffice().value(), seniority.getOffice());
		assertEquals(dest.isPartialIndicator(), seniority.getPartialIndicator());
		assertEquals(dest.getDocuments().get(0).getComment().getValue(), seniority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getDocuments().get(0).getFileName(), seniority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getDocuments().get(0).getLanguage().value(), seniority.getAttachedDocuments().get(0).getDocument().getLanguage());
		assertEquals(dest.getDocuments().get(0).getName(), seniority.getAttachedDocuments().get(0).getDocument().getName());
		assertEquals(dest.getDocuments().get(0).getKind().value(), seniority.getAttachedDocuments().get(0).getDocumentKind());

		assertEquals(seniority.getPartialGoodsServices().get(0).getClassNumber(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().getValue());
		assertEquals(seniority.getPartialGoodsServices().get(0).getLanguage(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getLanguage().value());
		assertEquals(seniority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), dest.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().getValue());

	}


	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.Seniority seniority = new eu.ohim.sp.filing.domain.tm.Seniority();
		seniority.setApplicationNumber("W23423423");
		seniority.setOffice(WIPOST3Code.fromValue("EM"));
		seniority.setFilingDate(new Date());
		seniority.setRegistrationDate(new Date());
		seniority.setRegistrationNumber("I23423423");
		seniority.setKind(new SeniorityKind());
		seniority.getKind().setValue("International trade mark");
		seniority.setInternationalTradeMarkCode(eu.ohim.sp.filing.domain.tm.InternationalTradeMarkCode.EU);
		
		eu.ohim.sp.filing.domain.tm.Document document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new Text());
		document.getComment().setValue("comment");
		document.setFileName("filename");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.SENIORITY_CERTIFICATE_TRANSLATION);
		document.setName("name");
		document.setLanguage(ISOLanguageCode.EN);
		
		seniority.getDocuments().add(document);
		seniority.setPartialIndicator(true);
		seniority.setPartialGoodsServices(new GoodsServices());
		seniority.getPartialGoodsServices().setClassDescriptionDetails(new ClassDescriptionDetails());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().add(new eu.ohim.sp.filing.domain.tm.ClassDescription());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).setClassNumber(new ClassNumber());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().setValue("44");

		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().add(new ClassificationTermDetails());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).setLanguage(ISOLanguageCode.EN);
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().add(new ClassificationTermType());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).setClassificationTermText(new Text());
		seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().setValue("test");

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		Seniority dest = 
			    dozerBeanMapper.map(seniority, Seniority.class);

		assertEquals(dest.getFilingDate(), seniority.getFilingDate());
		assertEquals(dest.getFilingNumber(), seniority.getApplicationNumber());
		assertEquals(dest.getRegistrationDate(), seniority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), seniority.getRegistrationNumber());

		assertEquals(dest.getOffice(), seniority.getOffice().value());
		assertEquals(dest.getPartialIndicator(), seniority.isPartialIndicator());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), seniority.getDocuments().get(0).getComment().getValue());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), seniority.getDocuments().get(0).getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), seniority.getDocuments().get(0).getLanguage().value());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), seniority.getDocuments().get(0).getName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), seniority.getDocuments().get(0).getKind().value());

		assertEquals(dest.getPartialGoodsServices().get(0).getClassNumber(), seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassNumber().getValue());
		assertEquals(dest.getPartialGoodsServices().get(0).getLanguage(), seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getLanguage().value());
		assertEquals(dest.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), 
				seniority.getPartialGoodsServices().getClassDescriptionDetails().getClassDescription().get(0).getClassificationTerms().get(0).getClassificationTerm().get(0).getClassificationTermText().getValue());
		
	}

}
