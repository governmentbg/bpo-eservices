package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;

/**
 * Created by Raya
 * 17.06.2019
 */
public interface BPOUMSearchService {

    Patent getUtilityModelByAppNumber(String appNum);
}
