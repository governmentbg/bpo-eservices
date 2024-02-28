/*
 *  FspDomain:: FSPEnumConverter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;

/**
 * 
 * @author karalch
 *
 */
public class FSPEnumConverter implements CustomConverter, Converter {

    /**
     * Logger of {@see FSPEnumConverter}
     */
    private static final Logger LOGGER = Logger.getLogger(FSPEnumConverter.class);

    @Override
	public Object convert(Class type, Object value) {
		if (value==null
				|| ((value instanceof String)
				&& StringUtils.isBlank((String) value))) {
			return null;
		} 
		Object object = null;
		Method methodSetter = null;
		try {
			Object input = null;
			//Find the value of the object that will be set
			if (value instanceof String) {
				input = value;
			} else {
				for (Method method : value.getClass().getMethods()) {
					if ((method.getName().equals("value") ||
							method.getName().equals("getValue"))
							&& method.getReturnType().equals(String.class)) {
						input =  method.invoke(value);
					}
				}
			}

			//Create instance of the object and find the setter
			for (Method method : type.getMethods()) {
				if (method.getName().equals("fromValue")) {
					methodSetter = method;
				} else if (method.getName().equals("setValue")) {
					object = type.newInstance();
					methodSetter = method;
				} else if (method.getName().equals("values")) {
					Object[] objects = (Object[]) method.invoke(null);
					for (Object objectEnum : objects) {
						if (objectEnum.getClass().getMethod("value").invoke(objectEnum).equals(input)) {
							return objectEnum;
						}
					}
				}
			}
			
			if (input != null 
					&& methodSetter != null) {
				if (methodSetter.getReturnType().equals(type)) {
					return methodSetter.invoke(object, input);
				} else {
					methodSetter.invoke(object, input);
					return object;
				}
			} else {
				return input;
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}

		
		
		return null;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}
	
	
}
