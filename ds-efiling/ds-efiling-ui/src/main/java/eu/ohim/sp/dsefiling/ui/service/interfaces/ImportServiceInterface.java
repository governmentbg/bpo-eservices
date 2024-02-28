//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2018 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.dsefiling.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.design.IndicationProductForm;
import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermsSearch;

import java.util.List;


/**
 * File created for RFC DS Class Integration
 * @author Shiv
 *
 */
public interface ImportServiceInterface {

	/**
	 * Validate indication product form.
	 *
	 * @param language the language
	 * @param indicationProductForm the indication product form
	 * @return the indication product form
	 */
	IndicationProductForm validateIndicationProductForm(String language, IndicationProductForm indicationProductForm);

	IndicationProductForm validateIndicationProductFormList(String language, List<IndicationProductForm> indicationProductFormList);

	LocarnoAbstractForm validateLocarnoForm(String language, LocarnoAbstractForm locarnoAbstractForm);

	/**
	 * Gets the design classification autocomplete.
	 *
	 * @param language the language
	 * @param text the text
	 * @param numberOfResults the number of results
	 * @return the design classification autocomplete
	 */
	String getLocarnoAutocomplete(String language, String text, Integer numberOfResults);

	/**
	 * Gets the locarno classes.
	 *
	 * @param language the language
	 * @param version the version
	 * @return the locarno classes
	 * @throws Exception 
     */
	List<KeyTextUIClass> getLocarnoClasses(String language, String version) throws Exception;
	
	/**
	 * Gets the locarno subclasses.
	 *
	 * @param locarnoClass the locarno class
	 * @param language the language
	 * @param version the version
	 * @return the locarno subclasses
	 * @throws Exception 
     */
	List<KeyTextUIClass> getLocarnoSubclasses(Integer locarnoClass, String language, String version) throws Exception;

	/**
	 * Gets the terms.
	 *
	 * @param offices the offices
	 * @param language the language
	 * @param page the page
	 * @param pageSize the page size
	 * @param searchInput the search input
	 * @param selectedClass the selected class
	 * @param selectedSubclass the selected subclass
	 * @param arguments the arguments
	 * @return the terms
	 */
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
	List<KeyTextUIClass> getTranslations(String languageFrom, String languageTo, String termTranslate, String productIndicationIdentifier, String selectedClass,
                                         String subclass);
}
