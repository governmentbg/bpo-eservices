package eu.ohim.sp.eservices.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ApplicantFactory;
import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;
import eu.ohim.sp.common.ui.form.person.HolderForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CopyPersonsController extends AddAbstractController {
	
	private static final Logger logger = Logger.getLogger(CopyPersonsController.class);

	@Autowired
    private ESFlowBean flowBean;
	
	 @Autowired
	 private FlowScopeDetails flowScopeDetails;
	    
	 @Autowired
	 private ApplicantFactory applicantFactory;
	 
	 @Autowired
	 private PersonServiceInterface personService;

	@PreAuthorize("hasRole('Applicant_Add')")
	@RequestMapping(value = "addApplicantsFromHolders", method = RequestMethod.POST)
	public ModelAndView addApplicantsFromHolders(){
		if (flowBean == null) {
			throw new SPException("Null flow bean");
		}
		boolean noHolders = true;
		if(flowBean.getHolders() != null && flowBean.getHolders().size() > 0) {
			for(HolderForm holder: flowBean.getHolders() ) {
				if( !flowBean.existsObject(ApplicantForm.class, holder.getId())){
					ApplicantForm appl = applicantFactory.convertFromHolderForm(holder);
					if(appl != null){
						flowBean.addObject(appl);
						noHolders = false;
					}
				}
			}
		}
		if (noHolders) {
			ModelAndView view = new ModelAndView("errors/importError");
			view.addObject("errorCode", "error.import.or.exists");
			return view;
		} else {
			return new ModelAndView("applicant/applicant_card_list");
		}
	}

	@PreAuthorize("hasRole('Applicant_Add')")
	@RequestMapping(value = "addApplicantsFromAssignees", method = RequestMethod.POST)
	public ModelAndView addApplicantsFromAssignees(){
		if (flowBean == null) {
			throw new SPException("Null flow bean");
		}
		boolean noAssignees = true;
		if(flowBean.getAssignees() != null && flowBean.getAssignees().size() > 0) {
			for(AssigneeForm assignee: flowBean.getAssignees() ) {
				if(!flowBean.existsObject(ApplicantForm.class, assignee.getId())){
					ApplicantForm appl = applicantFactory.convertFromAssigneeForm(assignee);
					if(appl != null){
						flowBean.addObject(appl);
						noAssignees = false;
					}
				}
			}
		}
		if (noAssignees) {
			ModelAndView view = new ModelAndView("errors/importError");
			view.addObject("errorCode", "error.import.or.exists");
			return view;
		} else {
			return new ModelAndView("applicant/applicant_card_list");
		}
	}

	@PreAuthorize("hasRole('Applicant_Add')")
	@RequestMapping(value = "addApplicantsFromOwners", method = RequestMethod.POST)
	public ModelAndView addApplicantsFromOwners(){
		if (flowBean == null) {
			throw new SPException("Null flow bean");
		}
		boolean noOwners = true;
		if (flowBean.getTmsList() != null && !flowBean.getTmsList().isEmpty()) {
			for (TMDetailsForm tmDetailsForm : flowBean.getTmsList()) {
				if (tmDetailsForm.getApplicants() != null) {
					for(ApplicantForm owner: tmDetailsForm.getApplicants()) {
						if(owner.getImported() == true && !flowBean.existsObject(ApplicantForm.class, owner.getId())){
							ApplicantForm appl = personService.importApplicant(owner.getId(), flowScopeDetails.getFlowModeId());
							if(appl != null){

								flowBean.addObject(appl);
								noOwners = false;
							}
						}
					}
				}
			}
		} else if (flowBean.getDssList() != null && !flowBean.getDssList().isEmpty()) {
			for (ESDesignDetailsForm esDesignDetailsForm : flowBean.getDssList()) {
				if (esDesignDetailsForm.geteSDesignApplicationData() != null && esDesignDetailsForm.geteSDesignApplicationData().getHolders() != null) {
					for(ApplicantForm owner: esDesignDetailsForm.geteSDesignApplicationData().getHolders()) {
						if(owner.getImported() == true && !flowBean.existsObject(ApplicantForm.class, owner.getId())){
							ApplicantForm appl = personService.importApplicant(owner.getId(), flowScopeDetails.getFlowModeId());
							if(appl != null){

								flowBean.addObject(appl);
								noOwners = false;
							}

						}
					}
				}
			}
		} else if (flowBean.getPatentsList() != null && !flowBean.getPatentsList().isEmpty()){
			for (ESPatentDetailsForm esPatentDetailsForm : flowBean.getPatentsList()) {
				if (esPatentDetailsForm.getApplicants() != null) {
					for(ApplicantForm owner: esPatentDetailsForm.getApplicants()) {
						if(owner.getImported() == true && !flowBean.existsObject(ApplicantForm.class, owner.getId())){
							ApplicantForm appl = personService.importApplicant(owner.getId(), flowScopeDetails.getFlowModeId());
							if(appl != null){
								flowBean.addObject(appl);
								noOwners = false;
							}
						}
					}
				}
			}
		}
		if (noOwners) {
			ModelAndView view = new ModelAndView("errors/importError");
			view.addObject("errorCode", "error.import.or.exists");
			return view;
		} else {
			return new ModelAndView("applicant/applicant_card_list");
		}
	}
}
