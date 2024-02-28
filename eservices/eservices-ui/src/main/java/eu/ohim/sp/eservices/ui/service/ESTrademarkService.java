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
import java.util.Map;

import eu.ohim.sp.common.ui.adapter.AppealFactory;
import eu.ohim.sp.common.ui.adapter.LicenceFactory;
import eu.ohim.sp.common.ui.adapter.design.DesignFactory;
import eu.ohim.sp.common.ui.adapter.design.ESDesignFactory;
import eu.ohim.sp.common.ui.adapter.patent.ESPatentFactory;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.id.Id;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.register.IPOAutocompleteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.LimitedTrademarkFactory;
import eu.ohim.sp.common.ui.adapter.TrademarkFactory;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.adapter.opposition.OppositionGroundFactory;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.service.ConfigurationServiceDelegator;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.domain.opposition.LegalActVersion;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;


@Service
public class ESTrademarkService implements ESTrademarkServiceInterface{

	@Autowired
	private TradeMarkSearchService tradeMarkService;

	@Autowired
	private ESApplicationServiceInterface applicationService;

	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	@Autowired
	private TrademarkFactory tmFactory;

	@Autowired
	private ESDesignFactory dsFactory;

	@Autowired
	private LimitedTrademarkFactory tmLimitedFactory;

	@Autowired
    private LegalActVersionFactory legalActVersionFactory;

	@Autowired
    private OppositionGroundFactory oppositionGroundFactory;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Value("${sp.office}")
    private String office;

	@Autowired
	private RulesService businessRulesService;

	@Autowired
	private LicenceFactory licenceFactory;

	@Autowired
	private AppealFactory appealFactory;

	@Autowired
	private ESPatentFactory ptFactory;

	@Autowired
	private IPOAutocompleteSearchService ipoAutocompleteSearchService;

	@Override
	public TMDetailsForm getTradeMark(String tradeMarkId) {
		return this.getTradeMark(tradeMarkId, office);
	}

	public TMDetailsForm getTradeMark(String tradeMarkId, String officeImport) {


		TMDetailsForm tmForm = null;
		try{
			TradeMark tm = tradeMarkService.getTradeMark(officeImport, tradeMarkId);
			tmForm = tmFactory.convertFrom(tm);

		}
		catch (Exception e) {
			throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
		}

		return tmForm;
	}


	@Override
	public String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults, boolean previousCTM, String flowModeId) {
		if(office.equals(this.office)){
			return ipoAutocompleteSearchService.ipoAutocomplete(text, numberOfResults, flowModeId);
		}
		if (previousCTM) {
			return tradeMarkService.getTradeMarkAutocomplete(office, text, numberOfResults);
		} else {
			return tradeMarkService.getForeignTradeMarkAutocomplete(office, text, numberOfResults);
		}
	}

	/**
	 * Configuration of blocking messages
	 * @param rulesInformation
	 * @param mandatoryBlocking
	 */
	private void configureBlockingMessages(RulesInformation rulesInformation, Boolean mandatoryBlocking) {
		if(mandatoryBlocking) {
			rulesInformation.getCustomObjects().put("renewableBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("opposableBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("renewalStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("transferStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("licenceStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("appealStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("remStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("securityStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("bankruptcyStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("surrenderStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("withdrawalStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("oppositionStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("oppositionPubDateOlderBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("oppositionPubDateFutureBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("invalidityStatusBlockingMessage", mandatoryBlocking);
			rulesInformation.getCustomObjects().put("revocationStatusBlockingMessage", mandatoryBlocking);
		} else {
			String blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_RENEWABLE_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("renewableBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_OPPOSABLE_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("opposableBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_RENEWAL_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("renewalStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_TRANSFER_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("transferStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_LICENCE_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("licenceStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_APPEAL_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("appealStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_REM_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("remStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_SECURITY_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("securityStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_BANKRUPTCY_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("bankruptcyStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_SURRENDER_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("surrenderStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_WITHDRAWAL_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("withdrawalStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_OPPOSITION_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("oppositionStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_OPPOSITION_PUBDATE_OLDERTHAN_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("oppositionPubDateOlderBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_OPPOSITION_PUBDATE_FUTURE_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("oppositionPubDateFutureBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_INVALIDITY_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("invalidityStatusBlockingMessage", Boolean.valueOf(blocking));
			blocking = configurationServiceDelegator.getValue(ConfigurationServiceDelegator.TM_REVOCATION_STATUS_MSG_BLOCKING, ConfigurationServiceDelegator.GENERAL_COMPONENT);
			rulesInformation.getCustomObjects().put("revocationStatusBlockingMessage", Boolean.valueOf(blocking));
        }
	}

	@Override
	public ErrorList validateTradeMark(String module, TMDetailsForm tradeMark,
			RulesInformation rulesInformation, Errors errors, String flowModeId) {

		//select rules
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rulesInformation.getCustomObjects().put("sections", sections);
    	rulesInformation.getCustomObjects().put("eservice", flowModeId);
    	rulesInformation.getCustomObjects().put("imported", Boolean.valueOf(tradeMark.getImported()));
    	//blocking on import (no db check), db check otherwise
    	if(!tradeMark.getCheckBdBlocking()) {
    		configureBlockingMessages(rulesInformation, Boolean.TRUE);
    		rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.TRUE);
    	} else {
    		configureBlockingMessages(rulesInformation, Boolean.FALSE);
    		rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.FALSE);
    	}
		//convert to core domain
		LimitedTradeMark tm = tmLimitedFactory.convertTo((TMDetailsForm)tradeMark);
		//validate
		ErrorList errs = tradeMarkService.validateTradeMark(EServicesConstants.TM_SV, tm, rulesInformation);
		if(errs!=null) {
			errs.getErrorList();
		}
		return errs;
	}


	@Override
	public List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filter(String applicationType, AbstractImportableForm form, GroundCategoryKindLegalAct category, List<String> codesFiltered) {
		//Variable declaration
		List<Object> objects = new ArrayList<Object>();
		List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> result = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
		RulesInformation rulesInformation = new RulesInformation();

		//transformation of the objects
		List<eu.ohim.sp.core.domain.opposition.LegalActVersion> filteredCore = new ArrayList<eu.ohim.sp.core.domain.opposition.LegalActVersion>();
    	List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filtered = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();

    	this.getCoreObjects(applicationType, codesFiltered, filteredCore, filtered);

		Id core = null;
		if (form instanceof TMDetailsForm) {
			core = tmFactory.convertTo((TMDetailsForm) form);
		} else if (form instanceof ESDesignDetailsForm) {
            DesignApplication designApplication = dsFactory.convertTo((ESDesignDetailsForm) form);
            if (designApplication != null && designApplication.getDesignDetails() != null
                    && !designApplication.getDesignDetails().isEmpty()) {
                core = designApplication.getDesignDetails().get(0);
            }
		} else if (form instanceof ESPatentDetailsForm) {
			core = ptFactory.convertTo((ESPatentDetailsForm) form);
		}

		// Prepares the objects to insert in the session
		rulesInformation.getCustomObjects().put("type", applicationType);
		rulesInformation.getCustomObjects().put("category", category.toString());
		objects.add(rulesInformation);
		objects.add(filteredCore);
     	objects.add(core);

     	Map<String, Object> resultMap =  businessRulesService.calculate(EServicesConstants.TM_SV, "legal_act_list", objects);
     	
     	List<Object> objectList = new ArrayList<Object>(resultMap.values()); 
     	List listLA = (List)objectList.get(0);
     	
     	for(Object object : listLA){
			if(object instanceof LegalActVersion){
				eu.ohim.sp.common.ui.form.opposition.LegalActVersion lA = legalActVersionFactory.convertFrom((LegalActVersion)object);
				lA.setGroundCategory(category);
				result.add(lA);
			}
		}
     	
        return result;
		
	}
	
	public void getCoreObjects(String applicationType, List<String> codesFiltered, List<LegalActVersion> filteredCore, List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filteredCommon){
		
		List<LegalActVersions.LegalActVersion> legalActVersions = configurationServiceDelegator.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , LegalActVersions.class).getLegalActVersion();
        
        for (LegalActVersions.LegalActVersion itemLa : legalActVersions) {
        	
        	if(codesFiltered.contains(itemLa.getCode())){
        		if (itemLa.isEnabled()){
        			//TODO filtered must be List of LegalActVersion core object.
        			eu.ohim.sp.common.ui.form.opposition.LegalActVersion legalActAvaible = legalActVersionFactory.convertToLegalActUI(itemLa, applicationType); 
        			if (!filteredCommon.contains(legalActAvaible)){ 
        				filteredCommon.add(legalActAvaible);	//end filtered must be List of LegalActVersion core object.	
        			}	
        		}  
        	}
        }
        
        for (eu.ohim.sp.common.ui.form.opposition.LegalActVersion lA : filteredCommon) {
        	eu.ohim.sp.core.domain.opposition.LegalActVersion lACore = legalActVersionFactory.convertTo(lA);
        	filteredCore.add(lACore);
        }

	}
	
	@Override
	public ErrorList validateOpposition(String module, OppositionBasisForm oppositionBasisForm, 
			RulesInformation rulesInformation, Errors errors, String flowModeId) {

		//select rules
    	Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
    	rulesInformation.getCustomObjects().put("sections", sections);    	  	
    	
		//convert to core domain
    	OppositionGround oppositionGround = oppositionGroundFactory.convertTo((OppositionBasisForm)oppositionBasisForm);
    	Boolean imported = false;
    	if (oppositionBasisForm != null && oppositionBasisForm.getRelativeGrounds()!=null && oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf()!=null &&
    			oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()!=null && oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails()!=null){
    		imported = oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getEarlierTradeMarkDetails().getImported();
    	}
    	rulesInformation.getCustomObjects().put("imported", imported);
    	rulesInformation.getCustomObjects().put("eservice", flowModeId);
    	//blocking on import (no db check), db check otherwise
    	if(oppositionBasisForm != null && !oppositionBasisForm.getCheckBdBlocking()) {
    		configureBlockingMessages(rulesInformation, Boolean.TRUE);
    		rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.TRUE);
    	} else {
    		configureBlockingMessages(rulesInformation, Boolean.FALSE);
    		rulesInformation.getCustomObjects().put(EServicesConstants.IMPORTACT, Boolean.FALSE);
    	}   
    	
    	   	
    	ErrorList errs = new ErrorList();    	
    	//validate
    	if(oppositionGround != null){
        	errs = tradeMarkService.validateOpposition(EServicesConstants.TM_SV, oppositionGround, rulesInformation);
    	}
	
		return errs;
	}

	@Override
	public ErrorList validateLicence(String module, LicenceForm licenceForm,
									 RulesInformation rulesInformation, Errors errors, String flowModeId) {
		Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		rulesInformation.getCustomObjects().put("sections", sections);

		Licence licence = licenceFactory.convertTo(licenceForm);
		rulesInformation.getCustomObjects().put("eservice", flowModeId);
		ErrorList errs = tradeMarkService.validateLicence(EServicesConstants.TM_SV, licence, rulesInformation);
		return errs;
	}

	@Override
	public ErrorList validateAppeal(String module, AppealForm appealForm,
									RulesInformation rulesInformation, Errors errors, String flowModeId) {
		Sections sections = sectionViewConfiguration.getViewConfiguration().get(flowModeId);
		rulesInformation.getCustomObjects().put("sections", sections);

		Appeal appeal = appealFactory.convertTo(appealForm);
		rulesInformation.getCustomObjects().put("eservice", flowModeId);
		ErrorList errs = new ErrorList();
		errs = tradeMarkService.validateAppeal(EServicesConstants.TM_SV, appeal, rulesInformation);
		return errs;
	}

}
