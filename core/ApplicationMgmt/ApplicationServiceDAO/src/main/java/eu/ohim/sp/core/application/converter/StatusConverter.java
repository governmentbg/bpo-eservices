package eu.ohim.sp.core.application.converter;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.application.ApplicationStatus;

public class StatusConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
        Object returnedObject = null;
        if (value instanceof Integer) {
			Integer status = ((Integer) value);
            returnedObject = ApplicationStatus.fromCode(status);
		} else if (value instanceof ApplicationStatus) {
			ApplicationStatus applicationStatus = ((ApplicationStatus) value);
			if (applicationStatus.getCode() != null) {
                returnedObject = applicationStatus.getCode();
			}
		}
		return returnedObject;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

}