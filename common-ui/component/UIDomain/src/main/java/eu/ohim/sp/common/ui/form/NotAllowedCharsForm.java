package eu.ohim.sp.common.ui.form;

import eu.ohim.sp.common.SPException;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 06.06.2021
 * Time: 11:09
 */
public class NotAllowedCharsForm implements Serializable {

    private static String ANGULAR_BRACKET_LEFT = "<";
    private static String ANGULAR_BRACKET_RIGHT = ">";
    private static String ESCAPED_ABL = "@ABL@";
    private static String ESCAPED_ABR = "@ABR@";

    private String originalValue;
    private String escapedValue;

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        if(originalValue != null && (originalValue.contains(ESCAPED_ABL) || originalValue.contains(ESCAPED_ABR))){
            throw new SPException("NotAllowedCharsForm original value can not contain escaped special values");
        }
        this.originalValue = originalValue;
        this.escapedValue = originalValue;
        if(originalValue != null && (originalValue.contains(ANGULAR_BRACKET_LEFT) || originalValue.contains(ANGULAR_BRACKET_RIGHT))) {
            this.escapedValue = originalValue.replaceAll(ANGULAR_BRACKET_LEFT, ESCAPED_ABL).replaceAll(ANGULAR_BRACKET_RIGHT, ESCAPED_ABR);
        }
    }

    public String getEscapedValue() {
        return escapedValue;
    }

    public void setEscapedValue(String escapedValue) {
        if(escapedValue != null && (escapedValue.contains(ANGULAR_BRACKET_LEFT) || escapedValue.contains(ANGULAR_BRACKET_RIGHT))){
            throw new SPException("NotAllowedCharsForm escaped value can not contain not allowed characters");
        }
        this.escapedValue = escapedValue;
        this.originalValue = escapedValue;
        if(escapedValue != null && (escapedValue.contains(ESCAPED_ABL) || escapedValue.contains(ESCAPED_ABR))){
            this.originalValue = escapedValue.replaceAll(ESCAPED_ABL, ANGULAR_BRACKET_LEFT).replaceAll(ESCAPED_ABR, ANGULAR_BRACKET_RIGHT);
        }
    }

    public static NotAllowedCharsForm fromEscapedValue(String escapedValue){
        NotAllowedCharsForm form = new NotAllowedCharsForm();
        form.setEscapedValue(escapedValue);
        return form;
    }

    public static NotAllowedCharsForm fromOriginalValue(String originalValue){
        NotAllowedCharsForm form = new NotAllowedCharsForm();
        form.setOriginalValue(originalValue);
        return form;
    }

    @Override
    public String toString() {
        return escapedValue;
    }
}
