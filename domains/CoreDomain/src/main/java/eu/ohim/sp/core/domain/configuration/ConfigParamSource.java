/*******************************************************************************
 * * $Id:: ConfigParamSource.java 50686 2012-11-14 09:10:31Z jaraful             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.configuration;

// TODO: Auto-generated Javadoc
/**
 * The Enum ConfigParamSource.
 */
public enum ConfigParamSource {

	/** The path. */
	PATH(0),

	/** The file contents. */
	FILE_CONTENTS(1);

	/**
	 * Instantiates a new config param source.
	 * 
	 * @param value
	 *            the value
	 */
	private ConfigParamSource(final int value) {
		this.value = value;
	}

	/** The value. */
	private final int value;

	/**
	 * Gets the config param source.
	 * 
	 * @return the config param source
	 */
	public int getConfigParamSource() {
		return value;
	}

}
