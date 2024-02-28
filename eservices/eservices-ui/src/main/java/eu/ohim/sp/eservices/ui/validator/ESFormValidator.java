package eu.ohim.sp.eservices.ui.validator;


import java.util.HashMap;

import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.eservices.ui.service.interfaces.ESPatentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.webflow.execution.RequestContextHolder;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.design.ESDesignApplicationDataForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKind;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.PersonService;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.domain.application.ApplicationKind;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.eservices.ui.domain.ESFlowBeanImpl;
import eu.ohim.sp.eservices.ui.service.ESDesignService;
import eu.ohim.sp.eservices.ui.service.interfaces.ESApplicationServiceInterface;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import eu.ohim.sp.eservices.ui.util.EServicesConstants;


@Component(value="formValidator")
public class ESFormValidator extends FormValidator{

	@Autowired
	private PersonService personService;

	@Autowired
	private ESDesignService designService;

	@Autowired
	private ESTrademarkServiceInterface tradeMarkService;

	@Autowired
	private ESApplicationServiceInterface applicationService;

	@Autowired
	private ESPatentServiceInterface esPatentService;

	@Autowired
	private ErrorFactory errorFactory;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationService;

	private static final String MODULE= "eservices";
	private static final String APPLICATIONEXISTCHECK = "CheckExistInPortal_";

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof Validatable) {
			validate((Validatable) target, errors, new FlowScopeDetails());
		}
	}

	/**
	 *
	 * @param target the form object that will be validated
	 * @param errors the errors object that will be displayed to the user
	 * @param flowScopeDetails the flow scope details of the cu
	 */
	public void validate(Validatable target, Errors errors, FlowScopeDetails flowScopeDetails) {
		RulesInformation rulesInformation = new RulesInformation();
		String flowModeId = flowScopeDetails.getFlowModeId();
		String stateId = flowScopeDetails.getStateId();

		if (RequestContextHolder.getRequestContext()!=null) {
			if (RequestContextHolder.getRequestContext().getActiveFlow()!=null
					&& flowModeId==null) {
				flowModeId = RequestContextHolder.getRequestContext().getActiveFlow().getId();
			}
			if (RequestContextHolder.getRequestContext().getCurrentState()!=null
					&& stateId==null) {
				stateId = RequestContextHolder.getRequestContext().getCurrentState().getId();
			}

			rulesInformation.setCustomObjects(new HashMap<String, Object>());

		}

		rulesInformation.getCustomObjects().put("isSectionValidation", false);

		//APPLICATION VALIDATIONS 

		if (target instanceof ESFlowBeanImpl){
			rulesInformation.getCustomObjects().put("isSectionValidation", true);
			ErrorList errorList = new ErrorList();
			validateApplicationPersons((ESFlowBeanImpl)target, rulesInformation, errors, flowModeId);
			rulesInformation.getCustomObjects().clear();
			rulesInformation.getCustomObjects().put("stateId", stateId);
			rulesInformation.getCustomObjects().put("flowModeId", flowModeId);
			rulesInformation.getCustomObjects().put("attachmentsMandatoryList", configurationService.getMandatoryAttachmentTypeForFlow(flowModeId, null, ((ESFlowBeanImpl) target).getChangeType()));
			errorList=applicationService.validateApplication((ESFlowBeanImpl)target, rulesInformation, flowModeId);
			errorFactory.coreErrorToUIError(errorList,errors);
		}

		validateForm(target, rulesInformation, errors, flowModeId);

	}

	private void validateForm(Validatable target, RulesInformation rulesInformation, Errors errors, String flowModeId){
		//DESIGN VALIDATIONS
		if (target instanceof ESDesignDetailsForm){
			ESDesignDetailsForm eSDesignDetailsForm = (ESDesignDetailsForm)target;
			ErrorList errorList = new ErrorList();
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "ds_details_section");
			if(configurationService.isServiceEnabled(APPLICATIONEXISTCHECK+flowModeId)) {
				String appNumber=(((ESDesignDetailsForm)target).geteSDesignApplicationData()!=null?((ESDesignApplicationDataForm)((ESDesignDetailsForm)target).geteSDesignApplicationData()).getApplicationNumber():"");
				if(flowModeId != null) {
					rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, applicationService.checkExistingApplication(flowModeId.replaceAll("-", "_").toUpperCase(), flowModeId, appNumber, ((ESDesignDetailsForm)target).getRegistrationNumber()));
				}
			} else {
				rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, false);
			}
			errorList = designService.validateDesign(MODULE,eSDesignDetailsForm,rulesInformation,errors,flowModeId);
			errorFactory.coreErrorToUIError(errorList,errors);
		}

		//SIGNATURE VALIDATIONS
		if (target instanceof SignatureForm){
			SignatureForm signatureForm = (SignatureForm)target;
			ErrorList errorList = new ErrorList();
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "signature_details_section");
			errorList = personService.validateSignature(MODULE,signatureForm,rulesInformation,errors,flowModeId);
			errorFactory.coreErrorToUIError(errorList,errors);
		}

		//OPPOSITION VALIDATIONS
		if (target instanceof OppositionBasisForm){
			OppositionBasisForm oppositionBasisForm = (OppositionBasisForm)target;
			ErrorList errorList = new ErrorList();

			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM,"opposition_basis_details_section");
			errorList =tradeMarkService.validateOpposition(MODULE,(OppositionBasisForm)target, rulesInformation, errors,flowModeId);

			if (oppositionBasisForm.getGroundCategory().toString().equalsIgnoreCase(GroundCategoryKind.RELATIVE_GROUNDS.toString())){
				String earlierSection = oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightTypeCode();
				//OPPOSITION_SET

//				if (oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails()!=null &&
//						oppositionBasisForm.getRelativeGrounds().getEarlierEntitlementRightInf().getEarlierEntitlementRightDetails().getFilterCountriesEarlierTradeMark()!=Boolean.TRUE){
				rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "sec_"+earlierSection+"_earlier_trademark_details");
				errorList.addAllErrors(tradeMarkService.validateOpposition(MODULE,(OppositionBasisForm)target, rulesInformation, errors,flowModeId));
				rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "sec_"+earlierSection);
				errorList.addAllErrors(tradeMarkService.validateOpposition(MODULE,(OppositionBasisForm)target, rulesInformation, errors,flowModeId));
//				}

			}
			errorFactory.coreErrorToUIError(errorList,errors);
		}

		//TRADEMARK VALIDATIONS
		if (target instanceof TMDetailsForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "tm_details_section");
			if(configurationService.isServiceEnabled(APPLICATIONEXISTCHECK+flowModeId) && flowModeId != null) {
				rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, applicationService.checkExistingApplication(flowModeId.replaceAll("-", "_").toUpperCase(), flowModeId, ((TMDetailsForm) target).getApplicationNumber(), ((TMDetailsForm) target).getRegistrationNumber()));
			} else {
				rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, false);
			}
			errorFactory.coreErrorToUIError(tradeMarkService.validateTradeMark(MODULE,(TMDetailsForm)target, rulesInformation, errors,flowModeId),errors);
		}

		//PATENT VALIDATIONS
		if (target instanceof ESPatentDetailsForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, AvailableSection.PATENT_DETAILS.value());
			if(configurationService.isServiceEnabled(APPLICATIONEXISTCHECK+flowModeId) && flowModeId != null) {
				rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, applicationService.checkExistingApplication(flowModeId.replaceAll("-", "_").toUpperCase(), flowModeId, ((TMDetailsForm) target).getApplicationNumber(), ((TMDetailsForm) target).getRegistrationNumber()));
			} else {
				rulesInformation.getCustomObjects().put(EServicesConstants.APP_EX_PT, false);
			}
			errorFactory.coreErrorToUIError(esPatentService.validatePatent(MODULE,(ESPatentDetailsForm)target, rulesInformation, errors,flowModeId),errors);
		}

		validatePersonForm(target, rulesInformation, errors, flowModeId);

		// APPLICATION CORRESPONDENCE ADDRESS
		if (target instanceof ApplicationCAForm) {
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, AvailableSection.APPLICATION_CA.value());
			errorFactory.coreErrorToUIError(designService.validateApplicationCA(MODULE, (ApplicationCAForm) target, rulesInformation, errors, flowModeId),errors);
		}

		//LICENCE
		if(target instanceof LicenceForm) {
			rulesInformation.getCustomObjects().put("flowModeId", flowModeId);
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, AvailableSection.LICENCE.value());
			errorFactory.coreErrorToUIError(tradeMarkService.validateLicence(MODULE,(LicenceForm)target, rulesInformation, errors, flowModeId), errors);
		}

		//APPEAL
		if(target instanceof AppealForm) {
			rulesInformation.getCustomObjects().put("flowModeId", flowModeId);
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "appeal");
			errorFactory.coreErrorToUIError(tradeMarkService.validateAppeal(MODULE,(AppealForm)target, rulesInformation, errors, flowModeId), errors);
		}

		//DOCUMENT
		if (target instanceof StoredFile) {
			StoredFile storedFile = (StoredFile)target;
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, AvailableSection.OTHER_ATTACHMENTS.value());
			rulesInformation.getCustomObjects().put("docType", storedFile != null && storedFile.getDocType() != null ? storedFile.getDocType().value() : "");
			errorFactory.coreErrorToUIError(designService.validateStoredFile(MODULE, storedFile, rulesInformation, errors, flowModeId), errors);
		}
	}

	private void validatePersonForm(Validatable target, RulesInformation rulesInformation, Errors errors, String flowModeId){
		//APPLICANT VALIDATIONS
		if (target instanceof NaturalPersonForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "applicant_naturalperson");
			errorFactory.coreErrorToUIError(personService.validateApplicant(MODULE,(ApplicantForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof LegalEntityForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "applicant_legalentity");
			errorFactory.coreErrorToUIError(personService.validateApplicant(MODULE,(ApplicantForm)target, rulesInformation, errors,flowModeId),errors);
		}

		//REPRESENTATIVE VALIDATIONS
		if (target instanceof ChangeRepresentativeNaturalPersonForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_naturalperson");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ChangeRepresentativeLegalEntityForm) {
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_legalentity");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId), errors);
		}else if (target instanceof ChangeRepresentativeAssociationForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_form_association");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ChangeProfessionalPractitionerForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_professionalpractitioner");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ChangeEmployeeRepresentativeForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_employeerepresentative");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ChangeLegalPractitionerForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_legalpractitioner");
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ChangeRepresentativeAddressForm){
			if (ChangePersonType.CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS.equals(((ChangeRepresentativeAddressForm)target).getChangeType())) {
				rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "personChange_correspondenceAddress");
			} else {
				rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "personChange_address");
			}
			errorFactory.coreErrorToUIError(personService.validatePersonChange(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof RepresentativeNaturalPersonForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_naturalperson");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof RepresentativeLegalEntityForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_legalentity");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof RepresentativeAssociationForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_form_association");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof ProfessionalPractitionerForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_professionalpractitioner");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof EmployeeRepresentativeForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_employeerepresentative");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof LegalPractitionerForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_legalpractitioner");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof LawyerCompanyForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_lawyercompany");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof LawyerAssociationForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_lawyerassociation");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof RepresentativeTemporaryForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_temporary");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		} else if (target instanceof IntlPRepresentativeForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "representative_intlprepresentative");
			errorFactory.coreErrorToUIError(personService.validateRepresentative(MODULE,(RepresentativeForm)target, rulesInformation, errors,flowModeId),errors);
		}

		//HOLDER VALIDATIONS
		if (target instanceof HolderNaturalPersonForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "holder_naturalperson");
			errorFactory.coreErrorToUIError(personService.validateHolder(MODULE,(HolderForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof HolderLegalEntityForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "holder_legalentity");
			errorFactory.coreErrorToUIError(personService.validateHolder(MODULE,(HolderForm)target, rulesInformation, errors,flowModeId),errors);
		}

		//ASSIGNEE VALIDATIONS
		if (target instanceof AssigneeNaturalPersonForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "assignee_naturalperson");
			errorFactory.coreErrorToUIError(personService.validateAssignee(MODULE,(AssigneeForm)target, rulesInformation, errors,flowModeId),errors);
		}else if (target instanceof AssigneeLegalEntityForm){
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "assignee_legalentity");
			errorFactory.coreErrorToUIError(personService.validateAssignee(MODULE,(AssigneeForm)target, rulesInformation, errors,flowModeId),errors);
		}

		//DESIGNER VALIDATIONS
		if (target instanceof DesignerForm) {
			rulesInformation.getCustomObjects().put(EServicesConstants.SECT_NM, "designer_notAGroup");
			errorFactory.coreErrorToUIError(personService.validateDesigner(MODULE, (DesignerForm) target, rulesInformation, errors, flowModeId), errors);
		}
	}


	private void validateApplicationPersons(ESFlowBeanImpl flowBean, RulesInformation rulesInformation,
												 Errors errors, String flowModeId) {
		if(!CollectionUtils.isEmpty(flowBean.getApplicants())){
			flowBean.getApplicants().stream().forEach(person -> validatePersonForm(person, rulesInformation, errors, flowModeId));
		}

		if(!CollectionUtils.isEmpty(flowBean.getRepresentatives())){
			flowBean.getRepresentatives().stream().forEach(person -> validatePersonForm(person, rulesInformation, errors, flowModeId));
		}

		if(!CollectionUtils.isEmpty(flowBean.getAssignees())){
			flowBean.getAssignees().stream().forEach(person -> validatePersonForm(person, rulesInformation, errors, flowModeId));
		}
	}

}
