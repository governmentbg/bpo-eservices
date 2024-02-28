package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;

/**
 * Created by Raya
 * 21.10.2019
 */
public interface BPOEPOPatentSearchService {

    Patent getPatentByAppNumber(String appNum);
    Patent getPatentByRegNumber(String regNum);
}
