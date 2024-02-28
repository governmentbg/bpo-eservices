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
package eu.ohim.sp.core.domain.design;
/**
 * #DS Class Integration changes.
 * The Enum ValidationCode.
 * @author Ramittal
 *
 */
public enum ValidationCode {

	/** The ok. */
	OK,

	/** The hint. */
	HINT,

	/** The not ok. */
	NOT_OK,

	/** The not found. */
	NOT_FOUND;

	/**
	 * Gets the validation code.
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

	public String value() {
		return name();
	}
}
