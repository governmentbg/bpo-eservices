/**
 * 
 */
package eu.ohim.sp.integration.adapter.openam.authentication;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import eu.ohim.sp.integration.adapter.openam.authentication.exception.OpenAMAuthenticationException;
import eu.ohim.sp.integration.adapter.openam.authentication.util.PropertyUtil;

/**
 * @author simoean
 * 
 */
public class OpenAMAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Value("${openam.adapter.login.url}")
    public String loginPageURL; // DB
	@Value("${openam.adapter.target.url}")
	public String targetURL;// DB

    /**
     * Redirects to OPENAM login page
     * 
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // Redirect to OpenAM login page
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(loginPageURL + "?goto=" + URLEncoder.encode(targetURL, "UTF-8"));

    }

}
