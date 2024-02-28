package eu.ohim.sp.external.utils;

import eu.ohim.sp.core.domain.person.PersonKind;
import org.dozer.CustomConverter;

/**
 * Date: 09/04/14
 * Time: 12:20
 *
 * @author gregor.lah@ext.oami.europa.eu
 */
public class CorePersonKindToExternalPersonKind implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        if (!destinationClass.isEnum()) {
            throw new IllegalArgumentException("destinationClass must be an enum");
        }

        if(sourceFieldValue == null) return null;


        if (sourceClass.equals(PersonKind.class) && destinationClass.equals(eu.ohim.sp.external.domain.person.PersonKind.class)) {

            return eu.ohim.sp.external.domain.person.PersonKind.fromValue(((PersonKind) sourceFieldValue).value());

        }

        throw new IllegalArgumentException("Enum constant [" + sourceFieldValue + "] not found in " + destinationClass);
    }

}
