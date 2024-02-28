package eu.ohim.sp.ptefiling.ui.validator;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.adapter.ErrorFactory;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.person.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.service.interfaces.PersonServiceInterface;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.common.ui.validator.Validatable;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBeanImpl;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTApplicationServiceInterface;
import eu.ohim.sp.ptefiling.ui.service.interfaces.PTPatentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.webflow.execution.RequestContextHolder;

/**
 * Created by Raya
 * 15.04.2019
 */
@Component(value="formValidator")
public class PTFormValidator extends FormValidator {

    private static final String MODULE = "ptefiling";

    private static final String STATE_ID = "stateId";
    private static final String FLOW_MODE_ID = "flowModeId";

    private static final String VALIDATE_WIZARD_STEP0 = "wiz0";

    private static final String VALIDATE_WIZARD_STEP1 = "wiz1";

    private static final String VALIDATE_WIZARD_STEP3 = "wiz3";

    @Autowired
    private PersonServiceInterface personService;

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationServiceDelegator;

    @Autowired
    private PTApplicationServiceInterface applicationService;

    @Autowired
    private ErrorFactory errorFactory;

    @Autowired
    private PTPatentServiceInterface ptPatentServiceInterface;


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

    @Override
    public void validate(Validatable target, Errors errors, FlowScopeDetails flowScopeDetails) {
        try {
            RulesInformation rulesInformation = new RulesInformation();
            String flowModeId = getFlowModeId(flowScopeDetails);
            String stateId = getStateId(flowScopeDetails);
            rulesInformation.getCustomObjects().put("isSectionValidation", false);

            ErrorList errorList = new ErrorList();
            if (target instanceof PTFlowBean) {
                rulesInformation.getCustomObjects().put("isSectionValidation", true);
                putStateId(rulesInformation, stateId);
                putFlowModeId(rulesInformation, flowModeId);

                PTFlowBean ptFlowBean = (PTFlowBeanImpl)target;
                errorList.addAllErrors(validateApplicationPersons(ptFlowBean, rulesInformation, errors, flowModeId));
                errorList.addAllErrors(validateApplicationInventors(ptFlowBean, rulesInformation, errors, flowModeId));
                rulesInformation.getCustomObjects().remove("sections");
                rulesInformation.getCustomObjects().remove("sectionName");
                errorList.addAllErrors(validateApplication(ptFlowBean, rulesInformation, flowModeId, stateId));

                ptFlowBean.getFormWarnings().clear();

            } else if (target instanceof AbstractForm) {
                putGeneralImported(target, rulesInformation);
                errorList = validateForm((AbstractForm) target, rulesInformation, errors, flowModeId);
            } else if (target instanceof StoredFile) {
                errorList = validateStoredFile((StoredFile) target, rulesInformation, errors, flowModeId);
            }

            if (errorList != null && errorList.getErrorList().size() > 0) {
                errorFactory.coreErrorToUIError(errorList, errors);
            }

        } catch (Exception e) {
            throw new SPException("Failed to validate object", e, "error.genericError");
        }
    }

    public void collectApplicationValidationWarnings(Validatable target, FlowScopeDetails flowScopeDetails) {
        RulesInformation rulesInformation = new RulesInformation();
        String flowModeId = getFlowModeId(flowScopeDetails);
        String stateId = getStateId(flowScopeDetails);
        ErrorList errorList = new ErrorList();
        if (target instanceof PTFlowBean) {
            rulesInformation.getCustomObjects().put("isSectionValidation", true);
            putStateId(rulesInformation, stateId);
            putFlowModeId(rulesInformation, flowModeId);

            PTFlowBean ptFlowBean = (PTFlowBeanImpl)target;
            ptFlowBean.getFormWarnings().clear();
            errorList.addAllErrors(validateApplication(ptFlowBean, rulesInformation, flowModeId, stateId));

            if(errorList.getErrorList() != null){
                errorList.getErrorList().stream().filter(err -> err.isWarning()).forEach(err -> ptFlowBean.getFormWarnings().add(err.getErrorCode()));
            }
        }
    }

    private ErrorList validateApplication(PTFlowBean flowBean, RulesInformation rulesInformation, String flowModeId,
                                          String stateId) {
        ErrorList errorList = applicationService.validateApplication(flowBean, rulesInformation, flowModeId, stateId);
        return errorList != null ? errorList : new ErrorList();
    }

    private ErrorList validateStoredFile(StoredFile storedFile, RulesInformation rulesInformation, Errors errors,
                                         String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.OTHER_ATTACHMENTS);
        rulesInformation.getCustomObjects().put("docType", storedFile != null && storedFile.getDocType() != null ? storedFile.getDocType().value() : "");
        return ptPatentServiceInterface.validateStoredFile(MODULE, storedFile, rulesInformation, errors, flowModeId);
    }

    private ErrorList validateApplicationPersons(PTFlowBean flowBean, RulesInformation rulesInformation,
                                                 Errors errors, String flowModeId) {
        ErrorList errorList = new ErrorList();
        String stateId = "";
        ErrorList resultErrorList;

        // Prepares the objects to insert in the session
        if (rulesInformation.getCustomObjects().containsKey(STATE_ID)) {
            stateId = (String) rulesInformation.getCustomObjects().get(STATE_ID);
            // We use the stateId to specify that we have to show the message in the red box (general message)
            if (!stateId.startsWith(VALIDATE_WIZARD_STEP0) && !stateId.startsWith(VALIDATE_WIZARD_STEP1)) {

                // Validate persons ( Applicants - Representatives )
                if(!flowBean.getApplicantsImportedFromTemplate()) {
                    for (AbstractForm af : flowBean.getApplicants()) {
                        resultErrorList = validateForm(af, rulesInformation, errors, flowModeId);
                        if (resultErrorList != null) {
                            errorList.addAllErrors(resultErrorList);
                        }
                    }
                }

                for (AbstractForm af : flowBean.getRepresentatives()) {
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

    private ErrorList validateApplicationInventors(PTFlowBean flowBean, RulesInformation rulesInformation,
                                                   Errors errors, String flowModeId) {
        ErrorList errorList = new ErrorList();
        String stateId = "";
        ErrorList resultErrorList;

        // Prepares the objects to insert in the session
        if (rulesInformation.getCustomObjects().containsKey(STATE_ID)) {
            stateId = (String) rulesInformation.getCustomObjects().get(STATE_ID);
            // We use the stateId to specify that we have to show the message in the red box (general message)
            if (!stateId.startsWith(VALIDATE_WIZARD_STEP0) && !stateId.startsWith(VALIDATE_WIZARD_STEP1)) {
                // Validate Designers
                if (VALIDATE_WIZARD_STEP3.equals(stateId) && !flowBean.isEarlierAppImported() && !flowBean.getPatentTemplateImported()) {
                    for (AbstractForm af : flowBean.getInventors()) {
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

    private ErrorList validateForm(AbstractForm target, RulesInformation rulesInformation, Errors errors,
                                   String flowModeId) {
        putGeneralImported(target, rulesInformation);
        if (target instanceof PTDivisionalApplicationForm || target instanceof PTPriorityForm
            || target instanceof PTTransformationForm || target instanceof PTParallelApplicationForm
            || target instanceof ExhPriorityForm || target instanceof PCTForm) {
            return validateClaim(target, rulesInformation, errors, flowModeId);
        } else if (target instanceof PersonForm) {
            return validatePerson((PersonForm) target, rulesInformation, errors, flowModeId);
        } else if (target instanceof PatentViewForm) {
            return validateView((PatentViewForm) target, rulesInformation, errors, flowModeId);
        }   else if (target instanceof ApplicationCAForm) {
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

    private ErrorList validateApplicationCA(ApplicationCAForm applicationCA, RulesInformation rulesInformation, Errors errors, String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.APPLICATION_CA);
        return ptPatentServiceInterface.validateApplicationCA(MODULE, applicationCA, rulesInformation, errors, flowModeId);
    }

    private ErrorList validateClaim(Validatable target, RulesInformation rulesInformation, Errors errors,
                                    String flowModeId) {
        if (target instanceof PTDivisionalApplicationForm) {
            return validateDivisionalApplication((PTDivisionalApplicationForm) target, rulesInformation, errors,
                flowModeId);
        } else if (target instanceof PTPriorityForm) {
            return validatePriority((PTPriorityForm) target, rulesInformation, errors, flowModeId);
        } else if (target instanceof PTTransformationForm) {
            return validateTransformation((PTTransformationForm) target, rulesInformation, errors, flowModeId);
        } else if(target instanceof PTParallelApplicationForm) {
            return validateParallelApplication((PTParallelApplicationForm) target, rulesInformation, errors, flowModeId);
        } else if(target instanceof ExhPriorityForm) {
            return validateExhibition((ExhPriorityForm) target, rulesInformation, errors, flowModeId);
        } else if(target instanceof PCTForm) {
            return validatePCT((PCTForm) target, rulesInformation, errors, flowModeId);
        } else {
            return null;
        }
    }

    private ErrorList validateDivisionalApplication(PTDivisionalApplicationForm divisionalApplication,
                                                    RulesInformation rulesInformation, Errors errors, String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.DIVISIONAL_APPLICATION_SECTION);
        return ptPatentServiceInterface.validateDivisionalApplication(MODULE, divisionalApplication, rulesInformation, errors,
            flowModeId);
    }


    private ErrorList validatePriority(PTPriorityForm priority, RulesInformation rulesInformation, Errors errors,
                                       String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.PRIORITY);
        return ptPatentServiceInterface.validatePriority(MODULE, priority, rulesInformation, errors, flowModeId);
    }


    private ErrorList validateTransformation(PTTransformationForm transformation, RulesInformation rulesInformation, Errors errors,
                                             String flowModeId) {
        putSectionName(rulesInformation, transformation.getAvailableSectionName());
        return ptPatentServiceInterface.validateTransformation(MODULE, transformation, rulesInformation, errors, flowModeId);
    }

    private ErrorList validateParallelApplication(PTParallelApplicationForm ptParallelApplicationForm, RulesInformation rulesInformation, Errors errors,
                                             String flowModeId) {
        putSectionName(rulesInformation, ptParallelApplicationForm.getAvailableSectionName());
        return ptPatentServiceInterface.validateParallelApplication(MODULE, ptParallelApplicationForm, rulesInformation, errors, flowModeId);
    }

    private ErrorList validateExhibition(ExhPriorityForm exhPriorityForm, RulesInformation rulesInformation, Errors errors,
                                             String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.EXHIBITION);
        return ptPatentServiceInterface.validateExhibition(MODULE, exhPriorityForm, rulesInformation, errors, flowModeId);
    }

    private ErrorList validatePCT(PCTForm pctForm, RulesInformation rulesInformation, Errors errors,
                                                  String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.PCT);
        return ptPatentServiceInterface.validatePCT(MODULE, pctForm, rulesInformation, errors, flowModeId);
    }

    private ErrorList validatePerson(PersonForm person, RulesInformation rulesInformation, Errors errors,
                                     String flowModeId) {
        putCountryList(rulesInformation);
        if (person instanceof ApplicantForm) {
            return validateApplicant((ApplicantForm) person, rulesInformation, errors, flowModeId);
        } else if (person instanceof RepresentativeForm) {
            return validateRepresentative((RepresentativeForm) person, rulesInformation, errors, flowModeId);
        } else if (person instanceof InventorForm) {
            return validateInventor((InventorForm) person, rulesInformation, errors, flowModeId);
        } else {
            return null;
        }
    }

    private ErrorList validateApplicant(ApplicantForm applicant, RulesInformation rulesInformation, Errors errors,
                                        String flowModeId) {
        if (applicant instanceof LegalEntityForm) {
            return validateApplicantLegalEntity(applicant, rulesInformation, errors, flowModeId);
        } else if (applicant instanceof NaturalPersonForm) {
            return validateApplicantNaturalPerson(applicant, rulesInformation, errors, flowModeId);
        } else {
            return null;
        }
    }

    private ErrorList validateApplicantLegalEntity(ApplicantForm applicant, RulesInformation rulesInformation,
                                                   Errors errors, String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.APPLICANT_LEGALENTITY);
        return personService.validateApplicant(MODULE, applicant, rulesInformation, errors, flowModeId);
    }


    private ErrorList validateApplicantNaturalPerson(ApplicantForm applicant, RulesInformation rulesInformation,
                                                     Errors errors, String flowModeId) {

        putSectionName(rulesInformation, AvailableSection.APPLICANT_NATURALPERSON);

        return personService.validateApplicant(MODULE, applicant, rulesInformation, errors, flowModeId);
    }


    private ErrorList validateRepresentative(RepresentativeForm representative, RulesInformation rulesInformation,
                                             Errors errors, String flowModeId) {
        putSectionName(rulesInformation, representative.getAvailableSectionName());
        return personService.validateRepresentative(MODULE, representative, rulesInformation,errors, flowModeId);
    }

    private ErrorList validateInventor(InventorForm inventorForm, RulesInformation rulesInformation, Errors errors,
                                       String flowModeId) {
        putSectionName(rulesInformation, AvailableSection.INVENTOR_NOT_A_GROUP);
        return personService.validateInventor(MODULE, inventorForm, rulesInformation, errors, flowModeId);
    }


    private ErrorList validateView(PatentViewForm target, RulesInformation rulesInformation, Errors errors,
                                   String flowModeId){
        putSectionName(rulesInformation, AvailableSection.PATENT_VIEW);
        return ptPatentServiceInterface.validatePatentView(MODULE, target, rulesInformation, errors, flowModeId);
    }


    private void putCountryList(RulesInformation rulesInformation) {
        rulesInformation.getCustomObjects().put("countries", configurationServiceDelegator.getCountriesObject());
    }

    private String getFlowModeId(FlowScopeDetails flowScopeDetails) {
        String flowModeId = flowScopeDetails.getFlowModeId();
        if (flowModeId == null && RequestContextHolder.getRequestContext() != null
            && RequestContextHolder.getRequestContext().getActiveFlow() != null) {
            flowModeId = RequestContextHolder.getRequestContext().getActiveFlow().getId();
        }
        return flowModeId;
    }

    private String getStateId(FlowScopeDetails flowScopeDetails) {
        String stateId = flowScopeDetails.getStateId();
        if (stateId == null && RequestContextHolder.getRequestContext() != null
            && RequestContextHolder.getRequestContext().getCurrentState() != null) {
            stateId = RequestContextHolder.getRequestContext().getCurrentState().getId();
        }
        return stateId;
    }

    private void putStateId(RulesInformation rulesInformation, String stateId) {
        rulesInformation.getCustomObjects().put(STATE_ID, stateId);
    }

    private void putFlowModeId(RulesInformation rulesInformation, String flowModeId){
        rulesInformation.getCustomObjects().put(FLOW_MODE_ID, flowModeId);
    }

    private void putSectionName(RulesInformation rulesInformation, AvailableSection section) {
        rulesInformation.getCustomObjects().put("sectionName", section.value());
    }
    private void putGeneralImported(Validatable target, RulesInformation rulesInformation) {
        if (target instanceof AbstractImportableForm) {
            rulesInformation.getCustomObjects().put("imported", ((AbstractImportableForm)target).getImported());
        } else {
            rulesInformation.getCustomObjects().put("imported", false);
        }
    }
}
