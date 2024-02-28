/*******************************************************************************
 * * $Id:: SPException.java 51515 2012-11-22 16:28:52Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common;

import eu.ohim.sp.common.ui.exception.SystemError;

/**
 * Exception that all the exceptions that are thrown on unexpected error
 * should extend. It has a private field for holding the error code
 * and a method to return error code.
 */
public class SPException extends RuntimeException {

    /** Serialization version control. */
    private static final long serialVersionUID = 2L;

    private String errorCode;

    private String args;

    /**
     * @return the args
     */
    public String getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(String args) {
        this.args = args;
    }

    /**
     * Constructor for EfilingException
     * 
     */
    public SPException() {
        super();
    }

    /**
     * Constructor for EfilingException
     * 
     * @param message message of exception
     */
    public SPException(String message) {
        super(message);
    }

    /**
     * Constructor for EfilingException
     * 
     * @param cause exception that caused it
     */
    public SPException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor for EfilingException
     * 
     * @param message message of exception
     * @param cause exception that caused it
     */
    public SPException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for EfilingException
     * 
     * @param message message of exception
     * @param errorCode error code
     */
    public SPException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * constructor thats used for setting the error code
     * 
     * @param message message of exception
     * @param cause exception that caused it
     * @param errorCode error code
     */
    public SPException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Creates an exception containing a system error
     * @param cause
     * @param systemError
     */
    public SPException(Throwable cause, SystemError systemError) {
    	this(systemError.getErrorMessage(), cause, systemError.getErrorCode());
    }
    
    /**
     * Creates an exception containing a system error
     * @param cause
     * @param systemError
     */
    public SPException(Throwable cause, SystemError systemError, String customDescription) {
    	this(customDescription, cause, systemError.getErrorCode());
    }
    
    /**
     * Method that gets ErrorCode
     * 
     * @return the ErrorCode
     * 
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Method that sets ErrorCode
     * 
     * @param code
     * 
     */
    public void setErrorCode(String code) {
        this.errorCode = code;
    }
}
