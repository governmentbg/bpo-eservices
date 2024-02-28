package eu.ohim.sp.external.domain.claim;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.resources.DocumentKind;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import eu.ohim.sp.core.domain.claim.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;

public class ClaimConverterTest {

	@Test
	public void priorityConverterTest() {
		Priority priority = new Priority();
		priority.setFilingDate(new Date());
		priority.setFilingNumber("fefef");
		
		priority.setFilingOffice("EM");
		
		Document document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");
		document.setDateCreated(new Date());
		
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
		myMappingFiles.add("dozerMapping.xml");
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		eu.ohim.sp.external.domain.claim.MarkPriority dest =
			    mapper.map(priority, eu.ohim.sp.external.domain.claim.MarkPriority.class);

		assertEquals(dest.getFilingDate(), priority.getFilingDate());
		assertEquals(dest.getFilingNumber(), priority.getFilingNumber());
		assertEquals(dest.getFilingOffice(), priority.getFilingOffice());		

		assertEquals(dest.getPartialIndicator(), priority.getPartialIndicator());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), priority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), priority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), priority.getAttachedDocuments().get(0).getDocumentKind());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), priority.getAttachedDocuments().get(0).getDocument().getLanguage());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), priority.getAttachedDocuments().get(0).getDocument().getName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getDateCreated(), priority.getAttachedDocuments().get(0).getDocument().getDateCreated());

		assertEquals(priority.getPartialGoodsServices().get(0).getClassNumber(), dest.getPartialGoodsServices().get(0).getClassNumber());
		assertEquals(priority.getPartialGoodsServices().get(0).getLanguage(), dest.getPartialGoodsServices().get(0).getLanguage());
		assertEquals(priority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), dest.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText());
	}

	
	@Test
	public void seniorityConverterTest() {
		
		Seniority seniority = new Seniority();
		seniority.setOffice("EM");
		seniority.setFilingDate(new Date());
		seniority.setFilingNumber("application number");
		seniority.setRegistrationDate(new Date());
		seniority.setRegistrationNumber("R21231");
		seniority.setKind(eu.ohim.sp.core.domain.claim.SeniorityKind.INTERNATIONAL_TRADE_MARK);
		seniority.setInternationalTradeMarkCode(eu.ohim.sp.core.domain.claim.InternationalTradeMarkCode.MADRID);
		
		Document document = new Document();
		document.setComment("comment");
		document.setFileName("filename");
		document.setName("name");
		document.setLanguage("en");
		document.setDateReceived(new Date());

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
		myMappingFiles.add("dozerMapping.xml");
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		eu.ohim.sp.external.domain.claim.MarkSeniority dest =
			    mapper.map(seniority, eu.ohim.sp.external.domain.claim.MarkSeniority.class);

		assertEquals(dest.getRegistrationDate(), seniority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), seniority.getRegistrationNumber());
		assertEquals(dest.getKind().value(), seniority.getKind().value());
		assertEquals(dest.getInternationalTradeMarkCode().value(), seniority.getInternationalTradeMarkCode().value());
		
		assertEquals(dest.getOffice(), seniority.getOffice());
		assertEquals(dest.getPartialIndicator(), seniority.getPartialIndicator());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), seniority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), seniority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), seniority.getAttachedDocuments().get(0).getDocument().getLanguage());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), seniority.getAttachedDocuments().get(0).getDocument().getName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), seniority.getAttachedDocuments().get(0).getDocumentKind());

		assertEquals(seniority.getPartialGoodsServices().get(0).getClassNumber(), dest.getPartialGoodsServices().get(0).getClassNumber());
		assertEquals(seniority.getPartialGoodsServices().get(0).getLanguage(), dest.getPartialGoodsServices().get(0).getLanguage());
		assertEquals(seniority.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText(), dest.getPartialGoodsServices().get(0).getClassificationTerms().get(0).getTermText());

	}

	@Test
	public void transformationConverterTest() {
		eu.ohim.sp.core.domain.claim.TransformationPriority priority = new eu.ohim.sp.core.domain.claim.TransformationPriority();
		priority.setCancellationDate(new Date());
		priority.setPriorityDate(new Date());
		priority.setRegistrationDate(new Date());
		priority.setRegistrationNumber("R21312313");

		DozerBeanMapper mapper = new DozerBeanMapper();
		
		IRTransformationPriority dest =
			    mapper.map(priority, IRTransformationPriority.class);
		
		assertEquals(dest.getCancellationDate(), priority.getCancellationDate());
		assertEquals(dest.getPriorityDate(), priority.getPriorityDate());
		assertEquals(dest.getRegistrationDate(), priority.getRegistrationDate());
		assertEquals(dest.getRegistrationNumber(), priority.getRegistrationNumber());
	}

	@Test
	public void exhibitionPriorityConverterTest() {
		
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
		attachedDocument.setDocumentKind(DocumentKind.EXHIBITION_PRIORITY_CERTIFICATE.value());
		
		priority.setAttachedDocuments(new ArrayList<AttachedDocument>());
		priority.getAttachedDocuments().add(attachedDocument);
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		eu.ohim.sp.external.domain.claim.ExhibitionPriority dest =
			    mapper.map(priority, eu.ohim.sp.external.domain.claim.ExhibitionPriority.class);

		assertEquals(dest.getDate(), priority.getDate());
		assertEquals(dest.getExhibition().getCity(), priority.getExhibition().getCity());
		assertEquals(dest.getExhibition().getCountry(), priority.getExhibition().getCountry());
		assertEquals(dest.getDate(), priority.getDate());
        //TODO do we need it?
		//assertEquals(dest.getFirstDisplayDate(), priority.getFirstDisplayDate());
		assertEquals(dest.getExhibition().getName(), priority.getExhibition().getName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getComment(), priority.getAttachedDocuments().get(0).getDocument().getComment());
		assertEquals(dest.getAttachedDocuments().get(0).getDocumentKind(), priority.getAttachedDocuments().get(0).getDocumentKind());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getFileName(), priority.getAttachedDocuments().get(0).getDocument().getFileName());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getLanguage(), priority.getAttachedDocuments().get(0).getDocument().getLanguage());
		assertEquals(dest.getAttachedDocuments().get(0).getDocument().getName(), priority.getAttachedDocuments().get(0).getDocument().getName());

	}


}
