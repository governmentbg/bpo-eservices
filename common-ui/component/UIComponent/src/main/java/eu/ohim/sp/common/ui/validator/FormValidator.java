/*******************************************************************************
 * * $Id:: FormValidator.java 53086 2012-12-14 09:01:44Z virgida                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;

public abstract class FormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof Validatable) {
			validate((Validatable) target, errors, new FlowScopeDetails());
		}
	}

	/**
	 * 
	 * @param target the form object that will be validated
	 * @param errors the errors object that will be displayed to the user
	 * @param flowScopeDetails the flow scope details of the cu
	 */
	public abstract void validate(Validatable target, Errors errors, FlowScopeDetails flowScopeDetails);
}
