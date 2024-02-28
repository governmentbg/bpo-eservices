/*******************************************************************************
 * * $Id:: AddAssigneeController.java 50771 2012-11-14 15:10:27Z karalch        $
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

import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.adapter.AssigneeFactory;
import eu.ohim.sp.common.ui.adapter.EnumAdapter;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.PersonKind;
import eu.ohim.sp.core.domain.person.PersonRole;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

/**
 * Controller in charge of handling the adding and the editing of the assignees.
 * @author ionitdi
 */
@Controller
public class AddUserAssigneeController extends AddAbstractController
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
    private AssigneeFactory assigneeFactory;

    @Autowired
    private ApplicantFactory applicantFactory;

  
    
    @PreAuthorize("hasRole('Assignee_Add')")    
    @RequestMapping(value = "addUserAssignee", method = RequestMethod.POST)
    public ModelAndView addUserPersonDetails(@RequestParam(value = "selectedAssigneeUser", required = false) List<String> selectedAssigneeUser)
    {
    	if (CollectionUtils.isNotEmpty(selectedAssigneeUser)){
    		if (CollectionUtils.isNotEmpty(flowBean.getUserAssigneesForm())){
	    		for (String selected : selectedAssigneeUser){
	    			for (AssigneeForm assigneeForm : flowBean.getUserAssigneesForm()){
	    				if (assigneeForm.getId().equalsIgnoreCase(selected)){
	    					personService.addPersonFormDetails(flowBean, assigneeForm, flowScopeDetails.getFlowModeId());
	    				}
	    			}
	    		}
    		}
    	}
      
        ModelAndView model = new ModelAndView("assignee/assignee_card_list");
        
        return model;
    }
    
    @PreAuthorize("hasRole('Assignee_Add')")    
    @RequestMapping(value = "addUserNaturalPersonDetailsAssignee", method = RequestMethod.POST)
    public ModelAndView addUserNaturalPersonDetails()
    {	
        return addPersonDetailsByKind(PersonKind.NATURAL_PERSON, PersonKind.NATURAL_PERSON_SPECIAL);
    }
     
    @PreAuthorize("hasRole('Assignee_Add')")    
    @RequestMapping(value = "addUserLegalEntityDetailsAssignee", method = RequestMethod.POST)
    public ModelAndView addUserLegalEntityDetails()
    {	    	
    	return addPersonDetailsByKind(PersonKind.LEGAL_ENTITY);
    }
    
    private ModelAndView addPersonDetailsByKind(PersonKind... kinds){
    	ModelAndView model = new ModelAndView("assignee/assignee_card_list");
        UserPersonDetails userPersonDetails = personService.getUserPersonDetails(flowScopeDetails.getFlowModeId());
        List <Applicant> assignees = userPersonDetails.getApplicants();
        int count = 0;
        List <AssigneeForm> assigneesForm = new ArrayList<AssigneeForm>();
        if (CollectionUtils.isNotEmpty(assignees)) {
			for (Applicant assignee : assignees) {
				if (Arrays.asList(kinds).contains(assignee.getKind())){
                    ApplicantForm applicantAssignee = applicantFactory.convertFrom(assignee);
				    if(!flowBean.existsObject(AssigneeForm.class, applicantAssignee.getId())) {
                        count++;
                        AssigneeForm assigneeForm = assigneeFactory.convertFromApplicantForm(applicantAssignee);
                        assigneeForm.setImported(true);
                        assigneeForm.setCurrentUserIndicator(true);
                        assigneesForm.add(assigneeForm);
                    } else {
				        AssigneeForm assigneeForm = flowBean.getObject(AssigneeForm.class, applicantAssignee.getId());
				        assigneeForm.setCurrentUserIndicator(true);
                        assigneeForm.setImported(true);
                    }
				}
			}
		}
        if (count==1){
        	personService.addPersonFormDetails(flowBean, assigneesForm.get(0) , flowScopeDetails.getFlowModeId());
        	model = new ModelAndView("assignee/assignee_card_list");
        }
        else if (count>1){
        	flowBean.setUserAssigneesForm(new ArrayList<AssigneeForm>());
        	flowBean.getUserAssigneesForm().addAll(assigneesForm);
        	model = new ModelAndView("assignee/assignee_addUserData");
        }
        return model;
    }
    
        
}
