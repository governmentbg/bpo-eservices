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
public class BPOSPCSearchServiceBean implements BPOSPCSearchServiceLocal, BPOSPCSearchServiceRemote {

    private Logger logger = Logger.getLogger(BPOSPCSearchServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private BPOSPCSearchService adapter;

    @Override
    public Patent getSPCByAppNumber(String appNum) {
        return adapter.getSPCByAppNumber(appNum);
    }
}
