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
public class EBDPatentPublicationsConverter implements CustomConverter {

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

            StringBuilder publicationInfoBuilder = new StringBuilder();
            if(publicationDetails != null && publicationDetails.getPublication() != null && publicationDetails.getPublication().size()>0){
                for(PublicationType pub: publicationDetails.getPublication()){
                    if(pub.getPublicationSectionCode() != null) {
                        if (pub.getPublicationSectionCode().equals("1")) {
                            patent.setPublicationDate(pub.getPublicationDate());
                            patent.setPublicationNumber(pub.getPublicationIdentifier());
                        }
                        if (pub.getPublicationSectionCode().equals("2")) {
                            patent.setRegistrationPublicationDate(pub.getPublicationDate());
                        }
                    }

                    publicationInfoBuilder.append(pub.getPublicationIdentifier() != null ? pub.getPublicationIdentifier()+"; " : "");
                }
            }
            if(publicationInfoBuilder.length()>0){
                patent.setPatentPublicationsInfo(publicationInfoBuilder.toString());
            }

            return patent;
        }
        return null;
    }
}
