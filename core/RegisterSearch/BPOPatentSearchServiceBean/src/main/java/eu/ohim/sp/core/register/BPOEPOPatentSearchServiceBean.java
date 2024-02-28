package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Raya
 * 30.10.2019
 */
@Stateless
public class BPOEPOPatentSearchServiceBean implements BPOEPOPatentSearchServiceLocal, BPOEPOPatentSearchServiceRemote {

    private Logger logger = Logger.getLogger(BPOEPOPatentSearchServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private BPOEPOPatentSearchService adapter;

    @Override
    public Patent getPatentByAppNumber(String appNum) {
        return adapter.getPatentByAppNumber(appNum);
    }

    @Override
    public Patent getPatentByRegNumber(String regNum) {
        return adapter.getPatentByRegNumber(regNum);
    }
}
