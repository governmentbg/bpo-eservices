/*******************************************************************************
 * * $Id:: ErrorFactory.java 53086 2012-12-14 09:01:44Z virgida                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.ohim.sp.core.domain.validation.ErrorCore;
import eu.ohim.sp.core.domain.validation.ErrorList;

@Component
public class ErrorFactory {
	
	public void coreErrorToUIError(ErrorList coreErrors, Errors errors) {
		if (coreErrors != null) {
			for (ErrorCore coreError : coreErrors.getErrorList()) {
				Object[] args = null;
				if (coreError.getErrorArgs()!=null){
					args = coreError.getErrorArgs().toArray();
				}
				if(!coreError.isWarning()){
					errors.rejectValue(coreError.getField(), coreError.getErrorCode(), args, coreError.getDisplayMessage());
				}
				
			}
		}
	}

}
