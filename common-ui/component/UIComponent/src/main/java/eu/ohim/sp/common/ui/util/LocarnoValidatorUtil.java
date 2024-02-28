package eu.ohim.sp.common.ui.util;

import eu.ohim.sp.common.ui.form.design.LocarnoAbstractForm;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Validation if the added term already exists or not
 * 
 */
public class LocarnoValidatorUtil {

	/**
     * <p>
     * This method checks if the entered terms is already there in the locarno form when user edits the term
     *
     * @param locarno list for new Locarno Classification
     * @param toAddLocarnoForm form Locarno Classification
     * @param id is unique value for edit
     * @return boolean true if term exists
     * 
     */
	public boolean validateIfNotExistsForEdit(List<LocarnoAbstractForm> locarno, LocarnoAbstractForm toAddLocarnoForm, String id){
		boolean exists = true;
		for (LocarnoAbstractForm exitsForms : locarno) {
			if(!StringUtils.equals(id, exitsForms.getId())){
				if(exitsForms.getLocarnoIndication().equals(toAddLocarnoForm.getLocarnoIndication()) && exitsForms.getLocarnoClassFormatted().equals(toAddLocarnoForm.getLocarnoClassFormatted())){
					exists =  false;
					break;
				}else {
					exists = true;
				}	
			}
		}
		return exists;
	}

	/**
     * <p>
     * This method checks if the entered terms is already there in the locarno form when user adds a new term
     *
     * @param locarno list for new Locarno Classification
     * @param toAddLocarnoForm form Locarno Classification
     * @return boolean true if term exists
     * 
     */
	public boolean validateIfNotExistsForAdd(List<LocarnoAbstractForm> locarno, LocarnoAbstractForm toAddLocarnoForm){
		boolean exists = true;
		for (LocarnoAbstractForm exitsForms : locarno) {
			if(exitsForms.getLocarnoIndication().equals(toAddLocarnoForm.getLocarnoIndication()) && exitsForms.getLocarnoClassFormatted().equals(toAddLocarnoForm.getLocarnoClassFormatted())){
				exists =  false;
				break;
			}else {
				exists = true;
			}	
		}
		return exists;
	}
}
