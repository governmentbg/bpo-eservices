//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.common.ui.adapter.design;

import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.design.ValidationCode;
import org.springframework.stereotype.Component;

/**
 * #DS Class Integration changes.
 * A factory for creating ValidationCode objects.
 * @author Ramittal
 *
 */
@Component
public class ValidationCodeFactory implements UIFactory<eu.ohim.sp.core.domain.design.ValidationCode, ValidationCode> {

	/**
	 * Convert to.
	 *
	 * @param form the form
	 * @return ValidationCode
	 */
	@Override
	public eu.ohim.sp.core.domain.design.ValidationCode convertTo(ValidationCode form) {
		switch (form) {
			case valid:
				return eu.ohim.sp.core.domain.design.ValidationCode.OK;
			case invalid:
				return eu.ohim.sp.core.domain.design.ValidationCode.NOT_FOUND;
			case editable:
				return eu.ohim.sp.core.domain.design.ValidationCode.HINT;
			case notfound:
				return eu.ohim.sp.core.domain.design.ValidationCode.NOT_OK;
			default:
				throw new IllegalArgumentException("eu.ohim.rcd.efiling.rcdefiling.ui.form.enums.ValidationCode not found " + form);
		}

	}

	/**
	 * Convert from.
	 *
	 * @param core the core
	 * @return the ValidationCode
	 */
	@Override
	public ValidationCode convertFrom(eu.ohim.sp.core.domain.design.ValidationCode core) {
		switch (core) {
			case OK:
				return ValidationCode.valid;
			case NOT_OK:
				return ValidationCode.invalid;
			case HINT:
				return ValidationCode.editable;
			case NOT_FOUND:
				return ValidationCode.notfound;
			default:
				throw new IllegalArgumentException("eu.ohim.rcd.efiling.common.core.domain.codes.ValidationCode not found " + core);
		}
	}
}
