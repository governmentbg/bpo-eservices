package eu.ohim.sp.integration.adapter.openam.authentication.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.client.RestTemplate;

public class TicketUtils {

    /**
     * If present, gets the artifact (ticket) from the {@link HttpServletRequest}.
     * 
     * @param request
     * @return if present the artifact from the {@link HttpServletRequest}, else null
     */
    public static String obtainArtifact(HttpServletRequest request, String artifactName) {
        return request.getParameter(artifactName);
    }

    /**
     * If present, gets the artifact (ticket) from the {@link HttpServletRequest}.
     * 
     * @param request
     * @return if present the artifact from the {@link HttpServletRequest}, else null
     */
    public static String obtainCookieArtifact(HttpServletRequest request, String artifactName) {
        String artifact = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (artifactName.equals(cookie.getName())) {
                    artifact = cookie.getValue();
                }
            }
        }
        return artifact;
    }

    /**
     * Validate a ticket against an external service.
     * 
     * @param ticket to validate
     * @return true if the ticket is valid, else false
     */
    public static boolean validateTicket(String artifact, String validateTicketEndpoint, String artifactName) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(validateTicketEndpoint + "?" + artifactName + "=" + artifact,
                    String.class);
            if ("true".equals(result)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Invalidate an existing ticket on external service.
     * 
     * @param ticket to invalidate
     * @return true if session invalidated, false if not
     */
    public static boolean invalidateTicket(String artifact, String logoutTicketEndpoint, String artifactName) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(logoutTicketEndpoint + "?" + artifactName + "=" + artifact,
                String.class);
        if ("true".equals(result)) {
            return true;
        } else {
            return false;
        }
    }

}
