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
package eu.ohim.sp.common.ui.form.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Indication product form.
 *
 * @author reissge
 */
public class IndicationProductForm implements java.io.Serializable {

	/** The Constant UNKNOWN_CLASS_CODE. */
	public static final Integer UNKNOWN_CLASS_CODE = Integer.valueOf(-1);
	
	/** The Constant UNKNOWN_CLASS_CODE_STR. */
	public static final String UNKNOWN_CLASS_CODE_STR = String.valueOf(UNKNOWN_CLASS_CODE);
	
	/** The Constant NUMBER_TWO */
	public static final int NUMBER_TWO = 2;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 652499214832666108L;

	/** The classification term id. */
	private String identifier;

	/** The locarno class number. */
	private String classCode;

	/** The locarno subclass number. */
	private String subclassCode;

	/** The product description. */
	private String text;

	/**
	 * The Term status.
	 */
	@JsonProperty("termStatus")
	private String termStatus;

	/** The validation. */
	@JsonProperty("validationState")
	private ValidationCode validation;

	/** The Suggestions. */
	@JsonProperty("relatedTerms")
	private List<IndicationProductForm> suggestions;
	
	/** The group title flag. */
	@JsonProperty("group")
	private boolean group;

	/** Headings flag. */
    private boolean heading;
	
	/**
	 * Instantiates a new indication product form.
	 */
	public IndicationProductForm() {
	}

	/**
	 * IndicationProductForm constructor.
	 *
	 * @param toCopy the to copy
	 */
	public IndicationProductForm(IndicationProductForm toCopy) {
		this.identifier = toCopy.identifier;
		this.classCode = toCopy.classCode;
		this.subclassCode = toCopy.subclassCode;
		this.text = toCopy.text;
		this.termStatus = toCopy.termStatus;
		this.validation = toCopy.validation;
		if(this.suggestions != null) {
			this.suggestions = new ArrayList<>(this.suggestions);
		}
	}


	/**
	 * Gets classification term id.
	 *
	 * @return the classification term id
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets classification term id.
	 *
	 * @param identifier the classification term id
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets locarno class.
	 *
	 * @return the locarno class
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * Sets locarno class no.
	 *
	 * @param classCode the locarno class
	 */
	public void setClassCode(String classCode) {
		this.classCode = StringUtils.leftPad(classCode, NUMBER_TWO, '0');
	}

	/**
	 * Gets locarno subclass.
	 *
	 * @return the locarno subclass
	 */
	public String getSubclassCode() {
        return subclassCode;
    }

	/**
	 * Sets locarno subclass.
	 *
	 * @param subclassCode the locarno subclass
	 */
	public void setSubclassCode(String subclassCode) {
		this.subclassCode = StringUtils.leftPad(subclassCode, NUMBER_TWO, '0');
    }

	/**
	 * Gets product description.
	 *
	 * @return the product description
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets product description.
	 *
	 * @param text the product description
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the validation.
	 *
	 * @return the validation
	 */
	public ValidationCode getValidation() {
		return validation;
	}

	/**
	 * Sets the validation.
	 *
	 * @param validation the validation to set
	 */
	public void setValidation(ValidationCode validation) {
		this.validation = validation;
	}

	/**
	 * Gets suggestions.
	 *
	 * @return the suggestions
	 */
	public List<IndicationProductForm> getSuggestions() {
		return suggestions;
	}

	/**
	 * Sets suggestions.
	 *
	 * @param suggestions the suggestions
	 */
	public void setSuggestions(List<IndicationProductForm> suggestions) {
		this.suggestions = suggestions;
	}

	/**
	 * Gets term status.
	 *
	 * @return the term status
	 */
	public String getTermStatus() {
		return termStatus;
	}

	/**
	 * Sets term status.
	 *
	 * @param termStatus the term status
	 */
	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}


	/**
	 * Checks if is group.
	 *
	 * @return true, if is group
	 */
	public boolean isGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(boolean group) {
		this.group = group;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object o) {
		if (this == o){
			return true;
        }
		if (o == null || getClass() != o.getClass()){
			return false;
        }
		IndicationProductForm that = (IndicationProductForm) o;

		return new EqualsBuilder().append(classCode, that.classCode).append(subclassCode,
				that.subclassCode).append(text, that.text).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override public int hashCode() {
		return new HashCodeBuilder().append(classCode).append(subclassCode).append(text).toHashCode();
	}
	
	/**
	 * Checks if is heading.
	 *
	 * @return true, if is heading
	 */
	public boolean isHeading() {
		return heading;
	}

	/**
	 * Sets the heading.
	 *
	 * @param heading the new heading
	 */
	public void setHeading(boolean heading) {
		this.heading = heading;
	}
}
