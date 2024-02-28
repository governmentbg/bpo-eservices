package eu.ohim.sp.common.ui.form.design;

import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Form used to look for Locarno Classifications.
 */
public class LocarnoSearchForm extends AbstractForm implements DSDesignForm {

	private static final long serialVersionUID = 6826923162137595310L;
	private LocarnoClassification searchData;
	private List<LocarnoComplexForm> locarnoClassificationsSelected;
	private String formMessages;
	
	/**
	 * Constructor.
	 */
	public LocarnoSearchForm() {
		searchData = new LocarnoClassification();
		locarnoClassificationsSelected = new ArrayList<LocarnoComplexForm>();
	}

	/**
	 * Method that returns the search data used to look for Locarno classifications.
	 * @return search data 
	 */
	public LocarnoClassification getSearchData() {
		return searchData;
	}

	/**
	 * Method that sets the search data.
	 * @param searchData
	 */
	public void setSearchData(LocarnoClassification searchData) {
		this.searchData = searchData;
	}

	/**
	 * Method that returns the Locarno classifications selected. 
	 * @return List of Locarno classifications.
	 */
	public List<LocarnoComplexForm> getLocarnoClassificationsSelected() {
		return locarnoClassificationsSelected;
	}

	/**
	 * Method that sets the Locarno classifications selected.
	 * @param locarnoClassificationsSelected List of Locarno classifications.
	 */
	public void setLocarnoClassificationsSelected(
			List<LocarnoComplexForm> locarnoClassificationsSelected) {
		this.locarnoClassificationsSelected = locarnoClassificationsSelected;
	}

	/**
	 * (non-Javadoc)
	 * @see eu.ohim.sp.common.ui.validator.Validatable#getAvailableSectionName()
	 */
	@Override
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.LOCARNO_ADD_CLASS;
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractForm#clone()
	 */
	@Override
	public AbstractForm clone() throws CloneNotSupportedException {
		LocarnoSearchForm locarnoSearchForm = new LocarnoSearchForm();
		locarnoSearchForm.setId(id);
		locarnoSearchForm.setSearchData(searchData.clone());
		for (LocarnoComplexForm locarnoForm : locarnoClassificationsSelected) {
			locarnoSearchForm.getLocarnoClassificationsSelected().add((LocarnoComplexForm) locarnoForm.clone());
		}
		return locarnoSearchForm;
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
				+ ((locarnoClassificationsSelected == null) ? 0
						: locarnoClassificationsSelected.hashCode());
		result = prime * result
				+ ((searchData == null) ? 0 : searchData.hashCode());
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
		LocarnoSearchForm other = (LocarnoSearchForm) obj;
		if (locarnoClassificationsSelected == null) {
			if (other.locarnoClassificationsSelected != null)
				return false;
		} else if (!locarnoClassificationsSelected
				.equals(other.locarnoClassificationsSelected))
			return false;
		if (searchData == null) {
			if (other.searchData != null)
				return false;
		} else if (!searchData.equals(other.searchData))
			return false;
		return true;
	}

	/**
	 * @return the formMessages
	 */
	public String getFormMessages() {
		return formMessages;
	}

	/**
	 * @param formMessages the formMessages to set
	 */
	public void setFormMessages(String formMessages) {
		this.formMessages = formMessages;
	}



	
}
