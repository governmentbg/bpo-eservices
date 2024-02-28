/*******************************************************************************
 * * $Id:: Designer.java 122910 2013-06-12 19:30:31Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.Objects;

public class PersonIdentifier extends Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;
    private String identifierKindCode;

    public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIdentifierKindCode() {
		return identifierKindCode;
	}
	public void setIdentifierKindCode(String identifierKindCode) {
		this.identifierKindCode = identifierKindCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonIdentifier that = (PersonIdentifier) o;
		return Objects.equals(value, that.value) && Objects.equals(identifierKindCode, that.identifierKindCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, identifierKindCode);
	}
}
