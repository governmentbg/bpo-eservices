/*******************************************************************************
 * * $Id:: TransformationFactory.java 53086 2012-12-14 09:01:44Z virgida         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import eu.ohim.sp.common.ui.form.claim.TransformationForm;
import eu.ohim.sp.core.domain.claim.TransformationPriority;
import org.springframework.stereotype.Component;

/**
 * Factory that converts between UI {@link TransformationForm} object 
 * and core {@link TransformationPriority} object.
 * @author karalch
 *
 */
@Component
public class TransformationFactory implements UIFactory<TransformationPriority, TransformationForm> {

	/**
	 * Receives as a parameter a UI {@link TransformationForm} object and
	 * converts it to a core {@link TransformationPriority} object.
	 * 
	 * @param form
	 *            the UI TransformationForm to convert
	 * @return the core TransformationPriority object
	 */
	@Override
	public TransformationPriority convertTo(TransformationForm form) {
		if (form == null) {
			return new TransformationPriority();
		}

		TransformationPriority core = new TransformationPriority();

		core.setRegistrationNumber(form.getIrNumber());
		core.setCancellationDate(form.getCancelationDate());
		core.setRegistrationDate(form.getIrDate());
		core.setPriorityDate(form.getPriosenioDate());
		core.setTransformationCountryCode(form.getTransformationCountryCode());
		//core.setImported(form.getImported());
		return core;
	}

	/**
	 * Receives as a parameter a core {@link TransformationPriority} object and
	 * converts it to a {@link TransformationForm} object.
	 * 
	 * @param transformation
	 *            the transformation to convert
	 * @return the transformation form object
	 */
	@Override
	public TransformationForm convertFrom(TransformationPriority transformation) {
		if (transformation == null) {
			return new TransformationForm();
		}

		TransformationForm form = new TransformationForm();
		form.setIrNumber(transformation.getRegistrationNumber());
		form.setCancelationDate(transformation.getCancellationDate());
		form.setIrDate(transformation.getRegistrationDate());
		form.setPriosenioDate(transformation.getPriorityDate());
		form.setTransformationCountryCode(transformation.getTransformationCountryCode());
		//form.setImported(transformation.isImported());
		return form;
	}

}
