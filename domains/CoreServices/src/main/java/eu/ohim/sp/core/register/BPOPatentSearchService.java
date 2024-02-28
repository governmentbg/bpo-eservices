package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * Created by Raya
 * 17.06.2019
 */
public interface BPOPatentSearchService {

    Patent getPatentByAppNumber(String appNum);
    ErrorList validatePatent(String module, Patent patent, RulesInformation rulesInformation);
}
