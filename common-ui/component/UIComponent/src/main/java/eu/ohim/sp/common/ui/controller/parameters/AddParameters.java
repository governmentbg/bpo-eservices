/*******************************************************************************
 * * $Id:: AddParameters.java 14218 2012-10-26 16:13:14Z villama                 $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller.parameters;

import eu.ohim.sp.common.ui.form.AbstractForm;

/**
 * Parameters passed when an Add request is done
 *
 * @author karalch
 */
public class AddParameters extends RequestParameters {

	/**
	 * Maximum entities that are allowed to add to the collection
	 * configurable
	 */
	private Integer maximumEntities;

	/**
	 * It is true if it should trigger validation before being added
	 */
	private boolean triggerValidation = true;

	public AddParameters(Class<? extends AbstractForm> commandClass, String commandName,
						 String successView, String formView, Integer maximumEntities) {
		super(commandClass, commandName, successView, formView);
		this.maximumEntities = maximumEntities;
	}

	public AddParameters(Class<? extends AbstractForm> commandClass, String commandName, String successView,
						 String formView, Integer maximumEntities, boolean triggerValidation) {
		super(commandClass, commandName, successView, formView);
		this.maximumEntities = maximumEntities;
		this.triggerValidation = triggerValidation;
	}

	public Integer getMaximumEntities() {
		return maximumEntities;
	}

	public void setMaximumEntities(Integer maximumEntities) {
		this.maximumEntities = maximumEntities;
	}

	public boolean isTriggerValidation() {
		return triggerValidation;
	}

	public void setTriggerValidation(boolean triggerValidation) {
		this.triggerValidation = triggerValidation;
	}

}
