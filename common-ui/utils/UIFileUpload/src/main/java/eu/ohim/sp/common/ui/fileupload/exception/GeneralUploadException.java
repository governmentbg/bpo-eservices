/*******************************************************************************
 * * $Id:: GeneralUploadException.java 113489 2013-04-22 14:59:26Z karalch       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload.exception;

import eu.ohim.sp.common.SPException;

public class GeneralUploadException extends SPException {
    /** Serialization version control. */
    private static final long serialVersionUID = 2L;   
    
    /**
     * Constructor for GeneralUploadException
     *
     */
    public GeneralUploadException() {
        super();
    }
    
    /**
     * Constructor for GeneralUploadException
     * @param arg0 message for exception
     */
    public GeneralUploadException(String arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for GeneralUploadException
     * @param arg0 exception that caused it 
     */
    public GeneralUploadException(Throwable arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for GeneralUploadException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     */
    public GeneralUploadException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor for GeneralUploadException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     * @param errorCode
     */
    public GeneralUploadException(String arg0, Throwable arg1, String errorCode) {
        super(arg0, arg1, errorCode);
    }
}
