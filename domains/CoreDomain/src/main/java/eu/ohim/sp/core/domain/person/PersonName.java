/*
 *  CoreDomain:: PersonName 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

import eu.ohim.sp.core.domain.common.CharacterSet;
import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * The Class PersonName.
 */
public class PersonName extends Id implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The character set.
     */
    private CharacterSet characterSet;

    /**
     * The title.
     */
    private String title;

    /**
     * The first name.
     */
    private String firstName;

    /**
     * The middle name.
     */
    private String middleName;

    /**
     * The last name.
     */
    private String lastName;

    /**
     * The second last name.
     */
    private String secondLastName;

    /**
     * The organization name.
     */
    private String organizationName;

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

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the second last name.
     *
     * @return the second last name
     */
    public String getSecondLastName() {
        return secondLastName;
    }

    /**
     * Sets the second last name.
     *
     * @param secondLastName the new second last name
     */
    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    /**
     * Gets the organization name.
     *
     * @return the organization name
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organization name.
     *
     * @param organizationName the new organization name
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
    @Override
    public String toString() {
        return "PersonName [characterSet=" + characterSet + ", firstName="
                + firstName + ", middleName=" + middleName + ", lastName="
                + lastName + ", secondLastName=" + secondLastName
                + ", organizationName=" + organizationName + "]";
    }

    public String getFullName(){
        StringBuilder sb = new StringBuilder();
        if(firstName != null){
            sb.append(firstName);
        }
        if(middleName != null){
            sb.append(" ").append(middleName);
        }
        if(lastName != null){
            sb.append(" ").append(lastName);
        }
        if(organizationName != null){
            sb.append(" ").append(organizationName);
        }
        return sb.toString().trim();
    }
}
