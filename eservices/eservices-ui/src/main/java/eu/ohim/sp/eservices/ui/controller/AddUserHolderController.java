/*******************************************************************************
 * * $Id:: AddHolderController.java 50771 2012-11-14 15:10:27Z karalch        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.EnumAdapter;
import eu.ohim.sp.common.ui.adapter.HolderFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

/**
 * Controller in charge of handling the adding and the editing of the holders.
 * @author ionitdi
 */
@Controller
public class AddUserHolderController extends AddAbstractController
{
    /**
     * session bean
     */
    @Autowired
    private ESFlowBean flowBean;

	@Autowired
    private PersonServiceInterface personService;
	
    @Autowired
    private FlowScopeDetails flowScopeDetails;
    
    @Autowired
    private HolderFactory holderFactory;

  
    
    @PreAuthorize("hasRole('Holder_Add')")    
    @RequestMapping(value = "addUserHolder", method = RequestMethod.POST)
    public ModelAndView addUserPersonDetails(@RequestParam(value = "selectedHolderUser", required = false) List<String> selectedHolderUser)
    {
    	if (CollectionUtils.isNotEmpty(selectedHolderUser)){
    		if (CollectionUtils.isNotEmpty(flowBean.getUserHoldersForm())){
	    		for (String selected : selectedHolderUser){
	    			for (HolderForm holderForm : flowBean.getUserHoldersForm()){
	    				if (holderForm.getId().equalsIgnoreCase(selected)){
	    					personService.addPersonFormDetails(flowBean, holderForm, flowScopeDetails.getFlowModeId());
	    				}
	    			}
	    		}
    		}
    	}
      
        ModelAndView model = new ModelAndView("holder/holder_card_list");
        
        return model;
    }
    
    @PreAuthorize("hasRole('Holder_Add')")    
    @RequestMapping(value = "addUserNaturalPersonDetailsHolder", method = RequestMethod.POST)
    public ModelAndView addUserNaturalPersonDetails()
    {	      
        return addPersonDetailsByKind("holderUserNatural", PersonKind.NATURAL_PERSON, PersonKind.NATURAL_PERSON_SPECIAL);
    }
    
    @PreAuthorize("hasRole('Holder_Add')")    
    @RequestMapping(value = "addUserLegalEntityDetailsHolder", method = RequestMethod.POST)
    public ModelAndView addUserLegalEntityDetails()
    {	      
        return addPersonDetailsByKind("holderUserLegal", PersonKind.LEGAL_ENTITY);
    }
    
    private ModelAndView addPersonDetailsByKind(String idPrefix, PersonKind... kinds){
    	ModelAndView model = new ModelAndView();
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> holders = userPersonDetails.getApplicants();
        int count = 0;
        List <HolderForm> holdersForm = new ArrayList<HolderForm>();
        if (CollectionUtils.isNotEmpty(holders)) {
			for (Applicant holder : holders) {
				if (Arrays.asList(kinds).contains(holder.getKind())){ 
					count++;
					PersonRole person = holder;
					Holder holderCore = new Holder();
					BeanUtils.copyProperties(person, holderCore);
					HolderForm holderForm = holderFactory.convertFrom(holderCore);
					holderForm.setImported(true);
					holderForm.setType(EnumAdapter.coreHolderKindToFormHolderKind(holder.getKind()));
					holderForm.setCurrentUserIndicator(true);
					holderForm.setId(idPrefix + count);
					holdersForm.add(holderForm);
				}
			}
		}
        if (count==1){
        	personService.addPersonFormDetails(flowBean, holdersForm.get(0) , flowScopeDetails.getFlowModeId());
        	model = new ModelAndView("holder/holder_card_list");
        }
        else if (count>1){
        	flowBean.setUserHoldersForm(new ArrayList<HolderForm>());
        	flowBean.getUserHoldersForm().addAll(holdersForm);
        	model = new ModelAndView("holder/holder_addUserData");
        }
        return model;
    }
    
    
}
