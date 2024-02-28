package eu.ohim.sp.tmefiling.ui.form.validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.claim.ExhPriorityForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * 
 * @author serrajo
 */
@Component
public class TMExhPriorityFormValidator extends TMAbstractFormValidator {

	private static final String FIELD_FILE_WRAPPER_ATTACHMENT = "fileWrapper.attachment";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ExhPriorityForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (isFieldVisible(AvailableSection.EXHIBITION, FIELD_FILE_WRAPPER_ATTACHMENT)) {
			ExhPriorityForm exhPriorityForm = (ExhPriorityForm) target;
			if (exhPriorityForm.getFileWrapper() != null &&
					BooleanUtils.isTrue(exhPriorityForm.getFileWrapper().getAttachment()) &&
					CollectionUtils.isEmpty(exhPriorityForm.getFileWrapper().getStoredFiles())) {
				addError(FIELD_FILE_WRAPPER_ATTACHMENT, ERROR_CODE_FIELD_MANDATORY, "This field is mandatory", errors);
			}
		}
	}

}
