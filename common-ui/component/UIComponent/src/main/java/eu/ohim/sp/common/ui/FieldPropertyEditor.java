/*******************************************************************************
 * * $Id:: FieldPropertyEditor.java 49948 2012-11-06 16:13:54Z villama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.exception.NotAcceptedCharacter;

public class FieldPropertyEditor extends PropertyEditorSupport {

    private final Logger logger = Logger.getLogger(FieldPropertyEditor.class);

    /**
     * Gets the property value as a string suitable
     * for presentation to a human to edit.
     */
    @Override
    public String getAsText() throws SPException {
        if (!StringUtils.containsNone((String) getValue(), "<>"))
            throw new NotAcceptedCharacter("Not accepted Characters", null, "error.");
        return (String) getValue();
    }

    /**
     * Set (or change) the object that is to be edited.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.containsNone(text, "<>"))
            throw new NotAcceptedCharacter("Not accepted Characters", null, "error.");
        // if the character is not a letter.
        text = text.replace((char) 96, '\'');
        text = text.replace((char) 180, '\'');
        text = text.replace((char) 8217, '\'');
        text = text.replace((char) 8220, '"');
        text = text.replace((char) 8221, '"');
        text = text.trim();
        setValue(text);
    }

}
