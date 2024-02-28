package eu.ohim.sp.common.security.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Filter that allows Anonymous access
 * <p>
 * 
 * @author OHIM
 * @author simonjo
 * @since 1.0.0
 * 
 */

public class SPAnonymousFilter extends GenericFilterBean {
	
	@Autowired
	@Value("${authentication.client.anonymous.access.allowed}")
	String anonymousAcessEnabledStr;
	

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		
		logger.debug("doFilter ::START ");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boolean anonymousAcessEnabled= new Boolean(anonymousAcessEnabledStr);
		logger.debug("doFilter ::anonymousAcessEnabled ["+anonymousAcessEnabled+"]");
		
		if (!anonymousAcessEnabled && (auth instanceof AnonymousAuthenticationToken)) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		logger.debug("doFilter :: END ");
		filterChain.doFilter(servletRequest, servletResponse);

	}

}
