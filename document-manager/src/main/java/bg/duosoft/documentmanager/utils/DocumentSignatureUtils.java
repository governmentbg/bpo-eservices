package bg.duosoft.documentmanager.utils;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.resources.SignedXmlDocument;

import java.io.ByteArrayInputStream;
import java.security.cert.X509Certificate;

/**
 * Created by Raya
 * 19.01.2021
 */
public class DocumentSignatureUtils {

    public static SignedXmlDocument getDocumentSignature(Document doc){
        try {
            X509Certificate cert = SignatureUtils.verifyXMLSignature(new ByteArrayInputStream(doc.getData()), null);
            return new SignedXmlDocument(doc, SignatureUtils.getIssuerName(cert), SignatureUtils.getCommonName(cert), SignatureUtils.getEmail(cert), SignatureUtils.getEgn(cert), SignatureUtils.getBulstat(cert), cert.getNotBefore(), cert.getNotAfter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
