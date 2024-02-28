/*
 * LocarnoClassificationService:: LocarnoClassificationService 04/09/13 13:57 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.classification;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.external.classification.LocarnoClassificationClientInside;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.ExceptionHandlingInterceptor;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;
import eu.ohim.sp.core.rules.RulesService;
import eu.ohim.sp.core.util.SectionUtil;

/**
 * The Class ClassificationService.
 */
@Interceptors(ExceptionHandlingInterceptor.class)
@Stateless
public class LocarnoClassificationServiceBean implements LocarnoClassificationServiceRemote,
        LocarnoClassificationServiceLocal {

    private final Logger logger = Logger.getLogger(LocarnoClassificationService.class);

    @EJB(lookup = "java:global/businessRulesLocal")
    private RulesService businessRulesService;

    @Inject @LocarnoClassificationClientInside
    private LocarnoClassificationService locarnoClassificationServiceProvider;

    /**
     * The Constant INDICATION_PRODUCT.
     */
    private static final String INDICATION_PRODUCT = "indication_product_set";

    @Override
    public List<ProductIndication> getFullClassification(String lang) {
        return locarnoClassificationServiceProvider.getFullClassification(lang);
    }

    @Override
	public List<ProductIndication> getProductIndications(String clazz, String subclass, String lang) {
		return locarnoClassificationServiceProvider.getProductIndications(clazz, subclass, lang);
	}

	@Override
    public List<ProductIndication> searchProductIndication(String indicationProduct, String clazz, String subclass,
            String lang) {
        return locarnoClassificationServiceProvider.searchProductIndication(indicationProduct, clazz, subclass, lang);
    }

    @Override
    public ErrorList validateProductIndication(String module, ProductIndication productIndication,
            RulesInformation rulesInformation) {
        if (productIndication != null) {
            logger.debug(">>> Validate Product Indication : " + productIndication.getDescription());
        }

        // Variable declaration
        List<Object> objects = new ArrayList<Object>();
        Section section = SectionUtil.getSectionByRulesInformation(rulesInformation);

        // Prepares the objects to insert in the session
        objects.add(section);
        objects.add(productIndication);

        // Starts the check
        ErrorList errors = businessRulesService.validate(module, INDICATION_PRODUCT, objects);

        if (logger.isDebugEnabled()) {
            logger.debug("<<< Validate Product Indication END");
        }

        return errors;
    }

    /*<RFC DS Class Integration*/
    @Override
    public String getLocarnoAutocomplete(String language, String text, Integer numberOfResults){
        return locarnoClassificationServiceProvider.getLocarnoAutocomplete(language, text, numberOfResults);
    }

    @Override
    public ClassificationHeading getLocarnoClasses(String language, String version) throws Exception {
        return locarnoClassificationServiceProvider.getLocarnoClasses(language, version);
    }

    @Override
    public List<LocarnoSubClassHeading> getLocarnoSubclasses(Integer locarnoClass, String language, String version) throws Exception {

        return locarnoClassificationServiceProvider.getLocarnoSubclasses(locarnoClass, language, version);
    }

    /**DS Class Integration changes start*/
    @Override
    public List<ClassDescription> validateClassDescriptions(List<ClassDescription> classDescriptions) {
        return locarnoClassificationServiceProvider.validateClassDescriptions(classDescriptions);
    }
    /**DS Class Integration changes end*/
    /*</RFC DS Class Integration*/

    @Override
    public TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput,
                                Integer selectedClass, Integer selectedSubclass, final Object... arguments) {

        return locarnoClassificationServiceProvider.getTerms(offices, language, page, pageSize, searchInput, selectedClass, selectedSubclass, arguments);

    }

    @Override
    public List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass, String subclass) {
        return locarnoClassificationServiceProvider.getTranslations(languageFrom, languageTo, termTranslate, productIndicationIdentifier, selectedClass, subclass);
    }
}
