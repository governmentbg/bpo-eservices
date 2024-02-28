package eu.ohim.sp.external.register.converter;

import bg.egov.regix.patentdepartment.ApplicantType;
import bg.egov.regix.patentdepartment.FormattedNameType;
import eu.ohim.sp.core.domain.person.PersonKind;
import org.dozer.CustomConverter;

/**
 * Created by Raya
 * 01.06.2020
 */
public class EBDPatentApplicantKindConverter implements CustomConverter {

    @Override
    public Object convert(Object destination,
                          Object source, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if(sourceClass.isAssignableFrom(ApplicantType.class) && destinationClass.isAssignableFrom(PersonKind.class)){
            ApplicantType extApplicant = (ApplicantType)source;

            if(extApplicant != null && extApplicant.getApplicantAddressBook() != null && extApplicant.getApplicantAddressBook().getFormattedNameAddress() != null &&
                extApplicant.getApplicantAddressBook().getFormattedNameAddress().getName() != null &&
                extApplicant.getApplicantAddressBook().getFormattedNameAddress().getName().getFormattedName() != null){
                FormattedNameType extName = extApplicant.getApplicantAddressBook().getFormattedNameAddress().getName().getFormattedName();
                if(extName.getOrganizationName() != null && !extName.getOrganizationName().isEmpty()){
                    return PersonKind.LEGAL_ENTITY;
                } else {
                    return PersonKind.NATURAL_PERSON;
                }
            }


            return null;
        }
        throw new IllegalArgumentException("Bad source or destination class in PatentApplicantKindConverter");
    }
}
