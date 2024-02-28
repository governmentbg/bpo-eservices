/*******************************************************************************
 * * $Id:: RequestParameters.java 14218 2012-10-26 16:13:14Z villama             $
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
 * Default information needed to pass on the request of an controller
 *
 * @author karalch
 */
public class RequestParameters {

	/**
	 * the class of the object that are related to the request
	 */
	private Class<? extends AbstractForm> commandClass;

	private String commandName;

	private String successView;

	private String formView;

	public RequestParameters(Class<? extends AbstractForm> commandClass, String commandName,
							 String successView, String formView) {
		this.commandClass = commandClass;
		this.commandName = commandName;
		this.formView = formView;
		this.successView = successView;
	}

	public Class<? extends AbstractForm> getCommandClass() {
		return commandClass;
	}

	public void setCommandClass(Class<? extends AbstractForm> commandClass) {
		this.commandClass = commandClass;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public String getFormView() {
		return formView;
	}

	public void setFormView(String formView) {
		this.formView = formView;
	}
}
