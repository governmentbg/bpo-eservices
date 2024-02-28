/*******************************************************************************
 * * $Id:: RepresentativeNaturalPersonForm.java 50053 2012-11-07 15:00:55Z ionit#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import org.springframework.util.StringUtils;

/**
 * @author velosma
 */
public class ChangeRepresentativeNaturalPersonForm extends RepresentativeNaturalPersonForm implements Cloneable, PreviousRepresentativeForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method that returns the id
	 *
	 * @return the id
	 */
	public String getId() {
		if (!StringUtils.isEmpty(getPreviousPersonId())) {
			return getPreviousPersonId();
		} else {
			return super.getId();
		}
	}

	/**
	 * Method that returns the id of the updated details
	 *
	 * @return the id of the updated details
	 */
	public String getUpdatedId() {
		return super.getId();
	}
}
