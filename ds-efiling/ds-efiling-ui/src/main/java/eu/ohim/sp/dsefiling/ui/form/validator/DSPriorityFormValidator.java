package eu.ohim.sp.dsefiling.ui.form.validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * 
 * @author serrajo
 */
@Component
public class DSPriorityFormValidator extends DSAbstractFormValidator {

	private static final String FIELD_FILE_WRAPPER_COPY_ATTACHMENT = "fileWrapperCopy.attachment";
	
	private static final String FIELD_FILE_WRAPPER_TRANSLATION_ATTACHMENT = "fileWrapperTranslation.attachment";	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return DSPriorityForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (isFieldVisible(AvailableSection.PRIORITY, FIELD_FILE_WRAPPER_COPY_ATTACHMENT)) {
			DSPriorityForm priorityForm = (DSPriorityForm) target;
			if (priorityForm.getFileWrapperCopy() != null &&
					BooleanUtils.isTrue(priorityForm.getFileWrapperCopy().getAttachment()) &&
					CollectionUtils.isEmpty(priorityForm.getFileWrapperCopy().getStoredFiles())) {
				addError(FIELD_FILE_WRAPPER_COPY_ATTACHMENT, ERROR_CODE_FIELD_MANDATORY, "This field is mandatory", errors);
			}
		}
		if (isFieldVisible(AvailableSection.PRIORITY, FIELD_FILE_WRAPPER_TRANSLATION_ATTACHMENT)) {
			DSPriorityForm priorityForm = (DSPriorityForm) target;
			if (priorityForm.getFileWrapperTranslation() != null &&
					BooleanUtils.isTrue(priorityForm.getFileWrapperTranslation().getAttachment()) &&
					CollectionUtils.isEmpty(priorityForm.getFileWrapperTranslation().getStoredFiles())) {
				addError(FIELD_FILE_WRAPPER_TRANSLATION_ATTACHMENT, ERROR_CODE_FIELD_MANDATORY, "This field is mandatory", errors);
			}
		}		
	}

}
