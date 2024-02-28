package eu.ohim.sp.core.domain.claim;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import eu.ohim.sp.core.domain.id.Id;

/**
 * The Class Exhibition.
 */
public class Exhibition extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968728639534456749L;

	/** The city. */
	private String city;
	
	/** The country. */
	private String country;
	
	/** The name. */
	private String name;
	
	/** The opening date. */
	private Date openingDate;
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the opening date.
	 *
	 * @return the opening date
	 */
	public Date getOpeningDate() {
		return DateUtil.cloneDate(openingDate);
	}
	
	/**
	 * Sets the opening date.
	 *
	 * @param openingDate the new opening date
	 */
	public void setOpeningDate(Date openingDate) {
		this.openingDate = DateUtil.cloneDate(openingDate);
	}

}
