package bg.duosoft.documentmanager.service;

import bg.duosoft.documentmanager.utils.DocumentSignatureUtils;
import bg.duosoft.documentmanager.utils.DraftApplicationUtils;
import bg.duosoft.documentmanager.utils.IPApplicationUtils;
import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.EServiceApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.SignedXmlDocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Raya
 * 19.01.2021
 */
@Service
@Qualifier("esReceiptService")
public class ESReceiptUtilityServiceImpl implements ReceiptUtilityService{

    public final static String MODULE = "eservices";
    public final static String MODULE_PACKAGE = "eu.ohim.sp.core.rules";

    @Autowired
    private ApplicationService esApplicationService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private DocumentUtilityService documentUtilityService;

    @Override
    public byte[] generateReceipt(String appId, String receiptType) {
        if(receiptType.equalsIgnoreCase("submitted")){
            return generateSubmittedReceipt(appId);
        } else {
            return generateAcceptedReceipt(appId);
        }
    }

    private byte[] generateSubmittedReceipt(String appId) {
        IPApplication ipApplication = esApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        DraftApplication draftApplication = esApplicationService.getDraftApplication("", null, appId);
        Document document = documentUtilityService.getDocumentByAppId(appId);
        String receiptModule = MODULE_PACKAGE+"."+draftApplication.getTyApplication().replace("_", "").toLowerCase();
        byte[] receiptContent;
        if(documentUtilityService.documentIsSigned(document)){
            SignedXmlDocument signedXmlDocument = DocumentSignatureUtils.getDocumentSignature(document);
            Date signedDate = documentUtilityService.getDocumentSignedDate(document);
            receiptContent = reportService.generateReport(receiptModule, "receipt", "en", ipApplication, null, false, null, true, signedXmlDocument, signedDate);
        } else {
            receiptContent = reportService.generateReport(receiptModule, "receipt", "en", ipApplication, null, false, new Date());
        }
        return receiptContent;
    }

    private byte[] generateAcceptedReceipt(String appId){
        IPApplication ipApplication = esApplicationService.retrieveApplication("BG", MODULE, FormatXML.APPLICATION_EPUB, appId);
        IPApplicationUtils.removeReceiptFromOtherDocs(ipApplication);
        DraftApplication draftApplication = esApplicationService.getDraftApplication("", null, appId);
        Document document = documentUtilityService.getDocumentByAppId(appId);
        String receiptModule = MODULE_PACKAGE+"."+draftApplication.getTyApplication().replace("_", "").toLowerCase();
        if(documentUtilityService.documentIsSigned(document)){
            SignedXmlDocument signedXmlDocument = DocumentSignatureUtils.getDocumentSignature(document);
            Date signedDate = documentUtilityService.getDocumentSignedDate(document);

            ipApplication.setApplicationNumber(draftApplication.getApplicationId());
            ((EServiceApplication)ipApplication).setApplicationDate(DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication));

            return reportService.generateReport(receiptModule, "receipt", "bg", ipApplication, null, false, DraftApplicationUtils.findApplicationDateInDraftApplication(draftApplication), true, signedXmlDocument, signedDate);
        } else {
            throw new RuntimeException("can not renerate receipt that for accepted application when app is not e-signed");
        }
    }

    @Override
    public String getModule() {
        return MODULE;
    }
}
