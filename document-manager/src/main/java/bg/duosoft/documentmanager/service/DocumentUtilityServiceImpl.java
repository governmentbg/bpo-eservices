package bg.duosoft.documentmanager.service;

import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.FODocument;
import eu.ohim.sp.core.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raya
 * 18.01.2021
 */
@Service
public class DocumentUtilityServiceImpl implements DocumentUtilityService {

    @Autowired
    private DocumentService documentService;

    public Document getDocumentByAppId(String appId) {
        Map<String, String> customProperties = new HashMap<String, String>();
        customProperties.put(FODocument.FILING_NUMBER, appId);
        customProperties.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_XML.value());

        List<Document> docList = documentService.searchDocument(customProperties, false);
        if(docList != null && docList.size() >0){
            Document found = docList.get(0);
            return found;
        } else {
            customProperties.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_EPUB.value());
            List<Document> docListEPUB = documentService.searchDocument(customProperties, false);
            if(docListEPUB != null && docListEPUB.size() >0){
                Document found = docListEPUB.get(0);
                return found;
            }
        }
        return null;
    }

    @Override
    public Document getDocumentById(String documentId) {
        return documentService.getDocument(documentId);
    }

    @Override
    public Document updateDocument(Document updated) {
        updated.getCustomProperties().remove("jcr:primaryType");
        updated.setFileSize((long)updated.getData().length);
        return documentService.updateDocument(updated);
    }

    public Document updateReceiptContent(String appId, byte[] newReceiptBytes){
        Map<String, String> customProperties = new HashMap<>();
        customProperties.put(FODocument.FILING_NUMBER, appId);
        customProperties.put("fileName", ReportService.RECEIPT_REPORT + ".pdf");
        customProperties.put(FODocument.ATTACHMENT_TYPE, FormatXML.APPLICATION_OTHER.value());
        List<Document> docs = documentService.searchDocument(customProperties, false);
        Document receipt = docs.get(0);

        receipt.setData(newReceiptBytes);

        return updateDocument(receipt);
    }

    @Override
    public String resolveFileExtension(String mimeType) {
        if(mimeType == null){
            throw new RuntimeException("Null mime");
        }
        if(mimeType.endsWith("/xml")){
            return ".xml";
        } else if(mimeType.equalsIgnoreCase("image/jpeg") || mimeType.equalsIgnoreCase("image/jpg")){
            return ".jpg";
        } else if(mimeType.equalsIgnoreCase("application/pdf")){
            return ".pdf";
        }
        throw new RuntimeException("Unknown mime. Please add it in DocumentUtilityServiceImpl");
    }

    @Override
    public boolean documentIsSigned(Document document){
        if(document != null && document.getCustomProperties() != null){
            if(document.getCustomProperties().get("SIGNED") != null
                && !document.getCustomProperties().get("SIGNED").isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
        throw new RuntimeException("Bad document");
    }

    @Override
    public Date getDocumentSignedDate(Document document) {
        String strDateSigned = document.getCustomProperties().get("DATE_SIGNED");
        if(strDateSigned != null && !strDateSigned.isEmpty()) {
            try {
                return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(strDateSigned);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
