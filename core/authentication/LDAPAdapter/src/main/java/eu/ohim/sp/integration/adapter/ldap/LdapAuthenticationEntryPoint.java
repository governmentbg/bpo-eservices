package eu.ohim.sp.integration.adapter.ldap;

import eu.ohim.sp.integration.adapter.util.LoginPageRenderer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * LdapAuthenticationEntryPoint - redirects unauthorized users to a login form
 *
 * If property ${ldap.login.page} is provided, it simply performs a redirection,
 * otherwise, it constructs a default simple login form
 *
 * @author simoean
 */
public class LdapAuthenticationEntryPoint implements AuthenticationEntryPoint {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${ldap.login.page.url}")
    private String loginPageUrl;

    /**
     * Redirects to login page
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        logger.info("commence :: START");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter pw = response.getWriter();

        if (StringUtils.isBlank(loginPageUrl)) {
            logger.info("commence :: Default login page");

            LoginPageRenderer.render(pw, request.getRequestURI(), request.getParameterMap());

            response.setContentType("text/html");
            response.flushBuffer();
        }
        else {
            logger.info("commence :: Custom login page");
            response.sendRedirect(response.encodeRedirectURL(loginPageUrl));
        }

        pw.close();
        logger.info("commence :: END");
    }

}
