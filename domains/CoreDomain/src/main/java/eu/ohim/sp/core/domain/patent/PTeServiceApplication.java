package eu.ohim.sp.core.domain.patent;

import eu.ohim.sp.core.domain.application.EServiceApplication;

import java.util.List;

/**
 * Created by Raya
 * 12.12.2019
 */
public class PTeServiceApplication extends EServiceApplication {

    private List<Patent> patentList;

    private MarketPermission marketPermission;

    public List<Patent> getPatentList() {
        return patentList;
    }

    public void setPatentList(List<Patent> patentList) {
        this.patentList = patentList;
    }

    public MarketPermission getMarketPermission() {
        return marketPermission;
    }

    public void setMarketPermission(MarketPermission marketPermission) {
        this.marketPermission = marketPermission;
    }
}
