package eu.ohim.sp.external.register.converter;

import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import org.dozer.CustomConverter;

import java.util.ArrayList;
import java.util.List;

public class PhonesConverter implements CustomConverter {

	@Override
	public Object convert(Object destination,
			Object source, Class<?> destinationClass,
			Class<?> sourceClass) {

		if(List.class.isAssignableFrom(sourceClass) &&  List.class.isAssignableFrom(destinationClass)) {
			List phonesStrs = (List)source;

			if (destination == null) {
				destination = new ArrayList<>();
			}

			for(Object phoneStr: phonesStrs) {
				if(!phoneStr.toString().isEmpty()) {
					Phone destPhone = new Phone();
					destPhone.setPhoneKind(PhoneKind.MOBILE_PHONE);
					destPhone.setNumber((String) phoneStr);
					((List) destination).add(destPhone);
				}
			}

			return destination;
		}

		throw new IllegalArgumentException("Bad source or destination class in PhonesConverter");
	}

}