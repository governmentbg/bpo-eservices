/*
 *  CoreDomain:: Address 15/10/13 16:15 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.contact;

import eu.ohim.sp.core.domain.common.CharacterSet;
import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * The Class Address.
 */
public class Address extends Id implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 163946561792347751L;

    /**
     * The kind.
     */
    private String kind;

    /**
     * The postal name.
     */
    private String postalName;

    /**
     * The street.
     */
    private String street;

    /**
     * The street number.
     */
    private String streetNumber;

    /**
     * The city.
     */
    private String city;

    /**
     * The post code.
     */
    private String postCode;

    /**
     * The state.
     */
    private String state;

    /**
     * The country code.
     */
    private String country;

    /**
     * The character set.
     */
    private CharacterSet characterSet;

    private boolean otherIndicator;

    /**
     * Get the postal name.
     *
     * @return the postal name
     */
    public String getPostalName() {
        return postalName;
    }

    /**
     * Set the postal name.
     *
     * @param postalName the postal name to set
     */
    public void setPostalName(String postalName) {
        this.postalName = postalName;
    }

    /**
     * Gets the kind.
     *
     * @return the kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Sets the kind.
     *
     * @param kind the new kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Get the street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get the postCode.
     *
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Set the postCode.
     *
     * @param postCode the pocstcode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Get the country code.
     *
     * @return the country code
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country code.
     *
     * @param country the country code
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get the house number.
     *
     * @return the house number
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Set the house number.
     *
     * @param streetNumber the house number
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Gets the character set.
     *
     * @return the character set
     */
    public CharacterSet getCharacterSet() {
        return characterSet;
    }

    /**
     * Sets the character set.
     *
     * @param characterSet the new character set
     */
    public void setCharacterSet(CharacterSet characterSet) {
        this.characterSet = characterSet;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean isOtherIndicator()
    {
        return otherIndicator;
    }

    public void setOtherIndicator(boolean otherIndicator)
    {
        this.otherIndicator = otherIndicator;
    }
}
