/*******************************************************************************
 * * $Id:: Validatable.java 49264 2012-10-29 13:23:34Z karalch                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.validator;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Interface which specifies that an
 * implementing class can be validated.
 */
public interface Validatable {

	/**
	 * Returns the name of the available section.
	 */
	AvailableSection getAvailableSectionName();

}
