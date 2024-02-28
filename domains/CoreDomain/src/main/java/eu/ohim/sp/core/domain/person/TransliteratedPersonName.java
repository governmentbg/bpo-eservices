/*******************************************************************************
 * * $Id:: TransliteratedPersonName.java 122910 2013-06-12 19:30:31Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

import eu.ohim.sp.core.domain.common.CharacterSet;

/**
 * The Class TransliteratedPersonName.
 */
public class TransliteratedPersonName extends PersonName {

	/**
	 * The transliteration character set.
	 */
	private CharacterSet transliterationCharacterSet;

	/**
	 * The transliterated first name.
	 */
	private String transliteratedFirstName;

	/**
	 * The transliterated middle name.
	 */
	private String transliteratedMiddleName;

	/**
	 * The transliterated last name.
	 */
	private String transliteratedLastName;

	/**
	 * The transliterated second last name.
	 */
	private String transliteratedSecondLastName;

	/**
	 * The transliterated organization name.
	 */
	private String transliteratedOrganizationName;

	/**
	 * Gets the transliteration character set.
	 *
	 * @return the transliteration character set
	 */
	public CharacterSet getTransliterationCharacterSet() {
		return transliterationCharacterSet;
	}

	/**
	 * Sets the transliteration character set.
	 *
	 * @param transliterationCharacterSet the new transliteration character set
	 */
	public void setTransliterationCharacterSet(
			CharacterSet transliterationCharacterSet) {
		this.transliterationCharacterSet = transliterationCharacterSet;
	}

	/**
	 * Gets the transliterated first name.
	 *
	 * @return the transliterated first name
	 */
	public String getTransliteratedFirstName() {
		return transliteratedFirstName;
	}

	/**
	 * Sets the transliterated first name.
	 *
	 * @param transliteratedFirstName the new transliterated first name
	 */
	public void setTransliteratedFirstName(String transliteratedFirstName) {
		this.transliteratedFirstName = transliteratedFirstName;
	}

	/**
	 * Gets the transliterated middle name.
	 *
	 * @return the transliterated middle name
	 */
	public String getTransliteratedMiddleName() {
		return transliteratedMiddleName;
	}

	/**
	 * Sets the transliterated middle name.
	 *
	 * @param transliteratedMiddleName the new transliterated middle name
	 */
	public void setTransliteratedMiddleName(String transliteratedMiddleName) {
		this.transliteratedMiddleName = transliteratedMiddleName;
	}

	/**
	 * Gets the transliterated last name.
	 *
	 * @return the transliterated last name
	 */
	public String getTransliteratedLastName() {
		return transliteratedLastName;
	}

	/**
	 * Sets the transliterated last name.
	 *
	 * @param transliteratedLastName the new transliterated last name
	 */
	public void setTransliteratedLastName(String transliteratedLastName) {
		this.transliteratedLastName = transliteratedLastName;
	}

	/**
	 * Gets the transliterated second last name.
	 *
	 * @return the transliterated second last name
	 */
	public String getTransliteratedSecondLastName() {
		return transliteratedSecondLastName;
	}

	/**
	 * Sets the transliterated second last name.
	 *
	 * @param transliteratedSecondLastName the new transliterated second last name
	 */
	public void setTransliteratedSecondLastName(String transliteratedSecondLastName) {
		this.transliteratedSecondLastName = transliteratedSecondLastName;
	}

	/**
	 * Gets the transliterated organization name.
	 *
	 * @return the transliterated organization name
	 */
	public String getTransliteratedOrganizationName() {
		return transliteratedOrganizationName;
	}

	/**
	 * Sets the transliterated organization name.
	 *
	 * @param transliteratedOrganizationName the new transliterated organization name
	 */
	public void setTransliteratedOrganizationName(
			String transliteratedOrganizationName) {
		this.transliteratedOrganizationName = transliteratedOrganizationName;
	}

}
