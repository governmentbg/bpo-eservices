/*
 *  FspDomain:: ExhibitionPriorityConverterTest 02/09/13 12:01 karalch $
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

import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.filing.domain.tm.DocumentKind;
import eu.ohim.sp.filing.domain.tm.ISOCountryCode;
import eu.ohim.sp.filing.domain.tm.ISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.Text;

public class ExhibitionPriorityConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		
		ExhibitionPriority priority = new ExhibitionPriority();
		priority.setExhibition(new Exhibition());
		priority.getExhibition().setCity("city");
		priority.getExhibition().setCountry("FR");
		priority.setDate(new Date());
		priority.setFirstDisplayDate(new Date());
		priority.getExhibition().setName("name");
		
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
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		eu.ohim.sp.filing.domain.tm.ExhibitionPriority dest =
			    dozerBeanMapper.map(priority, eu.ohim.sp.filing.domain.tm.ExhibitionPriority.class);
		
		assertEquals(dest.getDate(), priority.getDate());
		assertEquals(dest.getCity(), priority.getExhibition().getCity());
		assertEquals(dest.getCountry().toString(), priority.getExhibition().getCountry());
		assertEquals(dest.getDate(), priority.getDate());
		assertEquals(dest.getFirstDisplayDate(), priority.getFirstDisplayDate());
		assertEquals(dest.getName(), priority.getExhibition().getName());
		assertEquals(dest.getDocuments().get(0).getComment().getValue(), priority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getDocuments().get(0).getKind().value(), priority.getAttachedDocuments().get(0).getDocumentKind());
		assertEquals(dest.getDocuments().get(0).getFileName(), priority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getDocuments().get(0).getLanguage().toString(), priority.getAttachedDocuments().get(0).getDocument().getLanguage().toUpperCase());
		assertEquals(dest.getDocuments().get(0).getName(), priority.getAttachedDocuments().get(0).getDocument().getName());

	}


	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.ExhibitionPriority priority = new eu.ohim.sp.filing.domain.tm.ExhibitionPriority();
		priority.setCity("city");
		priority.setCountry(ISOCountryCode.AS);
		priority.setDate(new Date());
		priority.setFirstDisplayDate(new Date());
		priority.setName("name");

		eu.ohim.sp.filing.domain.tm.Document document = new eu.ohim.sp.filing.domain.tm.Document();
		document.setComment(new Text());
		document.getComment().setValue("comment");
		document.setFileName("filename");
		document.setKind(eu.ohim.sp.filing.domain.tm.DocumentKind.EXHIBITION_PRIORITY_CERTIFICATE_TRANSLATION);
		document.setName("name");
		document.setLanguage(ISOLanguageCode.EN);
		
		priority.getDocuments().add(document);

		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		ExhibitionPriority dest = 
			    dozerBeanMapper.map(priority, ExhibitionPriority.class);
		
		
		assertEquals(dest.getDate(), priority.getDate());
		assertEquals(dest.getExhibition().getCity(), priority.getCity());
		assertEquals(dest.getExhibition().getCountry(), priority.getCountry().value());
		assertEquals(dest.getDate(), priority.getDate());
		assertEquals(dest.getFirstDisplayDate(), priority.getFirstDisplayDate());
		assertEquals(dest.getExhibition().getName(), priority.getName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), priority.getDocuments().get(0).getComment().getValue());
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), priority.getDocuments().get(0).getKind().value());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), priority.getDocuments().get(0).getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), priority.getDocuments().get(0).getLanguage().value());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), priority.getDocuments().get(0).getName());
		
	}

}
