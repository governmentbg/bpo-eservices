package eu.ohim.sp.external.register.converter;

import eu.ohim.sp.core.domain.contact.Email;
import org.dozer.CustomConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 14.08.2019
 */
public class EmailsConverter implements CustomConverter {

    @Override
    public Object convert(Object destination,
                          Object source, Class<?> destinationClass,
                          Class<?> sourceClass) {

        if(List.class.isAssignableFrom(sourceClass) &&  List.class.isAssignableFrom(destinationClass)) {
            List emailsStrs = (List)source;

            if (destination == null) {
                destination = new ArrayList<>();
            }

            for(Object emailStr: emailsStrs) {
                if(!emailStr.toString().isEmpty()) {
                    Email destEmail = new Email();
                    destEmail.setEmailAddress(emailStr.toString());

                    ((List) destination).add(destEmail);
                }
            }

            return destination;
        }

        throw new IllegalArgumentException("Bad source or destination class in EmailsConverter");
    }
}
