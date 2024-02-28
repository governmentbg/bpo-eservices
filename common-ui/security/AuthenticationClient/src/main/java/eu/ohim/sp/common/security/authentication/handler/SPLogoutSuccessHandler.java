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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Class implementing the delegated logout handler for the Software Package
 * system.
 * <p>
 * Basically the {@link #onLogoutSuccess(HttpServletRequest, HttpServletResponse, Authentication)} method will call the
 * specific logout success strategy defined by the client's authentication provider.
 * 
 * @see LogoutSuccessHandler
 * @author OHIM
 * @since 1.0.0
 * 
 */

public class SPLogoutSuccessHandler implements LogoutSuccessHandler, ApplicationContextAware {
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
    @Value("${authentication.client.delegate.logout.success.handler.enabled}")
    String delegateLogoutSucessEnabledStr;
    
    ApplicationContext applicationContext;
    /*
     * (non-Javadoc)
     * @see
     * org.springframework.security.web.authentication.logout.LogoutSuccessHandler
     * #onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
    	logger.debug("onLogoutSuccess :: START ");
        
    	Boolean delegateLogoutSucessEnabled =  new Boolean(delegateLogoutSucessEnabledStr);
    	logger.debug("onLogoutSuccess ::delegateLogoutSucessEnabled ["+delegateLogoutSucessEnabled+"]");
    	
    	if (delegateLogoutSucessEnabled) {
            LogoutSuccessHandler spDelegateLogoutSuccessHandler = applicationContext.getBean(
                    "spDelegateLogoutSuccessHandler", LogoutSuccessHandler.class);
            spDelegateLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);
        }
    	logger.debug("onLogoutSuccess :: END ");
    }
    
    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
