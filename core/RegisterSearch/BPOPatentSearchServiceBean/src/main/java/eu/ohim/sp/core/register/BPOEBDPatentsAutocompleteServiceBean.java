package eu.ohim.sp.core.register;

import eu.ohim.sp.external.register.BPOPatentSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 20.04.2022
 * Time: 13:07
 */
@Stateless
public class BPOEBDPatentsAutocompleteServiceBean implements BPOEBDPatentsAutocompleteServiceLocal, BPOEBDPatentsAutocompleteServiceRemote {

    private Logger logger = Logger.getLogger(BPOEBDPatentsAutocompleteServiceBean.class);

    @Inject
    @BPOPatentSearchClientInside
    private BPOEBDPatentsAutocompleteService adapter;

    @Override
    public String ebdPatentsAutocomplete(String word, int rows) {
        return adapter.ebdPatentsAutocomplete(word, rows);
    }
}
