package bg.duosoft.documentmanager.service;

import bg.duosoft.documentmanager.utils.DraftApplicationUtils;
import bg.duosoft.documentmanager.utils.IPApplicationUtils;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Raya
 * 19.01.2021
 */
@Service
@Qualifier("dsReceiptService")
public class DSReceiptUtilityServiceImpl implements ReceiptUtilityService {

    public final static String MODULE = "dsefiling";

    @Autowired
    private ApplicationService dsApplicationService;

    @Autowired
    private ReportService reportService;

    @Override
    public byte[] generateReceipt(String appId, String receiptType) {
        if(receiptType.equalsIgnoreCase("submitted")){
            return generateSubmittedReceipt(appId);
        } else {
            return generateAcceptedReceipt(appId);
        }
    }

    private byte[] generateSubmittedReceipt(String appId){
        DraftApplication draftApplication = dsApplicationService.getDraftApplication("", null, appId);
        IPApplication ipApplication = dsApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        byte[] receiptContent = reportService.generateReport(MODULE, "receipt", "bg", ipApplication, null, false, null, DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));
        return receiptContent;
    }

    private byte[] generateAcceptedReceipt(String appId){
        DraftApplication draftApplication = dsApplicationService.getDraftApplication("", null, appId);
        IPApplication ipApplication = dsApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        ipApplication.setApplicationNumber(draftApplication.getApplicationId());
        ((DesignApplication)ipApplication).setApplicationDate(DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));
        ((DesignApplication)ipApplication).setReceiptNumber("small");
        byte[] receiptContent = reportService.generateReport(MODULE, "receipt", "bg", ipApplication, null, false, DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));

        return receiptContent;
    }

    @Override
    public String getModule() {
        return MODULE;
    }
}
