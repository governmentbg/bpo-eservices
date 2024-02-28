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
package eu.ohim.sp.external.common;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * #DS Class Integration changes.
 * The Enum VerificationResult.
 * @author Ramittal
 *
 */
public enum VerificationResult {
	//@formatter:off
	/** The ok. */
	OK("OK"),

	/** The hint. */
	HINT("HINT"),

	/** The not ok. */
	NOT_OK("NOT OK"),

	/** The not found. */
	NOT_FOUND("NOT FOUND");
	//formatter:on

	/**
	 * The Value.
	 */
	private String value;

	/**
     * Instantiates a new Verification result.
	 *
	 * @param value the value
	 */
	VerificationResult(String value) {
		this.value = value;
	}

	/**
	 * From name.
	 *
	 * @param name the name
	 * @return the verification result
	 */
	@JsonCreator
	public static VerificationResult fromName(String name) {
		for (VerificationResult vr : VerificationResult.values()) {
			if (vr.value.equals(name)) {
				return vr;
			}
		}
		throw new IllegalArgumentException("VerificationResult not found: " + name);
	}
}
