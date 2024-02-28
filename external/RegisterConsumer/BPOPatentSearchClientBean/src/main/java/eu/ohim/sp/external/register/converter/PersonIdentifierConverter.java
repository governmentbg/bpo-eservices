package eu.ohim.sp.external.register.converter;

import eu.ohim.sp.core.domain.person.PersonIdentifier;
import org.dozer.CustomConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raya
 * 13.08.2019
 */
public class PersonIdentifierConverter  implements CustomConverter {

    @Override
    public Object convert(Object destination,
                          Object source, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if(List.class.isAssignableFrom(sourceClass) &&  List.class.isAssignableFrom(destinationClass)){
            List ids = (List)source;

            if(destination == null){
                destination = new ArrayList<PersonIdentifier>();

            }

            for(Object id: ids){
                PersonIdentifier personIdentifier = new PersonIdentifier();
                personIdentifier.setValue(id.toString());
                ((List)destination).add(personIdentifier);
            }

            return destination;
        }
        throw new IllegalArgumentException("Bad source or destination class in PersonIdentifierConverter");
    }
}
