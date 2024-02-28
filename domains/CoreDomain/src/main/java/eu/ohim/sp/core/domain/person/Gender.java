/*******************************************************************************
 * * $Id:: Gender.java 122910 2013-06-12 19:30:31Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

/**
 * The Enum Gender.
 */
public enum Gender {
	
	/** The male. */
	MALE("Male"),
	
	/** The female. */
	FEMALE("Female");
	
	/**
	 * Instantiates a new gender.
	 *
	 * @param value the value
	 */
	private Gender(final String value) {
		this.value = value;
	}

	/** The value. */
	private final String value;

	/**
	 * Value.
	 *
	 * @return the string
	 */
	public String value() {
		return value;
	}

}
