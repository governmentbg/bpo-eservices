/*******************************************************************************
 * * $Id:: AddressForm.java 56993 2013-02-15 14:11:48Z soriama                   $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.contact;

import org.apache.commons.lang.StringUtils;

/**
 * Stores all the necessary information for the address of a person
 *
 * @author ckara & ionitdi
 */
public class AddressForm implements java.io.Serializable, Cloneable
{

    private static final long serialVersionUID = 1L;

	private static final int value31 = 31;
	
    private String street;
    private String houseNumber;
    private String city;
    private String stateprovince;
    private String postalCode;
    private String country;

    public String getFullAddress(){
        //return street+", "+houseNumber+". "+postalCode+". "+city+" - "+country;
        String fullAddress = StringUtils.isNotEmpty(street)?street:"";
        fullAddress =  fullAddress.concat(StringUtils.isNotBlank(houseNumber)?((StringUtils.isNotBlank(fullAddress)?", ":"")+houseNumber):"");
        fullAddress =  fullAddress.concat(StringUtils.isNotBlank(postalCode)?((StringUtils.isNotBlank(fullAddress)?". ":"")+postalCode):"");
        fullAddress =  fullAddress.concat(StringUtils.isNotBlank(city)?((StringUtils.isNotBlank(fullAddress)?". ":"")+city):"");
        fullAddress =  fullAddress.concat(StringUtils.isNotBlank(country)?((StringUtils.isNotBlank(fullAddress)?" - ":"")+country):"");
        return fullAddress;
    }

    /**
     * Method that returns the street
     *
     * @return the street
     */
    public String getStreet()
    {
        return street;
    }

    /**
     * Method that sets the street
     *
     * @param street the street to set
     */
    public void setStreet(String street)
    {
        this.street = street;
    }

    /**
     * Method that returns the city
     *
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Method that sets the city
     *
     * @param city the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Method that returns the stateprovince
     *
     * @return the stateprovince
     */
    public String getStateprovince()
    {
        return stateprovince;
    }

    /**
     * Method that sets the stateprovince
     *
     * @param stateprovince the stateprovince to set
     */
    public void setStateprovince(String stateprovince)
    {
        this.stateprovince = stateprovince;
    }

    /**
     * Method that returns the postal
     *
     * @return the postal
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     * Method that sets the postal
     *
     * @param postalCode the postal to set
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     * Method that returns the country
     *
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Method that sets the country
     *
     * @param country the country to set
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * @return the houseNumber
     */
    public String getHouseNumber()
    {
        return houseNumber;
    }

    /**
     * @param houseNumber the houseNumber to set
     */
    public void setHouseNumber(String houseNumber)
    {
        this.houseNumber = houseNumber;
    }

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#clone()
      */
    @Override
    public Object clone() 
    {
        AddressForm addressForm = new AddressForm();
        addressForm.setCity(city);
        addressForm.setHouseNumber(houseNumber);
        addressForm.setCountry(country);
        addressForm.setStateprovince(stateprovince);
        addressForm.setPostalCode(postalCode);
        addressForm.setStreet(street);
        return addressForm;
    }

    public boolean isEmpty()
    {
        return (StringUtils.isBlank(city) && StringUtils.isEmpty(houseNumber) && StringUtils.isEmpty(country) && StringUtils.isEmpty(
                stateprovince) && StringUtils.isEmpty(postalCode) && StringUtils.isEmpty(street));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AddressForm))
        {
            return false;
        }

        AddressForm that = (AddressForm) o;

        if (city != null ? !city.equals(that.city) : that.city != null)
        {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null)
        {
            return false;
        }
        if (houseNumber != null ? !houseNumber.equals(that.houseNumber) : that.houseNumber != null)
        {
            return false;
        }
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null)
        {
            return false;
        }
        if (stateprovince != null ? !stateprovince.equals(that.stateprovince) : that.stateprovince != null)
        {
            return false;
        }
        if (street != null ? !street.equals(that.street) : that.street != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = street != null ? street.hashCode() : 0;
        result = value31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = value31 * result + (city != null ? city.hashCode() : 0);
        result = value31 * result + (stateprovince != null ? stateprovince.hashCode() : 0);
        result = value31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = value31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
