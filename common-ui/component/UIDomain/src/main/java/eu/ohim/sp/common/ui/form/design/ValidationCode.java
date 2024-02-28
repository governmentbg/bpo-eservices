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
package eu.ohim.sp.common.ui.form.design;

/**
 * The Enum ValidationCode.
 */
public enum ValidationCode {

	/** The validated. */
	valid,

	/** The not validated. */
	invalid,

	/** The EDITABLE. */
	editable,

	/** The not found. */
	notfound;

	/**
	 * From value.
	 *
	 * @param code the code
	 * @return the validation code
	 */
	public static ValidationCode fromValue(String code) {
		for (ValidationCode validationCode : ValidationCode.values()) {
			if (validationCode.name().equals(code)) {
				return validationCode;
			}
		}
		throw new IllegalArgumentException(new StringBuilder("ValidationCode not found: ").append(code).toString());
	}
}
