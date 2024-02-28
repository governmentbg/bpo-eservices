package eu.ohim.sp.core.domain.person;

import java.io.Serializable;

/**
 * The Class PersonCriteria.
 */
public class PersonCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String country;

	private String name;

	private String referenceNumber;

	private String type;

	/**
	 * Gets country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets country.
	 *
	 * @param country the country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets reference number.
	 *
	 * @return the reference number
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * Sets reference number.
	 *
	 * @param referenceNumber the reference number
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}
}
