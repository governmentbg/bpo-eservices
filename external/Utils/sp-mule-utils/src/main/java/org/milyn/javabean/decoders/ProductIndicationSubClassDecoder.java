package org.milyn.javabean.decoders;

import org.apache.commons.lang.StringUtils;
import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

/**
 * Smooks custom decoder type to return the product indication subclass
 */
@DecodeType(ProductIndicationSubClassDecoder.class)
public class ProductIndicationSubClassDecoder implements DataDecoder {
 
  
	private static final long serialVersionUID = 1345345345345L;

	public Object decode(String data) throws DataDecodeException {
        String productIndicationSubClass= null;
        
        if ( (!StringUtils.isEmpty(data)) && (data.lastIndexOf(".") >0)  ){
    		productIndicationSubClass =  data.substring(data.lastIndexOf(".")+1);
        }
        return productIndicationSubClass;
    }
}

