package eu.ohim.sp.ptefiling.ui.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
