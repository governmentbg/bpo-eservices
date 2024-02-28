package eu.ohim.sp.core.domain.payment;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

/**
 * The Class FeeType.
 */
public class FeeType extends Id implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8835608483300870020L;

	/** The code. */
    private String code;

	/** The nameKey */
    private String nameKey;

	/** The description. */
    private String description;
    
    /** The currency code. */
    private String currencyCode;
    
    /** The default value. */
    private Double defaultValue;

    /** if the fee is some kind of config - percentage, count or else */
    private Boolean configFee;

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the new currency code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public Double getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets name key.
	 *
	 * @return the name key
	 */
	public String getNameKey() {
		return nameKey;
	}

	/**
	 * Sets name key.
	 *
	 * @param nameKey the name key
	 */
	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public Boolean getConfigFee() {
		return configFee;
	}

	public void setConfigFee(Boolean configFee) {
		this.configFee = configFee;
	}
}
