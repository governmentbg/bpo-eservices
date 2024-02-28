package eu.ohim.sp.dsefiling.ui.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * 
 * @author serrajo
 *
 */
public abstract class DSAbstractFormValidator implements Validator {

	protected static final String ERROR_CODE_FIELD_MANDATORY = "BRMandatory";
		
    @Autowired
    private FlowScopeDetails flowScopeDetails;
    
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	/**
	 * 
	 * @param section
	 * @param field
	 * @return
	 */
	protected boolean isFieldVisible(AvailableSection section, String field) {
		return sectionViewConfiguration.getRender(section, field, flowScopeDetails.getFlowModeId());
	}
	
	/**
	 * 
	 * @param field
	 * @param code
	 * @param defaultMessage
	 * @param errors
	 */
	protected void addError(String field, String code, String defaultMessage, Errors errors) {
		errors.rejectValue(field, code, null, defaultMessage);
	}
	
}
