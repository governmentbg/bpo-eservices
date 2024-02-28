package eu.ohim.sp.core.domain.converter.tm;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.filing.domain.tm.Identifier;

public class LimitedTradeMarkRecordConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof LimitedTradeMark) {
			if (!StringUtils.isEmpty(((LimitedTradeMark)value).getApplicationNumber())){
				Identifier identifier = new Identifier();
				identifier.setValue(((LimitedTradeMark)value).getApplicationNumber());
				identifier.setIdentifierKindCode("Application Number");
				return identifier;
			}
			else if (!StringUtils.isEmpty(((LimitedTradeMark)value).getRegistrationNumber())){
				Identifier identifier = new Identifier();
				identifier.setValue(((LimitedTradeMark)value).getRegistrationNumber());
				identifier.setIdentifierKindCode("Registration Number");
				return identifier;
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
