package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PTEntitlementForm;
import eu.ohim.sp.core.domain.application.Entitlement;
import eu.ohim.sp.core.domain.application.EntitlementKind;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PTEntitlementFactory  implements UIFactory<Entitlement, PTEntitlementForm> {

    @Autowired
    private ListAttachedDocumentFactory attachedDocumentFactory;

    @Override
    public Entitlement convertTo(PTEntitlementForm form) {
        Entitlement toReturn = new Entitlement();
        if(form == null){
            return toReturn;
        }
        if (form.isPatentNotOfficiary()) {
            toReturn.setKind(EntitlementKind.PATENT_IS_NOT_OFFICIARY);
            if (form.getPatentNotOfficiaryFiles() != null) {
                List<AttachedDocument> documents = attachedDocumentFactory.convertTo(form.getPatentNotOfficiaryFiles());
                toReturn.setAttachedDocuments(documents);
            }
        } else if (form.isPatentOfficiary()) {
            toReturn.setKind(EntitlementKind.PATENT_IS_OFFICIARY);
            if (form.getPatentOfficiaryFiles() != null) {
                List<AttachedDocument> documents = attachedDocumentFactory.convertTo(form.getPatentOfficiaryFiles());
                toReturn.setAttachedDocuments(documents);
            }
        } else if (form.isOtherGrounds()) {
            toReturn.setKind(EntitlementKind.OTHER_GROUNDS);
            toReturn.setDescription(form.getOtherGroundsDescription());
        } else if (form.isTransferContract()) {
            toReturn.setKind(EntitlementKind.TRANSFER_OF_RIGHTS);
            if (form.getTransferContractFiles() != null) {
                List<AttachedDocument> documents = attachedDocumentFactory.convertTo(form.getTransferContractFiles());
                toReturn.setAttachedDocuments(documents);
            }
        }
        return toReturn;
    }

    @Override
    public PTEntitlementForm convertFrom(Entitlement core) {
        PTEntitlementForm form = new PTEntitlementForm();
        if(core == null){
            return form;
        }
        if (core.getKind() != null){
            switch (core.getKind()) {
                case PATENT_IS_NOT_OFFICIARY:
                    form.setPatentNotOfficiary(true);
                    form.setPatentNotOfficiaryFiles(attachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
                    break;
                case PATENT_IS_OFFICIARY:
                    form.setPatentOfficiary(true);
                    form.setPatentOfficiaryFiles(attachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
                    break;
                case TRANSFER_OF_RIGHTS:
                    form.setTransferContract(true);
                    form.setTransferContractFiles(attachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
                    break;
                case OTHER_GROUNDS:
                    form.setOtherGrounds(true);
                    form.setOtherGroundsDescription(core.getDescription());
                    break;
            }
        }
        return form;
    }
}
