package eu.ohim.sp.core.domain.contact;

import eu.ohim.sp.core.domain.common.CharacterSet;

/**
 * The Class TransliteratedAddress.
 */
public class TransliteratedAddress extends Address {

	/** The translitared street. */
	private String translitaredStreet;

	/** The translitared postal name. */
	private String translitaredPostalName;

	/** The translitared city. */
	private String translitaredCity;

	/** The transliteration character set. */
	private CharacterSet transliterationCharacterSet;

	/**
	 * Gets the translitared street.
	 *
	 * @return the translitared street
	 */
	public String getTranslitaredStreet() {
		return translitaredStreet;
	}

	/**
	 * Sets the translitared street.
	 *
	 * @param translitaredStreet the new translitared street
	 */
	public void setTranslitaredStreet(String translitaredStreet) {
		this.translitaredStreet = translitaredStreet;
	}

	/**
	 * Gets the translitared postal name.
	 *
	 * @return the translitared postal name
	 */
	public String getTranslitaredPostalName() {
		return translitaredPostalName;
	}

	/**
	 * Sets the translitared postal name.
	 *
	 * @param translitaredPostalName the new translitared postal name
	 */
	public void setTranslitaredPostalName(String translitaredPostalName) {
		this.translitaredPostalName = translitaredPostalName;
	}

	/**
	 * Gets the translitared city.
	 *
	 * @return the translitared city
	 */
	public String getTranslitaredCity() {
		return translitaredCity;
	}

	/**
	 * Sets the translitared city.
	 *
	 * @param translitaredCity the new translitared city
	 */
	public void setTranslitaredCity(String translitaredCity) {
		this.translitaredCity = translitaredCity;
	}

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


}
