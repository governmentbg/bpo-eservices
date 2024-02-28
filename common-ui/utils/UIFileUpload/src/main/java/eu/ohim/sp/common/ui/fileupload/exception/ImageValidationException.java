/*******************************************************************************
 * * $Id:: ImageValidationException.java 113489 2013-04-22 14:59:26Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload.exception;

import eu.ohim.sp.common.SPException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author ionitdi
 */
public class ImageValidationException extends ValidationException
{


    /** Serialization version control. */
    private static final long serialVersionUID = 2L;

    /**
     * Constructor for ImageValidationException
     *
     */
    public ImageValidationException() {
        super();
    }

    /**
     * Constructor for ImageValidationException
     * @param arg0 message for exception
     */
    public ImageValidationException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor for ImageValidationException
     * @param arg0 exception that caused it 
     */
    public ImageValidationException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Constructor for ImageValidationException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     */
    public ImageValidationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor for ImageValidationException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     * @param errorCode
     */
    public ImageValidationException(String arg0, Throwable arg1, String errorCode) {
        super(arg0, arg1, errorCode);
    }
}
