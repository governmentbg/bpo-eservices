package eu.ohim.sp.core.register;

import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.userdoc.FilteredUserdocs;
import java.util.Map;

public interface BPOBackOfficeSearchService {

    String getUnpublishedApplicationsAutocomplete(String user, String applicationType, String text);

    TradeMark getUnpublishedTrademark(String user, String trademarkId);

    DesignApplication getUnpublishedDesignApp(String user, String designApplicationId);

    Patent getUnpublishedPatent(String user, String patentId);

    FilteredUserdocs getUserdocsForApplication(String applicationNumber, String applicationYear, String user, Map<String, Object> eserviceDetails);

}
