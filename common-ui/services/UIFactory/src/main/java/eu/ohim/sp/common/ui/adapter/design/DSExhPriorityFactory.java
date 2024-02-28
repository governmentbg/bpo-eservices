package eu.ohim.sp.common.ui.adapter.design;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.ExhibitionPriority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

@Component
public class DSExhPriorityFactory implements UIFactory<ExhibitionPriority, DSExhPriorityForm> {

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;
	
	@Autowired
	private DesignFactory designFactory;
	
	@Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
	@Override
	public ExhibitionPriority convertTo(DSExhPriorityForm form) {
		ExhibitionPriority exhPriority = new ExhibitionPriority();
		
		if (form != null) {
			
			exhPriority.setFirstDisplayDate(form.getFirstDate());
			for (StoredFile storedFile : form.getFileWrapper().getStoredFiles()) {
				storedFile.setDocType(AttachmentDocumentType.EXHIBITION_CERTIFICATE);
			}
			List<AttachedDocument> attachedDocuments = listAttachedDocumentFactory.convertTo(form.getFileWrapper()); 
	        exhPriority.setAttachedDocuments(attachedDocuments);
	        
	        List<Exhibition> exhibitionList = configurationServiceDelegator.getExhibitionsByModule("dsefiling");
	        for (Exhibition exhibition : exhibitionList){
	        	if(exhibition.getCode().equals(form.getExhibitionName())){
	        		eu.ohim.sp.core.domain.claim.Exhibition exhibitionClaim = new eu.ohim.sp.core.domain.claim.Exhibition();
	        		exhibitionClaim.setCity(exhibition.getCity());
	        		exhibitionClaim.setName(exhibition.getName());
	        		exhibitionClaim.setCountry(exhibition.getCountrycode());
	                if(exhibition.getExhibitionDate()!=null){
	                	exhibitionClaim.setOpeningDate(exhibition.getExhibitionDate());
	                }
	                
	                exhPriority.setExhibition(exhibitionClaim);
	                
	                break;
	        	}
	        }
	        
	        if (CollectionUtils.isNotEmpty(form.getDesignsLinked())) {
	        	List<Design> designs=new ArrayList<Design>();
	        	for(DesignForm designForm:form.getDesignsLinked()){
	        		Design toAdd=designFactory.convertTo(designForm);
	        		designs.add(toAdd);
	        	}
	        	exhPriority.setDesigns(designs);
			}
		}
		
		return exhPriority;
	}

	@Override
	public DSExhPriorityForm convertFrom(ExhibitionPriority core) {
		DSExhPriorityForm form = new DSExhPriorityForm();
		if (core != null) {
			form.setFirstDate(core.getFirstDisplayDate());
			
			if (core.getAttachedDocuments() != null && !core.getAttachedDocuments().isEmpty()) {
				form.setFileWrapper(listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
			}
			
			if (core.getExhibition() != null) {
				List<Exhibition> exhibitionList = configurationServiceDelegator.getExhibitions();
		        for (Exhibition exhibition : exhibitionList){
		        	if (StringUtils.equals(exhibition.getName(), core.getExhibition().getName())){
		        		form.setExhibitionName(exhibition.getCode());
		        	}
		        }
			}

			form.setDesignSequenceNumber(core.getSequenceNumber());
		}
		return form;
	}

}
