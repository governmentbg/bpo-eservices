package eu.ohim.sp.core.report;

import javax.ejb.Remote;

/**
 * Remote decoration of the {@link ReportService} support interface.
 * 
 * @see ReportService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Remote
public interface ReportServiceRemote extends ReportService {
}
