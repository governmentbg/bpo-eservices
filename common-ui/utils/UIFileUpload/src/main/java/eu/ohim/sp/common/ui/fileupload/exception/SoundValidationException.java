package eu.ohim.sp.common.ui.fileupload.exception;

import eu.ohim.sp.common.SPException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author ionitdi
 */
public class SoundValidationException extends ValidationException
{

    /** Serialization version control. */
    private static final long serialVersionUID = 2L;

    /**
     * Constructor for SoundValidationException
     *
     */
    public SoundValidationException() {
        super();
    }

    /**
     * Constructor for SoundValidationException
     * @param arg0 message for exception
     */
    public SoundValidationException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor for SoundValidationException
     * @param arg0 exception that caused it 
     */
    public SoundValidationException(Throwable arg0) {
        super(arg0);
    }

    /**
     * Constructor for SoundValidationException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     */
    public SoundValidationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor for SoundValidationException
     * @param arg0 message for exception
     * @param arg1 exception that caused it 
     * @param errorCode
     */
    public SoundValidationException(String arg0, Throwable arg1, String errorCode) {
        super(arg0, arg1, errorCode);
    }
}
