/*******************************************************************************
 * * $Id:: FeeCalculationService.java 132982 2013-08-05 19:30:03Z karalc#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.fee;

import java.util.List;
import java.util.Map;

import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeList;

/**
 * Core management service for managing the fees.
 * 
 * @author karalch
 */
public interface FeeCalculationService {
	/**
	 * Calculate the fees of the application. Depending on the calling module,
	 * can get more than one result list.
	 * 
	 * @param module
	 *            the name of the module wich is calling the rules
	 * @param objects
	 *            the list of objects needed to trigger the rules
	 * @return the list with the calculated fees
	 */
	List<Fee> calculateFees(String module, List<Object> objects);

	/**
	 * Get all fees that are configured for each module
	 * @param includeConfigurationFees - include fees that are also percentages or counts
	 * @return a map that for each module has the fees list
	 */
	Map<String, FeeList> getAllModulesFeesConfigured(Boolean includeConfigurationFees);
}
