package eu.ohim.sp.eservices.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import org.springframework.validation.Errors;

/**
 * Created by Raya
 * 12.12.2019
 */
public interface ESPatentServiceInterface {

    ESPatentDetailsForm getPatent(String id, String flowModeId);

    String autocompletePatent(String word, int rows, String flowModeId);

    ErrorList validatePatent(String module, ESPatentDetailsForm patentForm,
                                RulesInformation rulesInformation, Errors errors, String flowModeId);
}
