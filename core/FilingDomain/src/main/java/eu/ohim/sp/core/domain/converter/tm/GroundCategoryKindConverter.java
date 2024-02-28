package eu.ohim.sp.core.domain.converter.tm;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;

public class GroundCategoryKindConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		 if (value instanceof GroundCategoryKind) {
			switch ((GroundCategoryKind)value) {
			case ABSOLUTE_GROUNDS:
				return "Absolute Ground Basis";
			default :
				return "Other";
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
