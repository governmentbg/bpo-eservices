package eu.ohim.sp.common.security.filter;

import eu.ohim.sp.common.security.esapi.CustomSecurityWrapperRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.esapi.filters.SecurityWrapperRequest;
import org.owasp.esapi.filters.SecurityWrapperResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
* SPCrossSiteScriptingFilter
*
* Filter to prevent cross-site scripting attacks
*
* @author simonjo
*/
public class SPCrossSiteScriptingFilter implements Filter {
    @Value("${security.client.xss.protection.mode}")
    private String securityResponseMode;

    @Value("${security.client.xss.protection.enabled}")
    private String cssFilterEnabled;

    @Value("${security.client.secure.parameter.length}")
    private Integer secureParameterLength;


    private Log log = LogFactory.getLog(this.getClass());

	//Do nothing
	public void init(FilterConfig filterConfig) throws ServletException
    {
      //  SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

	public void destroy() {	}

	/**
	 * Wraps original request with ESAPI's Security Request Wrapper
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        if(!Boolean.parseBoolean(cssFilterEnabled))
        {
            filterChain.doFilter(request, response);
            return;
        }
        if(request instanceof MultipartRequest)
        {
            log.info(" *** Detected multipart request. ***");
            filterChain.doFilter(request, response);
            return;
        }

        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            filterChain.doFilter(request, response);
            return;
        }

		log.debug("doFilter :: START ");

		//Prevent against XSS attack (wrap original request)
        CustomSecurityWrapperRequest secureRequest = new CustomSecurityWrapperRequest((HttpServletRequest) request, secureParameterLength);

        secureRequest.setAllowableContentRoot("/WEB-INF");

        HttpServletResponse secureResponse = new SecurityWrapperResponse((HttpServletResponse) response, securityResponseMode);

        log.debug("doFilter :: END");
        filterChain.doFilter(secureRequest, secureResponse);
	}

    public void setCssFilterEnabled(String cssFilterEnabled)
    {
        this.cssFilterEnabled = cssFilterEnabled;
    }

    public void setSecurityResponseMode(String securityResponseMode)
    {
        this.securityResponseMode = securityResponseMode;
    }

    public void setSecureParameterLength(Integer secureParameterLength) {
        this.secureParameterLength = secureParameterLength;
    }
}