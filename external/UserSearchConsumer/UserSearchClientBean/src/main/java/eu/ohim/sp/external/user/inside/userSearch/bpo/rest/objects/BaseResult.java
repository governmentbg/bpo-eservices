package eu.ohim.sp.external.user.inside.userSearch.bpo.rest.objects;

public class BaseResult {
    private String exception;
    private String message;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
