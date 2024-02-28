package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Raya
 * 18.06.2019
 */
@Stateless
public class BPOUMSearchServiceBean implements BPOUMSearchServiceLocal, BPOUMSearchServiceRemote{

    private Logger logger = Logger.getLogger(BPOUMSearchServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private BPOUMSearchService adapter;

    @Override
    public Patent getUtilityModelByAppNumber(String appNum) {
        return adapter.getUtilityModelByAppNumber(appNum);
    }
}
