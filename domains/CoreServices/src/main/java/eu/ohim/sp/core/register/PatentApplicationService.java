package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.patent.*;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * Created by Raya
 * 12.07.2019
 */
public interface PatentApplicationService {

    ErrorList validatePatentTransformation(String module, PatentTransformation transformation, RulesInformation rulesInformation);
    ErrorList validatePCT(String module, PCT pct, RulesInformation rulesInformation);
    ErrorList validateParallelApplication(String module, ParallelApplication parallelApplication, RulesInformation rulesInformation);
    ErrorList validateDivisionalApplication(String module, DivisionalApplicationDetails divisional, RulesInformation rulesInformation);


    ErrorList validatePatentView(String module, PatentView patentView, RulesInformation rulesInformation);
    ErrorList validateApplicationCA(String module, ContactDetails applicationCA, RulesInformation rulesInformation);

}
