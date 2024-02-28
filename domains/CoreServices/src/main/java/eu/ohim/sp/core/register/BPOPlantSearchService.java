package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.04.2022
 * Time: 13:34
 */
public interface BPOPlantSearchService {

    Patent getPlantByAppNumber(String appNum);
}
