/**
 * 
 */
package eu.ohim.sp.integration.adapter.openam.authentication.filter;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import eu.ohim.sp.integration.adapter.openam.authentication.OpenAMAuthenticationEntryPoint;
import eu.ohim.sp.integration.adapter.openam.authentication.exception.OpenAMAuthenticationException;
import eu.ohim.sp.integration.adapter.openam.authentication.util.PropertyUtil;
import eu.ohim.sp.integration.adapter.openam.authentication.util.TicketUtils;

/**
 * @author simoean
 * 
 */
public class OpenAMAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Value("${openam.adapter.validate.ticket.artifact.name}")
    String artifactName;

    @Autowired
    OpenAMAuthenticationEntryPoint entryPoint;

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        entryPoint.commence(request, response, new OpenAMAuthenticationException(failed.getMessage()));
    }

    protected OpenAMAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * this method has to be moved to CLIENT
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);

    }

    /**
     * Gets parameter from request and set username with its value, password set to empty string.
     * Creates a new Atentication object with both values and passed it to AuthenticationManager
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        // obtain token from request
        String username = TicketUtils.obtainCookieArtifact(request, artifactName);
        String password = "";

        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                password);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Looks for parameter 'openid' in request
     */
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String cookieValue = TicketUtils.obtainCookieArtifact(request, artifactName);
        if (StringUtils.hasText(cookieValue) && SecurityContextHolder.getContext().getAuthentication() == null) {
            return true;
        }
        return false;
    }
}
