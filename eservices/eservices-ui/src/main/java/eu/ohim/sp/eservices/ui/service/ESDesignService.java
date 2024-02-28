/*******************************************************************************
 * * $Id:: ESTrademarkService.java 50771 2012-11-14 15:10:27Z medinju        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.service;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.adapter.DocumentFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.register.IPOAutocompleteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.design.Design;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.design.DesignView;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.DesignSearchService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESDesignServiceInterface;

@Service
public class ESDesignService implements ESDesignServiceInterface{
	
	@Autowired
	private DesignSearchService  designService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;	
	
	@Autowired
	private ESDesignFactory dsFactory;
	
    @Autowired
    private ContactDetailsFactory contactDetailsFactory;

	@Autowired
	private DocumentFactory documentFactory;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private IPOAutocompleteSearchService ipoAutocompleteSearchService;
		
	@Value("${sp.office}")
    private String office;
	
	@Override
	public ESDesignDetailsListForm getDesignApplication(String designId) {
		return getDesignApplication(office,designId, null, false);
	}

	@Override
	public ESDesignDetailsListForm getDesignApplicationByApplicationId(String applicationId) {
		return getDesignApplication(office, null, applicationId, false);
	}
	
	@Override
	public ESDesignDetailsListForm getDesignApplication(String office, String designId, String applicationId, boolean extraImport) {
		ESDesignDetailsListForm form = null;
		try{
			DesignApplication ds = designService.getDesignApplication(office, designId, applicationId, Boolean.FALSE);
			List<ESDesignDetailsForm> dsForms = dsFactory.convertListFromSingle(ds);
			form = new ESDesignDetailsListForm(dsForms);
		}
		catch (Exception e) {
			throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
		}

		return form;
	}
	
	@Override
	public String getDesignAutocomplete(String office, String text, Integer numberOfResults, String flowModeId) {
		if(office.equals(this.office)){
			return ipoAutocompleteSearchService.ipoAutocomplete(text, numberOfResults, flowModeId);
		} else {
			return designService.getDesignAutocomplete(office, text, numberOfResults);
		}
	}
	
	/**
	 * Configuration of blocking messages
	 * @param rulesInformation
	 * @param mandatoryBlocking
	 */
	private void configureBlockingMessages(RulesInformation rulesInformation, Boolean mandatoryBlocking) {
		
		if(mandatoryBlocking) {
			rulesInformation.getCustomObjects().put("renewableDesignBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("renewableDesign25YearsBlockingMessage", mandatoryBlocking);
		} else {		
			String blocking = configurationServiceDelegator.getValue(configurationServiceDelegator.DS_RENEWABLE_MSG_BLOCKING, configurationServiceDelegator.GENERAL_COMPONENT);       		
			rulesInformation.getCustomObjects().put("renewableDesignBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(configurationServiceDelegator.DS_RENEWABLE_25_YEARS_MSG_BLOCKING, configurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("renewableDesign25YearsBlockingMessage", Boolean.valueOf(blocking));
		} 
	}
	
	@Override
	public ErrorList validateDesign(String module, ESDesignDetailsForm design,
			RulesInformation rulesInformation, Errors errors, String flowModeId) {

		//select rules
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rulesInformation.getCustomObjects().put("sections", sections);
    	rulesInformation.getCustomObjects().put("eservice", flowModeId);
    	rulesInformation.getCustomObjects().put("imported", Boolean.valueOf(design.getImported()));
    	//blocking on import (no db check), db check otherwise
    	if(!design.getCheckBdBlocking()) {
    		configureBlockingMessages(rulesInformation, Boolean.TRUE);
    		rulesInformation.getCustomObjects().put("importAction", Boolean.TRUE);
    	} else {
    		configureBlockingMessages(rulesInformation, Boolean.FALSE);
    		rulesInformation.getCustomObjects().put("importAction", Boolean.FALSE);
    	}    	    	
		//convert to core domain
    	DesignApplication ds = dsFactory.convertTo((ESDesignDetailsForm)design);			
		//validate
		ErrorList errs = designService.validateDesignApplication("eservices", ds, rulesInformation);
    	
   		if(errs!=null) {
			errs.getErrorList();
		}		
		return errs;
	}
	
	
    @Override
	public ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId){ 
    	putSections(rules, flowModeId);

		rules.getCustomObjects().put("imported", Boolean.valueOf(applicationCAForm.getImported()));

		ContactDetails contactDetails = contactDetailsFactory.convertTo(applicationCAForm.getCorrespondenceAddressForm());    	   
    	return designService.validateApplicationCA(module, contactDetails, rules);
    }

    @Override
	public ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors, String flowModeId) {
		putSections(rules, flowModeId);

		Document document = documentFactory.convertTo(storedFile);

		return documentService.validateDocument(module, document, rules);
	}
    
    /**
     * 
     * @param rules
     * @param flowModeId
     */
    private void putSections(RulesInformation rules, String flowModeId) {
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rules.getCustomObjects().put("sections", sections);
    }    
}
