package org.milyn.javabean.decoders;

import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;

public class ImageExtractorDecoder implements DataDecoder {

    private static final long serialVersionUID = 1L;
    
    @Override
    public final Object decode(String uri) throws DataDecodeException {
    	String id =null;
        if (!StringUtils.isEmpty(uri)){
        	id=uri.substring(uri.lastIndexOf("/")+1);
        }
        return id;
    }
}
