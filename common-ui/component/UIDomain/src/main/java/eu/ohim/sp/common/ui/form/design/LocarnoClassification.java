package eu.ohim.sp.common.ui.form.design;

import java.io.Serializable;
import java.util.List;

/**
 * POJO that contains information about a Locarno classification.
 * TODO Revise with Locarno integration
 */
public class LocarnoClassification implements Serializable, Cloneable {

	private static final long serialVersionUID = 457575486892671329L;
	private String id;
	private LocarnoClass locarnoClass;
	private String indication;

	//DS Class Integration changes start
	private ValidationCode validation = ValidationCode.notfound;
	private List<LocarnoClassification> suggestions;
	private boolean group;
	private boolean heading;
	//DS Class Integration changes end
	
	/**
	 * Constructor.
	 */
	public LocarnoClassification() {
		locarnoClass = new LocarnoClass();
	}
	
	/**
	 * Constructor.
	 * @param id
	 * @param clazz
	 * @param subclass
	 * @param indication
	 */
	public LocarnoClassification(String id, String clazz, String subclass, String indication) {
		this.id = id;
		this.locarnoClass = new LocarnoClass(clazz, subclass);
		this.indication = indication;
	}
	
	/**
	 * Method that returns the Locarno id.
	 *
	 * @return the Locarno id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method that sets the Locarno id.
	 *
	 * @param clazz the Locarno id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Method that returns the Locarno class.
	 *
	 * @return the Locarno class.
	 */
	public LocarnoClass getLocarnoClass() {
		return locarnoClass;
	}

	/**
	 * Method that sets the Locarno class.
	 *
	 * @param clazz the Locarno class to set.
	 */
	public void setLocarnoClass(LocarnoClass locarnoClass) {
		this.locarnoClass = locarnoClass;
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
	 * @param clazz the Locarno indication to set.
	 */
	public void setIndication(String indication) {
		this.indication = indication;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public LocarnoClassification clone() {
		LocarnoClassification locarnoClassification = new LocarnoClassification();
		locarnoClassification.setId(id);
		locarnoClassification.setLocarnoClass(locarnoClass.clone());
		locarnoClassification.setIndication(indication);
		//DS Class Integration changes start
		locarnoClassification.setGroup(group);
		locarnoClassification.setHeading(heading);
		locarnoClassification.setSuggestions(suggestions);
		locarnoClassification.setValidation(validation);
		//DS Class Integration changes end
	    return locarnoClassification;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((indication == null) ? 0 : indication.hashCode());
		result = prime * result
				+ ((locarnoClass == null) ? 0 : locarnoClass.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocarnoClassification other = (LocarnoClassification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (indication == null) {
			if (other.indication != null)
				return false;
		} else if (!indication.equals(other.indication))
			return false;
		if (locarnoClass == null) {
			if (other.locarnoClass != null)
				return false;
		} else if (!locarnoClass.equals(other.locarnoClass))
			return false;
		return true;
	}

	//DS Class Integration changes start

	public ValidationCode getValidation() {
		return validation;
	}

	public void setValidation(ValidationCode validation) {
		this.validation = validation;
	}

	public List<LocarnoClassification> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<LocarnoClassification> suggestions) {
		this.suggestions = suggestions;
	}

	public boolean getGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public boolean getHeading() {
		return heading;
	}

	public void setHeading(boolean heading) {
		this.heading = heading;
	}

	//DS Class Integration changes end

	public String getLocarnoClassFormatted() {
		String classification;
		if (getLocarnoClass() != null) {
			classification = getLocarnoClass().getClassFormatted();
		} else {
			classification = "";
		}
		return classification;
	}
   
}
