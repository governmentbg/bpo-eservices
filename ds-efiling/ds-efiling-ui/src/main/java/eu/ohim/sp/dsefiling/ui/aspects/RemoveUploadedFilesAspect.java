package eu.ohim.sp.dsefiling.ui.aspects;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import eu.ohim.sp.common.ui.form.design.DSExhPriorityForm;
import eu.ohim.sp.common.ui.form.design.DSPriorityForm;
import eu.ohim.sp.common.ui.form.design.DesignForm;
import eu.ohim.sp.common.ui.form.design.DesignViewForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;

/**
 * 
 * @author serrajo
 *
 */
@Aspect
public class RemoveUploadedFilesAspect {

	@Autowired
	private FileServiceInterface fileService;

	/**
	 * 
	 * @param mv
	 */
	@AfterReturning(pointcut = "execution(* eu.ohim.sp.dsefiling.ui.controller.RemoveDesignController.handleRemoveDesign(..))", returning = "mv")
	public void removeDesign(ModelAndView mv) {
		if (isFormSuccessfullyRemoved(mv)) {
			DesignForm design = getCommand(mv, DesignForm.class);
			for (DesignViewForm designView : design.getViews()) {
				removeStoredFiles(designView.getView());
			}
		}
	}
	
	/**
	 * 
	 * @param mv
	 */
	@AfterReturning(pointcut = "execution(* eu.ohim.sp.common.ui.controller.RemoveController.handleRemovePriority(..))", returning = "mv")
	public void removePriority(ModelAndView mv) {
		if (isFormSuccessfullyRemoved(mv)) {
			DSPriorityForm priority = getCommand(mv, DSPriorityForm.class);
			removeStoredFiles(priority.getFilePriorityCertificate());
			removeStoredFiles(priority.getFileWrapperCopy());
			removeStoredFiles(priority.getFileWrapperTranslation());
		}
	}
	
	/**
	 * 
	 * @param mv
	 */
	@AfterReturning(pointcut = "execution(* eu.ohim.sp.common.ui.controller.RemoveController.handleRemoveExhPriority(..))", returning = "mv")
	public void removeExhPriority(ModelAndView mv) {
		if (isFormSuccessfullyRemoved(mv)) {
			DSExhPriorityForm exhibition = getCommand(mv, DSExhPriorityForm.class);
			removeStoredFiles(exhibition.getFileWrapper());
		}
	}

	/**
	 * 
	 * @param mv
	 */
	@AfterReturning(pointcut = "execution(* eu.ohim.sp.common.ui.controller.RemoveController.handleRemoveRepresentative(..))", returning = "mv")
	public void removeRepresentative(ModelAndView mv) {
		if (isFormSuccessfullyRemoved(mv)) {
			RepresentativeForm representative = getCommand(mv, RepresentativeForm.class);
			removeStoredFiles(representative.getRepresentativeAttachment());
		}
	}
	
	/**
	 * 
	 * @param fileWrapper
	 */
	private void removeStoredFiles(FileWrapper fileWrapper) {
		if (fileWrapper != null && CollectionUtils.isNotEmpty(fileWrapper.getStoredFiles())) {
			for (StoredFile storedFile :fileWrapper.getStoredFiles()) {
				fileService.removeFile(storedFile);
			}
		}
	}
	
	/**
	 * 
	 * @param mv
	 * @return
	 */
	private boolean isFormSuccessfullyRemoved(ModelAndView mv) {
		return getCommand(mv, Object.class) != null; 	
	}
	
	/**
	 * 
	 * @param <T>
	 * @param <T>
	 * @param mv
	 * @param clazz 
	 * @return
	 */
	private <T> T getCommand(ModelAndView mv, Class<T> clazz) {
		return clazz.cast(mv.getModelMap().get("command"));
	}
}
