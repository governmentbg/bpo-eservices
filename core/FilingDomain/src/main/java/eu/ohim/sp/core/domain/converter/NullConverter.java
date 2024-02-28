package eu.ohim.sp.core.domain.converter;

import org.dozer.CustomConverter;

public class NullConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return null;
	}

}
