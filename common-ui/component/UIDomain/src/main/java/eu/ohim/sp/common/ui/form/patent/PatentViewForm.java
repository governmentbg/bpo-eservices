package eu.ohim.sp.common.ui.form.patent;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 09.04.2019
 */
public class PatentViewForm extends AbstractImportableForm {

    private int sequence;
    private String description;
    private boolean publishInColour;
    private boolean publishInBlackWhite;
    private String publicationSize;
    private String type;

    private FileWrapper view;

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PatentViewForm patentViewForm = new PatentViewForm();
        patentViewForm.setId(this.getId());
        patentViewForm.setImported(this.getImported());
        patentViewForm.setSequence(this.sequence);
        patentViewForm.setDescription(this.description);
        patentViewForm.setPublicationSize(this.publicationSize);
        patentViewForm.setPublishInBlackWhite(this.publishInBlackWhite);
        patentViewForm.setType(this.type);
        patentViewForm.setPublishInColour(this.publishInColour);
        patentViewForm.setView(view);
        return patentViewForm;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PATENT_VIEW;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublishInColour() {
        return publishInColour;
    }

    public void setPublishInColour(boolean publishInColour) {
        this.publishInColour = publishInColour;
    }

    public boolean isPublishInBlackWhite() {
        return publishInBlackWhite;
    }

    public void setPublishInBlackWhite(boolean publishInBlackWhite) {
        this.publishInBlackWhite = publishInBlackWhite;
    }

    public String getPublicationSize() {
        return publicationSize;
    }

    public void setPublicationSize(String publicationSize) {
        this.publicationSize = publicationSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileWrapper getView() {
        return view;
    }

    public void setView(FileWrapper view) {
        this.view = view;
    }
}
