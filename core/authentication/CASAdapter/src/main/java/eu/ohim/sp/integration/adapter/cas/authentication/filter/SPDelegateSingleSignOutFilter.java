package eu.ohim.sp.integration.adapter.cas.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
public final class SPDelegateSingleSignOutFilter extends GenericFilterBean {

	@Autowired
	AbstractConfigurationFilter casDelegateSingleSignOutFilter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain) We act as a
	 * FilterChain itself so here we only cut the normal FilterChain, and we put
	 * ourself in the middle.
	 */
	@Override
	public void doFilter(final ServletRequest servletRequest,
			final ServletResponse servletResponse, final FilterChain filterChain)
			throws IOException, ServletException {

		casDelegateSingleSignOutFilter.doFilter(servletRequest,
				servletResponse, filterChain);
	}

}
