package eu.ohim.sp.core.report;

/**
 * All exceptions thrown during report generation should be based on ReportException
 */
public class ReportException extends RuntimeException {
    public ReportException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
