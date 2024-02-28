package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;

/**
 * Created by Raya
 * 04.07.2019
 */
public interface BPOEBDPatentSearchService {

    Patent getPatentByAppNumber(String appNum);
    Patent getPatentByRegNumber(String regNum);
}
