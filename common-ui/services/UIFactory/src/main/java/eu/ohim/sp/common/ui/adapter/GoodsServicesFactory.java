/*******************************************************************************
 * * $Id:: GoodsServicesFactory.java 113496 2013-04-22 15:03:04Z karalch         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.validation.ErrorType;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorEnum;
import eu.ohim.sp.core.domain.trademark.ClassificationErrorType;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;

@Component
public class GoodsServicesFactory implements UIFactory<ClassDescription, GoodAndServiceForm> {

	@Value("${gs.terms.seperator}")
    private String termSeparator;

	/**
	 * Receives as a parameter a UI {@link GoodAndServiceForm} object and converts it to
	 * a core {@link ClassDescription} object.
	 * 
	 * @param goodAndServiceForm
	 *            the UI GoodAndServiceForm to convert
	 * @return core GoodsServices object
	 */
	@Override
	public ClassDescription convertTo(GoodAndServiceForm goodAndServiceForm) {
		if(goodAndServiceForm.getTermForms().size()>0){
			ClassDescription clazz = new ClassDescription();
			clazz.setClassNumber(goodAndServiceForm.getClassId());
			clazz.setLanguage(goodAndServiceForm.getLangId());
			clazz.setFullClassCoverageIndicator(goodAndServiceForm.isDisabledRemoval());
			for (TermForm termForm : goodAndServiceForm.getTermForms()) {
				ClassificationTerm classificationTerm = new ClassificationTerm();
				classificationTerm.setTermText(termForm.getDescription());
				if (termForm.getError() != null) {
					ClassificationErrorType errorType = new ClassificationErrorType();
					errorType.setErrorEnum(termForm.getError().getErrorEnum());
					errorType.setVerificationAssessment(termForm.getError().getVerificationAssessment());
					errorType.setMatchedTerms(termForm.getError().getMatchedTerms());
					classificationTerm.setTermAssessment(errorType);
				}
				if(termForm.getNiceError() != null){
					ClassificationErrorType niceErrorType = new ClassificationErrorType();
					niceErrorType.setErrorEnum(termForm.getNiceError().getErrorEnum());
					niceErrorType.setVerificationAssessment(termForm.getNiceError().getVerificationAssessment());
					niceErrorType.setMatchedTerms(termForm.getNiceError().getMatchedTerms());
					classificationTerm.setNiceTermAssessment(niceErrorType);
				}
				clazz.getClassificationTerms().add(classificationTerm);
			}
			return clazz;
		}
		else{
			return null;
		}
		
	}

	/**
	 * Receives as a parameter a core {@link ClassDescription} object and converts it
	 * to a {@link GoodAndServiceForm} object.
	 *
	 * @param core the core GoodsServices object
	 * @return the converted UI GoodAndServiceForm
	 */
	@Override
	public GoodAndServiceForm convertFrom(ClassDescription core) {
		
		if (core == null) {
			return new GoodAndServiceForm();
		}
		GoodAndServiceForm gsForm = new GoodAndServiceForm();
        gsForm.setClassId(core.getClassNumber());
        gsForm.setLangId(core.getLanguage());
        gsForm.setClassId(core.getClassNumber());
        gsForm.setDesc(core.getGoodsServicesDescription());
        gsForm.setDisabledRemoval((core.isFullClassCoverageIndicator() != null ? core.isFullClassCoverageIndicator() : false));
		if (core.getClassificationTerms() != null) {
			for (ClassificationTerm classificationTerm : core.getClassificationTerms()) {
				TermForm termForm = new TermForm();
				termForm.setDescription(classificationTerm.getTermText());
				termForm.setIdClass(core.getClassNumber());
				
				if (classificationTerm.getTermAssessment() != null && classificationTerm.getTermAssessment().getErrorEnum() != null) {
					ErrorType errorType = new ErrorType();
					errorType.setErrorEnum(ClassificationErrorEnum.valueOf(classificationTerm.getTermAssessment().getErrorEnum().toString()));
					errorType.setVerificationAssessment(classificationTerm.getTermAssessment().getVerificationAssessment());
					termForm.setError(errorType);
				}
				if (classificationTerm.getNiceTermAssessment() != null && classificationTerm.getNiceTermAssessment().getErrorEnum() != null) {
					ErrorType errorType = new ErrorType();
					errorType.setErrorEnum(ClassificationErrorEnum.valueOf(classificationTerm.getNiceTermAssessment().getErrorEnum().toString()));
					errorType.setVerificationAssessment(classificationTerm.getNiceTermAssessment().getVerificationAssessment());
					termForm.setNiceError(errorType);
				}
				
				gsForm.addTermForm(termForm);
			}
		}
		return gsForm;
	}

	/**
	 * Receives as a parameter a UI {@link GoodAndServiceForm} object associated to a PriorityForm and converts it to
	 * a core {@link ClassDescription} object.
	 * This method is different from convertTo because this Form object contains the GS terms as a comma-separated string
	 * in the description, instead of different Term objects as the other method
	 * 
	 * @param goodAndServiceForm
	 *            the UI GoodAndServiceForm to convert
	 * @return the core GoodsServices object
	 */
	public ClassDescription convertPartialGSTo(GoodAndServiceForm goodAndServiceForm) {
        ClassDescription clazz = new ClassDescription();
		clazz.setClassNumber(goodAndServiceForm.getClassId());
		clazz.setLanguage(goodAndServiceForm.getLangId());
		String[] terms = goodAndServiceForm.getDesc().split(termSeparator);
		for (String term : terms) {
			term = term.trim();
			if(!term.isEmpty()) {
				ClassificationTerm classificationTerm = new ClassificationTerm();
				classificationTerm.setTermText(term.trim());
				clazz.getClassificationTerms().add(classificationTerm);
			}
		}		
		return clazz;
	}
	
	/**
	 * Receives as a parameter a core {@link ClassDescription} object and converts it
	 * to a {@link GoodAndServiceForm} object that is associated to a partial 
	 * priority claim (PriorityForm)
	 *
	 *
	 * @param core the core GoodsServices object
	 * @return the converted UI GoodAndServiceForm
	 */
	public GoodAndServiceForm convertPartialGSFrom(ClassDescription core) {
		if (core == null) {
			return new GoodAndServiceForm();
		}

		GoodAndServiceForm form = new GoodAndServiceForm();
		form.setClassId(core.getClassNumber());
		form.setLangId(core.getLanguage());
		
		if (core.getClassificationTerms() != null) {
			StringBuffer desc = new StringBuffer();
			int iterator = 0;
            int numberOfTerms = core.getClassificationTerms().size();
			for (ClassificationTerm classificationTerm : core.getClassificationTerms()) {
				desc.append(classificationTerm.getTermText());
				if ((++iterator) < numberOfTerms) {
					desc.append(termSeparator+" ");
				}
			}
			form.setDesc(desc.toString());
		}

		return form;
	}

}
