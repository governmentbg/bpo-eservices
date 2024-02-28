/*******************************************************************************
 * * $Id:: SPMultipartException.java 49260 2012-10-29 13:02:02Z karalch         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.exception;

import eu.ohim.sp.common.SPException;

public class SPMultipartException extends SPException {

    /**
     * Constructor for EfilingException
     * @param cause exception that caused it 
     */
    public SPMultipartException(Throwable cause) {
        super(cause);
    }
    
    public SPMultipartException(String message, Throwable cause, String errorCode) {
    	super(message, cause, errorCode);
    }
    
    public Throwable getRootCause() {
    	return SPMultipartException.innerGetRootCause(this.getCause());
    }
    
    public static Throwable innerGetRootCause(Throwable cause) {
    	if (cause.getCause()!=null) {
    		return innerGetRootCause(cause.getCause());
    	} else {
    		return cause;
    	}
    	
    }
}
