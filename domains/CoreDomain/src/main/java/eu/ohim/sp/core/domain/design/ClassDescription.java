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
package eu.ohim.sp.core.domain.design;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * #DS Class Integration changes.
 * @author Ramittal
 *
 */
public class ClassDescription {

	/**
	 * The constant LOCARNO_CLASS_NUMBER_FORMAT.
	 */
	private static final String LOCARNO_CLASS_NUMBER_FORMAT = "([0-9]{2})?.([0-9]{2})?";

	/**
	 * The Locarno pattern.
	 */
	private static final Pattern LOCARNO_PATTERN = Pattern.compile(LOCARNO_CLASS_NUMBER_FORMAT);

	/** Class ID */
	private Integer classId;
	/** sub class ID */
	private Integer subClassId;

	/**
	 * The Product descriptions.
	 */
	private List<LocalizedText> productDescriptions;
	/**
	 * The Valid.
	 */
	private ValidationCode valid;
	/**
	 * The Classification term identifier.
	 */
	private String classificationTermIdentifier;

	/**
	 * The Suggestions.
	 */
	private List<ClassDescription> suggestions;

	/**
	 * Gets the value of the classNumber property.
	 *
	 * @return possible object is
	 */
	public String getLocarnoClassNumber() {
		return new StringBuilder(classId.intValue() >= 0? StringUtils.leftPad(String.valueOf(classId), 2, "0") : "")
				.append(".").append(subClassId >= 0? StringUtils.leftPad(String.valueOf(subClassId), 2, "0") : "").toString();
	}

	/**
	 * Sets the value of the classNumber property.
	 *
	 * @param value allowed object is
	 * @throws IllegalArgumentException when the given value is not in a valid Locarno Class Number
	 *             format
	 */
	public void setLocarnoClassNumber(String value) {
		if (StringUtils.isEmpty(value) || !LOCARNO_PATTERN.matcher(value).matches()) {
			throw new IllegalArgumentException("The locarno class number is not in a valid format: " + LOCARNO_CLASS_NUMBER_FORMAT + ". Received: "
					+ value);
		}
		String tmpClassId = value.substring(0, value.indexOf('.'));
		this.classId = StringUtils.isEmpty(tmpClassId)? Integer.valueOf(-1) : Integer.valueOf(tmpClassId);
		String tmpSubclassId = value.substring(value.indexOf('.') + 1);
		this.subClassId = StringUtils.isEmpty(tmpSubclassId)? Integer.valueOf(-1) : Integer.valueOf(tmpSubclassId);
	}

	/**
	 * Gets class id.
	 *
	 * @return the class id
	 */
	public Integer getClassId() {
		return classId;
	}

	/**
	 * Gets sub class id.
	 *
	 * @return the sub class id
	 */
	public Integer getSubClassId() {
		return subClassId;
	}

	/**
	 * Gets the value of the productDescription property.
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside the JAXB object. This is
	 * why there is not a <CODE>set</CODE> method for the productDescription property.
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getProductDescription().add(newItem);
	 * </pre>
	 * Objects of the following type(s) are allowed in the list
	 * {@link LocalizedText }
	 * @return the product descriptions
	 */
	public List<LocalizedText> getProductDescriptions() {
		if (productDescriptions == null) {
			productDescriptions = new ArrayList<>();
		}
		return this.productDescriptions;
	}

	/**
	 * Gets the value of the classificationTermDetails property.
	 *
	 * @return possible object is
	 */
	public String getClassificationTermIdentifier() {
		return classificationTermIdentifier;
	}

	/**
	 * Sets the value of the classificationTermDetails property.
	 *
	 * @param value allowed object is
	 */
	public void setClassificationTermIdentifier(String value) {
		this.classificationTermIdentifier = value;
	}

	/**
	 * Gets valid.
	 *
	 * @return the valid
	 */
	public ValidationCode getValid() {
		return valid;
	}

	/**
	 * Sets valid.
	 *
	 * @param valid the valid to set
	 */
	public void setValid(ValidationCode valid) {
		this.valid = valid;
	}

	/**
	 * Gets suggestions.
	 *
	 * @return the suggestions
	 */
	public List<ClassDescription> getSuggestions() {
		if (suggestions == null) {
			suggestions = new ArrayList<>();
		}
		return suggestions;
	}

	/**
	 * Sets suggestions.
	 *
	 * @param suggestions the suggestions
	 */
	public void setSuggestions(List<ClassDescription> suggestions) {
		if (suggestions == null) {
			this.suggestions = new ArrayList<>();
		} else {
			this.suggestions = suggestions;
		}
	}

	/**
	 * Sets class id.
	 *
	 * @param classId the class id
     */
	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	/**
	 * Sets sub class id.
	 *
	 * @param subClassId the sub class id
     */
	public void setSubClassId(Integer subClassId) {
		this.subClassId = subClassId;
	}

	/**
	 * Equals boolean.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ClassDescription rhs = (ClassDescription) obj;
		return new EqualsBuilder().append(ClassDescription.LOCARNO_PATTERN, ClassDescription.LOCARNO_PATTERN)
		        .append(this.productDescriptions, rhs.productDescriptions)
				.append(this.valid, rhs.valid)
				.append(this.classId, rhs.classId)
				.append(this.subClassId, rhs.subClassId)
		        .append(this.classificationTermIdentifier, rhs.classificationTermIdentifier)
				.append(this.suggestions, rhs.suggestions).isEquals();
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(ClassDescription.LOCARNO_PATTERN)
				.append(classId)
				.append(subClassId)
				.append(productDescriptions)
				.append(valid)
		        .append(classificationTermIdentifier)
				.append(suggestions).toHashCode();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("locarnoPattern", LOCARNO_PATTERN)
				.append("classId", classId)
				.append("subClassId", subClassId)
		        .append("productDescriptions", productDescriptions)
				.append("valid", valid)
		        .append("classificationTermIdentifier", classificationTermIdentifier)
				.append("suggestions", suggestions).toString();
	}
}
