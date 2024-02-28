package org.milyn.javabean.decoders;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.milyn.cdr.SmooksConfigurationException;
import org.milyn.javabean.DataDecodeException;
import org.milyn.util.ClassUtil;

public class SPEnumDecoder extends EnumDecoder{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class enumType;
    
    private String defaultValue;
	
	 public void setConfiguration(Properties resourceConfig) throws SmooksConfigurationException {
		 super.setConfiguration(resourceConfig);
	        String enumTypeName = resourceConfig.getProperty("enumType");

	        if(enumTypeName == null || enumTypeName.trim().equals(""))
	        {
	            throw new SmooksConfigurationException("Invalid EnumDecoder configuration. 'enumType' param not specified.");
	        }

	        try {
	            enumType = ClassUtil.forName(enumTypeName.trim(), EnumDecoder.class);
	        } catch (ClassNotFoundException e) {
	            throw new SmooksConfigurationException("Invalid Enum decoder configuration.  Failed to resolve '" + enumTypeName + "' as a Java Enum Class.", e);
	        }

	        if(!Enum.class.isAssignableFrom(enumType)) {
	            throw new SmooksConfigurationException("Invalid Enum decoder configuration.  Resolved 'enumType' '" + enumTypeName + "' is not a Java Enum Class.");
	        }

	        
	        defaultValue= resourceConfig.getProperty("defaultValue");
	    }

	
	@Override
    public Object decode(String data) throws DataDecodeException {
		Object toReturn=null;
		try{
			toReturn=super.decode(data);
		}
		catch(DataDecodeException ee){
			if(StringUtils.isNotEmpty(defaultValue)){
		        try {
		            return Enum.valueOf(enumType,defaultValue);
		        } catch(IllegalArgumentException e) {
		            throw new DataDecodeException("Failed to decode the default value '" + defaultValue + "' as a valid Enum constant of type '" + enumType.getName() + "'.");
		        }
			}

		}
		
		return toReturn;
    }
}


