/*
 *  FspDomain:: GoodsServicesDetailsConverter 15/10/13 13:28 karalch $
 *  * . * .
 *  * * RRRR * Copyright Â© 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorType;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.filing.domain.tm.ClassDescriptionDetails;
import eu.ohim.sp.filing.domain.tm.ClassNumber;
import eu.ohim.sp.filing.domain.tm.ClassificationTermDetails;
import eu.ohim.sp.filing.domain.tm.ClassificationTermType;
import eu.ohim.sp.filing.domain.tm.GoodsServices;
import eu.ohim.sp.filing.domain.tm.GoodsServicesDetails;
import eu.ohim.sp.filing.domain.tm.ISOLanguageCode;
import eu.ohim.sp.filing.domain.tm.TermAssessmentType;
import eu.ohim.sp.filing.domain.tm.Text;

public class GoodsServicesDetailsConverter implements CustomConverter, Converter, MapperAware {

	private Mapper mapper;
	
	@Override
	public Object convert(Class type, Object value) {
		if (type.equals(GoodsServicesDetails.class)) {
			GoodsServicesDetails goodsServicesDetails = new GoodsServicesDetails();
			goodsServicesDetails.getGoodsServices().add(new GoodsServices());
			goodsServicesDetails.getGoodsServices().get(0).setClassDescriptionDetails(new ClassDescriptionDetails());
			for (eu.ohim.sp.core.domain.trademark.ClassDescription g : (List<ClassDescription>) value) {
				goodsServicesDetails.getGoodsServices().get(0).getClassDescriptionDetails()
					.getClassDescription().add(coreGoodsServicesToFspClassDescription(g));
	        }
		
			return goodsServicesDetails;
		}
		return null;
	}
	
	
    /**
     * Receives as a parameter an FSP domain GoodsServices object and converts it to a core GoodsServices object.
     * 
     * @param fsp the FSP domain GoodsServices object to convert
     * @return the GoodsServices core object
     */
    public List<ClassDescription> fspGoodsServicestToCoreGoodsServices(eu.ohim.sp.filing.domain.tm.GoodsServices fsp) {
        List<ClassDescription> listOfGoodsServices = new ArrayList<ClassDescription>();

        for (eu.ohim.sp.filing.domain.tm.ClassDescription classDescription : fsp.getClassDescriptionDetails().getClassDescription()) {
            listOfGoodsServices.add(fspClassDescriptionToCoreGoodsServices(classDescription));
        }

        return listOfGoodsServices;
    }

    public ClassDescription fspClassDescriptionToCoreGoodsServices(eu.ohim.sp.filing.domain.tm.ClassDescription fsp) {
    	ClassDescription core = new ClassDescription();
        core.setClassNumber(fsp.getClassNumber().getValue());
        core.setFullClassCoverageIndicator(fsp.isFullClassCoverageIndicator());
        for (ClassificationTermDetails classificationTermDetails : fsp.getClassificationTerms()) {
        	if (classificationTermDetails.getLanguage() != null) {
        		core.setLanguage(classificationTermDetails.getLanguage().value());
        	}
            for (ClassificationTermType classificationTermType : classificationTermDetails.getClassificationTerm()) {
                ClassificationTerm classificationTerm = new ClassificationTerm();
                classificationTerm.setTermText(classificationTermType.getClassificationTermText().getValue());
                ClassificationErrorType termAssessment = new ClassificationErrorType();
                ClassificationErrorType niceTermAssessment = new ClassificationErrorType();

                Map<String, String> map = new HashMap<String, String>();
                map.put(TermAssessmentType.REJECTED_TERM.value(), ClassificationErrorType.REJECTED_TERM);
                map.put(TermAssessmentType.CONTROLLED_TERM.value(), ClassificationErrorType.CONTROLLED_TERM);
                map.put(TermAssessmentType.CONTAINS_CONTROLLED_TERM.value(), ClassificationErrorType.CONTAINS_CONTROLLED_TERM);
                map.put(TermAssessmentType.LEGACY_TERM.value(), ClassificationErrorType.LEGACY_TERM);
                map.put(TermAssessmentType.CONTAINS_LEGACY_TERM.value(), ClassificationErrorType.CONTAINS_LEGACY_TERM);
                map.put(TermAssessmentType.TERM_NOT_FOUND.value(), ClassificationErrorType.NOT_FOUND);
                map.put(TermAssessmentType.NO_CLASS_GIVEN_TERM.value(), ClassificationErrorType.NO_CLASS_GIVEN);
                map.put(TermAssessmentType.EQUIVALENT_TERM_IN_ANOTHER_CLASS.value(), ClassificationErrorType.SYNONYM_WRONG_CLASS);
                map.put(TermAssessmentType.EQUIVALENT_TERM.value(), ClassificationErrorType.SYNONYM);
                map.put(TermAssessmentType.SIMILAR_TERM.value(), ClassificationErrorType.WRONG_CLASS);
                map.put(TermAssessmentType.MULTICLASS_TERM.value(), ClassificationErrorType.HOMONYM);
                
                if (classificationTermType.getClassificationTermAssessment() != null) {
                	termAssessment.setVerificationAssessment(map.get(classificationTermType.getClassificationTermAssessment().value()));
                	if (classificationTermType.getClassificationTermAssessment() != TermAssessmentType.CONTROLLED_TERM) {
                		termAssessment.setErrorEnum(ClassificationErrorEnum.NOT_OK);
                	}
                }
                if (classificationTermType.getNiceClassificationTermAssessment() != null) {
                    niceTermAssessment.setVerificationAssessment(map.get(classificationTermType.getNiceClassificationTermAssessment().value()));
                    if (classificationTermType.getNiceClassificationTermAssessment() != TermAssessmentType.CONTROLLED_TERM) {
                        niceTermAssessment.setErrorEnum(ClassificationErrorEnum.NOT_OK);
                    }
                }
				classificationTerm.setTermAssessment(termAssessment);
				classificationTerm.setNiceTermAssessment(niceTermAssessment);

                core.getClassificationTerms().add(classificationTerm);
            }
        }
        return core;
    }
    
    
    public eu.ohim.sp.filing.domain.tm.ClassDescription coreGoodsServicesToFspClassDescription(
            ClassDescription goodsServices) {
        eu.ohim.sp.filing.domain.tm.ClassDescription classDescription = new eu.ohim.sp.filing.domain.tm.ClassDescription();

        ClassificationTermDetails classificationTermDetailsType = new ClassificationTermDetails();
        classDescription.getClassificationTerms().add(classificationTermDetailsType);
        classDescription.setClassNumber(new ClassNumber(goodsServices.getClassNumber()));
        classDescription.setFullClassCoverageIndicator(goodsServices.isFullClassCoverageIndicator());

        classificationTermDetailsType.setLanguage(mapper.map(goodsServices.getLanguage(), ISOLanguageCode.class));

        Map<String, String> map = new HashMap<String, String>();
        map.put(ClassificationErrorType.REJECTED_TERM, TermAssessmentType.REJECTED_TERM.value());
        map.put(ClassificationErrorType.CONTROLLED_TERM, TermAssessmentType.CONTROLLED_TERM.value());
        map.put(ClassificationErrorType.CONTAINS_CONTROLLED_TERM, TermAssessmentType.CONTAINS_CONTROLLED_TERM.value());
        map.put(ClassificationErrorType.LEGACY_TERM, TermAssessmentType.LEGACY_TERM.value());
        map.put(ClassificationErrorType.SIMILAR_TERM, TermAssessmentType.SIMILAR_TERM.value());
        map.put(ClassificationErrorType.CONTAINS_LEGACY_TERM, TermAssessmentType.CONTAINS_LEGACY_TERM.value());
        map.put(ClassificationErrorType.NOT_FOUND, TermAssessmentType.TERM_NOT_FOUND.value());
        map.put(ClassificationErrorType.NO_CLASS_GIVEN, TermAssessmentType.NO_CLASS_GIVEN_TERM.value());
        map.put(ClassificationErrorType.SYNONYM_WRONG_CLASS, TermAssessmentType.EQUIVALENT_TERM_IN_ANOTHER_CLASS.value());
        map.put(ClassificationErrorType.SYNONYM, TermAssessmentType.EQUIVALENT_TERM.value());
        map.put(ClassificationErrorType.WRONG_CLASS, TermAssessmentType.SIMILAR_TERM.value());
        map.put(ClassificationErrorType.HOMONYM, TermAssessmentType.MULTICLASS_TERM.value());

        for (ClassificationTerm classificationTerm : goodsServices.getClassificationTerms()) {
            ClassificationTermType classificationTermType = new ClassificationTermType();
            classificationTermType.setClassificationTermText(new Text(classificationTerm.getTermText(), null));
            if (classificationTerm.getTermAssessment() != null
                    && classificationTerm.getTermAssessment().getVerificationAssessment() != null) {
                classificationTermType.setClassificationTermAssessment(TermAssessmentType.fromValue(map
                			.get(classificationTerm.getTermAssessment().getVerificationAssessment())));

            }
            if (classificationTerm.getNiceTermAssessment() != null
                && classificationTerm.getNiceTermAssessment().getVerificationAssessment() != null) {
                classificationTermType.setNiceClassificationTermAssessment(TermAssessmentType.fromValue(map
                    .get(classificationTerm.getNiceTermAssessment().getVerificationAssessment())));

            }
            classificationTermDetailsType.getClassificationTerm().add(classificationTermType);
        }

        return classDescription;
    }


	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue instanceof ClassDescription) {
			return coreGoodsServicesToFspClassDescription((ClassDescription) sourceFieldValue);
		} else if (sourceFieldValue instanceof eu.ohim.sp.filing.domain.tm.ClassDescription) {
			return fspClassDescriptionToCoreGoodsServices((eu.ohim.sp.filing.domain.tm.ClassDescription) sourceFieldValue);
		} else {
            return null;
        }
	}


	@Override
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}
