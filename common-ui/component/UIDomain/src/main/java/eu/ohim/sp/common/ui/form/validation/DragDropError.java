package eu.ohim.sp.common.ui.form.validation;

/**
 * Created by Raya
 * 01.08.2019
 */
public class DragDropError {

    private String field;
    private String displayMessage;
    private String code;

    public DragDropError() {
    }

    public DragDropError(String field, String displayMessage, String code) {
        this.field = field;
        this.displayMessage = displayMessage;
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
