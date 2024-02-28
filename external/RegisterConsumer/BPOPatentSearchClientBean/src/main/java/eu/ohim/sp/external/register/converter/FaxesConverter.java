package eu.ohim.sp.external.register.converter;

import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import org.dozer.CustomConverter;

import java.util.ArrayList;
import java.util.List;

public class FaxesConverter implements CustomConverter {

	@Override
	public Object convert(Object destination,
						  Object source, Class<?> destinationClass,
						  Class<?> sourceClass) {

		if(List.class.isAssignableFrom(sourceClass) &&  List.class.isAssignableFrom(destinationClass)) {
			List faxesStr = (List)source;

			if (destination == null) {
				destination = new ArrayList<>();
			}

			for(Object faxStr: faxesStr) {
				if(!faxStr.toString().isEmpty()) {
					Phone destPhone = new Phone();
					destPhone.setPhoneKind(PhoneKind.FAX);
					destPhone.setNumber((String) faxStr);
					((List) destination).add(destPhone);
				}
			}

			return destination;
		}

		throw new IllegalArgumentException("Bad source or destination class in FaxesConverter");
	}

}