package eu.ohim.sp.common.ui.form.design;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;

import java.util.List;

/**
 * Form to add a new Locarno Classification.
 */
public abstract class LocarnoAbstractForm extends AbstractImportableForm implements DSDesignForm {
	private static final long serialVersionUID = 1608049291115602876L;
	
	/**
	 * Method that returns the Locarno id. 
	 * @return Locarno id.
	 */
	public abstract String getLocarnoId();
	
	/**
	 * Returns the Locarno class formatted.
	 * @return Locarno class formateed.
	 */
	public abstract String getLocarnoClassFormatted();

	/**
	 * Method that returns the Locarno indication.
	 * @return the Locarno indication.
	 */
	public abstract String getLocarnoIndication();

	//DS Class Integration changes start

	/**
	 * Method that returns the Validation result.
	 * @return the Validation result.
	 */
	public abstract ValidationCode getValidationCode();

	public abstract void setValidationCode(ValidationCode validation);

	/**
	 * Method that returns the suggestions.
	 * @return the suggestions.
	 */
	public abstract List<LocarnoClassification> getSuggestions();

	/**
	 * Method that returns the group.
	 * @return the group.
	 */
	public abstract boolean getGroup();

	/**
	 * Method that returns the heading.
	 * @return the heading.
	 */
	public abstract boolean getHeading();

	//DS Class Integration changes end

	public abstract LocarnoComplexKindForm getType();
	public abstract void setType(LocarnoComplexKindForm type);

}
