package eu.ohim.sp.common.security.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
* SPSessionExpiredFilter
*
* Filter that put sessionExpired cookie on the client
*
* @author albemar
*/
public class SPSessionExpiredFilter implements Filter {

	private Log log = LogFactory.getLog(this.getClass());

    public static final String SESSION_EXPIRED = "sessionExpired";

	public void init(FilterConfig filterConfig) throws ServletException
    {
    }

	public void destroy() {	}

	/**
	 * Set the url redirection for session expired management.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
		log.debug("doFilter :: START ");

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            if(((HttpServletRequest)request).getSession().isNew()) {
				((HttpServletResponse) response).setHeader(SESSION_EXPIRED, "true");
				request.setAttribute(SESSION_EXPIRED, "true");

				//AJAX request (see SPAccessDeniedHandler.java for flow requests)
				Cookie cookie = new Cookie(SESSION_EXPIRED,
						System.getProperty(((HttpServletRequest) request).getContextPath().replace("/", "") + ".session.expire.url") != null ?
								System.getProperty(((HttpServletRequest) request).getContextPath().replace("/", "") + ".session.expire.url") :
								((HttpServletRequest) request).getContextPath());
				((HttpServletResponse) response).addCookie(cookie);
            }
        }

        log.debug("doFilter :: END");
        filterChain.doFilter(request, response);
	}
}