//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.common.ui.adapter.design;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.IndicationProductForm;
import eu.ohim.sp.core.domain.classification.TermLocarno;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.EUFirstLanguageCode;
import eu.ohim.sp.core.domain.design.LocalizedText;
import eu.ohim.sp.core.domain.design.ValidationCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * #DS Class Integration changes.
 * The type Indication product factory.
 * @author Ramittal
 *
 */
@Component
public class IndicationProductFactory implements UIFactory<ClassDescription, IndicationProductForm> {

	/** The localized text factory. */
	@Autowired
	private LocalizedTextFactory localizedTextFactory;

	/** The validation code factory. */
	@Autowired
	private ValidationCodeFactory validationCodeFactory;

	@Override
	public ClassDescription convertTo(IndicationProductForm form) {
		return convertTo(form, null);
	}

	/**
	 * Convert the class description
	 *
	 * @param form
	 * @param firstLang
	 * @return ClassDescription
	 */
	public ClassDescription convertTo(IndicationProductForm form, String firstLang) {
		ClassDescription core = new ClassDescription();
		core.setClassificationTermIdentifier(form.getIdentifier());
		core.setClassId(StringUtils.isEmpty(form.getClassCode()) ? Integer.valueOf(-1) : Integer.valueOf(form.getClassCode()));
		core.setSubClassId(StringUtils.isEmpty(form.getSubclassCode()) ? Integer.valueOf(-1) : Integer.valueOf(form.getSubclassCode()));
		core.getProductDescriptions().add(firstLang == null? localizedTextFactory.convertTo(form.getText()) : localizedTextFactory.convertTo(form.getText(), firstLang));
		if (form.getValidation() != null) {
			core.setValid(validationCodeFactory.convertTo(form.getValidation()));
		}
		return core;
	}

	@Override
	public IndicationProductForm convertFrom(ClassDescription core) {
		IndicationProductForm form = new IndicationProductForm();
		form.setIdentifier(core.getClassificationTermIdentifier());
		if (StringUtils.isNotEmpty(core.getLocarnoClassNumber()) && !".".equals(core.getLocarnoClassNumber())) {
			String[] parts = core.getLocarnoClassNumber().split("[.]");
			form.setClassCode(parts[0]);
			if (parts.length > 1) {
				form.setSubclassCode(parts[1]);
			} else {
				form.setSubclassCode(IndicationProductForm.UNKNOWN_CLASS_CODE_STR);
			}
		} else {
			form.setClassCode(IndicationProductForm.UNKNOWN_CLASS_CODE_STR);
			form.setSubclassCode(IndicationProductForm.UNKNOWN_CLASS_CODE_STR);
		}
		form.setText(!core.getProductDescriptions().isEmpty() ? localizedTextFactory.convertFrom(core.getProductDescriptions().get(0)) : null);
		if (core.getValid() != null) {
			form.setValidation(validationCodeFactory.convertFrom(core.getValid()));
			form.setTermStatus(form.getValidation().name());
			final List<ClassDescription> suggestions = core.getSuggestions();
			if (Arrays.asList(ValidationCode.HINT, ValidationCode.NOT_FOUND).contains(core.getValid()) && suggestions != null) {
				form.setSuggestions(suggestions.stream().map(this::convertFrom).collect(Collectors.toList()));
			}
		}

		return form;
	}

	/**
	 * Convert the Indication Product Form
	 *
	 * @param core
	 * @return IndicationProductForm
	 */
	public IndicationProductForm convertFrom(TermLocarno core) {
		IndicationProductForm form = new IndicationProductForm();
		form.setIdentifier(core.getIdentifier());
		form.setText(core.getProductIndication());
		form.setClassCode(core.getClassCode() != null ? String.valueOf(core.getClassCode()) : "-1");
		form.setGroup(core.isGroup());
		form.setHeading(core.isHeading());
		form.setSubclassCode(core.getSubclassCode() != null ? String.valueOf(core.getSubclassCode()) : "-1");
		if (core.getRelatedTerms() != null) {
			List<IndicationProductForm> relatedTerms = new ArrayList<>(core.getRelatedTerms().size());
			for (TermLocarno termLocarno : core.getRelatedTerms()) {
				relatedTerms.add(convertFrom(termLocarno));
			}
			form.setSuggestions(relatedTerms);
		}
		return form;
	}

	/**
	 * Convert To Term Locarno
	 * 
	 * @param form
	 * @return TermLocarno
	 */
	public TermLocarno convertToTermLocarno(IndicationProductForm form) {
		TermLocarno core = new TermLocarno();
		core.setIdentifier(form.getIdentifier());
		core.setProductIndication(form.getText());
		if (StringUtils.isNotEmpty(form.getClassCode())) {
			core.setClassCode(Integer.valueOf(form.getClassCode()));
		}
		if (StringUtils.isNotEmpty(form.getSubclassCode())) {
			core.setSubclassCode(Integer.valueOf(form.getSubclassCode()));
		}
		
		List<IndicationProductForm> suggestions = form.getSuggestions();
		if(suggestions != null) {
			core.setRelatedTerms(suggestions.stream().map(this::convertToTermLocarno).collect(Collectors.toCollection(ArrayList::new)));
		}
		return core;
	}

	/**
	 * Convert to.
	 *
	 * @param language the language
	 * @param form the form
	 * @return the class description
	 */
	public ClassDescription convertTo(String language, IndicationProductForm form) {
		ClassDescription core = convertTo(form);
		EUFirstLanguageCode languageCode = EUFirstLanguageCode.fromValue(language);
		List<LocalizedText> newProductDescriptions = new ArrayList<>(core.getProductDescriptions().size());
		for (LocalizedText productDescription : core.getProductDescriptions()) {
			LocalizedText newLocalizedText = new LocalizedText();
			newLocalizedText.setLanguageCode(languageCode);
			newLocalizedText.setValue(productDescription.getValue());
			newProductDescriptions.add(newLocalizedText);
		}
		core.getProductDescriptions().clear();
		core.getProductDescriptions().addAll(newProductDescriptions);
		return core;
	}

	/**
	 * Convert to.
	 *
	 * @param fromList the from list
	 * @return the list
	 */
	public List<ClassDescription> convertTo(List<IndicationProductForm> fromList) {
		return convertTo(fromList, null);
	}

	/**
	 * Convert to.
	 *
	 * @param fromList the from list
	 * @param firstLang
	 * @return the list
	 */
	public List<ClassDescription> convertTo(List<IndicationProductForm> fromList, String firstLang) {
		return fromList.stream().map(pi -> convertTo(pi, firstLang)).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Convert from.
	 *
	 * @param coreList the core list
	 * @return the list
	 */
	public List<IndicationProductForm> convertFrom(List<ClassDescription> coreList) {
		return coreList.stream().map(this::convertFrom).collect(Collectors.toCollection(ArrayList::new));
	}

}
