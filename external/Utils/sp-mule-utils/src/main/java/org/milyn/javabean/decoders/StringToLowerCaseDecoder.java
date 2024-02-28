/*******************************************************************************
 * * $Id:: StringToLowerCaseDecoder.java 14329 2012-10-29 13:02:02Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package org.milyn.javabean.decoders;

import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

/**
 * Smooks generic decoder type to returns the string value as lower case.
 */
@DecodeType(StringToLowerCaseDecoder.class)
public class StringToLowerCaseDecoder implements DataDecoder {

    /*
     * (non-Javadoc)
     * @see org.milyn.javabean.StringToUpperCaseDecoder#decode(java.lang.String)
     */
    @Override
    public Object decode(String data) throws DataDecodeException {
        if (data != null)
            return data.toLowerCase();
        return data;
    }
}
