package org.milyn.javabean.decoders;

import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

/**
 * Smooks generic decoder type to returns the string value as lower case.
 */
@DecodeType(ST13Decoder.class)
public class ST13Decoder implements DataDecoder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
     * (non-Javadoc)
     * @see org.milyn.javabean.StringToUpperCaseDecoder#decode(java.lang.String)
     */
    @Override
    public Object decode(String data) throws DataDecodeException {
        if (data != null)
            return StringUtils.substringAfterLast(data, "ST13=");
        return data;
    }
}