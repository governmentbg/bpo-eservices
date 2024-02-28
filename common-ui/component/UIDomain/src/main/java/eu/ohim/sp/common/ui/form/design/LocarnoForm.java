package eu.ohim.sp.common.ui.form.design;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

import java.util.List;

/**
 * Form to add a new Locarno Classification.
 */
public class LocarnoForm extends LocarnoAbstractForm {
	private static final long serialVersionUID = 1608049291115602876L;

	private LocarnoClassification locarnoClassification;
	private LocarnoComplexKindForm type = LocarnoComplexKindForm.SINGLE_PRODUCT;
	/**
	 * Constructor.
	 */
	public LocarnoForm() {
		locarnoClassification = new LocarnoClassification();
	}
	
	/**
	 * Method that returns the Locarno classification.
	 *
	 * @return the Locarno classification.
	 */
	public LocarnoClassification getLocarnoClassification() {
		return locarnoClassification;
	}

	/**
	 * Method that sets the Locarno classification.
	 *
	 * @param locarnoClassification the Locarno classification to set.
	 */
	public void setLocarnoClassification(LocarnoClassification locarnoClassification) {
		this.locarnoClassification = locarnoClassification;
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoId()
	 */
	@Override
	public String getLocarnoId() {
		return locarnoClassification.getId();
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoClassFormatted()
	 */
	@Override
	public String getLocarnoClassFormatted() {
		if (locarnoClassification != null) {
			return locarnoClassification.getLocarnoClassFormatted();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoIndication()
	 */
	@Override
	public String getLocarnoIndication() {
		return locarnoClassification.getIndication();
	}
	
	/**
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.validator.Validatable#getAvailableSectionName()
	 */
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.LOCARNO_NEW_PRODUCT;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractForm#clone()
	 */
	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		LocarnoForm locarnoForm = new LocarnoForm();
		locarnoForm.setId(id);
		locarnoForm.setImported(getImported());
		locarnoForm.setLocarnoClassification(locarnoClassification.clone());
		locarnoForm.setType(type);
		return locarnoForm;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractForm#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((locarnoClassification == null) ? 0 : locarnoClassification
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractForm#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocarnoForm other = (LocarnoForm) obj;
		if (locarnoClassification == null) {
			if (other.locarnoClassification != null)
				return false;
		} else if (!locarnoClassification.equals(other.locarnoClassification))
			return false;
		return true;
	}

	//DS Class Integration changes start

	@Override
	public ValidationCode getValidationCode() {
		return locarnoClassification.getValidation();
	}

	@Override
	public void setValidationCode(ValidationCode validation) {
		locarnoClassification.setValidation(validation);
	}

	@Override
	public List<LocarnoClassification> getSuggestions() {
		return locarnoClassification.getSuggestions();
	}

	@Override
	public boolean getGroup() {
		return locarnoClassification.getGroup();
	}

	@Override
	public boolean getHeading() {
		return locarnoClassification.getHeading();
	}

	//DS Class Integration changes end


	@Override
	public LocarnoComplexKindForm getType() {
		return type;
	}

	@Override
	public void setType(LocarnoComplexKindForm type) {
		this.type = type;
	}
}
