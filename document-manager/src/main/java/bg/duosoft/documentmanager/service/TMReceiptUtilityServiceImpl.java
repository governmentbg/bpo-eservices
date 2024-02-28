package bg.duosoft.documentmanager.service;

import bg.duosoft.documentmanager.utils.DraftApplicationUtils;
import bg.duosoft.documentmanager.utils.IPApplicationUtils;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.core.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Raya
 * 19.01.2021
 */
@Service
@Qualifier("tmReceiptService")
public class TMReceiptUtilityServiceImpl implements ReceiptUtilityService {

    public final static String MODULE = "tmefiling";

    @Autowired
    private ApplicationService tmApplicationService;

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

    @Override
    public String getModule() {
        return MODULE;
    }

    private byte[] generateSubmittedReceipt(String appId){
        DraftApplication draftApplication = tmApplicationService.getDraftApplication("", null, appId);
        IPApplication ipApplication = tmApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        if(ipApplication == null){
            ipApplication = tmApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_XML, appId);
        }
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        byte[] receiptContent = reportService.generateReport(MODULE, "receipt", "bg", ipApplication, null, false, null, DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));
        return receiptContent;
    }

    private byte[] generateAcceptedReceipt(String appId){
        DraftApplication draftApplication = tmApplicationService.getDraftApplication("", null, appId);
        IPApplication ipApplication = tmApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        if(ipApplication == null){
            ipApplication = tmApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_XML, appId);
        }
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        ipApplication.setApplicationNumber(draftApplication.getApplicationId());
        ((TradeMarkApplication)ipApplication).getTradeMark().setApplicationDate(DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));
        byte[] receiptContent = reportService.generateReport(MODULE, "receipt", "bg", ipApplication, null, false, DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));

        return receiptContent;
    }
}
