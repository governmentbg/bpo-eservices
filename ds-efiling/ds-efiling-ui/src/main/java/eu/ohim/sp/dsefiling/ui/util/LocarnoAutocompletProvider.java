//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.dsefiling.ui.util;

/**
 * The Enum LocarnoSearchTermAutocompletProvider.
 */
public enum LocarnoAutocompletProvider {

	//@formatter:off
	/**
	 * The TMDS view.
	 */
	TMDS_VIEW("TMDSView"),

	/**
	 * The Design class.
	 */
	DESIGN_CLASS("DesignClass");
	//@formatter:on

	/** The name. */
	private String name;

	/**
	 * Instantiates a new locarno search term autocomplet provider.
	 *
	 * @param name the name
	 */
	LocarnoAutocompletProvider(String name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
