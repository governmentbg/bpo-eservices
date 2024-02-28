package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.userdoc.FilteredUserdocs;
import eu.ohim.sp.external.register.BPOBackOfficeSearchClientInside;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;

@Stateless
public class BPOBackOfficeSearchServiceBean implements BPOBackOfficeSearchServiceLocal, BPOBackOfficeSearchServiceRemote {

    private Logger logger = Logger.getLogger(BPOBackOfficeSearchServiceBean.class);

    @Inject
    @BPOBackOfficeSearchClientInside
    private BPOBackOfficeSearchService bpoBackOfficeSearchService;

    @Override
    public String getUnpublishedApplicationsAutocomplete(String user, String applicationType, String text) {
        return bpoBackOfficeSearchService.getUnpublishedApplicationsAutocomplete(user, applicationType, text);
    }

    @Override
    public TradeMark getUnpublishedTrademark(String user, String trademarkId) {
        return bpoBackOfficeSearchService.getUnpublishedTrademark(user, trademarkId);
    }

    @Override
    public DesignApplication getUnpublishedDesignApp(String user, String designApplicationId) {
        return bpoBackOfficeSearchService.getUnpublishedDesignApp(user, designApplicationId);
    }

    @Override
    public Patent getUnpublishedPatent(String user, String patentId) {
        return bpoBackOfficeSearchService.getUnpublishedPatent(user, patentId);
    }

    @Override
    public FilteredUserdocs getUserdocsForApplication(String applicationNumber, String applicationYear, String user, Map<String, Object> eserviceDetails) {
        return bpoBackOfficeSearchService.getUserdocsForApplication(applicationNumber, applicationYear, user, eserviceDetails);
    }
}
