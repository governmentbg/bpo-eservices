/*
 *  RegisterService:: TradeMarkService 06/09/13 09:40 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.register;

import java.util.List;

import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.licence.Licence;
import eu.ohim.sp.core.domain.opposition.OppositionGround;
import eu.ohim.sp.core.domain.trademark.ForeignRegistration;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * The {@code TradeMarkSearchService} business interface provides User Interface modules
 * ({@code TM e-Filing}, {@code DS e-Filing} and {@code e-Services}) with a methods 
 * to search, import and validate IP applications.
 *
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface TradeMarkSearchService {

	/**
	 * Calls an external service and retrieves the details of
	 * a trade mark.
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param tradeMarkId ID of the trade mark being imported.
	 * 
	 * @return {@code TradeMark} object that contains the details of the trade mark.
	 */
	TradeMark getTradeMark(String office, String tradeMarkId);

	/**
	 * Calls an external service and retrieves the details of
	 * a foreign trade mark.
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param tradeMarkId ID of the foreign trade mark being imported.
	 * 
	 * @return {@code TradeMark} object that contains the details of the trade mark.
	 */
	TradeMark getForeignTradeMark(String office, String tradeMarkId);

	/**
	 * Calls an external service and retrieves the details of
	 * an international trade mark.
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param tradeMarkId ID of the international trade mark being imported.
	 * @param extraImport Flag to indicate whether extra fields should be imported.
	 * 
	 * @return {@code TradeMark} object that contains the details of the trade mark.
	 */
	TradeMark getInternationalTradeMark(String office, String tradeMarkId, Boolean extraImport);

	/**
	 * Calls an external service and retrieves a list of trademarks in which
	 * a user may be interested depending on the text he provides
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param text User input
	 * @param numberOfResults Maximum number of expected results
	 * 
	 * @return Text containing the list of trade marks in {@code JSON} format.
	 */
	String getTradeMarkAutocomplete(String office, String text, Integer numberOfResults);

	/**
	 * Calls an external service and retrieves a list of foreign trade marks in which
	 * a user may be interested depending on the text he provides.
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param text User input
	 * @param numberOfResults Maximum number of expected results
	 * 
	 * @return Text containing the list of trade marks in {@code JSON} format.
	 */
	String getForeignTradeMarkAutocomplete(String office, String text, Integer numberOfResults);

	/**
	 * Calls an external service and gets a list of similar trademarks with the inserted one
	 * following some rules of the external service.
	 *
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param tradeMarkApplication Trade mark for which clearance report is requested.
	 * 
	 * @return List of similar trade marks ({@code List<TradeMark>}).
	 */
	List<TradeMark> getPreclearanceReport(String office, TradeMark tradeMarkApplication);

	/**
	 * Validates the trademark according to some rules defined by the office.
	 *
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param tradeMark the object with all the information about the trademark
	 * @param rulesInformation extra information object needed for validation
	 * 
	 * @return List ({@code ErrorList}) containing validation errors and/or warnings.
	 */
	ErrorList validateTradeMark(String module, TradeMark tradeMark, RulesInformation rulesInformation);

	/**
	 * Validates the opposition according to some rules defined by the office.
	 *
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param opposition the object with all the information about the opposition
	 * @param rulesInformation extra information object needed for validation
	 * 
	 * @return List ({@code ErrorList}) containing validation errors and/or warnings.
	 */
	ErrorList validateOpposition(String module, OppositionGround opposition, RulesInformation rulesInformation);

	/**
	 * Validates the licence according to some rules defined by the office.
	 *
	 * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param licence the object with all the information about the licence
	 * @param rulesInformation extra information object needed for validation
	 *
	 * @return List ({@code ErrorList}) containing validation errors and/or warnings.
	 */
	ErrorList validateLicence(String module, Licence licence, RulesInformation rulesInformation);

	ErrorList validateAppeal(String module, Appeal appeal, RulesInformation rulesInformation);

	ErrorList validateApplicationCA(String module, ContactDetails contactDetails, RulesInformation rulesInformation);

	ErrorList validateMarkView(String module, ImageSpecification markView, RulesInformation rulesInformation);

	ErrorList validateForeignRegistration(String module, ForeignRegistration foreignRegistration, RulesInformation rulesInformation);

}
