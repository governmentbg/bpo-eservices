/*******************************************************************************
 * * $Id:: PhoneForm.java 49264 2012-10-29 13:23:34Z karalch                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.contact;

/**
 * Stores necessary phone information
 * 
 * @author ionitdi
 * 
 */
public class PhoneForm implements java.io.Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private String type;
	private String number;

	/**
	 * Method that returns the type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Method that sets the type
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Method that returns the number
	 * 
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Method that sets the number
	 * 
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PhoneForm phoneForm = new PhoneForm();
		phoneForm.setNumber(number);
		phoneForm.setType(type);
		return phoneForm;
	}
}
