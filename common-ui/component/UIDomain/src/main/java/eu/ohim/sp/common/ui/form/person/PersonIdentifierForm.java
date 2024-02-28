/*******************************************************************************
 * * $Id:: PersonForm.java 54183 2013-01-11 12:03:33Z ionitdi                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import org.apache.commons.lang.StringUtils;


/**
 * @author garjuan
 */

public class PersonIdentifierForm implements java.io.Serializable, Cloneable
{

    private static final long serialVersionUID = 1L;

	private static final int value31 = 31;


	private String afimi;
	private String doy;  	


	/**
	 * @return the afimi
	 */
	public String getAfimi() {
		return afimi;
	}


	/**
	 * @param afimi the afimi to set
	 */
	public void setAfimi(String afimi) {
		this.afimi = afimi;
	}


	/**
	 * @return the doy
	 */
	public String getDoy() {
		return doy;
	}


	/**
	 * @param doy the doy to set
	 */
	public void setDoy(String doy) {
		this.doy = doy;
	}


    
	  /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   PersonIdentifierForm personIdentifierForm = new PersonIdentifierForm();
	   personIdentifierForm.setAfimi(afimi);
	   personIdentifierForm.setDoy(doy);
       return personIdentifierForm;
   }

   public boolean isEmpty()
   {
       return (StringUtils.isBlank(afimi) && StringUtils.isBlank(doy));
   }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PersonIdentifierForm that = (PersonIdentifierForm) o;

		if (afimi != null ? !afimi.equals(that.afimi) : that.afimi != null) return false;
		return doy != null ? doy.equals(that.doy) : that.doy == null;

	}

	@Override
    public int hashCode() {
		int result = afimi != null ? afimi.hashCode() : 0;
		result = value31 * result + (doy != null ? doy.hashCode() : 0);
		return result;
   	}
	 
}
