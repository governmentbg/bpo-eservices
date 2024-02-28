package eu.ohim.sp.common.ui.form.design;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Form to add a new complex Locarno Classification.
 */
public class LocarnoComplexForm extends LocarnoAbstractForm {

	private static final long serialVersionUID = 2180000247456871084L;

	private List<LocarnoClass> classes;
	private LocarnoComplexKindForm type;
	private String indication;

	private ValidationCode validation = ValidationCode.notfound;
	private List<LocarnoClassification> suggestions;
	
	public LocarnoComplexForm() {
		classes = new ArrayList<LocarnoClass>();
	}
	
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.LOCARNO_NEW_COMPLEX_PRODUCT;
	}

	@Override
	public LocarnoComplexForm clone() {
		LocarnoComplexForm locarnoComplexForm = new LocarnoComplexForm();
		locarnoComplexForm.setId(getId());
		locarnoComplexForm.setImported(getImported());
		locarnoComplexForm.setIndication(getIndication());
		locarnoComplexForm.setType(type);
		for (LocarnoClass locarnoClass : classes) {
			locarnoComplexForm.getClasses().add(locarnoClass.clone());
		}
		locarnoComplexForm.setValidationCode(validation);
		locarnoComplexForm.setSuggestions(suggestions);
		return locarnoComplexForm;
	}

	public List<LocarnoClass> getClasses() {
		return classes;
	}

	public void setClasses(List<LocarnoClass> classes) {
		this.classes = classes;
	}

	public LocarnoComplexKindForm getType() {
		return type;
	}

	public void setType(LocarnoComplexKindForm type) {
		this.type = type;
	}

	/**
	 * Method that returns the Locarno indication.
	 *
	 * @return the Locarno indication.
	 */
	public String getIndication() {
		return indication;
	}

	/**
	 * Method that sets the Locarno indication.
	 *
	 * @param indication the Locarno indication to set.
	 */
	public void setIndication(String indication) {
		this.indication = indication;
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoId()
	 */
	@Override
	public String getLocarnoId() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoClassFormatted()
	 */
	@Override
	public String getLocarnoClassFormatted() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<LocarnoClass> it = classes.iterator(); it.hasNext(); ) {
			LocarnoClass locarnoClass = it.next();
			sb.append(locarnoClass.getClassFormatted());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.form.LocarnoAbstractForm#getLocarnoIndication()
	 */
	@Override
	public String getLocarnoIndication() {
		return getIndication();
	}

	//DS Class Integration changes start

	@Override
	public ValidationCode getValidationCode() {
		return validation;
	}

	@Override
	public List<LocarnoClassification> getSuggestions() {
		return suggestions;
	}

	public void setValidationCode(ValidationCode validation) {
		this.validation = validation;
	}

	public void setSuggestions(List<LocarnoClassification> suggestions) {
		this.suggestions = suggestions;
	}

	@Override
	public boolean getGroup() {
		return false;
	}

	@Override
	public boolean getHeading() {
		return false;
	}

	//DS Class Integration changes end
}
