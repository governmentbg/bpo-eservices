/*******************************************************************************
 * * $Id:: ErrorList.java 128442 2013-07-11 13:35:23Z jaraful                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.validation;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Core holder class that holds information about the 
 * errors found on the business rules
 * @author jaraful
 *
 */
public class ErrorList extends Id implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7624252908455918177L;
	
	
	private List<ErrorCore> errorList;

	/**
	 * Constructor that creates and initiates a list of ErrorCore object
	 */
	public ErrorList(){
		errorList = new ArrayList<ErrorCore>();
	}

	/**
	 * Gets the list of ErrorCore objects
	 * @return the list of ErrorCore objects
	 */
	public List<ErrorCore> getErrorList() {
		return errorList;
	}

	/**
	 * Set the error list
	 * @param errorList the error list to set
	 */
	public void setErrorList(List<ErrorCore> errorList) {
		this.errorList = errorList;
	}
	
	/**
	 * Add an error core
	 * @param errorCore the error core to add
	 */
	public void addError(ErrorCore errorCore){
		errorList.add(errorCore);
	}
	
	/**
	 * Add an error list
	 * @param errors the error list to add
	 */
	public void addAllErrors(ErrorList errors){
		if (errorList == null) {
			errorList = new ArrayList<ErrorCore>();
		}
		if(errors != null) {
			errorList.addAll(errors.getErrorList());
		}
	}
}
