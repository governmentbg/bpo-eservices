package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.form.trademark.MarkViewForm;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Raya
 * 15.08.2019
 */
@Component
public class MarkViewFactory  implements UIFactory<ImageSpecification, MarkViewForm> {

    @Autowired
    private AttachedDocumentFactory fileFactory;

    @Autowired
    private DocumentFactory documentFactory;

    @Override
    public ImageSpecification convertTo(MarkViewForm form) {
        ImageSpecification core = new ImageSpecification();
        if(form != null){
            core.setImageNumber(form.getImageNumber());
            core.setTitle(form.getTitle());

            if (form.getView() != null && CollectionUtils.isNotEmpty(form.getView().getStoredFiles())) {
                StoredFile storedFile = form.getView().getStoredFiles().get(0);
                core.setRepresentation(fileFactory.convertTo(storedFile).getDocument());
            }

        }

        return core;
    }

    @Override
    public MarkViewForm convertFrom(ImageSpecification core) {
        MarkViewForm form = new MarkViewForm();
        if (core == null) {
            return form;
        }

        if(core.getRepresentation() != null) {
            form.getView().setFilename(core.getRepresentation().getFileName());
            form.getView().setAttachment(Boolean.TRUE);
            StoredFile storedFile = documentFactory.convertFrom(core.getRepresentation());
            form.getView().getStoredFiles().add(storedFile);

        }

        form.setImageNumber(core.getImageNumber());
        form.setTitle(core.getTitle());
        return form;
    }
}
