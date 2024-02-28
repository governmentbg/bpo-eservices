/*******************************************************************************
 * * $Id:: PaymentFactory.java 113496 2013-04-22 15:03:04Z karalc#$
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.application.EntitlementForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.payment.PaymentForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author soriama
 * 
 */
@Component
public class AttachmentsFactory {

    /**
     * 
     * @param attachments
     * @param priorities
     */
    public void addPrioritiesAttachments(List<String> attachments, List<DSPriorityForm> listForm) {
    	if (CollectionUtils.isNotEmpty(listForm)) {
   			for (DSPriorityForm dsPriority : listForm) {
   				addAttachaments(attachments, dsPriority.getFileWrapperCopy());
   				addAttachaments(attachments, dsPriority.getFileWrapperTranslation());
   			}	
   		}
    }
    
    /**
     * 
     * @param attachments
     * @param exhibitions
     */
    public void addExhibitionsAttachments(List<String> attachments, List<DSExhPriorityForm> listForm) {
   		if (CollectionUtils.isNotEmpty(listForm)) {
   			for (DSExhPriorityForm dsExhPriority : listForm) {
   				addAttachaments(attachments, dsExhPriority.getFileWrapper());
    		}	
    	}
    }
    
    /**
     * 
     * @param attachments
     */
    public void addRepresentativesAttachaments(List<String> attachments, List<RepresentativeForm> listForm) {
   		if (CollectionUtils.isNotEmpty(listForm)) {
   			for (RepresentativeForm representative : listForm) {
   				addAttachaments(attachments, representative.getRepresentativeAttachment());
   			}    		
   		}	
    }
    
    /**
     * 
     * @param attachments
     */
    public void addOtherAttachments(List<String> attachments, FileWrapper  fileWrapper ) {
   		addAttachaments(attachments, fileWrapper);
	}
    
    /**
     * 
     * @param attachments
     */
    public void addEntitlementAttachments(List<String> attachments, EntitlementForm form) {
    
    		addAttachaments(attachments, form.getDesignOfficiaryFiles());
    		addAttachaments(attachments, form.getDesignNotOfficiaryFiles());     
	}
    
    /**
     * 
     * @param attachments
     */
    public void addPaymentAttachments(List<String> attachments, PaymentForm form) {
    	if (form != null) {
    		addAttachaments(attachments, form.getPayLaterAttachment());
    	} 
	}

    /**
     * 
     * @param attachments
     * @param fileWrapper
     */
    public void addAttachaments(List<String> attachments, FileWrapper fileWrapper) {
    	if (fileWrapper != null) {
    		for (StoredFile storedFile : fileWrapper.getStoredFiles()) {
    			attachments.add(storedFile.getOriginalFilename());		        	
    		}
    	}
    }
}
