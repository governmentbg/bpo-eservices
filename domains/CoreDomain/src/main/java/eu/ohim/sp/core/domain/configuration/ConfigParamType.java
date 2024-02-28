/*******************************************************************************
 * * $Id:: ConfigParamType.java 50686 2012-11-14 09:10:31Z jaraful               $
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
 * The Enum ConfigParamType.
 */
public enum ConfigParamType {
	
	/** The simple. */
	SIMPLE(0),
	
	/** The list. */
	LIST(1),
    
    /** The xml. */
    XML(2),
	
	/** The object. */
	OBJECT(3)
	;
	
	/**
	 * Instantiates a new config param type.
	 *
	 * @param value the value
	 */
	private ConfigParamType(final int value) {
		this.value = value;
	}

	/** The value. */
	private final int value;
	
	/**
	 * Gets the config param type.
	 *
	 * @return the config param type
	 */
	public int getConfigParamType() {
		return value;
	}

}
