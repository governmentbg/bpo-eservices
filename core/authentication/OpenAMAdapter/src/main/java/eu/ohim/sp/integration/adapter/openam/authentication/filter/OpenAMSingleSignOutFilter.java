package eu.ohim.sp.integration.adapter.openam.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filter that delegates the Single Sign Out protocol to the client's
 * authentication provider.
 * <p>
 * 
 * @author OHIM
 * @since 1.0.0
 * 
 */
public final class OpenAMSingleSignOutFilter extends GenericFilterBean implements ApplicationContextAware {

    ApplicationContext applicationContext = null;

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

        // Configuration service - authentication.client.ssout.enabled
        // TODO SINGLE SIGN OUT ON REMOTE SERVER

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
