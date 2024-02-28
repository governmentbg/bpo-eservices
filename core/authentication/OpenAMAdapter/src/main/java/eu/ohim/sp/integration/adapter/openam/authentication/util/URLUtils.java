package eu.ohim.sp.integration.adapter.openam.authentication.util;

import javax.servlet.http.HttpServletRequest;

public class URLUtils {

    /**
     * Clean url removing artifact if present
     * 
     * @param url requested
     * @return url requested with artifact removed
     */
    public static String removeArtifactfromURL(String url, String artifactName) {

        if (url == null) {
            return null;
        }

        int artifactIndex = url.indexOf("?" + artifactName);
        if (artifactIndex != -1) {
            return url.substring(0, artifactIndex);
        }

        artifactIndex = url.indexOf("&" + artifactName);
        if (artifactIndex != -1) {
            return url.substring(0, artifactIndex);
        }

        artifactIndex = url.indexOf(artifactName);
        if (artifactIndex != -1) {
            return url.substring(0, artifactIndex);
        }

        return url;
    }

    /**
     * Clean url removing tickets and parameters
     * 
     * @param url
     * @return url with artifact removed
     */
    public static String prepareUrl(HttpServletRequest request, String artifactNameParam) {

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String queryString = request.getQueryString();

        requestURI = requestURI.substring(contextPath.length());
        queryString = URLUtils.removeArtifactfromURL(queryString, artifactNameParam);

        if (queryString != null && !queryString.equals("")) {
            requestURI += "?" + queryString;
        }

        return requestURI;
    }

}
