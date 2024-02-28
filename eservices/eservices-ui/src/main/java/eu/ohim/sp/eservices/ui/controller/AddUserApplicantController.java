/*******************************************************************************
 * * $Id:: AddApplicantController.java 50771 2012-11-14 15:10:27Z karalch        $
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

/**
 * Controller in charge of handling the adding and the editing of the applicants.
 * @author ionitdi
 */
@Controller
public class AddUserApplicantController extends AddAbstractController
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
    private ApplicantFactory applicantFactory;

  
    
    @PreAuthorize("hasRole('Applicant_Add')")    
    @RequestMapping(value = "addUserApplicant", method = RequestMethod.POST)
    public ModelAndView addUserPersonDetails(@RequestParam(value = "selectedApplicantUser", required = false) List<String> selectedApplicantUser)
    {
    	if (CollectionUtils.isNotEmpty(selectedApplicantUser)){
    		if (CollectionUtils.isNotEmpty(flowBean.getUserApplicantsForm())){
	    		for (String selected : selectedApplicantUser){
	    			for (ApplicantForm applicantForm : flowBean.getUserApplicantsForm()){
	    				if (applicantForm.getId().equalsIgnoreCase(selected)){
	    					personService.addPersonFormDetails(flowBean, applicantForm, flowScopeDetails.getFlowModeId());
	    				}
	    			}
	    		}
    		}
    	}
      
        ModelAndView model = new ModelAndView("applicant/applicant_card_list");
        
        return model;
    }
    
    @PreAuthorize("hasRole('Applicant_Add')")    
    @RequestMapping(value = "addUserNaturalPersonDetails", method = RequestMethod.POST)
    public ModelAndView addUserNaturalPersonDetails()
    {	        
        return addPersonDetailsByKind(PersonKind.NATURAL_PERSON, PersonKind.NATURAL_PERSON_SPECIAL);
    }
    
    @PreAuthorize("hasRole('Applicant_Add')")    
    @RequestMapping(value = "addUserLegalEntityDetails", method = RequestMethod.POST)
    public ModelAndView addUserLegalEntityDetails()
    {	
    	return addPersonDetailsByKind(PersonKind.LEGAL_ENTITY);
    }
    
    private ModelAndView addPersonDetailsByKind(PersonKind... kinds){
    	ModelAndView model = new ModelAndView("applicant/applicant_card_list");
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> applicants = userPersonDetails.getApplicants();
        int count = 0;
        List <ApplicantForm> applicantsForm = new ArrayList<ApplicantForm>();
        if (CollectionUtils.isNotEmpty(applicants)) {
			for (Applicant applicant : applicants) {
				if (Arrays.asList(kinds).contains(applicant.getKind())){
                    ApplicantForm applicantForm = applicantFactory.convertFrom(applicant);
				    if(!flowBean.existsObject(ApplicantForm.class, applicantForm.getId())) {
                        count++;
                        applicantForm.setImported(true);
                        applicantForm.setCurrentUserIndicator(true);
                        applicantsForm.add(applicantForm);
                    } else {
				        applicantForm = flowBean.getObject(ApplicantForm.class, applicantForm.getId());
				        applicantForm.setCurrentUserIndicator(true);
                        applicantForm.setImported(true);
                    }
				}
			}
		}
        if (count==1){
        	personService.addPersonFormDetails(flowBean, applicantsForm.get(0) , flowScopeDetails.getFlowModeId());
        	model = new ModelAndView("applicant/applicant_card_list");
        }
        else if (count>1){
        	flowBean.setUserApplicantsForm(new ArrayList<ApplicantForm>());
        	flowBean.getUserApplicantsForm().addAll(applicantsForm);
        	model = new ModelAndView("applicant/applicant_addUserData");
        }
        return model;
    }
    
    
}
