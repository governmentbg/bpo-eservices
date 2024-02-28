package bg.duosoft.model;

public class ApplicationCount {
    private String key;
    private Long submittedCount;
    private Long errorsCount;

    public ApplicationCount(String key, Long submittedCount, Long errorsCount) {
        this.key = key;
        this.submittedCount = submittedCount;
        this.errorsCount = errorsCount;
    }

    public String getKey() {
        return key;
    }

    public Long getSubmittedCount() {
        return submittedCount;
    }

    public Long getErrorsCount() {
        return errorsCount;
    }
}
