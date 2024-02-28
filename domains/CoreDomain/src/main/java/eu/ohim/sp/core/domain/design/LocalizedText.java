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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.nio.charset.Charset;

/**
 * #DS Class Integration changes.
 * Localized Text
 * @author Ramittal
 *
 */
public class LocalizedText {

	/**
	 * The Value.
	 */
	private String value;
	/**
	 * The Language code.
	 */
	private EUFirstLanguageCode languageCode;

	/**
	 * Instantiates a new Localized text.
	 */
	public LocalizedText() {
		// No action
	}

	/**
	 * Instantiates a new Localized text.
	 *
	 * @param value the value
	 * @param languageCode the language code
	 */
	public LocalizedText(String value, EUFirstLanguageCode languageCode) {
		this.value = value;
		this.languageCode = languageCode;
	}

	/**
	 * Gets the value of the value property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets an array of bytes from the value of the value property and given a character set.
	 *
	 * @return possible object is {@link String }
	 */
	public byte[] getValue(Charset charset) {
		return this.getValue().getBytes(charset);
	}
	
	/**
	 * Sets the value of the value property.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value of the languageCode property.
	 */
	public EUFirstLanguageCode getLanguageCode() {
		return languageCode;
	}

	/**
	 * Sets the value of the languageCode property.
	 */
	public void setLanguageCode(EUFirstLanguageCode value) {
		this.languageCode = value;
	}

	/**
	 * Equals boolean.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		LocalizedText rhs = (LocalizedText) obj;
		return new EqualsBuilder().append(this.value, rhs.value).append(this.languageCode, rhs.languageCode).isEquals();
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(value).append(languageCode).toHashCode();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("value", value).append("languageCode", languageCode).toString();
	}
}