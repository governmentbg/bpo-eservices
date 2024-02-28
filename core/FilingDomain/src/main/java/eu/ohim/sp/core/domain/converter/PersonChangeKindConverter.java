package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.person.PersonChangeKind;
import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

public class PersonChangeKindConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof PersonChangeKind) {
			return ((PersonChangeKind) value).value();
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
