
package eu.ohim.sp.integration.adapter.mockticket.authentication.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

import eu.ohim.sp.integration.adapter.mockticket.authentication.MockAuthenticationEntryPoint;
import eu.ohim.sp.integration.adapter.mockticket.authentication.exception.MockAuthenticationException;
import eu.ohim.sp.integration.adapter.mockticket.authentication.util.TicketUtils;

/**
 * @author simonjo
 * 
 */
public class MockAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


	@Value("${mock.adapter.ticket.name}")
	String ticketName; //local cookie contining ticket ie CAS ticket grantingtiicket

	@Autowired
	MockAuthenticationEntryPoint entryPoint;

	protected MockAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}


	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		logger.debug("unsuccessfulAuthentication :: START ");
		entryPoint.commence(request, response, new MockAuthenticationException(failed.getMessage()));
		logger.debug("unsuccessfulAuthentication :: END ");
	}


	/**
	 * this method has to be moved to CLIENT
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		logger.debug("successfulAuthentication :: START ");

		final String ticketValue = UUID.randomUUID().toString();
		setCookie(response,ticketValue);
		logger.debug("successfulAuthentication :: ADDED COOKIE TO RESPONSE ");

		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
		logger.debug("successfulAuthentication :: END ");

	}

	/**
	 * Gets parameter from request and set username with its value, password set to empty string.
	 * Creates a new Atentication object with both values and passed it to AuthenticationManager
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		logger.debug("attemptAuthentication :: START ");
		Authentication out= null;

		// obtain token from request
		String username = TicketUtils.obtainArtifact(request, ticketName);
		String password = "";

		final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
				password);

		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		
		out = this.getAuthenticationManager().authenticate(authRequest);
		logger.debug("attemptAuthentication :: END out ["+out+"]");
		return out;

	}

	/**
	 * Looks for parameter 'openid' in request
	 */
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("attemptAuthentication :: START ");	

		boolean out=false; 
		String cookieValue = TicketUtils.obtainArtifact(request, ticketName);
		if (StringUtils.hasText(cookieValue)) { // && SecurityContextHolder.getContext().getAuthentication() == null) {
			return true;
		}
		logger.debug("attemptAuthentication :: END out ["+out+"]");
		return out;
	} 


	private void setCookie(HttpServletResponse response, String ticketValue) {
		Cookie cookie = new Cookie(ticketName, ticketValue);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

}
