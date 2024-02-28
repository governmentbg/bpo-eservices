package eu.ohim.sp.core.domain.converter;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.contact.Email;

public class EmailConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof String) {
			Email email = new Email();
			email.setEmailAddress((String) value);
			return email;
		} else if (value instanceof Email) {
			return ((Email) value).getEmailAddress();
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
