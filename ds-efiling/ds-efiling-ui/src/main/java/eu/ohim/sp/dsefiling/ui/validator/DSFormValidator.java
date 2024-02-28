package eu.ohim.sp.dsefiling.ui.validator;

import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.person.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.webflow.execution.RequestContextHolder;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.design.DSDesignForm;
import eu.ohim.sp.common.ui.form.design.DSDivisionalApplicationForm;
import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.design.LocarnoComplexForm;
import eu.ohim.sp.common.ui.form.design.LocarnoForm;
import eu.ohim.sp.common.ui.form.design.LocarnoSearchForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.dsefiling.ui.domain.DSFlowBeanImpl;
import eu.ohim.sp.dsefiling.ui.service.DSDesignsService;
import eu.ohim.sp.dsefiling.ui.service.interfaces.DSApplicationServiceInterface;
import eu.ohim.sp.core.domain.validation.ErrorCore;

import java.util.ArrayList;

@Component(value="formValidator")
public class DSFormValidator extends FormValidator{
	
	private static final String MODULE = "dsefiling";

	private static final String VALIDATE_WIZARD_STEP0 = "wiz0";
	
	private static final String VALIDATE_WIZARD_STEP1 = "wiz1";
		
	private static final String VALIDATE_WIZARD_STEP3 = "wiz3";
	
	private static final String VALIDATE_ONEFORM_STEP1 = "oneform";	
	
	private static final String VALIDATE_ONEFORM_STEP2 = "review";	
	
	private static final String STATE_ID = "stateId";		
	
	@Autowired
	private DSApplicationServiceInterface dsApplicationService;
	
	@Autowired
	private PersonServiceInterface personService;

	@Autowired
	private DSDesignsService designsService;
	
    @Autowired
    private ErrorFactory errorFactory;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

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
	 * @param flowScopeDetails the flow scope details of the current application
	 */
	@Override
	public void validate(Validatable target, Errors errors, FlowScopeDetails flowScopeDetails) {
		try {
			RulesInformation rulesInformation = new RulesInformation();
			String flowModeId = getFlowModeId(flowScopeDetails);
			String stateId = getStateId(flowScopeDetails);	
	    	putStateId(rulesInformation, stateId);
			rulesInformation.getCustomObjects().put("isSectionValidation", false);
	    	
			ErrorList errorList = new ErrorList();
			
		    if (target instanceof DSFlowBeanImpl) {
				rulesInformation.getCustomObjects().put("isSectionValidation", true);
		    	DSFlowBeanImpl dsFlowBean = (DSFlowBeanImpl) target;
		    	errorList.addAllErrors(validateApplicationPersons(dsFlowBean, rulesInformation, errors, flowModeId));
		    	errorList.addAllErrors(validateApplicationDesigners(dsFlowBean, rulesInformation, errors, flowModeId));	
		    	rulesInformation.getCustomObjects().clear();		    	
		    	errorList.addAllErrors(validateApplication(dsFlowBean, rulesInformation, flowModeId, stateId));

				dsFlowBean.setWarningsMessage(new ArrayList<>());
				for(ErrorCore errorCore:errorList.getErrorList()) {
					if (errorCore.isWarning()){
						dsFlowBean.getWarningsMessage().add(errorCore.getErrorCode());
					}
				}
		    } else if (target instanceof AbstractForm) {
		    	putImported(rulesInformation, false);
		    	errorList = validateForm((AbstractForm) target, rulesInformation, errors, flowModeId);
			} else if (target instanceof StoredFile) {
				errorList = validateStoredFile((StoredFile) target, rulesInformation, errors, flowModeId);
			}
			
			if (errorList != null && errorList.getErrorList().size() > 0) {
				errorFactory.coreErrorToUIError(errorList, errors);
			}


		} catch(Exception e) {
			throw new SPException("Failed to validate object", e, "error.genericError");
		}
	}
	

	/**
	 * 
	 * @param flowBean
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @param stateId
	 * @return
	 */
	private ErrorList validateApplicationPersons(DSFlowBeanImpl flowBean, RulesInformation rulesInformation, Errors errors, String flowModeId)  {
		ErrorList errorList = new ErrorList();
		String stateId = "";
    	ErrorList resultErrorList;		
		
		// Prepares the objects to insert in the session
		if(rulesInformation.getCustomObjects().containsKey(STATE_ID)){
			stateId = (String) rulesInformation.getCustomObjects().get(STATE_ID);
	    	// We use the stateId to specify that we have to show the message in the red box (general message)
	    	if   (!VALIDATE_WIZARD_STEP0.equals(stateId) && !VALIDATE_WIZARD_STEP1.equals(stateId)) {
	    		// Import validations
	        	putImported(rulesInformation, true);
	
	        	//Validate persons ( Applicants - Representatives )
		    	for (AbstractForm af : flowBean.getPersons()) {
		    		resultErrorList = validateForm(af, rulesInformation, errors, flowModeId);
		    		if (resultErrorList != null) {
		    			errorList.addAllErrors(resultErrorList);
		    		}
		    	}
		    	for (AbstractForm af : flowBean.getMainForm().getCorrespondanceAddresses()){
		    		resultErrorList = validateForm(af, rulesInformation, errors, flowModeId);
		    		if (resultErrorList != null) {
		    			errorList.addAllErrors(resultErrorList);
		    		}
		    	}
	    	}
		}
    	
    	return errorList;
	}
	
	/**
	 * 
	 * @param flowBean
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @param stateId
	 * @return
	 */
	private ErrorList validateApplicationDesigners(DSFlowBeanImpl flowBean, RulesInformation rulesInformation, Errors errors, String flowModeId)  {
		ErrorList errorList = new ErrorList();
		String stateId = "";
    	ErrorList resultErrorList;
    	
		// Prepares the objects to insert in the session
		if(rulesInformation.getCustomObjects().containsKey(STATE_ID)){
			stateId = (String) rulesInformation.getCustomObjects().get(STATE_ID);
	    	// We use the stateId to specify that we have to show the message in the red box (general message)
	    	if   (!VALIDATE_WIZARD_STEP0.equals(stateId) && !VALIDATE_WIZARD_STEP1.equals(stateId)) {
	    		// Import validations
	        	putImported(rulesInformation, true);
		    	
		    	//Validate Designers		    	
		    	if (VALIDATE_WIZARD_STEP3.equals(stateId) || VALIDATE_ONEFORM_STEP1.equals(stateId) || VALIDATE_ONEFORM_STEP2.equals(stateId)){
			    	for (AbstractForm af : flowBean.getDesigners()) {
			    		resultErrorList = validateForm(af, rulesInformation, errors, flowModeId);
			    		if (resultErrorList != null) {
			    			errorList.addAllErrors(resultErrorList);
			    		}
			    	}	
	    		}
	    	}
		}
    	
    	return errorList;
	}	
	
	/**
	 * 
	 * @param flowScopeDetails
	 * @return
	 */
	private String getFlowModeId(FlowScopeDetails flowScopeDetails) {
		String flowModeId = flowScopeDetails.getFlowModeId();
		if (flowModeId == null && RequestContextHolder.getRequestContext() != null  && RequestContextHolder.getRequestContext().getActiveFlow() != null) {
			flowModeId = RequestContextHolder.getRequestContext().getActiveFlow().getId();
		}
		return flowModeId;
	}
	
	/**
	 * 
	 * @param flowScopeDetails
	 * @return
	 */
	private String getStateId(FlowScopeDetails flowScopeDetails) {
		String stateId = flowScopeDetails.getStateId();
		if (stateId == null && RequestContextHolder.getRequestContext() != null && RequestContextHolder.getRequestContext().getCurrentState() != null) {
			stateId = RequestContextHolder.getRequestContext().getCurrentState().getId();
		}
		return stateId;
	}
		
	/**
	 * 
	 * @param rulesInformation
	 * @param section
	 */
	private void putSectionName(RulesInformation rulesInformation, AvailableSection section) {
		rulesInformation.getCustomObjects().put("sectionName", section.value());
	}
	
	/**
	 * Add the list of countries to the rules information.
	 * @param rulesInformation
	 */
	private void putCountryList(RulesInformation rulesInformation) {
		rulesInformation.getCustomObjects().put("countries", configurationServiceDelegator.getCountriesObject());
	}
	
	/**
	 * 
	 * @param rulesInformation
	 * @param section
	 */
	private void putImported(RulesInformation rulesInformation, boolean imported) {
		rulesInformation.getCustomObjects().put("imported", imported);
	}
	
	/**
	 * 
	 * @param rulesInformation
	 * @param section
	 */
	private void putStateId(RulesInformation rulesInformation, String stateId) {
		rulesInformation.getCustomObjects().put(STATE_ID, stateId);
	}		
	
	/**
	 * 
	 * @param flowBean
	 * @param rulesInformation
	 * @param flowModeId
	 * @param stateId
	 * @return
	 */
	private ErrorList validateApplication(DSFlowBeanImpl flowBean, RulesInformation rulesInformation, String flowModeId, String stateId)  {
		putCountryList(rulesInformation);
		ErrorList errorList = dsApplicationService.validateApplication(flowBean, rulesInformation, flowModeId, stateId);
		return errorList != null ? errorList : new ErrorList();
	}

	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateForm(AbstractForm target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof DSDesignForm) {
			return validateDSDesignForm((DSDesignForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof DSDivisionalApplicationForm || target instanceof DSPriorityForm || target instanceof DSExhPriorityForm) {
			return validateClaim(target, rulesInformation, errors, flowModeId);
		} else if (target instanceof PersonForm) {
			return validatePerson((PersonForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof ApplicationCAForm) {
			return validateApplicationCA((ApplicationCAForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof SignatureForm) {
			return validateSignature ((SignatureForm) target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}

	private ErrorList validateSignature(SignatureForm signature, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.SIGNATURE_DETAILS_SECTION);
		return personService.validateSignature(MODULE, signature, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDSDesignForm(DSDesignForm target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof DesignForm || target instanceof DesignViewForm) {
			return validateDesignOrDesignView(target, rulesInformation, errors, flowModeId);
		} else if (target instanceof LocarnoForm || target instanceof LocarnoSearchForm  || target instanceof LocarnoComplexForm) {
			return validateLocarno(target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDesignOrDesignView(DSDesignForm target,
			RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof DesignForm) {
			return validateDesign((DesignForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof DesignViewForm) {
			return validateDesignView((DesignViewForm) target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateClaim(Validatable target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof DSDivisionalApplicationForm) {
	    	return validateDivisionalApplication((DSDivisionalApplicationForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof DSPriorityForm) {
	    	return validatePriority((DSPriorityForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof DSExhPriorityForm) {
	    	return validateExhibition((DSExhPriorityForm) target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param divisionalApplication
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDivisionalApplication(DSDivisionalApplicationForm divisionalApplication, RulesInformation rulesInformation, Errors errors, String flowModeId) {
    	putSectionName(rulesInformation, AvailableSection.DIVISIONAL_APPLICATION_SECTION);
    	return designsService.validateDivisionalApplication(MODULE, divisionalApplication, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param priority
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validatePriority(DSPriorityForm priority, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.PRIORITY);
    	return designsService.validatePriority(MODULE, priority, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param exhibition
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateExhibition(DSExhPriorityForm exhibition, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.EXHIBITION);
		return designsService.validateExhPriority(MODULE,exhibition, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param design
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDesign(DesignForm design, RulesInformation rulesInformation, Errors errors, String flowModeId) {
    	putSectionName(rulesInformation, AvailableSection.DESIGN);
    	return designsService.validateDesign(MODULE, design, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param designView
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDesignView(DesignViewForm designView, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.DESIGN_VIEWS);
		return designsService.validateDesignView(MODULE, designView, rulesInformation, errors, flowModeId);	
	}
	
	/**
	 * 
	 * @param locarno
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateLocarnoNewProduct(LocarnoForm locarno, RulesInformation rulesInformation, Errors errors, String flowModeId) {
    	putSectionName(rulesInformation, AvailableSection.LOCARNO_NEW_PRODUCT);
    	return designsService.validateLocarno(MODULE, locarno, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateLocarnoSearch(LocarnoSearchForm locarnoSearchForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.LOCARNO_ADD_CLASS);
		return designsService.validateLocarnoSearch(MODULE, locarnoSearchForm, rulesInformation, errors, flowModeId);
	}

	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateLocarnoComplex(LocarnoComplexForm locarnoComplexForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.LOCARNO_NEW_COMPLEX_PRODUCT);
		return designsService.validateLocarnoComplex(MODULE, locarnoComplexForm, rulesInformation, errors, flowModeId);
	}
	/**	
	 * 
	 * @param person
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validatePerson(PersonForm person, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putCountryList(rulesInformation);
		if (person instanceof ApplicantForm) {
	    	return validateApplicant((ApplicantForm) person, rulesInformation, errors, flowModeId);
	    } else if (person instanceof RepresentativeForm) {
	    	return validateRepresentative((RepresentativeForm) person, rulesInformation, errors, flowModeId);
		} else if (person instanceof DesignerForm) {
			return validateDesigner((DesignerForm) person, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param applicant
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateApplicant(ApplicantForm applicant, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (applicant instanceof LegalEntityForm) {
			return validateApplicantLegalEntity(applicant, rulesInformation, errors, flowModeId);
		} else if (applicant instanceof NaturalPersonForm) {
			return validateApplicantNaturalPerson(applicant, rulesInformation, errors, flowModeId);
		} else if (applicant instanceof UniversityForm) {
			return validateUniversity(applicant, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateLocarno(DSDesignForm target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof LocarnoForm) {
	    	return validateLocarnoNewProduct((LocarnoForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof LocarnoSearchForm) {
	    	return validateLocarnoSearch((LocarnoSearchForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof LocarnoComplexForm) {
	    	return validateLocarnoComplex((LocarnoComplexForm) target, rulesInformation, errors, flowModeId);
	    } else {
	    	return null;
	    }
		
	}
	
	/**
	 * 
	 * @param applicant
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateApplicantLegalEntity(ApplicantForm applicant, RulesInformation rulesInformation, Errors errors, String flowModeId) {
    	putSectionName(rulesInformation, AvailableSection.APPLICANT_LEGALENTITY);
		return personService.validateApplicant(MODULE, applicant, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param applicant
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateApplicantNaturalPerson(ApplicantForm applicant, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		
		putSectionName(rulesInformation, AvailableSection.APPLICANT_NATURALPERSON);
		
		return personService.validateApplicant(MODULE, applicant, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param applicant
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateUniversity (ApplicantForm applicant, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		
		putSectionName(rulesInformation, AvailableSection.APPLICANT_UNIVERSITY);
		
		return personService.validateApplicant(MODULE, applicant, rulesInformation, errors, flowModeId);
	}	

	/**
	 * 
	 * @param representative
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateRepresentative(RepresentativeForm representative, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, representative.getAvailableSectionName());
		return personService.validateRepresentative(MODULE, representative, rulesInformation, errors, flowModeId);
	}

	/**
	 * 
	 * @param designer
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateDesigner(DesignerForm designer, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (designer.isWaiver()) {
			putSectionName(rulesInformation, AvailableSection.DESIGNERS);
		} else if (designer.isBelongsToAGroup()) {
			putSectionName(rulesInformation, AvailableSection.DESIGNER_GROUP);
		} else {
			putSectionName(rulesInformation, AvailableSection.DESIGNER_NOT_A_GROUP);
		}
		return personService.validateDesigner(MODULE, designer, rulesInformation, errors, flowModeId);
	}

	/**
	 * 
	 * @param storedFile
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateStoredFile(StoredFile storedFile, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.OTHER_ATTACHMENTS);
		rulesInformation.getCustomObjects().put("docType", storedFile != null && storedFile.getDocType() != null ? storedFile.getDocType().value() : "");
		return designsService.validateStoredFile(MODULE, storedFile, rulesInformation, errors, flowModeId);
	}
	
	/**
	 * 
	 * @param applicationCA
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateApplicationCA(ApplicationCAForm applicationCA, RulesInformation rulesInformation, Errors errors, String flowModeId) {
	    putSectionName(rulesInformation, AvailableSection.APPLICATION_CA);
	    return designsService.validateApplicationCA(MODULE, applicationCA, rulesInformation, errors, flowModeId);
	}
	
}
