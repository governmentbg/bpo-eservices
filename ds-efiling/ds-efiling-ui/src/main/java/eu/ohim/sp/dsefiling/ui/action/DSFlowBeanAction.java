package eu.ohim.sp.dsefiling.ui.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
import org.apache.log4j.Logger;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.adapter.design.DesignerFactory;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.NaturalPersonForm;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.core.domain.design.Designer;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBean;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSApplicationServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSDesignsServiceInterface;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSImportDesignsServiceInterface;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;

@Service(value="flowBeanAction")
public class DSFlowBeanAction {

    private static final Logger LOGGER = Logger.getLogger(DSFlowBeanAction.class);
    
    @Autowired
    private DSApplicationServiceInterface draftApplicationService;
	
	@Autowired
	private DSDesignsServiceInterface dsDesignsService;
	
	@Autowired
	private DSImportDesignsServiceInterface dsImportDesignsService;
	
	@Autowired
	private PersonServiceInterface personService;
	
	@Autowired
	private DesignerFactory designerFactory;

    @Autowired
    private FlowScopeDetails flowScopeSession;

	@Autowired
	private ImportServiceInterface importServiceInterface;
	
	/**
	 * 
	 * @param flowBean
	 * @param flowModeId
	 */
	public UserPersonDetails addUserPersonDetails(DSFlowBean flowBean, String flowModeId) {
		try {
			UserPersonDetails userPersonDetails = personService.addUserPersonDetails(flowBean, flowModeId);
			if (userPersonDetails != null) {
				addDesignersDetails(flowBean, userPersonDetails.getDesigners(), flowModeId);
			}
			return userPersonDetails;
		} catch(Exception e) {
			LOGGER.warn("Error obtaining user person details", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param flowBean
	 * @param designers
	 * @param flowModeId
	 */
	private void addDesignersDetails(DSFlowBean flowBean, List<Designer> designers, String flowModeId) {
		if (CollectionUtils.isNotEmpty(designers)) {
			for (Designer designer : designers) {
				DesignerForm designerForm = designerFactory.convertFrom(designer);
				if (designerForm != null) {
					dsDesignsService.fillDesignerDesignsListsFromToLink(flowBean, designerForm);
					personService.addPersonFormDetails(flowBean, designerForm, flowModeId);	
				}
			}
		}
	}
	
    /**
     * In a possible come back, and the user delete designs, if there is only one we have to add it in the linked lists. 
     * @param flowBean
     */
    public void checkThereIsOnlyOneDesign(DSFlowBean flowBean) {
    	if (flowBean.getDesigns().size() == 1) {
    		DesignForm designForm = flowBean.getDesigns().get(0);
    		dsDesignsService.removeDesignFromLists(designForm, flowBean);
    		dsDesignsService.addDesignToLinkedLists(designForm, flowBean);
    	}
    }
    
    /**
     * 
     * @param flowBean
     * @param idRegisteredDesign
     * @param loadAllApplication
     */
    public DSFlowBean importDesignApplication(DSFlowBean flowBean, String idDesign, String applicationId) {
    	dsImportDesignsService.importDesignApplication(idDesign, applicationId, flowBean);
		if(flowBean.getDesigns() != null){
			for(DesignForm designForm: flowBean.getDesigns()) {
				if(designForm.getLocarno() != null) {
					for (LocarnoAbstractForm form : designForm.getLocarno()) {
						importServiceInterface.validateLocarnoForm(flowBean.getFirstLang(), form);
					}
				}
			}
		}
    	return flowBean;
    }
    
	/**
	 * Clear the possible Locarno classes that remain in the flow bean.
	 * For example, when you add Locarno classes to a design and you cancel the design creation, these
	 * Locarno classes remain in the flow.
	 * @param flowBean Domain object.
	 */
	public void clearLocarno(DSFlowBean flowBean) {
		dsDesignsService.clearLocarnoFlow(flowBean);
	}

	/**
	 * Add the Designers when DesignerIndicator of applicant is true.
	 * @param flowBean Domain object. 
	 */
	
	public boolean addDesignerFromApplicant(DSFlowBean flowBean) {
		boolean isThere = false;
		
		Map<String,String> designersFromApplicants = flowBean.getDesignersFromApplicants();
		NaturalPersonForm applicant;
		DesignerForm designer;
		
		//To check if it's deleted some applicant	
		checkApplicants(flowBean, designersFromApplicants, flowBean.getApplicants());
		
		for (ApplicantForm applicantForm : flowBean.getApplicants()) {
			if (applicantForm instanceof NaturalPersonForm) {
				applicant = (NaturalPersonForm) applicantForm;
				if (applicant.getDesignerIndicator()) {
					
					if (designersFromApplicants.containsKey(applicant.getId())) {
						String id = designersFromApplicants.get(applicant.getId());
						designer = flowBean.getObject(DesignerForm.class, id);
					} else {
						designer = new DesignerForm();
						if(applicant.getImported()) {
							designer.setImported(true);
							designer.setId(applicant.getId());
						}
					}

					designer.setFirstName(applicant.getFirstName());
					designer.setMiddleName(applicant.getMiddleName());
					designer.setSurname(applicant.getSurname());        	 
					designer.setAddress(applicant.getAddress());
					designer.setCorrespondenceAddresses(applicant.getCorrespondenceAddresses());
					designer.setEmail(applicant.getEmail());
					designer.setFax(applicant.getFax());
					designer.setPhone(applicant.getPhone());
					designer.setWebsite(applicant.getWebsite());
					designer.setNationality(applicant.getNationality());
		    		
					if (!designersFromApplicants.containsKey(applicant.getId())) {
						dsDesignsService.fillDesignerDesignsListsFromToLink(flowBean, designer);
						flowBean.addObject(designer);
						designersFromApplicants.put(applicant.getId(), designer.getId());
					}
					
					isThere = true;
				}
			}
		}
		return isThere;
	}	

	/**
	 * Update the Applicant when DesignerIndicator of applicant is true.
	 * @param flowBean Domain object. 
	 */
	public void updateApplicantFromDesigner(DSFlowBean flowBean) {
		for (DesignerForm designerForm : flowBean.getDesigners()) {
			Map<String,String> designersFromApplicants = flowBean.getDesignersFromApplicants();
			for (Iterator<Entry<String, String>> it = designersFromApplicants.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				if (designerForm.getId().equals(entry.getValue())) {					
		    		NaturalPersonForm commandNaturalPersonForm = flowBean.getObject(NaturalPersonForm.class, entry.getKey());
		    		if (commandNaturalPersonForm.getDesignerIndicator()) {
				    	commandNaturalPersonForm.setFirstName(designerForm.getFirstName());
				    	commandNaturalPersonForm.setMiddleName(designerForm.getMiddleName());
				    	commandNaturalPersonForm.setSurname(designerForm.getSurname());        	 
				    	commandNaturalPersonForm.setAddress(designerForm.getAddress());
				    	commandNaturalPersonForm.setCorrespondenceAddresses(designerForm.getCorrespondenceAddresses());
				    	commandNaturalPersonForm.setEmail(designerForm.getEmail());
				    	commandNaturalPersonForm.setFax(designerForm.getFax());
				    	commandNaturalPersonForm.setPhone(designerForm.getPhone());
				    	commandNaturalPersonForm.setWebsite(designerForm.getWebsite());				    		
				    	break;
		    		} else {
		    			designersFromApplicants.remove(entry.getKey());
		    		}
				}
			}
		}
	}

    public DSFlowBean loadApplication(String formId, String lid) {
        String temp = flowScopeSession.getLid();
        DSFlowBean dsFlowBean = new DSFlowBeanImpl();
        if(formId == null || lid == null) {
        	return dsFlowBean;
        } else if(lid.equals(temp)) {
            return draftApplicationService.loadApplicationLocally(formId);
        }
        return dsFlowBean;
    }
	
	/**
	 * Use to check:
	 * 1.- If some designer from 'designersFromApplicants' has been deleted
	 * 2.- If for each designer from 'designersFromApplicants' has been deleted its applicant associated	
	 * @param designersFromApplicants 
	 * @param listNaturalPerson
	 */
	private void checkApplicants(DSFlowBean flowBean, Map<String,String> designersFromApplicants, List<ApplicantForm> applicants) {
		List<String> keysToRemove = new ArrayList<>();
		for (Iterator<Entry<String, String>> it = designersFromApplicants.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, String> entry = it.next();
			boolean found = false;
			if (flowBean.getObject(DesignerForm.class, entry.getValue()) != null) {
				for (ApplicantForm applicantForm : applicants) {
					if (applicantForm instanceof NaturalPersonForm) {
						NaturalPersonForm applicant = (NaturalPersonForm) applicantForm;
						if (entry.getKey().equals(applicant.getId())) {
							found = true;
							break;
						}
					}
				}
			}
			if (!found) {
				keysToRemove.add(entry.getKey());
			}
		}

		for(String key: keysToRemove){
			designersFromApplicants.remove(key);
		}
	}
}
