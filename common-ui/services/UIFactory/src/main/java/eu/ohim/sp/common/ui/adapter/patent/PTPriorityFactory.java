package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PTPriorityForm;
import eu.ohim.sp.core.domain.patent.PatentPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PTPriorityFactory implements UIFactory<PatentPriority, PTPriorityForm> {

    @Autowired
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Override
    public PatentPriority convertTo(PTPriorityForm form) {
        PatentPriority core = new PatentPriority();
        if(form == null){
            return core;
        }
        core.setFilingDate(form.getDate());
        core.setFilingNumber(form.getNumber());
        core.setFilingOffice(form.getCountry());
        if(form.getFilePriorityCertificate() != null){
            if(form.getFilePriorityCertificate().getAttachment()){
                core.setCerticateAttachedIndicator(true);
            }
            if (form.getFilePriorityCertificate().getStoredFiles() != null && form.getFilePriorityCertificate().getStoredFiles().size() > 0) {
                core.setAttachedDocuments(listAttachedDocumentFactory.convertTo(form.getFilePriorityCertificate()));
            }
        }
        return core;
    }

    @Override
    public PTPriorityForm convertFrom(PatentPriority core) {
        PTPriorityForm form = new PTPriorityForm();
        if(core == null) {
            return form;
        }
        form.setCountry(core.getFilingOffice());
        form.setDate(core.getFilingDate());
        form.setNumber(core.getFilingNumber());
        if(core.getAttachedDocuments() != null && core.getAttachedDocuments().size()>0){
            form.setFilePriorityCertificate(listAttachedDocumentFactory.convertFrom(core.getAttachedDocuments()));
            form.getFilePriorityCertificate().setAttachment(true);
        }
        return form;
    }
}
