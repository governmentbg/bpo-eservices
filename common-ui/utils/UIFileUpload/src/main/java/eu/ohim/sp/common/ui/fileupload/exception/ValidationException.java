package eu.ohim.sp.common.ui.fileupload.exception;

import org.springframework.web.multipart.MultipartException;

/**
 * @author ionitdi
 */
public class ValidationException extends Exception
{
    public ValidationException()
    {

    }

    public ValidationException(Throwable cause)
    {
        super(cause);
    }

    public ValidationException(String msg)
    {
        super(msg);
    }

    public ValidationException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public ValidationException(String msg, Throwable cause, String code)
    {
        super(msg, cause);
        errorCode = code;
    }

    private String errorCode;

    public String getErrorCode()
    {
        return errorCode;
    }
}
