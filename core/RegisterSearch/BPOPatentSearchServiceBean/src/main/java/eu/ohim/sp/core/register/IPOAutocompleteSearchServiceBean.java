package eu.ohim.sp.core.register;

import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 13.04.2022
 * Time: 14:46
 */
@Stateless
public class IPOAutocompleteSearchServiceBean implements IPOAutocompleteSearchServiceLocal, IPOAutocompleteSearchServiceRemote {

    private Logger logger = Logger.getLogger(IPOAutocompleteSearchServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private IPOAutocompleteSearchService adapter;

    @Override
    public String ipoAutocomplete(String word, int rows, String appType) {
        return adapter.ipoAutocomplete(word, rows, appType);
    }
}
