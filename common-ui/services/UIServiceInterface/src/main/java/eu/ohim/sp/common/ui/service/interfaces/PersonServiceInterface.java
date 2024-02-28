package eu.ohim.sp.common.ui.service.interfaces;

import java.util.List;

import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.core.domain.validation.ErrorList;

import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.flow.section.PersonFlowBean;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.core.domain.validation.RulesInformation;

public interface PersonServiceInterface {

    ApplicantForm importApplicant(String applicantId, String flowModeId);

    RepresentativeForm importRepresentative(String representativeId, String flowModeId);

    /**
     * Checks whether an applicant/representative is imported from a loaded application
     * and then imports again the applicant/representative details, if the external service
     * is enabled, otherwise it removes it.
     * 
     * @param flowBean
     */
    void importLoadedPersons(PersonFlowBean flowBean);	
	
    List<ApplicantForm> matchApplicant(ApplicantForm applicantForm, int maxResults);
    
    List<RepresentativeForm> matchRepresentative(RepresentativeForm representativeForm, int maxResults);
    
    String getApplicantAutocomplete(String text, int numberOfRows);
    
    String getApplicantAutocomplete(String text, int numberOfRows, String flowModeId);
    
    String getRepresentativeAutocomplete(String text, int numberOfRows);
    
    String getRepresentativeAutocomplete(String text, int numberOfRows, String flowModeId);

    String getDesignerAutocomplete(String text, int numberOfRows);
    
    DesignerForm importDesigner(String designerId, String flowModeId);

    String getInventorAutocomplete(String text, int numberOfRows);

    InventorForm importInventor(String designerId, String flowModeId);
    
    ErrorList validateApplicant (String module, ApplicantForm applicantForm, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateRepresentative (String module, RepresentativeForm applicantForm, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validatePersonChange(String module, RepresentativeForm representativeForm, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateHolder(String module, HolderForm holderForm, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateDesigner (String module, DesignerForm designerForm, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateInventor (String module, InventorForm inventorForm, RulesInformation rules, Errors errors, String flowModeId);

	ErrorList validateAssignee(String module, AssigneeForm assigneeForm, RulesInformation rules, Errors errors, String flowModeId);
	
	ErrorList validateSignature(String module, SignatureForm signatureForm, RulesInformation rules, Errors errors, String flowModeId);
	
	UserPersonDetails getUserPersonDetails(String flowModeId);
	
	UserPersonDetails addUserPersonDetails(FlowBean flowBean, String flowModeId);

    UserPersonDetails clearUserApplicantDetails(FlowBean flowBean, String flowModeId);

    UserPersonDetails clearUserInventorDetails(FlowBean flowBean, String flowModeId);

	void addUserDetails(FlowBean flowBean, String flowModeId);

	void addPersonFormDetails(FlowBean flowBean, PersonForm form, String flowModeId);
	
	void initializePersonsForRegisteredUsers(FlowBean flowBean, String flowModeId);
	
	User getUser(String flowModeId);

    List<DesignerForm> matchDesigner(DesignerForm designerForm, int maxResults);

    List<InventorForm> matchInventor(InventorForm inventorForm, int maxResults);

    RepresentativeForm importIntlPRepresentative(String representativeId, String flowModeId);

    String getIntlPRepresentativeList();
}
