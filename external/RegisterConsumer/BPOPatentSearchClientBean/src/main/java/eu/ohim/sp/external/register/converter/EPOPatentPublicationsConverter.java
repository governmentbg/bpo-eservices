package eu.ohim.sp.external.register.converter;

import bg.egov.regix.patentdepartment.PatentType;
import bg.egov.regix.patentdepartment.PublicationType;
import eu.ohim.sp.core.domain.patent.Patent;
import org.dozer.CustomConverter;

import java.util.Optional;

/**
 * Created by Raya
 * 11.07.2019
 */
public class EPOPatentPublicationsConverter implements CustomConverter {

    @Override
    public Object convert(Object destination,
                          Object source, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if(sourceClass.isAssignableFrom(PatentType.PublicationDetails.class) && destinationClass.isAssignableFrom(Patent.class)){
            PatentType.PublicationDetails publicationDetails = (PatentType.PublicationDetails)source;
            Patent patent = (Patent)destination;
            if(patent == null){
                patent = new Patent();
            }

            if(publicationDetails != null && publicationDetails.getPublication() != null && publicationDetails.getPublication().size()>0){
                Optional<PublicationType> optionalPublication = publicationDetails.getPublication().stream().filter(p -> p.getPublicationSectionCode().equals("31")).findAny();
                if(optionalPublication.isPresent()){
                    patent.setPatentValidated(true);
                }
            }

            return patent;
        }
        return null;
    }
}
