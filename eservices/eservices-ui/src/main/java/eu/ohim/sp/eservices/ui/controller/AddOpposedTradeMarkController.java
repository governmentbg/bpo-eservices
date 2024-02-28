package eu.ohim.sp.eservices.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.common.ui.adapter.TrademarkFactory;
import eu.ohim.sp.common.ui.adapter.opposition.LegalActVersionFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OpposedTradeMarkForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds;
import eu.ohim.sp.core.configuration.domain.legalActVersion.xsd.LegalActVersions;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddOpposedTradeMarkController extends AddAbstractController{
	
    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;
    
    @Autowired
    private TrademarkFactory trademarkFactory;
    
    @Autowired
    private LegalActVersionFactory legalActVersionFactory;
    
    @Autowired
    private ConfigurationService configurationService;
    
    @Value("${general.component}")
    private String generalComponent;
    
	/* --------- APPLICANTS ---------- */
    /* ------------------------------- */

    /**
     * It returns a new Applicant obj if no parameters are passed, so a new
     * Applicant to be created by the user. If a parameter is passed by the
     * request then the object to be returned will be populated with the values
     * stored in the session to edit its details.
     *
     * @param id the id of the edited object, or a new object if it is null
     * @return a modelAndView object with the object
     * @throws Exception if it fails to load the applicant
     */
    @RequestMapping(value = "addOpposedTradeMark", method = RequestMethod.GET)
    public ModelAndView formBackingOpposedTradeMark(@RequestParam(required = false, value = "id") String id)
    {
        ModelAndView model = innerFormBackingObject(id, flowBean, new AddParameters(OpposedTradeMarkForm.class, "opposedTradeMarkForm",
                                                                                    "opposition/opposed_trademark_section",
                                                                                    "opposition/opposed_trademark_section", getMaxNumber()));
        return model;
    }

    /**
     * Adds or edits on the collection stored in the session
     *
     * @param command object that contains the Applicant information.
     * @param result  manage the validation results of the form object
     * @return Applicant object view with the new applicant added
     */
    @RequestMapping(value = "addOpposedTradeMark", method = RequestMethod.POST)
    public ModelAndView onSubmitOpposedTradeMark(@ModelAttribute("opposedTradeMarkForm") OpposedTradeMarkForm command,
    										BindingResult result,
                                            @RequestParam(value = "ignoreMatches", required = false) Boolean ignoreMatches, 
                                            @RequestParam(value = "applicationType", required = true) String applicationType)
    {
        AddParameters addParameters = new AddParameters(OpposedTradeMarkForm.class, "opposedTradeMarkForm",
        								"opposition/tm_opposed_card_list","opposition/opposed_trademark_section", getMaxNumber());
        TradeMark tm = trademarkFactory.convertTo(command);
        
        refreshAvaibleLegalActVersions(tm, applicationType);
        
        return addFormCheckMatches(command, result, addParameters, ignoreMatches);
    }
    
    /**
     * Entry point to get an applicant form for edit.
     * This method figures out the type of the applicant and uses another method that can handle
     * that type of applicant.
     * If the type is not found or there is no such applicant with that id, it returns null.
     * @param id the id of the applicant
     * @return the view containing the applicant form to edit
     */
    @RequestMapping(value = "getOpposedTradeMarkForEdit", method = RequestMethod.GET)
    public ModelAndView getOpposedTM(@RequestParam(required = true, value = "id") String id)
    {
        return formBackingOpposedTradeMark(id);
    }

    public void refreshAvaibleLegalActVersions(TradeMark tm, String applicationType){
    	
    	flowBean.setAvaibleLegalActVersions(null);
    	List<eu.ohim.sp.core.domain.opposition.LegalActVersion> filteredCore = new ArrayList<eu.ohim.sp.core.domain.opposition.LegalActVersion>();
    	List<eu.ohim.sp.common.ui.form.opposition.LegalActVersion> filtered = new ArrayList<eu.ohim.sp.common.ui.form.opposition.LegalActVersion>();
    	List<String> codesFiltered = new ArrayList<String>();
    	
 
        
        
        List<LegalActVersions.LegalActVersion> legalActVersions = configurationService.getObject(ConfigurationServiceDelegatorInterface.LEGAL_ACT_VERSIONS_PARAM , generalComponent, LegalActVersions.class).getLegalActVersion();
        
        
        if (!applicationType.equals("tm-revocation")){
        	List<RelativeGrounds.RelativeGround> relativeGrounds = configurationService.getObject(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM , generalComponent, RelativeGrounds.class).getRelativeGround();
            List<AbsoluteGrounds.AbsoluteGround> absoluteGrounds = configurationService.getObject(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM , generalComponent, AbsoluteGrounds.class).getAbsoluteGround();
	        for (AbsoluteGrounds.AbsoluteGround itemAb : absoluteGrounds) {
	            if (itemAb.getApplicationType().equals(applicationType)){
	            	if(!codesFiltered.contains(itemAb.getLegalActVersion())){
	            		if (itemAb.isEnabled()){
	            			codesFiltered.add(itemAb.getLegalActVersion());
	            		}
	            	}  
	            }
	        }
	        for (RelativeGrounds.RelativeGround itemRe : relativeGrounds) {
	        	if (itemRe.getApplicationType().equals(applicationType)){
	        		if(!codesFiltered.contains(itemRe.getLegalActVersion())){
	        			if (itemRe.isEnabled()){
	        				codesFiltered.add(itemRe.getLegalActVersion());
	        			}
	        		}  
	        	}
	        }
        }
        else{
        	List<RevocationGrounds.RevocationGround> revocationGrounds = configurationService.getObject(ConfigurationServiceDelegatorInterface.REVOCATION_GROUNDS_PARAM , generalComponent, RevocationGrounds.class).getRevocationGround();
	        for (RevocationGrounds.RevocationGround itemRev : revocationGrounds) {
	            if(!codesFiltered.contains(itemRev.getLegalActVersion())){
	            	if (itemRev.isEnabled()){
	            		codesFiltered.add(itemRev.getLegalActVersion());
	            	}
	            }              
	        }
        }
        for (LegalActVersions.LegalActVersion itemLa : legalActVersions) {
        	
        	if(codesFiltered.contains(itemLa.getCode())){
        		if (itemLa.isEnabled()){
        			//TODO filtered must be List of LegalActVersion core object.
        			eu.ohim.sp.common.ui.form.opposition.LegalActVersion legalActAvaible = convertToLegalActUI(itemLa, applicationType); 
        			if (!filtered.contains(legalActAvaible)){
        				filtered.add(legalActAvaible);	//end filtered must be List of LegalActVersion core object.	
        			} 
        				
        		}	  
        	}
        }
        
        for (LegalActVersion lA : filtered) {
        	eu.ohim.sp.core.domain.opposition.LegalActVersion lACore = legalActVersionFactory.convertTo(lA);
        	filteredCore.add(lACore);
        }
        
        /*	Drl for legalActVersions.  	Parameters= applicationType, TradeMark (core object), groundCategory, filteredCore (coreobject).
         * 								Result = List <LegalActVersion> (coreobject)
         * 								
         * 	Two calls, 	1 - with groundCategory ABSOLUTE_GROUNDS -> List <LegalActVersion> (convert) (ui object).groundCategory=ABSOLUTE_GROUNDS
         * 				2 - with groundCategory RELATIVE_GROUNDS -> List <LegalActVersion> (convert) (ui object).groundCategory=RELATIVE_GROUNDS
         * 
         *  filteredResult = two lists results merged -> FlowBean.avaibleLegalActVersions
         */
        
        flowBean.setAvaibleLegalActVersions(filtered);
        
        
    }
   
	public eu.ohim.sp.common.ui.form.opposition.LegalActVersion convertToLegalActUI(LegalActVersions.LegalActVersion la , String applicationType){
		eu.ohim.sp.common.ui.form.opposition.LegalActVersion legalActAvaible = new eu.ohim.sp.common.ui.form.opposition.LegalActVersion();
		legalActAvaible.setCode(la.getCode());
		legalActAvaible.setConfirmLegalActVersion(false);
		legalActAvaible.setEntryForceDate(la.getEntryIntoForceDate());
		legalActAvaible.setEndApplicabilityDate(la.getEndApplicabilityDate());
		if (applicationType.equals("tm-revocation")) {
			legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.REVOCATION_GROUNDS);
		}
		else{
			if (applicationType.equals("tm-invalidity")) {
			legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.BOTH); //TODO change to null
			}
			else{
				if (applicationType.equals("tm-opposition")) {
				legalActAvaible.setGroundCategory(GroundCategoryKindLegalAct.RELATIVE_GROUNDS); //TODO change to null
				}
			}
		}
		legalActAvaible.setNameVersion(la.getValue());
		return legalActAvaible;
	}
    
    public Integer getMaxNumber()
    {
    	return 1;
    }

}
