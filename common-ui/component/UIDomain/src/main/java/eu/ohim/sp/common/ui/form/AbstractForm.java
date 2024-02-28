/*******************************************************************************
 * * $Id:: AbstractForm.java 49264 2012-10-29 13:23:34Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form;

import eu.ohim.sp.common.ui.interfaces.Identifiable;
import eu.ohim.sp.common.ui.validator.Validatable;

import java.io.Serializable;

/**
 * @author ionitdi
 */
public abstract class AbstractForm implements Identifiable, Serializable, Validatable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7600171567967220954L;

	protected String id;

	/**
	 * Method that returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method that sets the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AbstractForm other = (AbstractForm) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public abstract AbstractForm clone() throws CloneNotSupportedException;
}
