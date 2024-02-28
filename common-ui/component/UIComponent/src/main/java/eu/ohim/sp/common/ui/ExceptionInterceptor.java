/*******************************************************************************
 * * $Id:: ExceptionInterceptor.java 49264 2012-10-29 13:23:34Z karalch          $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import eu.ohim.sp.common.SPException;

@Aspect
public class ExceptionInterceptor {

    /**
     * Logger for this class and subclasses
     */
    private static final Logger logger = Logger.getLogger(ExceptionInterceptor.class);
	
	@AfterThrowing(pointcut="execution(* eu.ohim.sp.*.ui.service.*.*(..))", throwing="e")
	public void handleExceptionService(JoinPoint joinPoint, Throwable e) {
		logger.error("Failed to call the service", e);
		if(e instanceof SPException) {
			throw (SPException) e;
		} else {
			//Try to get the error code looping the error causes until we get an SPException object
			Throwable causeException = e.getCause();
			while(causeException != null) {				
				if (causeException instanceof SPException) {
					throw (SPException) causeException;
				}
				causeException = causeException.getCause();
			} 
			//If there is no SPException send a generic code
			throw new SPException("Failed to call the service", e, "generic.errors.service.fail");
		}
	}
	
}
