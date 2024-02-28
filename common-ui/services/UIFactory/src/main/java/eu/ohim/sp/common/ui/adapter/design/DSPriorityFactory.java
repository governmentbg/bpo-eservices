package eu.ohim.sp.common.ui.adapter.design;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.resources.AttachmentDocumentType;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.Priority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author serrajo
 */
@Component
public class DSPriorityFactory implements UIFactory<Priority, DSPriorityForm> {

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;
	
	@Autowired
	private DesignFactory designFactory;
	
	@Override
	public Priority convertTo(DSPriorityForm form) {
		Priority priority = new Priority();
		
		if (form != null) {

            if (StringUtils.isEmpty(form.getType()))
            {
                priority.setInternal(null);
            }
            else
            {
                if (form.getType().equalsIgnoreCase("Internal priority"))
                {
                    priority.setInternal(true);
                }
                else
                {
                    priority.setInternal(false);
                }
            }

			priority.setFilingDate(form.getDate());
			priority.setFilingNumber(form.getNumber());
			priority.setFilingOffice(form.getCountry());
			priority.setApplicantName(form.getApplicantName());
			
			for (StoredFile storedFile : form.getFileWrapperCopy().getStoredFiles()) {
				storedFile.setDocType(AttachmentDocumentType.PRIORITY_CERTIFICATE);
			}
			
			for (StoredFile storedFile : form.getFileWrapperTranslation().getStoredFiles()) {
				storedFile.setDocType(AttachmentDocumentType.PRIORITY_TRANSLATION);
			}
			
			List<AttachedDocument> attachedDocuments = new ArrayList<AttachedDocument>();
			attachedDocuments.addAll(listAttachedDocumentFactory.convertTo(form.getFileWrapperCopy()));
			attachedDocuments.addAll(listAttachedDocumentFactory.convertTo(form.getFileWrapperTranslation()));
			priority.setAttachedDocuments(attachedDocuments);
			
			if (CollectionUtils.isNotEmpty(form.getDesignsLinked())) {
				List<Design> designs = new ArrayList<Design>();
	        	for (DesignForm designForm : form.getDesignsLinked()) {
	        		designs.add(designFactory.convertTo(designForm));
	        	}
	        	priority.setDesigns(designs);
			}
		}
		
		return priority;
	}

	@Override
	public DSPriorityForm convertFrom(Priority core) {
		DSPriorityForm form = new DSPriorityForm();
		
		if (core != null) {
            if(Boolean.TRUE.equals(core.isInternal())) {
                form.setType("Internal priority");
            }
            else if(Boolean.FALSE.equals(core.isInternal())) {
                form.setType("Paris convention");
            }

			form.setDate(core.getFilingDate());
			form.setNumber(core.getFilingNumber());
			form.setCountry(core.getFilingOffice());
			form.setApplicantName(core.getApplicantName());
			
			if (CollectionUtils.isNotEmpty(core.getAttachedDocuments())) {
				FileWrapper fileWrapperAux = listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments());
				for (StoredFile storedFile : fileWrapperAux.getStoredFiles()) {
					switch (storedFile.getDocType()) {
						case PRIORITY_CERTIFICATE:
							form.getFileWrapperCopy().getStoredFiles().add(storedFile);
							break;
						case PRIORITY_TRANSLATION:
							form.getFileWrapperTranslation().getStoredFiles().add(storedFile);
							break;
						default:
							break;
					}
				}
			}

			if(form.getFileWrapperCopy().getStoredFiles() == null || form.getFileWrapperCopy().getStoredFiles().isEmpty()){
				form.getFileWrapperCopy().setAttachment(false);
			} else {
				form.getFileWrapperCopy().setAttachment(true);
			}

			if(form.getFileWrapperTranslation().getStoredFiles() == null || form.getFileWrapperTranslation().getStoredFiles().isEmpty()){
				form.getFileWrapperTranslation().setAttachment(false);
			} else {
				form.getFileWrapperTranslation().setAttachment(true);
			}
			
			form.setDesignSequenceNumber(core.getSequenceNumber());
		}
		
		return form;
	}

}
