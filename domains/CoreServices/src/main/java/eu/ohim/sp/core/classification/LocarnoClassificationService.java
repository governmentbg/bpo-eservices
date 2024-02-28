/*
 * ClassificationService:: LocarnoClassificationService 16/10/13 20:16 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.classification;

import java.util.List;

import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.ClassDescription;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.ProductIndication;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * The Locarno Classification business interface ({@code LocarnoClassificationService})
 * provides the {@code DS e-Filing} User Interface module with methods to classify design
 * applications (product indications) according to the Locarno classification.
 * <p>
 * The reference implementation {@code LocarnoClassificationServiceBean} interfaces with the following SP components:
 * <dl>
 * <dt><b>Business:</b>
 * </dl>
 * <ul>
 * <li>Locarno Classification Consumer: external searches and retrievals of product indications.</li>
 * </ul>
 * <dl>
 * <dt><b>Support:</b>
 * </dl>
 * <ul>
 * <li>{@code RulesService}: validation of product indications.</li>
 * </ul>
 * 
 * @see <a href="http://www.wipo.int/classifications/locarno/en/" target="_blank">Locarno Classification</a>
 * @see eu.ohim.sp.core.rules.RulesService RulesService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface LocarnoClassificationService {

    /**
     * Retrieves the full list of product indications for a given language.
     * <p>
     * Interfaces with Locarno Classification Consumer.
     * 
     * @param lang Language of product indications (i.e. <i>en</i>).
     * 
     * @return Full list of {@code ProductIndication}.
     */
    List<ProductIndication> getFullClassification(String lang);

    /**
     * Retrieves a list of product indications that match specified criteria.
     * <p>
     * Interfaces with Locarno Classification Consumer.
     *
     * @param clazz High level grouping of product indications (i.e. <i>1</i>).
     * @param subclass Further grouping of product indications (i.e. <i>01</i>).
     * @param lang Language of product indications (i.e. <i>en</i>).
     *
     * @return List of {@code ProductIndication}.
     */
	List<ProductIndication> getProductIndications(String clazz, String subclass, String lang);

    /**
     * Searches product indications that match specified criteria.
     * <p>
     * Interfaces with Locarno Classification Consumer.
     * 
     * @param indicationProduct Indication, or part of indication, being searched (i.e. <i>baker</i>).
     * @param clazz High level grouping of product indications (i.e. <i>1</i>).
     * @param subclass Further grouping of product indications (i.e. <i>01</i>).
     * @param lang Language of product indications (i.e. <i>en</i>).
     * 
     * @return List of {@code ProductIndication}.
     */
    List<ProductIndication> searchProductIndication(String indicationProduct, String clazz, String subclass, String lang);

    /**
     * Validates a provided {@code ProductIndication} according to a set of business rules.
     * <p>
     * Interfaces with {@code RulesService}.
     * 
     * @see eu.ohim.sp.core.rules.RulesService#validate(String, String, List)
     * 
     * @param module User interface module (i.e. <i>DS e-Filing</i>).
     * @param productIndication Product indication ({@code ProductIndication}) to be validated.
     * @param rulesInformation Extra information needed to execute validation.
     * 
     * @return List ({@code ErrorList}) containing validation errors and/or warnings.
     */
    ErrorList validateProductIndication(String module, ProductIndication productIndication,
            RulesInformation rulesInformation);

    //DS Class Integration Changes Start
    String getLocarnoAutocomplete(String language, String text, Integer numberOfResults);

    /**
     * gets locarno subclases for a specified class.
     * @param language the language
     * @param version the version
     * @return the object
     * @throws Exception
     */
    ClassificationHeading getLocarnoClasses(String language, String version) throws Exception;

    /**
     * gets locarno subclases for a specified class.
     *
     * @param locarnoClass the locarno class
     * @param language the language
     * @param version the version
     * @return the list
     * @throws Exception
     */
    List<LocarnoSubClassHeading> getLocarnoSubclasses(Integer locarnoClass, String language, String version) throws Exception;

    /**
     * Validate class descriptions.
     *
     * @param classDescriptions the class descriptions
     * @return the list
     */
    List<ClassDescription> validateClassDescriptions(List<ClassDescription> classDescriptions);


    TermsSearch getTerms(String offices, String language, Integer page, Integer pageSize, String searchInput,
                         Integer selectedClass, Integer selectedSubclass, final Object... arguments);

    /**
     * Gets the translation.
     *
     * @param languageFrom the languageFrom
     * @param languageTo the languageTo
     * @param termTranslate the term to be translated
     * @param productIndicationIdentifier the term identifier
     * @param selectedClass the Locarno class
     * @param subclass the Locarno subclass
     * @return the locarno translation
     */
    List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass, String subclass);

    //DS Class Integration Changes End
}
