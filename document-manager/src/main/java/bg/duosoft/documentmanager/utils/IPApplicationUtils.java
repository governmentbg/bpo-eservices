package bg.duosoft.documentmanager.utils;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.trademark.IPApplication;

import java.util.Optional;

public class IPApplicationUtils {

    public static void removeReceiptFromOtherDocs(IPApplication ipApplication){
        if(ipApplication.getDocuments() != null){
            Optional<AttachedDocument> receiptOpt = ipApplication.getDocuments().stream().filter(d -> d.getDocumentKind() != null &&
                    d.getDocumentKind().equals("Application Receipt")).findFirst();
            if(receiptOpt.isPresent()){
                ipApplication.getDocuments().remove(receiptOpt.get());
            }
        }
    }
}
