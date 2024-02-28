package eu.ohim.sp.common.security.filter;

import eu.ohim.sp.common.security.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;



/**
 * SPSessionHijackingFilter
 *
 * Filter in order to prevent session hijacking attack
 *
 * It also sets rowId, userName, remoteAdress as parameter for the Log context
 *
 * @author simonjo
 */

public class SPSessionHijackingFilter implements Filter {

	private Log log = LogFactory.getLog(this.getClass());

	//Do nothing
	public void init(FilterConfig arg0) throws ServletException { 	}
	public void destroy() {	}

	/**
	 * Retrieves userName from request
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		log.debug("doFilter :: START ");
		HttpServletRequest req = (HttpServletRequest) request;

		//Get parameters for logging
		String username = SecurityUtils.getUserName(req);
		String remoteAddress = SecurityUtils.getRemoteAddress(req);
		String sessionAddress = SecurityUtils.getSessionAddress(req);

		log.debug("doFilter :: username ["+username+"] ::  remoteAddress ["+remoteAddress+"] :: sessionAddress ["+sessionAddress+"]");

		registerLogKey(SecurityUtils.ID_KEY,UUID.randomUUID().toString());
		registerLogKey(SecurityUtils.USER_KEY,username);
		registerLogKey(SecurityUtils.IP_KEY, remoteAddress);

		//Check attack
			if (StringUtils.isEmpty(sessionAddress)) { //first filter access, register remoteadress
				req.getSession().setAttribute(SecurityUtils.IP_KEY, remoteAddress);
				log.info("doFilter :: set IP_KEY in session");
			} else {
				//attempt to access to the user session with a different remote address than registered before-> Deny access
				if (!StringUtils.equals(sessionAddress, remoteAddress)) {
					req.getSession().invalidate();
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Potential security problems detected in the request.");
					log.error("SESSION HIJACKING ATTACK! USER: " + username + " - USER IP: " + sessionAddress + " - ATTACKER IP: " + remoteAddress + "\n");
					return;
				}
			}

		log.debug("doFilter :: END");
		fc.doFilter(request, response);
	}

	/**
	 * Register key in the log context
	 *
	 */
	private void registerLogKey(String key, String value) {
		if (value != null && value.trim().length() > 0) {
			MDC.put(key, value);
		} else {
			MDC.put(key, SecurityUtils.UNKNOWN);
		}
	}

}
