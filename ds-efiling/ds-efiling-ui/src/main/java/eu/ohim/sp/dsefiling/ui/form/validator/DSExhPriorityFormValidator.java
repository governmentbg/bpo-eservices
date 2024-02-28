package eu.ohim.sp.dsefiling.ui.form.validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * 
 * @author serrajo
 */
@Component
public class DSExhPriorityFormValidator extends DSAbstractFormValidator {

	private static final String FIELD_FILE_WRAPPER_ATTACHMENT = "fileWrapper.attachment";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return DSExhPriorityForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (isFieldVisible(AvailableSection.EXHIBITION, FIELD_FILE_WRAPPER_ATTACHMENT)) {
			DSExhPriorityForm exhPriorityForm = (DSExhPriorityForm) target;
			if (exhPriorityForm.getFileWrapper() != null &&
					BooleanUtils.isTrue(exhPriorityForm.getFileWrapper().getAttachment()) &&
					CollectionUtils.isEmpty(exhPriorityForm.getFileWrapper().getStoredFiles())) {
				addError(FIELD_FILE_WRAPPER_ATTACHMENT, ERROR_CODE_FIELD_MANDATORY, "This field is mandatory", errors);
			}
		}
	}

}
