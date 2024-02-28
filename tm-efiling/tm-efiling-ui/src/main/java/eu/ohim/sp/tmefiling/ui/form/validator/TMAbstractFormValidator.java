package eu.ohim.sp.tmefiling.ui.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Base class for tm-efiling validators
 *
 * @author serrajo
 */
public abstract class TMAbstractFormValidator implements Validator {

	protected static final String ERROR_CODE_FIELD_MANDATORY = "BRMandatory";
		
    @Autowired
    private FlowScopeDetails flowScopeDetails;
    
	@Autowired
	private SectionViewConfiguration sectionViewConfiguration;

	/**
	 * Checks if field is visible in section for current flowMode located in {@link FlowScopeDetails}
	 *
	 * @param section - section to check
	 * @param field - field to check
	 * @return - boolean, true is visible, false if not
	 */
	protected boolean isFieldVisible(AvailableSection section, String field) {
		return sectionViewConfiguration.getRender(section, field, flowScopeDetails.getFlowModeId());
	}

	protected void addError(String field, String code, String defaultMessage, Errors errors) {
		errors.rejectValue(field, code, null, defaultMessage);
	}
	
}
