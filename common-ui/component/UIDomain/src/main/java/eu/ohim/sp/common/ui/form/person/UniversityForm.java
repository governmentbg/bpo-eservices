package eu.ohim.sp.common.ui.form.person;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
/**
 * Stores all the necessary information for the Universities
 * 
 * @author segovro
 * 
 */
public class UniversityForm extends ApplicantForm  implements Cloneable {

	private static final long serialVersionUID = 1L;
	


	public UniversityForm() {
		setType(ApplicantKindForm.UNIVERSITY);
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public UniversityForm clone() throws CloneNotSupportedException {
		UniversityForm universityForm = copyApp(UniversityForm.class);
		
		return universityForm;
	}
	
	public AvailableSection getAvailableSectionName() {
		return AvailableSection.APPLICANT_UNIVERSITY;
	}

}
