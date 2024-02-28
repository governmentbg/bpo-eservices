package eu.ohim.sp.common.security.authentication.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * Class implementing the delegated logout handler for the Software Package
 * system.
 * <p>
 * Basically the {@link #logout(HttpServletRequest, HttpServletResponse, Authentication)} method will call the specific
 * logout strategy defined by the client's authentication provider, if active. Either way, terminates HTTP session and
 * clears application context
 * 
 * @see LogoutSuccessHandler
 * @author OHIM
 * @author simonjo
 * @since 1.0.0
 * 
 */

public class SPLogoutHandler implements LogoutHandler, ApplicationContextAware {

	   
    ApplicationContext applicationContext = null;
    
    @Autowired
    SecurityContextLogoutHandler spLogoutHandler;

    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.web.authentication.logout.LogoutHandler
     * #logout(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */ 
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        spLogoutHandler.logout(request, response, authentication);

    }
    
    


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
