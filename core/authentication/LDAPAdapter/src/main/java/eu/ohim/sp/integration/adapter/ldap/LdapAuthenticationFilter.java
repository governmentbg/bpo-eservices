package eu.ohim.sp.integration.adapter.ldap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LdapAuthenticationFilter - retrieves credentials from request and
 * validate it against an LDAP server.
 *
 * Also, may implement custom behaviour on authentication events.
 *
 * @author simoean
 */
public class LdapAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${ldap.login.field.username}")
    private String usernameField;

    @Value("${ldap.login.field.password}")
    private String passwordField;

    protected LdapAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        logger.info("requiresAuthentication :: START");

        String username = request.getParameter(usernameField);
        String password = request.getParameter(passwordField);
        logger.debug("requiresAuthentication :: User/Password - "+username+"/"+password);

        boolean result = (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password));
        //result = (result && super.requiresAuthentication(request, response));
        logger.debug("requiresAuthentication :: Result - "+result);

        logger.info("requiresAuthentication :: END");
        return result;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        logger.info("attemptAuthentication :: START");

        String username = httpServletRequest.getParameter(usernameField);
        String password = httpServletRequest.getParameter(passwordField);

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
        logger.debug("attemptAuthentication :: Token          - "+token);

        Authentication authentication = this.getAuthenticationManager().authenticate(token);
        logger.debug("attemptAuthentication :: Authentication - "+authentication);

        logger.info("attemptAuthentication :: END");
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("successfulAuthentication :: START");

        SecurityContextHolder.getContext().setAuthentication(authResult);

        logger.info("successfulAuthentication :: END");
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.info("unsuccessfulAuthentication :: START");
        super.unsuccessfulAuthentication(request, response, failed);
        logger.info("unsuccessfulAuthentication :: END");
    }

}
