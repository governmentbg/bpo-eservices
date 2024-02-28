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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;
import java.util.Optional;

/**
 * #DS Class Integration changes.
 * The Class Term.
 * @author Ramittal
 *
 */
public class Term implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The language code. */
	@JsonProperty("LanguageCode")
	private String languageCode;

	/** The term text. */
	@JsonProperty("TermText")
	private String termText;

	/**
	 * Gets the language code.
	 *
	 * @return the language code
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * Sets the language code.
	 *
	 * @param languageCode
	 *            the new language code
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Gets the term text.
	 *
	 * @return the term text
	 */
	public String getTermText() {
		return termText;
	}

	/**
	 * Sets the term text.
	 *
	 * @param termText
	 *            the new term text
	 */
	public void setTermText(String termText) {
		this.termText = termText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Optional.of(languageCode).map(String::hashCode).orElse(0);
		return prime * result + Optional.of(termText).map(String::hashCode).orElse(0);
	}

	@Override
	public boolean equals(Object obj) {
		final boolean isEquals;
		if (this == obj) {
			isEquals = true;
		} else if (obj == null) {
			isEquals = false;
		} else if (getClass() != obj.getClass()) {
			isEquals = false;
		} else {
			Term other = (Term) obj;
			isEquals = new EqualsBuilder().append(languageCode, other.languageCode).append(termText, other.termText).isEquals();
		}
		return isEquals;
	}
}
