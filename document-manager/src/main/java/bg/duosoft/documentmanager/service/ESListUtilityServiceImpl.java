package bg.duosoft.documentmanager.service;

import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 22.03.2021
 * Time: 13:38
 */
@Service
public class ESListUtilityServiceImpl implements ESListUtilityService {

    @Autowired
    private ApplicationService esApplicationService;

    @Override
    public List<IPApplication> findUnpublishedApplications(String since) {
        List<IPApplication> ipapps = new ArrayList<>();
        Map<String, String> searchCriteria = new HashMap<>();
        searchCriteria.put("currentStatus", "TRANSFER_OK");
        searchCriteria.put("firstDate", since);
        int count = (int) esApplicationService.getApplicationsCount("BG", "eservices", searchCriteria);
        int pages = count / 100;
        if (pages % 100 > 0) {
            pages = pages + 1;
        }
        for (int i = 0; i < pages; i++) {
            List<DraftApplication> drafts = esApplicationService.searchDraftApplication("BG", "eservices",
                    searchCriteria, i*100, 100, true, "dtCreated");

            drafts.stream().forEach(d -> {
                if(!(d.getTyApplication().equals("TM") || d.getTyApplication().equals("DS") ||
                d.getTyApplication().equals("PT_EFILING") || d.getTyApplication().equals("UM_EFILING")
                || d.getTyApplication().equals("EP_EFILING"))) {

                    IPApplication ipApp = esApplicationService.retrieveApplication("BG", "eservices", FormatXML.APPLICATION_EPUB, d.getProvisionalId());
                    if (ipApp != null) {
                        if (ipApp instanceof TMeServiceApplication) {
                            if (((TMeServiceApplication) ipApp).getTradeMarks().get(0).getUnpublished() != null &&
                                    ((TMeServiceApplication) ipApp).getTradeMarks().get(0).getUnpublished()) {
                                ipapps.add(ipApp);
                            }
                        } else if (ipApp instanceof DSeServiceApplication) {
                            if (((DSeServiceApplication) ipApp).getDesignDetails().get(0).getDesignDetails().get(0).getUnpublished() != null &&
                                    ((DSeServiceApplication) ipApp).getDesignDetails().get(0).getDesignDetails().get(0).getUnpublished()) {
                                ipapps.add(ipApp);
                            }
                        } else if (ipApp instanceof PTeServiceApplication) {
                            if (((PTeServiceApplication) ipApp).getPatentList().get(0).getUnpublished() != null &&
                                    ((PTeServiceApplication) ipApp).getPatentList().get(0).getUnpublished()) {
                                ipapps.add(ipApp);
                            }
                        }
                    }
                }
            });
        }

        return ipapps;
    }
}
