package bg.duosoft.documentmanager.service;

import eu.ohim.sp.core.domain.resources.Document;

import java.util.Date;

/**
 * Created by Raya
 * 18.01.2021
 */
public interface DocumentUtilityService {

    Document getDocumentByAppId(String appId);

    Document getDocumentById(String documentId);

    Document updateDocument(Document updated);

    Document updateReceiptContent(String appId, byte[] newReceiptBytes);

    String resolveFileExtension(String mimeType);

    boolean documentIsSigned(Document document);

    Date getDocumentSignedDate(Document document);
}
