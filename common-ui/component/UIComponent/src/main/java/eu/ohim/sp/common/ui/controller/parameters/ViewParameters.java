/*******************************************************************************
 * * $Id:: ViewParameters.java 14218 2012-10-26 16:13:14Z villama                $
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
 * Parameters passed when a View request is done
 *
 * @author karalch
 */
public class ViewParameters extends RequestParameters {

	public ViewParameters(Class<? extends AbstractForm> commandClass, String commandName,
						  String successView, String formView) {
		super(commandClass, commandName, successView, formView);
	}
}

