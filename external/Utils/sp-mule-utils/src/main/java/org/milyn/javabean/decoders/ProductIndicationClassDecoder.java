package org.milyn.javabean.decoders;

import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

/**
 * Smooks custom decoder type to return the product indication class
 */
@DecodeType(ProductIndicationClassDecoder.class)
public class ProductIndicationClassDecoder implements DataDecoder {

   
	private static final long serialVersionUID = 12343462672345L;

	public Object decode(String data) throws DataDecodeException {
        String productIndicationClass= null;
        
    	if ( (!StringUtils.isEmpty(data)) && (data.lastIndexOf(".") >0)  ){
    		productIndicationClass =  data.substring(0,data.lastIndexOf("."));
        }
        return productIndicationClass;
    }
}

