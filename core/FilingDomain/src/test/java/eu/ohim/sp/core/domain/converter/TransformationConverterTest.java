/*
 *  FspDomain:: TransformationConverterTest 02/09/13 12:01 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.filing.domain.tm.DocumentKind;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TransformationConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvertToFSP() {
		TransformationPriority priority = new TransformationPriority();
		priority.setCancellationDate(new Date());
		priority.setPriorityDate(new Date());
		priority.setRegistrationDate(new Date());
		priority.setRegistrationNumber("R21312313");

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
		
		eu.ohim.sp.filing.domain.tm.TransformationPriority dest = 
			    dozerBeanMapper.map(priority, eu.ohim.sp.filing.domain.tm.TransformationPriority.class);
		
		assertEquals(dest.getCancellationDate(), priority.getCancellationDate());
		assertEquals(dest.getPriorityDate(), priority.getPriorityDate());
		assertEquals(dest.getRegistrationDate(), priority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), priority.getRegistrationNumber());
	}


	@Test
	public void testConvertToCore() {
		eu.ohim.sp.filing.domain.tm.TransformationPriority priority = new eu.ohim.sp.filing.domain.tm.TransformationPriority();
		priority.setCancellationDate(new Date());
		priority.setPriorityDate(new Date());
		priority.setRegistrationDate(new Date());
		priority.setRegistrationNumber("R21312313");
		
		List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);
		
		TransformationPriority dest = 
			    dozerBeanMapper.map(priority, TransformationPriority.class);
		
		assertEquals(dest.getCancellationDate(), priority.getCancellationDate());
		assertEquals(dest.getPriorityDate(), priority.getPriorityDate());
		assertEquals(dest.getRegistrationDate(), priority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), priority.getRegistrationNumber());
	}

}
