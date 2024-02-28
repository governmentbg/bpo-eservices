/**
 * 
 */
package eu.ohim.sp.dsefiling.ui.service;

import eu.ohim.sp.common.ui.adapter.design.IndicationProductFactory;
import eu.ohim.sp.common.ui.form.design.*;
import eu.ohim.sp.core.classification.LocarnoClassificationService;
import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoClassHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface;
import eu.ohim.sp.dsefiling.ui.transformer.ValidationServiceTransformer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
@EnableCaching
@Service(value = "importService")
public class ImportService implements ImportServiceInterface {

	@Autowired
	private LocarnoClassificationService locarnoClassificationService;

	@Autowired
	private IndicationProductFactory indicationProductFactory;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface#
	 * getClassificationAutocomplete(java.lang.String, java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
    @Cacheable(value = "locarnoAutocompleteUI", key="{#p0,#p1,#p2}", condition = "#p0!=null && #p1!=null && #p2 != null")
	public String getLocarnoAutocomplete(String language, String text, Integer numberOfResults) {
		return locarnoClassificationService.getLocarnoAutocomplete(language,  text, numberOfResults);
	}
	
	@Override
    @Cacheable(value = "locarnoClassesUI", key="{#p0,#p1}", condition = "#p0!=null")
    public List<KeyTextUIClass> getLocarnoClasses(String language, String version) throws Exception {
		List<KeyTextUIClass> ret = new ArrayList<>();

		ClassificationHeading classifications = locarnoClassificationService.getLocarnoClasses(language, version);

		if (classifications != null && classifications.getClassification() != null) {
			for (LocarnoClassHeading lch : classifications.getClassification()) {
				ret.add(new KeyTextUIClass(padClass(lch.getClassCode()), padClass(lch.getClassCode())));
			}
		}
		return ret;
	}
	
	private String padClass(Integer integer) {
		return StringUtils.leftPad(String.valueOf(integer), 2, '0');
	}
	
	@Override
    @Cacheable(value = "locarnoSubclassesUI", key="{#p0,#p1}", condition = "#p0!=null && #p1 != null")
	public List<KeyTextUIClass> getLocarnoSubclasses(Integer locarnoClass, String language, String version) throws Exception {
		List<KeyTextUIClass> ret = new ArrayList<>();

		List<LocarnoSubClassHeading> listSubClassifications = locarnoClassificationService.getLocarnoSubclasses(locarnoClass, language, version);

		if (listSubClassifications != null) {
			for (LocarnoSubClassHeading lsh : listSubClassifications) {
				ret.add(new KeyTextUIClass(padClass(lsh.getSubClassCode()),  padClass(lsh.getSubClassCode())));
			}
		}
		return ret;
	}
	
	@Override
	public IndicationProductForm validateIndicationProductForm(String language, IndicationProductForm indicationProductForm) {
		IndicationProductForm validatedIndicationProductForm = indicationProductForm;
		if (indicationProductForm == null) {
			throw new IllegalArgumentException("Parameter indicationProductForm can´t be null");
		}
        if (StringUtils.isBlank(language)) {
            throw new IllegalArgumentException("Parameter language can´t be empty");
        }
		ClassDescription classDescription = indicationProductFactory.convertTo(language, indicationProductForm);
		List<ClassDescription> classDescriptions = locarnoClassificationService.validateClassDescriptions(Arrays.asList(classDescription));

		if (classDescriptions != null && classDescriptions.size() == 1) {
			validatedIndicationProductForm = indicationProductFactory.convertFrom(classDescriptions.get(0));
		}
		return validatedIndicationProductForm;
	}

	@Override
	public IndicationProductForm validateIndicationProductFormList(String language, List<IndicationProductForm> indicationProductFormList) {
        if (StringUtils.isBlank(language)) {
            throw new IllegalArgumentException("Parameter language can´t be empty");
        }
	    IndicationProductForm validatedIndicationProductForm = new IndicationProductForm();
		validatedIndicationProductForm.setSuggestions(new ArrayList<>());

		if(indicationProductFormList != null && !indicationProductFormList.isEmpty()) {
			List<ClassDescription> classDescriptions = new ArrayList<>();
			for(IndicationProductForm indicationProductForm: indicationProductFormList) {
				ClassDescription classDescription = indicationProductFactory.convertTo(language, indicationProductForm);
				classDescriptions.add(classDescription);
			}

			List<ClassDescription> validationResults = locarnoClassificationService.validateClassDescriptions(classDescriptions);
			if (validationResults != null && validationResults.size() >0) {
				boolean valid = true;
				boolean modifiable = true;
				for(ClassDescription validationResult: validationResults){
					IndicationProductForm resultTempForm = indicationProductFactory.convertFrom(validationResult);
					if(resultTempForm.getSuggestions() != null) {
						validatedIndicationProductForm.getSuggestions().addAll(resultTempForm.getSuggestions());
					}

					if(resultTempForm.getValidation().equals(ValidationCode.invalid) || resultTempForm.getValidation().equals(ValidationCode.notfound)){
						valid = false;
						modifiable = false;
					}
					if(resultTempForm.getValidation().equals(ValidationCode.editable)){
						valid = false;
					}
				}

				if(valid){
					validatedIndicationProductForm.setValidation(ValidationCode.valid);
				} else if(modifiable){
					validatedIndicationProductForm.setValidation(ValidationCode.editable);
				} else {
					validatedIndicationProductForm.setValidation(ValidationCode.notfound);
				}

			}
		}
		return validatedIndicationProductForm;
	}

	@Override
	public LocarnoAbstractForm validateLocarnoForm(String language, LocarnoAbstractForm locarnoAbstractForm) {
		LocarnoAbstractForm result = null;
		if(locarnoAbstractForm != null){
			if(locarnoAbstractForm instanceof LocarnoForm){
				IndicationProductForm validatedIndicationProductForm = validateIndicationProductForm(language,
					ValidationServiceTransformer.convertToIndicationProductForm((LocarnoForm) locarnoAbstractForm));
				result = ValidationServiceTransformer.convertToLocarnoForm(validatedIndicationProductForm, (LocarnoForm)locarnoAbstractForm);
			} else if(locarnoAbstractForm instanceof LocarnoComplexForm){
				IndicationProductForm validatedIndicationProductForm = validateIndicationProductFormList(language,
					ValidationServiceTransformer.convertToIndicationProductFormList((LocarnoComplexForm) locarnoAbstractForm));
				result = ValidationServiceTransformer.convertToLocarnoComplexForm(validatedIndicationProductForm, (LocarnoComplexForm) locarnoAbstractForm);
			}
		}
		return result;
	}

	@Override
	public TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput,
                                Integer selectedClass, Integer selectedSubclass, final Object... arguments) {
		
		TermsSearch resp = locarnoClassificationService.getTerms(offices,language,page,pageSize,searchInput,selectedClass, selectedSubclass, arguments);
		return resp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.ohim.sp.dsefiling.ui.service.interfaces.ImportServiceInterface#getTranslations(java.lang.String, java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass, String subclass) {
		return locarnoClassificationService.getTranslations(languageFrom, languageTo, termTranslate, productIndicationIdentifier, selectedClass, subclass);
	}
	
}
