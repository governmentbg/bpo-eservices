/*******************************************************************************
 * * $Id:: SpFlowExecutionExceptionHandler.java 113489 2013-04-22 14:59:26Z kar#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.support.DefaultTargetStateResolver;
import org.springframework.webflow.execution.FlowExecutionException;

import eu.ohim.sp.common.SPException;

public class SpFlowExecutionExceptionHandler implements FlowExecutionExceptionHandler {

	@Override
	public boolean canHandle(FlowExecutionException exception) {
		if (exception!=null 
				&& exception.getCause() instanceof SPException && exception.getStateId() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void handle(FlowExecutionException exception,
			RequestControlContext context) {
		//Set a flashscope message and go back to the last state
		SPException spException = (SPException) exception.getCause();
		if (spException.getErrorCode()!=null) {
			context.getFlashScope().put("error_code", spException.getErrorCode());
		}
		String state = exception.getStateId();
		if(state.equalsIgnoreCase("submitted")){
			state = "failureState";
		}
		context.execute(new Transition(new DefaultTargetStateResolver(state)));
	}

}
