package eu.ohim.sp.core.application.provided;

import eu.ohim.sp.core.application.ApplicationService;
import eu.ohim.sp.core.domain.common.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
@Path("/")
public class ApplicationResourceProvider implements ApplicationResource {
    private static final Logger LOGGER = Logger.getLogger(ApplicationResourceProvider.class);
    public static final String FILE_EXTENSION_ZIP = ".zip";

    InitialContext ic;

    protected ApplicationService getService() throws NamingException {
        if(ic == null)
            ic = new InitialContext();
        return (ApplicationService)ic.lookup("java:global/defaultApplicationLocal");
    }

    /*
     * (non-Javadoc)
     * @see
     * .ohim.fsp.external.application.provider.ApplicationResource#getApplication
     * (String)
     */
    @Override
    @GET
    @Produces("application/epub+zip")
    @Path("/{filingnumber}")
    public Response getApplication(@PathParam("filingnumber") String filingNumber) {
        ResponseBuilder responseBuilder = null;

        LOGGER.info("Retrieving application: " + filingNumber);
        try {
            byte[] application = getService().getApplication(null, null, null, filingNumber);
            if (application != null) {
                responseBuilder = Response.ok(application).status(Status.OK)
                        .header("Content-Disposition", "attachment; filename=" + filingNumber + FILE_EXTENSION_ZIP);
            }
            else {
                responseBuilder = Response.status(Status.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve application", e);
            responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);
        }

        return responseBuilder.build();
    }

    @Override
    @POST
    @Path("/{filingnumber}")
    public Response updateApplicationStatus(@PathParam("filingnumber") String filingNumber,
                                                  @FormParam("statuscode") String statusCode,
                                                  @FormParam("statusdescription") String statusDescription) {
        List<String>  validStatuses = new ArrayList<>(Arrays.asList("received", "error"));
        ResponseBuilder responseBuilder = null;

        LOGGER.info("Updating status of application: " + filingNumber + " / " + statusCode);
        try {
            if (StringUtils.isEmpty(statusCode) || !validStatuses.contains(statusCode.toLowerCase())) {
                responseBuilder = Response.status(Status.BAD_REQUEST);
            } else {
                Result result = getService().updateDraftApplicationStatus(filingNumber, statusCode,
                        statusDescription);
                LOGGER.info("result.getResult(): " + result.getResult().toString());
                switch (result.getResult()) {
                    case Result.NOTFOUND:
                        responseBuilder = Response.status(Status.NOT_FOUND);
                        break;
                    case Result.FAILURE:
                    case Result.SERVERERROR:
                        responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);
                        break;
                    default:
                        responseBuilder = Response.status(Status.ACCEPTED);
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to update application status", e);
            responseBuilder = Response.status(Status.INTERNAL_SERVER_ERROR);
        }

        return responseBuilder.build();
    }
}
