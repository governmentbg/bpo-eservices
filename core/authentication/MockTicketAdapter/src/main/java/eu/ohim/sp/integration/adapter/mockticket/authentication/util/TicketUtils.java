package eu.ohim.sp.integration.adapter.mockticket.authentication.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TicketUtils {
	
	/** Logger available to subclasses */
	protected final static  Log logger = LogFactory.getLog(TicketUtils.class.getName());
	

    /**
     * If present, gets the artifact (ticket) from the {@link HttpServletRequest}.
     * 
     * @param request
     * @return if present the artifact from the {@link HttpServletRequest}, else null
     */
    public static String obtainArtifact(HttpServletRequest request, String artifactName) {
    	logger.debug("obtainArtifact :: request paremeter :: START ");
    	
        String out=  request.getParameter(artifactName);
        
        logger.debug("obtainArtifact :: request paremeter END :: OUT ["+out+"]");
        return out;
    }

    /**
     * If present, gets the artifact (ticket) from the {@link HttpServletRequest}.
     * 
     * @param request
     * @return if present the artifact from the {@link HttpServletRequest}, else null
     */
    public static String obtainCookieArtifact(HttpServletRequest request, String artifactName) {
    	logger.debug("obtainArtifact ::  cookie :: START ");
    	
        String out = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (artifactName.equals(cookie.getName())) {
                	out = cookie.getValue();
                }
            }
        }
        
        logger.debug("obtainArtifact :: cookie :: END :: OUT ["+out+"]");
        return out;
    }


}
