/*******************************************************************************
 * * $Id:: PhoneKind.java 121785 2013-06-06 18:45:12Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.contact;

public enum PhoneKind {
    FIXED("Fixed"),
    MOBILE_PHONE("Mobile Phone"),
    OTHER("Other"),
	FAX("Fax"),
	UNDEFINED("Undefined");
	
	private final String value;
	
	private PhoneKind(final String value) {
		this.value = value;
	}

	public String value(){
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
