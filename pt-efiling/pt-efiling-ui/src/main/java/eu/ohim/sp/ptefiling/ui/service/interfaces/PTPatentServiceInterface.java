package eu.ohim.sp.ptefiling.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.common.ui.form.patent.*;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import org.springframework.validation.Errors;

/**
 * Created by Raya
 * 12.07.2019
 */
public interface PTPatentServiceInterface {

    ErrorList validateDivisionalApplication(String module, PTDivisionalApplicationForm form, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validatePriority(String module, PTPriorityForm form, RulesInformation rules, Errors errors, String flowModeId);
    ErrorList validateTransformation(String module, PTTransformationForm form, RulesInformation rules, Errors errors, String flowModeId);
    ErrorList validateParallelApplication(String module, PTParallelApplicationForm form, RulesInformation rules, Errors errors, String flowModeId);
    ErrorList validateExhibition(String module, ExhPriorityForm form, RulesInformation rules, Errors errors, String flowModeId);
    ErrorList validatePCT(String module, PCTForm form, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors,
                                 String flowModeId);

    ErrorList validatePatentView(String module, PatentViewForm form, RulesInformation rules, Errors errors, String flowModeId);

    ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId);

    ESPatentDetailsForm getNationalPatent(String applicationId);
    ESPatentDetailsForm getEPOPatent(String applicationId);

    ErrorList validatePatent(String module, ESPatentDetailsForm patentForm,
                             RulesInformation rulesInformation, Errors errors, String flowModeId);

    String autocompletePatent(String office, String word, int rows, String flowModeId);

}
