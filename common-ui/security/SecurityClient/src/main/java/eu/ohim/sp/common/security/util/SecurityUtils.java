package eu.ohim.sp.common.security.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    private static Log log = LogFactory.getLog(SecurityUtils.class);

	public static final String USER_KEY = "userName";
	public static final String IP_KEY = "RemoteIP";
	public static final String ID_KEY = "rowID";
	public static final String UNKNOWN = " -- ";

	/**
	 * Retrieves userName from request
	 *
	 */
	public static String getUserName(HttpServletRequest request) {
		String username = "";
		if (request.getUserPrincipal()!=null && !StringUtils.isEmpty(request.getUserPrincipal().getName()))  {
			username = request.getUserPrincipal().getName();
		}
		if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(request.getRemoteUser())) { 
			username = request.getRemoteUser();
		}
		return username;
	}
	
	/**
	 * Retrieves remoteAdress from request header
	 * 
	 * Load balancers store the remote adressip in different header field ie F5 (HTTP_X_FORWARDED_FOR)
	 *
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String remoteAddress = request.getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(remoteAddress)) {
			remoteAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(remoteAddress)) {
			remoteAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(remoteAddress)) {
			remoteAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(remoteAddress)) {
			remoteAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(remoteAddress)) {
			remoteAddress = request.getRemoteAddr();
		}

        log.info("::::::::::::: getRemoteAddress  is :: ["+remoteAddress+"] ");
		return remoteAddress;
	}
	
	/**
	 * Retrieves remoteAdress from user session
	 *
	 */
	public static String getSessionAddress(HttpServletRequest request) {
		String remoteAddress = "";
		if (!StringUtils.isEmpty((String)request.getSession().getAttribute(IP_KEY))) {
			remoteAddress = (String)request.getSession().getAttribute(IP_KEY); 
		}

        log.info("::::::::::::: getSessionAddress  is :: ["+remoteAddress+"] ");
		return remoteAddress;
	}
	
}
