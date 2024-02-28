/*******************************************************************************
 * * $Id:: TrademarkFactory.java 50771 2012-11-14 15:10:27Z medinju        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/

package eu.ohim.sp.common.ui.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.trademark.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.ApplicantKindForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.resources.Document;


@Component
public class TrademarkFactory implements UIFactory<TradeMark, TMDetailsForm> {
	
	@Autowired
    private GoodsServicesFactory goodsServicesFactory;
	
	@Autowired
    private TMKindFactory tmKindFactory;
	
	@Autowired
    private ApplicantFactory applicantFactory;

	@Autowired
	private RepresentativeFactory representativeFactory;
	
	@Autowired
    private NaturalPersonFactory naturalPersonFactory;
	
	@Autowired
	private DocumentFactory documentFactory;

	@Override
	public TradeMark convertTo(TMDetailsForm form) {

		if(form == null){
			return new TradeMark();
		}
	
		TradeMark coreObject = new TradeMark();
		coreObject.setApplicationNumber(form.getApplicationNumber());
		coreObject.setRegistrationNumber(form.getRegistrationNumber());
		coreObject.setApplicationDate(form.getApplicationDate());
		coreObject.setCurrentStatus(form.getTradeMarkStatus());
		coreObject.setRegistrationDate(form.getRegistrationDate());
		coreObject.setExpirationDate(form.getExpiryDate());
		coreObject.setPublicationDate(form.getPublicationDate());
		coreObject.setOppositionPeriodEnd(form.getOppositionPeriodEndDate());
		coreObject.setOppositionPeriodStart(form.getOppositionPeriodStartDate());
		coreObject.setRegistrationOffice(form.getRegistrationOffice());
		coreObject.setApplicationLanguage(form.getApplicationLanguage());
		coreObject.setGoodsServicesComment(form.getGoodsServicesComment());
		coreObject.setUnpublished(form.getUnpublished());
		if(form.getPriorityDate() != null){
			Priority priority = new Priority();
			priority.setFilingDate(form.getPriorityDate());
			List<Priority> priorities = new ArrayList<Priority>();
			priorities.add(priority);
			coreObject.setPriorities(priorities);
		}
		
		if(form.getApplicants() != null){
			List<Applicant> coreApplicants = new ArrayList<Applicant>();
			for(ApplicantForm applicantForm : form.getApplicants()){
				if(applicantForm.getType() == null){
					applicantForm.setType(ApplicantKindForm.NATURAL_PERSON);
				}
				Applicant applicant = applicantFactory.convertTo(applicantForm);
				if(applicant != null) {
					applicant.setDomicileLocality(applicantForm.getDomicile());
				}
								
				coreApplicants.add(applicant);
			}
			coreObject.setApplicants(coreApplicants);
		}
		
        if (form.getApplicationRepresentation() != null)
        {
        	WordSpecification word = new WordSpecification();
        	word.setWordElements(form.getApplicationRepresentation());
        	coreObject.setWordSpecifications(new ArrayList());
        	coreObject.getWordSpecifications().add(word);       	
            
        }
        
        
        
		
		//transforming the original Goods and Services of the application to the core TradeMark Object
		if (form.getOriginalGS()!=null && !form.getOriginalGS().isEmpty()){
			coreObject.setClassDescriptions(new ArrayList<ClassDescription>());
			for (GoodAndServiceForm item : form.getOriginalGS()) {
				coreObject.getClassDescriptions().add(goodsServicesFactory.convertTo(item));
			}
		}
		
		
		//transforming the attached File to core object
		if((form.getRepresentationAttachment() != null)||((form.getRepresentationAttachment()==null) && (form.getImported()==true)) ){			
			Document document= null;
			if(form.getRepresentationAttachment() != null && form.getRepresentationAttachment().getStoredFiles()!=null && form.getRepresentationAttachment().getStoredFiles().size()>0){
				document = new Document();
				document.setDocumentId(form.getRepresentationAttachment().getStoredFiles().get(0).getDocumentId());
				document.setFileName(form.getRepresentationAttachment().getStoredFiles().get(0).getOriginalFilename());
				document.setName(form.getRepresentationAttachment().getStoredFiles().get(0).getFilename());
				document.setFileFormat(form.getRepresentationAttachment().getStoredFiles().get(0).getContentType());
			}
			if(StringUtils.isNotBlank(form.getImageRepresentationURI())) {
				if(document == null){
					document = new Document();
				}
				document.setUri(form.getImageRepresentationURI());
			}
			if(document != null) {
				ImageSpecification image = new ImageSpecification();
				image.setRepresentation(document);
				List<ImageSpecification> imageSpecificationList = new ArrayList<ImageSpecification>();
				imageSpecificationList.add(image);
				coreObject.setImageSpecifications(imageSpecificationList);
			}
		}

		//transforming the attached multimedia File to core object
		if((form.getMultimediaAttachment() != null)||((form.getMultimediaAttachment()==null) && (form.getImported()==true)) ){
			Document document= null;
			if(form.getMultimediaAttachment() != null && form.getMultimediaAttachment().getStoredFiles()!=null && form.getMultimediaAttachment().getStoredFiles().size()>0){
				document = new Document();
				document.setDocumentId(form.getMultimediaAttachment().getStoredFiles().get(0).getDocumentId());
				document.setFileName(form.getMultimediaAttachment().getStoredFiles().get(0).getOriginalFilename());
			}
			if(StringUtils.isNotBlank(form.getMultimediaURI())) {
				if(document == null){
					document = new Document();
				}
				document.setUri(form.getMultimediaURI());
			}
			if(document != null) {
				MediaRepresentation multimedia = new MediaRepresentation();
				multimedia.setRepresentation(document);
				List<MediaRepresentation> multimediaList = new ArrayList<MediaRepresentation>();
				multimediaList.add(multimedia);
				coreObject.setMediaRepresentations(multimediaList);
			}
		}
		
		//transforming the trademark kind of the application to the core TradeMark Object
		if(form.getTradeMarkKind() != null){
			coreObject.setMarkRightKind(tmKindFactory.convertTo(form.getTradeMarkKind()));
		}
		
		
		if (!(StringUtils.isBlank(form.getTradeMarkType())))
        {
			if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.WORD.toString())){ 
				coreObject.setMarkKind(MarkFeature.WORD);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.CHIMNEY.toString())){ 
				coreObject.setMarkKind(MarkFeature.CHIMNEY);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.COLOUR.toString())){ 
				coreObject.setMarkKind(MarkFeature.COLOUR);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.COMBINED.toString())){ 
				coreObject.setMarkKind(MarkFeature.COMBINED);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.FIGURATIVE.toString())){ 
				coreObject.setMarkKind(MarkFeature.FIGURATIVE);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.HOLOGRAM.toString())){ 
				coreObject.setMarkKind(MarkFeature.HOLOGRAM);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.KENNFADEN.toString())){ 
				coreObject.setMarkKind(MarkFeature.KENNFADEN);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.MARK_IN_SERIES.toString())){ 
				coreObject.setMarkKind(MarkFeature.MARK_IN_SERIES);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.MOTION.toString())){ 
				coreObject.setMarkKind(MarkFeature.MOTION);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.MUNICIPAL.toString())){ 
				coreObject.setMarkKind(MarkFeature.MUNICIPAL);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.NUMBER.toString())){ 
				coreObject.setMarkKind(MarkFeature.NUMBER);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.OLFACTORY.toString())){ 
				coreObject.setMarkKind(MarkFeature.OLFACTORY);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.OTHER.toString())){ 
				coreObject.setMarkKind(MarkFeature.OTHER);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.SOUND.toString())){ 
				coreObject.setMarkKind(MarkFeature.SOUND);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.STYLIZED_CHARACTERS.toString())){ 
				coreObject.setMarkKind(MarkFeature.STYLIZED_CHARACTERS);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.THREE_D.toString())){ 
				coreObject.setMarkKind(MarkFeature.THREE_D);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.POSITION.toString())){
				coreObject.setMarkKind(MarkFeature.POSITION);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.THREE_D_SHAPE.toString())){
				coreObject.setMarkKind(MarkFeature.THREE_D_SHAPE);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.PATTERN.toString())){
				coreObject.setMarkKind(MarkFeature.PATTERN);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.MULTIMEDIA.toString())){
				coreObject.setMarkKind(MarkFeature.MULTIMEDIA);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.UNDEFINED.toString())){
				coreObject.setMarkKind(MarkFeature.UNDEFINED);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.GEOGRAPHIC_INDICATION.toString())){
				coreObject.setMarkKind(MarkFeature.GEOGRAPHIC_INDICATION);
			}
			else if (form.getTradeMarkType().equalsIgnoreCase(MarkFeature.ORIGIN_NAME.toString())){
				coreObject.setMarkKind(MarkFeature.ORIGIN_NAME);
			}
        }else{
        	coreObject.setMarkKind(MarkFeature.UNDEFINED);
        }
				
		return coreObject;
	}

	@Override
	public TMDetailsForm convertFrom(TradeMark core) {
		
		//The UIDomain object we are going to populate from the core object
		TMDetailsForm uiObject = new TMDetailsForm();
				
		//If the core object is null we returned null to the view to display an error message
		if(core == null){
			return null;
		}
		
		uiObject.setApplicationDate(core.getApplicationDate());
		uiObject.setApplicationNumber(core.getApplicationNumber());
		uiObject.setApplicationStatus(core.getCurrentStatus()); //Its not applicable for renewal, we need to find out how to fill it for revocation/invalidity/etc.
		uiObject.setRegistrationNumber(core.getRegistrationNumber());
		uiObject.setRegistrationDate(core.getRegistrationDate());
		uiObject.setPublicationDate(core.getPublicationDate());
		uiObject.setExpiryDate(core.getExpirationDate());
		uiObject.setRegistrationOffice(core.getRegistrationOffice());
		uiObject.setApplicationLanguage(core.getApplicationLanguage());
		uiObject.setGoodsServicesComment(core.getGoodsServicesComment());
		uiObject.setUnpublished(core.getUnpublished());
		if (core.getPriorities()!=null && core.getPriorities().size()>0){
			uiObject.setPriorityDate(core.getPriorities().get(0).getFilingDate());
		}
			
		
		//Recovering the list of applicants and transforming in UIDomain objects
		List<ApplicantForm> uiApplicants = new ArrayList<ApplicantForm>();
		List<Applicant> coreApplicants = core.getApplicants();
		
		if(!CollectionUtils.isEmpty(coreApplicants)){
			for(Applicant applicant : coreApplicants){
				ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
				if (applicantForm!=null){
					applicantForm.setInternalApplicantId(applicantForm.getId());
					if(applicantForm.getAddress() != null){
						StringBuilder infoString = new StringBuilder();
						infoString.append(applicantForm.getAddress().getStreet() == null? "" : applicantForm.getAddress().getStreet().trim());
						infoString.append(applicantForm.getAddress().getHouseNumber() == null? "" : ", "+applicantForm.getAddress().getHouseNumber().trim());
						infoString.append(applicantForm.getAddress().getPostalCode() == null? "" : ".\n"+applicantForm.getAddress().getPostalCode().trim());
						infoString.append(applicantForm.getAddress().getCity() == null? "" : ".\n"+applicantForm.getAddress().getCity().trim());
						infoString.append(applicantForm.getAddress().getCountry() == null? "" : " - "+applicantForm.getAddress().getCountry().trim());
						if(infoString.length() > 0) {
							infoString.append(".");
						} else {
							infoString.append(applicant.getDomicileLocality() == null? "" : applicant.getDomicileLocality());
						}
						
						applicantForm.setDomicile(infoString.toString());
					}
					uiApplicants.add(applicantForm);
				}
			}
		}
		
		uiObject.setApplicants(uiApplicants);
		
		if(uiApplicants != null && !uiApplicants.isEmpty()){
			ApplicantForm applicantForm = uiApplicants.get(0);
			if(applicantForm != null){
				uiObject.setApplicant(applicantForm.getName());
			}
			/*ApplicantKindForm kind = applicantForm.getType();
				if(kind != null){
					switch(kind){
					case NATURAL_PERSON :
						uiObject.setApplicant(applicantForm.getName()+" "+applicantForm.getName().getLastName());
						break;
					case LEGAL_ENTITY :
						uiObject.setApplicant(applicantForm.getName().getOrganizationName());
						break;
					default :
						uiObject.setApplicant("");
					}
				}
				else{
					uiObject.setApplicant(applicant.getName().getFirstName()+" "+applicant.getName().getLastName());
				}*/
		}

		if(!CollectionUtils.isEmpty(core.getRepresentatives())) {
			uiObject.setRepresentatives(new ArrayList<RepresentativeForm>());
			for (Representative representative : core.getRepresentatives()) {
				uiObject.getRepresentatives().add(representativeFactory.convertFrom(representative));
			}
		}
		
		if (core.getMarkKind() != null)
        {
            uiObject.setTradeMarkType((core.getMarkKind().toString()));
        }
		
        if (core.getWordSpecifications() != null && !core.getWordSpecifications().isEmpty())
        {
            uiObject.setApplicationRepresentation(core.getWordSpecifications().get(0).getWordElements());
            uiObject.setTradeMarkName(core.getWordSpecifications().get(0).getWordElements());
        }
        
        
        
        Set<GoodAndServiceForm> uiGAndS = new TreeSet<GoodAndServiceForm>();
        Set<GoodAndServiceForm> originalGAndS = new TreeSet<GoodAndServiceForm>();
        
		if(core.getClassDescriptions() != null){
			
			for (ClassDescription classes : core.getClassDescriptions()) {
				     
	            GoodAndServiceForm gsForm = goodsServicesFactory.convertFrom(classes);
	            
	            if (gsForm != null) {
	            	try{
		            	GoodAndServiceForm originalGS = gsForm.clone();
		            	originalGAndS.add(originalGS);
		            }
		            catch (CloneNotSupportedException e) {
		                throw new SPException("Failed to find duplicate object", e, "error.genericError");
		        	}
	            	uiGAndS.add(gsForm);
	            }
	        }
		}
		uiObject.setGoodsAndServices(uiGAndS);
		uiObject.setOriginalGS(originalGAndS);
		uiObject.setTradeMarkKind(tmKindFactory.convertFrom(core.getMarkRightKind()));
		
		uiObject.setOppositionPeriodEndDate(core.getOppositionPeriodEnd());
		uiObject.setOppositionPeriodStartDate(core.getOppositionPeriodStart());

		uiObject.setImageRepresentationURI( (core.getImageSpecifications()!=null)?
				((core.getImageSpecifications().size()>0)?
						((core.getImageSpecifications().get(0).getRepresentation()!=null)?core.getImageSpecifications().get(0).getRepresentation().getUri()
						:"")
				:"")
			:""
		);	
		
		FileWrapper fileWrapper = new FileWrapper();
		fileWrapper.setStoredFiles(new ArrayList<StoredFile>());
		StoredFile storedFile = new StoredFile();
		if(core.getImageSpecifications()!=null && core.getImageSpecifications().size()>0 && core.getImageSpecifications().get(0).getRepresentation()!=null &&
				core.getImageSpecifications().get(0).getRepresentation().getDocumentId() != null){
			storedFile.setDocumentId(core.getImageSpecifications().get(0).getRepresentation().getDocumentId());
			storedFile.setOriginalFilename(core.getImageSpecifications().get(0).getRepresentation().getFileName());
			storedFile.setFilename(core.getImageSpecifications().get(0).getRepresentation().getName());
			storedFile.setContentType(core.getImageSpecifications().get(0).getRepresentation().getFileFormat());
			fileWrapper.getStoredFiles().add(storedFile);
			fileWrapper.setAttachment(true);
		}		
		uiObject.setRepresentationAttachment(fileWrapper);

		FileWrapper fileWrapperM = new FileWrapper();
		fileWrapperM.setStoredFiles(new ArrayList<StoredFile>());
		StoredFile storedFileM = new StoredFile();
		if(core.getMediaRepresentations()!=null && core.getMediaRepresentations().size()>0 && core.getMediaRepresentations().get(0).getRepresentation()!=null
			&& core.getMediaRepresentations().get(0).getRepresentation().getDocumentId() != null){
			storedFileM.setDocumentId(core.getMediaRepresentations().get(0).getRepresentation().getDocumentId());
			fileWrapperM.getStoredFiles().add(storedFileM);
			fileWrapperM.setAttachment(true);
		}
		uiObject.setMultimediaAttachment(fileWrapperM);
		

		uiObject.setTradeMarkStatus(core.getCurrentStatus());
		
		return uiObject;
	}
/*
	public TradeMark convertToLimitedTradeMark(TMDetailsForm form) {
		TradeMark limitedTradeMark = new TradeMark();
		limitedTradeMark = convertTo(form);
		limitedTradeMark.setLimitedGoodsServices(form.getGoodsAndServices());
		
	}*/
}
