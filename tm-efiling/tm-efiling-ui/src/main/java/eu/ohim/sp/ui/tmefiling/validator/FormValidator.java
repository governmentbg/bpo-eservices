/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.validator;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.*;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ConversionForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.claim.PriorityForm;
import eu.ohim.sp.common.ui.form.claim.SeniorityForm;
import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.ForeignRegistrationForm;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.Signatory;
import eu.ohim.sp.core.domain.claim.ConversionPriority;
import eu.ohim.sp.core.domain.claim.ExhibitionPriority;
import eu.ohim.sp.core.domain.claim.Seniority;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.register.IPApplicationService;
import eu.ohim.sp.core.register.TradeMarkSearchService;
import eu.ohim.sp.ui.tmefiling.adapter.FlowBeanFactory;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.service.interfaces.ApplicationServiceInterface;
import eu.ohim.sp.ui.tmefiling.util.MarkViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.webflow.execution.RequestContextHolder;


@Component
public class FormValidator extends eu.ohim.sp.common.ui.validator.FormValidator implements Validator {

	private static final String STATE_ID = "stateId";
	
	private static final String SECTION_NAME = "sectionName";
	
	private static final String MODULE = "tmefiling";
	
	private static final String VALIDATE_WIZARD_STEP0 = "wiz0";
	
	private static final String VALIDATE_WIZARD_STEP1 = "wiz1";
	
	private static final String VALIDATE_WIZARD_STEP2 = "wiz2";
	
	private static final String FLOW_MODE_ID_CONF = "-conf";
	private static final String FLOW_MODE_ID = "flowModeId";

    @Autowired
	ApplicationServiceInterface applicationService;
	
	@Autowired
	private FlowBeanFactory flowBeanFactory;
	
	@Autowired
	private PersonServiceInterface personService;
	
	@Autowired
    private ErrorFactory errorFactory;
	
	@Autowired
	private DocumentFactory documentFactory;

    @Autowired
    private ContactDetailsFactory contactDetailsFactory;

	@Autowired
	private ConfigurationServiceDelegatorInterface configurationServiceDelegator;
	
	@Autowired
	private IPApplicationService ipApplicationService;
	
	@Autowired
    private DocumentService documentService;

    @Autowired
    private TradeMarkSearchService tradeMarkService;

    @Autowired
	private MarkViewFactory markViewFactory;

	@Autowired
	private ForeignRegistrationFactory foreignRegistrationFactory;
	
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
    @Override
    public void validate(Validatable target, Errors errors, FlowScopeDetails flowScopeDetails) {
    	try {
    		RulesInformation rulesInformation = new RulesInformation();
			String flowModeId = getFlowModeId(flowScopeDetails);
			String stateId = getStateId(flowScopeDetails);	
	    	putStateId(rulesInformation, stateId);
			putFlowModeId(rulesInformation, flowModeId);
			rulesInformation.getCustomObjects().put("isSectionValidation", false);
	    	
			ErrorList errorList = new ErrorList();
			
			// Add sectionName
			if (target.getAvailableSectionName() != null) {
				putSectionName(rulesInformation, target.getAvailableSectionName());
			}
			
			if (target instanceof FlowBeanImpl) {
				rulesInformation.getCustomObjects().put("isSectionValidation", true);
				FlowBeanImpl flowBeanImpl = (FlowBeanImpl) target;
				errorList.addAllErrors(validateApplicationPersons(flowBeanImpl, rulesInformation, errors, flowModeId));
				rulesInformation.getCustomObjects().clear();

				validateApplicationForeignRegistrations(errors, rulesInformation, flowModeId, errorList, flowBeanImpl);

				if(flowBeanImpl.getMarkViews() != null){
					for(MarkViewForm viewForm: flowBeanImpl.getMarkViews()){
						ErrorList errList = validateMarkView(viewForm, rulesInformation, errors, flowModeId);
						if(errList.getErrorList() != null && errList.getErrorList().size()>0){
							errorList.addAllErrors(errList);
							break;
						}
					}
				}
				rulesInformation.getCustomObjects().clear();
				putFlowModeId(rulesInformation, flowModeId);
				putCountryList(rulesInformation);
				errorList.addAllErrors(applicationService.validateApplication(flowBeanImpl, rulesInformation, flowModeId,stateId));
			} else if (target instanceof AbstractForm) {
				putImported(rulesInformation, false);
				errorList.addAllErrors(validateForm((AbstractForm) target, rulesInformation, errors, flowModeId));
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
	 * @return
	 */
	private ErrorList validateApplicationPersons(FlowBeanImpl flowBean, RulesInformation rulesInformation, Errors errors, String flowModeId)  {
		ErrorList errorList = new ErrorList();
		String stateId = "";
    	ErrorList resultErrorList;		
		
		// Prepares the objects to insert in the session
		if(rulesInformation.getCustomObjects().containsKey(STATE_ID)){
			stateId = (String) rulesInformation.getCustomObjects().get(STATE_ID);
	    	// We use the stateId to specify that we have to show the message in the red box (general message)
	    	if   (!VALIDATE_WIZARD_STEP0.equals(stateId) 
	    			&& !VALIDATE_WIZARD_STEP1.equals(stateId)
	    			&& !VALIDATE_WIZARD_STEP2.equals(stateId)) {
	    		// Import validations
	        	putImported(rulesInformation, true);
	
	        	//Validate Applicants
		    	for (AbstractForm af : flowBean.getApplicants()) {
		    		resultErrorList = validateForm(af, rulesInformation, errors, flowModeId);
		    		if (resultErrorList != null) {
		    			errorList.addAllErrors(resultErrorList);
		    		}
		    	}
		    	// Validate Representatives
		    	for (AbstractForm af : flowBean.getRepresentatives()){
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
	 * @param target
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateForm(AbstractForm target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		if (target instanceof PriorityForm || target instanceof ExhPriorityForm || target instanceof TransformationForm || target instanceof SeniorityForm || target instanceof ConversionForm) {
			return validateClaim(target, rulesInformation, errors, flowModeId);
		} else if (target instanceof PersonForm) {
			return validatePerson((PersonForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof ApplicationCAForm) {
            return validateApplicationCA ((ApplicationCAForm) target, rulesInformation, errors, flowModeId);
        } else if (target instanceof SignatureForm) {
            return validateSignature ((SignatureForm) target, rulesInformation, errors, flowModeId);
        } else if (target instanceof MarkViewForm) {
			return validateMarkView ((MarkViewForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof ForeignRegistrationForm) {
			return validateForeignRegistration((ForeignRegistrationForm) target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
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
		ErrorList errorList = new ErrorList();
    	
		if (person instanceof ApplicantForm) {
	    	errorList.addAllErrors(validateApplicant((ApplicantForm) person, rulesInformation, errors, flowModeId));
	    } else if (person instanceof RepresentativeForm) {
	    	errorList.addAllErrors(validateRepresentative((RepresentativeForm) person, rulesInformation, errors, flowModeId));
		}
		
		return errorList;
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
			putSectionName(rulesInformation, AvailableSection.APPLICANT_LEGALENTITY);
		} else if (applicant instanceof NaturalPersonForm) {
			putSectionName(rulesInformation, AvailableSection.APPLICANT_NATURALPERSON);
		} else if (applicant instanceof NaturalPersonSpecialForm) {
			putSectionName(rulesInformation, AvailableSection.APPLICANT_NATURALPERSONSPECIAL);
		} else {
			return null;
		}
		
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
     * @param applicant
     * @param rulesInformation
     * @param errors
     * @param flowModeId
     * @return
     */
    private ErrorList validateApplicationCA(ApplicationCAForm applicant, RulesInformation rulesInformation, Errors errors, String flowModeId) {
        putSections(rulesInformation, flowModeId);
        putSectionName(rulesInformation, AvailableSection.APPLICATION_CA);
        ContactDetails correspondent = contactDetailsFactory.convertTo(applicant.getCorrespondenceAddressForm());
        return tradeMarkService.validateApplicationCA(MODULE, correspondent, rulesInformation);
    }

    /**
     *
     * @param signature
     * @param rulesInformation
     * @param errors
     * @param flowModeId
     * @return
     */
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
	private ErrorList validateClaim(Validatable target, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSections(rulesInformation, flowModeId);
		if (target instanceof PriorityForm) {
			return validatePriority((PriorityForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof ExhPriorityForm) {
	    	return validateExhibition((ExhPriorityForm) target, rulesInformation, errors, flowModeId);
	    } else if (target instanceof TransformationForm) {
	    	return validateTransformation((TransformationForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof SeniorityForm) {
			return validateSeniority((SeniorityForm) target, rulesInformation, errors, flowModeId);
		} else if (target instanceof ConversionForm) {
			return validateConversion((ConversionForm) target, rulesInformation, errors, flowModeId);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param priorityForm
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validatePriority(PriorityForm priorityForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.PRIORITY);
		UIFactory factory = flowBeanFactory.getFactory(priorityForm.getClass());
		Priority priority = (Priority) factory.convertTo(priorityForm);
		return ipApplicationService.validatePriorityClaim(MODULE, priority, rulesInformation);
	}
	
	/**
	 * 
	 * @param exhibitionForm
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateExhibition(ExhPriorityForm exhibitionForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.EXHIBITION);
		UIFactory factory = flowBeanFactory.getFactory(exhibitionForm.getClass());
		ExhibitionPriority exhibitionPriority = (ExhibitionPriority) factory.convertTo(exhibitionForm);
		return ipApplicationService.validateExhibitionPriorityClaim(MODULE, exhibitionPriority, rulesInformation);
	}
	
	/**
	 * 
	 * @param transformationForm
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateTransformation(TransformationForm transformationForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.TRANSFORMATION);
		UIFactory factory = flowBeanFactory.getFactory(transformationForm.getClass());
		TransformationPriority transformationPriority = (TransformationPriority) factory.convertTo(transformationForm);
		return ipApplicationService.validateTransformation(MODULE, transformationPriority, rulesInformation);
	}
	
	
	private ErrorList validateConversion(ConversionForm conversionForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.CONVERSION);
		UIFactory factory = flowBeanFactory.getFactory(conversionForm.getClass());
		ConversionPriority conversionPriority = (ConversionPriority) factory.convertTo(conversionForm);
		return ipApplicationService.validateConversion(MODULE, conversionPriority, rulesInformation);
	}
	/**
	 * 
	 * @param seniorityForm
	 * @param rulesInformation
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	private ErrorList validateSeniority(SeniorityForm seniorityForm, RulesInformation rulesInformation, Errors errors, String flowModeId) {
		putSectionName(rulesInformation, AvailableSection.SENIORITY);
		UIFactory factory = flowBeanFactory.getFactory(seniorityForm.getClass());
		Seniority seniority = (Seniority) factory.convertTo(seniorityForm);
		return ipApplicationService.validateSeniorityClaim(MODULE, seniority, rulesInformation);
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
		putSections(rulesInformation, flowModeId);
    	Document document = documentFactory.convertTo(storedFile);
    	rulesInformation.getCustomObjects().put("docType", storedFile != null && storedFile.getDocType() != null ? storedFile.getDocType().value() : "");
    	return documentService.validateDocument(MODULE, document, rulesInformation);
	}

	private ErrorList validateMarkView(MarkViewForm form,  RulesInformation rulesInformation, Errors errors, String flowModeId){
		putSectionName(rulesInformation, AvailableSection.fromValue(MarkViewUtil.createViewsSectionId(form.getMarkType())));
		ImageSpecification imageSpecification = markViewFactory.convertTo(form);
		putSections(rulesInformation, flowModeId);
		return tradeMarkService.validateMarkView(MODULE, imageSpecification, rulesInformation);
	}

	private void validateApplicationForeignRegistrations(Errors errors, RulesInformation rulesInformation, String flowModeId, ErrorList errorList, FlowBeanImpl flowBeanImpl) {
		if(flowBeanImpl.getForeignRegistrations() != null && flowBeanImpl.getForeignRegistrations().size()>0){
			rulesInformation.getCustomObjects().put("isSectionValidation", true);
			for(ForeignRegistrationForm foreignReg: flowBeanImpl.getForeignRegistrations()) {
				errorList.addAllErrors(validateForeignRegistration(foreignReg, rulesInformation, errors, flowModeId));
			}
			rulesInformation.getCustomObjects().clear();
		}
	}

	private ErrorList validateForeignRegistration(ForeignRegistrationForm form, RulesInformation rulesInformation, Errors errors, String flowModeId){
		putSectionName(rulesInformation, AvailableSection.FOREIGN_REGISTRATION);
		ForeignRegistration foreignRegistration = foreignRegistrationFactory.convertTo(form);
		putSections(rulesInformation, flowModeId);
		return tradeMarkService.validateForeignRegistration(MODULE, foreignRegistration, rulesInformation);
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
		rulesInformation.getCustomObjects().put(SECTION_NAME, section.value());
	}
	
	/**
	 * 
	 * @param rulesInformation
	 * @param stateId
	 */
	private void putStateId(RulesInformation rulesInformation, String stateId) {
		rulesInformation.getCustomObjects().put(STATE_ID, stateId);
	}

	private void putFlowModeId(RulesInformation rulesInformation, String flowModeId) {
		rulesInformation.getCustomObjects().put(FLOW_MODE_ID, flowModeId);
	}
	
	/**
	 * 
	 * @param rulesInformation
	 * @param imported
	 */
	private void putImported(RulesInformation rulesInformation, boolean imported) {
		rulesInformation.getCustomObjects().put("imported", imported);
	}
	
	/**
	 * Add the list of countries to the rules information.
	 * @param rulesInformation
	 */
	private void putCountryList(RulesInformation rulesInformation) {
		rulesInformation.getCustomObjects().put("countries", configurationServiceDelegator.getCountriesObject());
	}
	
	/**
	 * Add the list of sections to the rules information.
	 * @param rulesInformation
	 * @param flowModeId
	 */
	private void putSections(RulesInformation rulesInformation,
			String flowModeId) {
		Sections sections = configurationServiceDelegator.getObjectFromComponent(flowModeId + FLOW_MODE_ID_CONF, MODULE, Sections.class);
		if (sections != null) {
			rulesInformation.getCustomObjects().put("sections", sections);
		}
	}

}
