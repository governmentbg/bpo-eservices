/*******************************************************************************
 * * $Id:: ExhibitionPriorityFactory.java 113496 2013-04-22 15:03:04Z karalch    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.claims.xsd.Exhibitions.Exhibition;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.resources.AttachedDocument;

@Component
public class ExhibitionPriorityFactory implements UIFactory<ExhibitionPriority, ExhPriorityForm> {
	
	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;
	
	@Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
	@Value("${sp.module}")
    private String module;
	
    /**
     * Receives as a parameter a UI {@link ExhPriorityForm} object and converts it to a
     * core {@link ExhibitionPriority} object.
     *
     * @param form the UI ExhPriorityForm to convert
     * @return the core ExhibitionPriority object
     */
	@Override
	public ExhibitionPriority convertTo(ExhPriorityForm form) {
        ExhibitionPriority exhPriority = new ExhibitionPriority();

       // exhPriority.setExhibitionName(form.getExhibitionName());
        exhPriority.setFirstDisplayDate(form.getFirstDate());
        
        exhPriority.setAttachedDocuments(new ArrayList<AttachedDocument>());
        exhPriority.getAttachedDocuments().addAll(
        		listAttachedDocumentFactory.convertTo(form.getFileWrapper()));

        //exhPriority.setImported(form.getImported());
        
		eu.ohim.sp.core.domain.claim.Exhibition exhibitionClaim = new eu.ohim.sp.core.domain.claim.Exhibition();
		exhibitionClaim.setName(form.getExhibitionName());
		exhPriority.setExhibition(exhibitionClaim);
	                
        return exhPriority;
	}

	/**
	 * Receives as a parameter a core {@link ExhibitionPriority} object and converts it
	 * to a {@link ExhPriorityForm} object.
	 *
	 * @param core the exhibition priority to convert
	 * @return the exhibition priority form object
	 */
	@Override
	public ExhPriorityForm convertFrom(ExhibitionPriority core) {
		if (core == null) {
			return new ExhPriorityForm();
		}

		ExhPriorityForm form = new ExhPriorityForm();

		if (core.getExhibition() != null) {
			eu.ohim.sp.core.domain.claim.Exhibition exhibitionClaim = core.getExhibition();
			form.setExhibitionName(exhibitionClaim.getName());
		}
		
		form.setFirstDate(core.getFirstDisplayDate());
		
		if (core.getAttachedDocuments() != null && !core.getAttachedDocuments().isEmpty()) {
			form.setFileWrapper(listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
			form.getFileWrapper().setAttachment(true);
		}
		//form.setImported(core.isImported());
		return form;
	}

}
