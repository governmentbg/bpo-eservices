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

import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsListForm;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

import org.springframework.validation.Errors;

public interface ESDesignServiceInterface {

	/**
	 * Service that is used to import a design in the section
	 * @param designId the id of the design
	 * @return the imported design
	 */
	ESDesignDetailsListForm getDesignApplication(String designId);

	ESDesignDetailsListForm getDesignApplication(String office, String designId, String applicationId, boolean extraImport);

	ESDesignDetailsListForm getDesignApplicationByApplicationId(String applicationId);
	
	/**
	 * Autocomplete service that is looking for design containing text
	 * @param office the code of the office
	 * @param text the inserted text used to autocomplete
	 * @param numberOfResults number of results expected by the service
	 * @param flowModeId - the flow mode id
	 * @return a list of json objects string
	 */
	String getDesignAutocomplete(String office, String text, Integer numberOfResults, String flowModeId);
	
	/**
	 * Validate a design
	 * @param module Module we're dealing with
	 * @param design The Design itself
	 * @param rulesInformation The rules
	 * @param errors 
	 * @param flowModeId
	 * @return
	 */
	ErrorList validateDesign(String module, ESDesignDetailsForm design, RulesInformation rulesInformation, Errors errors, String flowModeId);

    /**
     *     
    * @param module
    * @param applicationCAForm
    * @param rules
    * @param errors
    * @param flowModeId
    * @return
    */    
    ErrorList validateApplicationCA(String module, ApplicationCAForm applicationCAForm, RulesInformation rules, Errors errors, String flowModeId);

	/**
	 *
	 * @param module
	 * @param storedFile
	 * @param rules
	 * @param errors
	 * @param flowModeId
	 * @return
	 */
	ErrorList validateStoredFile(String module, StoredFile storedFile, RulesInformation rules, Errors errors, String flowModeId);

}
