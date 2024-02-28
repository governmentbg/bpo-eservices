/*******************************************************************************
 * * $Id:: ESTrademarkServiceInterface.java 50771 2012-11-14 15:10:27Z medinju        $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.eservices.ui.service.interfaces;

import java.util.List;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.application.AppealForm;
import eu.ohim.sp.common.ui.form.licence.LicenceForm;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;


public interface ESTrademarkServiceInterface {

	/**
	 * Service that is used to import application in the section 
	 * of previous CTM
	 * @param office the code of the office
	 * @param tradeMarkId the id of the trade mark
	 * @param flowModeId indicates the flow mode (wizard, oneform...)
	 * @return the imported trade mark transformed into flowBean and filtered
	 */
	TMDetailsForm getTradeMark(String tradeMarkId);

	TMDetailsForm getTradeMark(String tradeMarkId, String officeImport);

	/**
	 * Autocomplete service that is looking for trademark containing text
	 * @param office the code of the office
	 * @param text the inserted text used to autocomplete
	 * @param numberOfResults number of results expected by the service
	 * @return a list of json objects string
	 */
	String getTradeMarkAutocomplete(String office, String text,
			Integer numberOfResults, boolean previousCTM, String flowModeId);
	
	/**
	 * Validate a trademark
	 * @param module Module we're dealing with
	 * @param tradeMark The tradeMark itself
	 * @param rulesInformation The rules
	 * @param errors 
	 * @param flowModeId
	 * @return
	 */
	ErrorList validateTradeMark(String module, TMDetailsForm tradeMark,
			RulesInformation rulesInformation, Errors errors, String flowModeId);

	List<LegalActVersion> filter(String applicationType, AbstractImportableForm core, GroundCategoryKindLegalAct category, List<String> codesFiltered);
	
	ErrorList validateOpposition(String module,	OppositionBasisForm oppositionBasisForm, RulesInformation rulesInformation, Errors errors, String flowModeId);

	ErrorList validateLicence(String module, LicenceForm licenceForm,
							  RulesInformation rulesInformation, Errors errors, String flowModeId);

	ErrorList validateAppeal(String module, AppealForm appealForm,
							 RulesInformation rulesInformation, Errors errors, String flowModeId);
}
