package eu.ohim.sp.core.application.provided;

import javax.ws.rs.core.Response;

/**
 * This class provides the functionality to retrieve IP Applications and update
 * application statuses.
 * 
 * @author villama
 */
public interface ApplicationResource {
	
	/**
	 * Returns a compressed application file (ePUB) from a provided applicationId
	 * @param applicationId the application ID to be retrieved
	 * @return ePub byte array
	 */
	public Response getApplication(String applicationId);

    /**
     * Updates application status in SP FO database
     * @param applicationId the application retrieved
     * @param statusCode the status
     * @param statusDescription the description
     * @return Service response
     */
    public Response updateApplicationStatus(String applicationId, String statusCode, String statusDescription);

}
