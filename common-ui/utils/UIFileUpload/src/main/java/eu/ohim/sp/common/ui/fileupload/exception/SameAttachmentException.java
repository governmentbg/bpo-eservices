/*******************************************************************************
 * * $Id:: SameAttachmentException.java 113489 2013-04-22 14:59:26Z karalch      $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload.exception;

import eu.ohim.sp.common.SPException;

public class SameAttachmentException extends SPException {
    
    /** Serialization version control. */
    private static final long serialVersionUID = 2L;   
    
    /**
     * Constructor for SameAttachmentException
     *
     */
    public SameAttachmentException() {
        super();
    }
    
    /**
     * Constructor for SameAttachmentException
     * @param arg0 message for exception
     */
    public SameAttachmentException(String arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for SameAttachmentException
     * @param arg0 exception that caused it 
     */
    public SameAttachmentException(Throwable arg0) {
        super(arg0);
    }
    
    /**
     * Constructor for SameAttachmentException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     */
    public SameAttachmentException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor for SameAttachmentException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     * @param errorCode
     */
    public SameAttachmentException(String arg0, Throwable arg1, String errorCode) {
        super(arg0, arg1, errorCode);
    }
}
