/*******************************************************************************
 * * $Id:: SearchAbstractController.java 49264 2012-10-29 13:23:34Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;


/**
 * 
 * Generic abstract controller that is responsible for calling the specific service to retrieve a json object
 * Must be extended by a class which contains the actual mappings
 * 
 * @author soriama
 * 
 */
public abstract class SearchAbstractController {

	/**
	 * The actual controller with the mappings that extends this class must call this method
	 * 
	 * @param id string sent to the service to make the search
	 * @return json string
	 */
	public String autocomplete(String id) {
		return autocompleteService(id);		
	}
	
	/**
	 * The abstract autocomplete service
	 * 
	 * @param id string sent to the service to make the search
	 * @return json string
	 */
	public abstract String autocompleteService(String id);	
}
