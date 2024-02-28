package eu.ohim.sp.dsefiling.ui.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author serrajo
 *
 */
public class ErrorHandlerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Nothing to do.

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;

	    if (!(response instanceof ErrorAwareRequestWrapper)) {
	    	response = new ErrorAwareRequestWrapper(response);
	    }

	    chain.doFilter(servletRequest, response);

	}

	@Override
	public void destroy() {
		// Nothing to do.
	}

}
