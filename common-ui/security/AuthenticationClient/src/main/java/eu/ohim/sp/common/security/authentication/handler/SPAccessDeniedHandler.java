package eu.ohim.sp.common.security.authentication.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Class implementing the delegated access denied handler for the Software
 * Package system.
 * <p>
 * Basically the {@link #handle(HttpServletRequest, HttpServletResponse, AccessDeniedException)} method will call the
 * specific access denied strategy defined by the client's authentication provider.
 * 
 * @see LogoutSuccessHandler
 * @author simonjo
 * @since 1.0.0
 * 
 */


public class SPAccessDeniedHandler implements AccessDeniedHandler, ApplicationContextAware {
	
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	@Value("${authentication.client.delegate.access.denied.handler.enabled}")
	String delegateAccessDeniedHandlerEnabledStr;

	ApplicationContext applicationContext = null;

	private static final String SESSION_EXPIRED = "sessionExpired";
	private static final String ERROR403 = "SPRING_SECURITY_403_EXCEPTION";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		Boolean delegateAccessDeniedHandlerEnabled= new Boolean(delegateAccessDeniedHandlerEnabledStr);
		logger.debug("handle ::delegateAccessDeniedHandlerEnabled ["+delegateAccessDeniedHandlerEnabled+"]");
		
		if (delegateAccessDeniedHandlerEnabled) {
			AccessDeniedHandler spDelegateAccessDeniedHandler = applicationContext.getBean(
					"spDelegateAccessDeniedHandler", AccessDeniedHandler.class);
			spDelegateAccessDeniedHandler.handle(request, response, accessDeniedException);
		}
		else {
			logger.debug("handle :: setting ACCESS_DENIED_403");
			request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

			if(request.getAttribute(SESSION_EXPIRED)=="true" ||
			   (request.getAttribute(ERROR403)!=null &&
			   request.getAttribute(ERROR403) instanceof Exception &&
			   (((Exception)request.getAttribute(ERROR403)).getMessage().toLowerCase().contains("csrf token not found") ||
			   ((Exception)request.getAttribute(ERROR403)).getMessage().toLowerCase().contains("invalid csrf token"))))
			{
				request.setAttribute("session.timeout.url",
						System.getProperty(((HttpServletRequest) request).getContextPath().replace("/","") + ".session.expire.url")!=null?
						System.getProperty(((HttpServletRequest) request).getContextPath().replace("/","") + ".session.expire.url"):
						((HttpServletRequest) request).getContextPath());

				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Session Expired!");
			} else {
				// Send 403 to client.
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
