package eu.ohim.sp.common.security.authentication.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Filter that delegates the Single Sign Out protocol to the client's
 * authentication provider.
 * <p>
 * 
 * @author OHIM
 * @since 1.0.0
 * 
 */
public final class SPSingleSignOutFilter extends GenericFilterBean implements ApplicationContextAware {

    ApplicationContext applicationContext = null;

    @Autowired
    @Value("${authentication.client.sso.enabled}")
    private String strSsoEnabled;

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain) We act as a
     * FilterChain itself so here we only cut the normal FilterChain, and we put
     * ourself in the middle.
     */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {

        Boolean ssoEnabled = new Boolean(strSsoEnabled);

        // if sso is enabled, sign out on external system
        if (ssoEnabled) {
            GenericFilterBean spDelegateSingleSignOutFilter = applicationContext.getBean(
                    "spDelegateSingleSignOutFilter", GenericFilterBean.class);
            spDelegateSingleSignOutFilter.doFilter(servletRequest, servletResponse, filterChain);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
