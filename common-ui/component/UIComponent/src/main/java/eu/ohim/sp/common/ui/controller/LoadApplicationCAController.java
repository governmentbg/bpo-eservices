package eu.ohim.sp.common.ui.controller;

import java.util.List;
import java.util.Optional;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.controller.AddAbstractController;
import eu.ohim.sp.common.ui.exception.MaximumEntitiesException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.contact.AddressForm;
import eu.ohim.sp.common.ui.form.contact.CorrespondenceAddressForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;

/**
 * 
 * @author serrajo
 *
 */
@Controller
public class LoadApplicationCAController extends AddAbstractController  {
    /**
     * session bean
     */
    @Autowired
    private FlowBean flowBean;
    
    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

	@Autowired
	private FlowScopeDetails flowScopeDetails;

	@Autowired
	private PersonServiceInterface personService;

    private Integer maxNumber = -1;

    /**
     * Gets the maximum number of correspondence address entities allowed
     * @return Integer with the maximum number
     */
    public Integer getMaxNumber() {
		if(maxNumber == -1) {
	        maxNumber = getIntegerSetting(configurationServiceDelegator,
	                ConfigurationServiceDelegatorInterface.CORRESPONDENCE_ADDRESS_ADD_MAXNUMBER,
	                ConfigurationServiceDelegatorInterface.GENERAL_COMPONENT);
		}
		return maxNumber;
    }    

	/**
	 * 
	 * @param id
	 * @return
	 */
    @PreAuthorize("hasRole('ApplicationCA_Add')")
    @RequestMapping(value = "loadCAFromPerson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
    public ApplicationCAForm handleLoadCAExist(@RequestParam(required = false, value = "id") String id)  {

		if (flowBean.getCollection(ApplicationCAForm.class) != null
				&& flowBean.getCollection(ApplicationCAForm.class).size() > getMaxNumber()) {
			throw new MaximumEntitiesException("Maximum entities reached", new Exception(), "error.ef.max.ApplicationCAForm", getMaxNumber().toString());
		}

		return loadApplicationCAList(id, true);
    }

	private ApplicationCAForm loadApplicationCAList(String id, boolean imported) {
		// Get the person
		PersonForm personForm = null;
		String flowModeId = flowScopeDetails.getFlowModeId();
		if (flowModeId.endsWith("-change")){
			personForm = flowBean.getObject(HolderForm.class, id);
		} else if(flowModeId.endsWith("-transfer") || flowModeId.endsWith("-rem") || flowModeId.endsWith("-licence") || flowModeId.endsWith("-compulsorylicence")
			|| flowModeId.endsWith("-security") || flowModeId.endsWith("-bankruptcy")) {
			personForm = flowBean.getObject(AssigneeForm.class, id);
		}

		if (personForm == null) {
			personForm = flowBean.getObject(ApplicantForm.class, id);
		}
		if (personForm == null) {
			personForm = flowBean.getObject(RepresentativeForm.class, id);
		}
		if(personForm == null && flowBean.getUserRepresentativesForm() != null){
			Optional<RepresentativeForm> found = flowBean.getUserRepresentativesForm().stream().filter(userRep -> userRep.getId() != null && userRep.getId().equals(id)).findFirst();
			if(found.isPresent()) {
				personForm = found.get();
			}
		}
		if(personForm == null) {
			personForm = findPersonFormInTM(flowBean, id);
		}
		if(personForm == null) {
			personForm = findPersonFormInDS(flowBean, id);
		}
		if(personForm == null) {
			personForm = findPersonFormInPT(flowBean, id);
		}

		ApplicationCAForm applicationCAForm = new ApplicationCAForm();

		CorrespondenceAddressForm correspondenceAddressForm = new CorrespondenceAddressForm();
		if(imported) {
			correspondenceAddressForm.setImported(true);
		}
		correspondenceAddressForm.setAddressForm((AddressForm) personForm.getAddress().clone());
		correspondenceAddressForm.setCorrespondenceEmail(personForm.getEmail());
		correspondenceAddressForm.setCorrespondenceFax(personForm.getFax());
		correspondenceAddressForm.setCorrespondenceName(personForm.getName());
		correspondenceAddressForm.setCorrespondencePhone(personForm.getPhone());
		applicationCAForm.setCorrespondenceAddressForm(correspondenceAddressForm);
		return applicationCAForm;
	}

	private PersonForm findPersonFormInTM(FlowBean flowBean, String id){
		PersonForm personForm = null;
		List<TMDetailsForm> tmsList = flowBean.getCollection(TMDetailsForm.class);
		if(tmsList != null && !tmsList.isEmpty() ){
			for(TMDetailsForm tmForm : tmsList){
				personForm = findApplicant(tmForm.getApplicants(), id);
				if(personForm == null){
					personForm = findRepresentative(tmForm.getRepresentatives(), id);
				}
				if(personForm != null){
					break;
				}
			}
		}
		return personForm;
	}

	private PersonForm findPersonFormInDS(FlowBean flowBean, String id){
		PersonForm personForm = null;
		List<ESDesignDetailsForm> dssList = flowBean.getCollection(ESDesignDetailsForm.class);
		if(dssList != null && !dssList.isEmpty()){
			ESDesignDetailsForm esDesignDetailsForm = dssList.get(0);
			if(esDesignDetailsForm != null && esDesignDetailsForm.geteSDesignApplicationData() != null){
				personForm = findApplicant(esDesignDetailsForm.geteSDesignApplicationData().getHolders(), id);
				if(personForm == null){
					personForm = findRepresentative(esDesignDetailsForm.geteSDesignApplicationData().getRepresentatives(), id);
				}
			}
		}
		return personForm;
	}

	private PersonForm findPersonFormInPT(FlowBean flowBean, String id){
		PersonForm personForm = null;
		List<ESPatentDetailsForm> ptsList = flowBean.getCollection(ESPatentDetailsForm.class);
		if(ptsList != null && !ptsList.isEmpty() ){
			for(ESPatentDetailsForm ptForm : ptsList){
				personForm = findApplicant(ptForm.getApplicants(), id);
				if(personForm == null){
					personForm = findRepresentative(ptForm.getRepresentatives(), id);
				}
				if(personForm != null){
					break;
				}
			}
		}
		return personForm;
	}

	private PersonForm findApplicant(List<ApplicantForm> applicants, String id){
		if(applicants != null) {
			return applicants.stream().filter(appl -> appl.getId() != null && appl.getId().equals(id)).findFirst().orElse(null);
		}
		return null;
	}

	private PersonForm findRepresentative(List<RepresentativeForm> representatives, String id){
		if(representatives != null) {
			Optional<RepresentativeForm> found = representatives.stream().filter(repr -> repr.getId() != null && repr.getId().equals(id)).findFirst();
			if(found.isPresent()){
				return personService.importRepresentative(id, flowScopeDetails.getFlowModeId());
			}

		}
		return null;
	}

}
