package eu.ohim.sp.common.ui;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.NotAllowedCharsForm;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 06.06.2021
 * Time: 11:31
 */
public class NotAllowedCharsPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        if(getValue() == null){
            return null;
        }
        if(getValue() instanceof NotAllowedCharsForm){
            return ((NotAllowedCharsForm)getValue()).getEscapedValue();
        } else {
            throw new SPException("Bad property binding");
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(text != null){
            text = text.replace((char) 96, '\'');
            text = text.replace((char) 180, '\'');
            text = text.replace((char) 8217, '\'');
            text = text.replace((char) 8220, '"');
            text = text.replace((char) 8221, '"');
            text = text.trim();
            setValue(NotAllowedCharsForm.fromEscapedValue(text));
        } else {
            setValue(null);
        }
    }
}
