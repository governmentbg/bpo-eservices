package eu.ohim.sp.core.domain.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.contact.Email;

public class CoreEnumConverter implements Converter, CustomConverter {

	private final Logger LOGGER = Logger.getLogger(CoreEnumConverter.class);

	@Override
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		} else if (type.equals(String.class)) {
			if (value instanceof String) {
				return value;
			} else if (value instanceof Email) {
				return new EmailConverter().convert(type, value);
			} else {
				for (Method method : value.getClass().getMethods()) {
					if (method.getName().equals("value")
							|| method.getName().equals("getValue")) {
						try {
							return (String) method.invoke(value);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							LOGGER.warn(e);
						}
					}
				}
				
			}
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
