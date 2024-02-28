package eu.ohim.sp.core.domain.dossier.letters;

import java.io.Serializable;

import eu.ohim.sp.core.domain.dossier.letters.enums.LetterTypeSendMethod;
import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The Recipient associated to a Letter. It represents the personal information
 * of the person whom the letter is sent.
 * 
 */
public class Recipient extends Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idPerson;
	private String name;
	private String street;
	private String streetNumber;
	private String postCode;
	private String city;
	private String state;
	private String country;
	private String telephone;
	private String email;
	private String nameFull;
	private LetterTypeSendMethod typeMethod;

	public Recipient() {
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNameFull() {
		return nameFull;
	}

	public void setNameFull(String nameFull) {
		this.nameFull = nameFull;
	}

	public LetterTypeSendMethod getTypeMethod() {
		return typeMethod;
	}

	public void setTypeMethod(LetterTypeSendMethod typeMethod) {
		this.typeMethod = typeMethod;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return super.equals(obj);
		}

		Recipient actual = (Recipient) obj;

		return EqualsBuilder.reflectionEquals(this, actual);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}