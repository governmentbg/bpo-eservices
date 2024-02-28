/*******************************************************************************
 * * $Id:: TermForm.java 50674 2012-11-13 18:48:15Z karalch                      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.classification;

import eu.ohim.sp.common.ui.form.validation.ErrorType;

import java.util.Arrays;

public class TermForm implements java.io.Serializable, Cloneable, Comparable<TermForm>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8674114308107994173L;
	
	private String idClass;
	private String description;
	private boolean scopeAvailabilty;
	private boolean added;
	private boolean importedNiceClassHeading;
	private ErrorType error;
	private ErrorType niceError;
	private String[] origin;
	
	public TermForm() {
		added = false;
	}

	public TermForm(String id) {
		added = false;
	}
	
	public TermForm(String idClass, String description, boolean added) {
		this.idClass = idClass;
		this.description = description;
		this.added = added;
	}
	
	/**
	 * Method that returns the idClass
	 * @return the idClass
	 */
	public String getIdClass() {
		return idClass;
	}
	/**
	 * Method that sets the idClass
	 * @param idClass the idClass to set
	 */
	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}
	/**
	 * Method that returns the description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The term is concept if it is false
	 * @return false if the term is a concept
	 */
	public boolean isScopeAvailabilty() {
		return scopeAvailabilty;
	}

	/**
	 * Sets the scope of the term
	 * @param scopeAvailabilty
	 */
	public void setScopeAvailabilty(boolean scopeAvailabilty) {
		this.scopeAvailabilty = scopeAvailabilty;
	}

	/**
	 * Method that sets the description
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Method that returns the added
	 * @return the added
	 */
	public boolean isAdded() {
		return added;
	}
	/**
	 * Method that sets the added
	 * @param added the added to set
	 */
	public void setAdded(boolean added) {
		this.added = added;
	}

	/**
	 * It returns true if only the term is the same as on Nice Class Heading
	 * @return true if only the term is the same as on Nice Class Heading
	 */
	public boolean isImportedNiceClassHeading() {
		return importedNiceClassHeading;
	}

	/**
	 * This will be set as true if only the term is the same as on Nice Class Heading
	 * @param importedNiceClassHeading
	 */
	public void setImportedNiceClassHeading(boolean importedNiceClassHeading) {
		this.importedNiceClassHeading = importedNiceClassHeading;
	}

	/**
	 * @return the origin
	 */
	public String[] getOrigin() {
        return (origin!=null?origin.clone():null);
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String[] origin) {
		// Constructors and methods receiving arrays should clone objects
		// and store the copy. This prevents that future changes from
		// the user affect the internal functionality.
        this.origin = (origin!=null?origin.clone():null);
	}

	/**
	 * @return the error
	 */
	public ErrorType getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(ErrorType error) {
		this.error = error;
	}

	public ErrorType getNiceError() {
		return niceError;
	}

	public void setNiceError(ErrorType niceError) {
		this.niceError = niceError;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.trim().toLowerCase().hashCode());
		result = prime * result + ((idClass == null) ? 0 : idClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TermForm other = (TermForm) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.trim().equalsIgnoreCase(other.description.trim())) {
			return false;
		}
		if (idClass == null) {
			if (other.idClass != null) {
				return false;
			}
		} else if (!idClass.trim().equalsIgnoreCase(other.idClass.trim())) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TermForm o) {
		if ((o!=null) && (this!=null)) {
			return this.hashCode() - o.hashCode();
		}
		return 0;
	}
	
	/**
	 * Creates a clone of the original object
	 */
	@Override
	public TermForm clone() throws CloneNotSupportedException{
		TermForm termForm = new TermForm();
		termForm.setAdded(added);
		termForm.setDescription(description);
		termForm.setError(error);
		termForm.setNiceError(niceError);
		termForm.setIdClass(idClass);
		termForm.setImportedNiceClassHeading(importedNiceClassHeading);
		termForm.setScopeAvailabilty(scopeAvailabilty);
		return termForm;
	}

}
