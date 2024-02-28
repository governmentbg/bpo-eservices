package eu.ohim.sp.common.ui.fileupload.exception;

public class ExifToolException  extends Exception {

    public ExifToolException() {
        super();
    }

    public ExifToolException(String message) {
        super(message);
    }

    public ExifToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExifToolException(Throwable cause) {
        super(cause);
    }

    protected ExifToolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
