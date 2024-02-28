package eu.ohim.sp.common.ui.form.trademark;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Created by Raya
 * 15.08.2019
 */
public class MarkViewForm extends AbstractImportableForm {

    private FileWrapper view;

    private String title;
    private String imageNumber;
    private int sequence;

    private String markType;

    public MarkViewForm() {
        view = new FileWrapper();
        this.markType = null;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return null;
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        MarkViewForm form = new MarkViewForm();
        form.setId(this.getId());
        form.setImported(this.getImported());
        form.setImageNumber(this.imageNumber);
        form.setTitle(this.title);
        form.setView(this.view);
        form.setSequence(this.sequence);
        return form;
    }

    public String getMarkType() {
        return markType;
    }

    public void setMarkType(String markType) {
        this.markType = markType;
    }

    public FileWrapper getView() {
        return view;
    }

    public void setView(FileWrapper view) {
        this.view = view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(String imageNumber) {
        this.imageNumber = imageNumber;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getOrderField(){
        if(imageNumber != null && !imageNumber.isEmpty()){
            return imageNumber.toLowerCase();
        }
        if(title != null && !title.isEmpty()){
            return title.toLowerCase();
        }

        return new Integer(sequence).toString();
    }
}
