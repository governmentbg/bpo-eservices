/*******************************************************************************
 * * $Id:: MaximumEntitiesException.java 49260 2012-10-29 13:02:02Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.exception;

import eu.ohim.sp.common.SPException;

public class MaximumEntitiesException extends SPException {
    
    /** Serialization version control. */
    private static final long serialVersionUID = 2L;   
    
    private String argument;
    
    /**
	 * Method that returns the argument
	 * @return the argument
	 */
	public String getArgument() {
		return argument;
	}

	/**
	 * Method that sets the argument
	 * @param argument the argument to set
	 */
	public void setArgument(String argument) {
		this.argument = argument;
	}

	/**
     * Constructor for MaximumEntitiesException
     *
     */
    public MaximumEntitiesException() {
        super();
    }
    
    /**
     * Constructor for MaximumEntitiesException
     * @param arg0 message for exception
     */
    public MaximumEntitiesException(String arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for MaximumEntitiesException
     * @param arg0 exception that caused it 
     */
    public MaximumEntitiesException(Throwable arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for MaximumEntitiesException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     */
    public MaximumEntitiesException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor for MaximumEntitiesException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     * @param errorCode
     */
    public MaximumEntitiesException(String arg0, Throwable arg1, String errorCode, String argument) {
        super(arg0, arg1, errorCode);
        this.argument = argument;
    }
}
