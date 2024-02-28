package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 12.04.2022
 * Time: 14:41
 */
@Stateless
public class BPOPlantSearchServiceBean implements BPOPlantSearchServiceLocal, BPOPlantSearchServiceRemote {

    private Logger logger = Logger.getLogger(BPOSPCSearchServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private BPOPlantSearchService adapter;

    @Override
    public Patent getPlantByAppNumber(String appNum) {
        return adapter.getPlantByAppNumber(appNum);
    }
}
