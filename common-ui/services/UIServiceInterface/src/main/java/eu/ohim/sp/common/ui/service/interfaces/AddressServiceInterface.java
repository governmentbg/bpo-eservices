/*******************************************************************************
 * * $Id:: FeeServiceInterface.java 50925 2012-11-15 17:10:35Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.service.interfaces;

import java.util.List;

import eu.ohim.sp.common.ui.form.contact.AddressForm;

/**
 * The Interface for the AddressService.
 */
public interface AddressServiceInterface {

	/**
	 * Gets the autocomplete Address Information for persons with the integration contract agreed.
	 *
	 * @param flowBean 
	 * @return flowBean object
	 */
	public List<AddressForm> addressAutoComplete(String module, String text);

}
