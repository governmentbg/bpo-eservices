package eu.ohim.sp.integration.adapter.mockticket.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

import eu.ohim.sp.integration.adapter.mockticket.authentication.exception.MockAuthenticationException;
import eu.ohim.sp.integration.adapter.mockticket.authentication.util.ExternalUserDetails;
import eu.ohim.sp.integration.adapter.mockticket.authentication.util.PropertyUtil;
import eu.ohim.sp.integration.adapter.mockticket.authorisation.token.MockAuthenticationToken;

/**
 * @author simonjo
 * 
 */
public class MockAuthenticationProvider implements AuthenticationProvider {
	
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private Properties usersProp;

	/**
	 * Validates token against external service and get user details
	 * Creates a new user with user/groups on authorities.
	 * 
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logger.info("MockAuthenticationProvider :: authenticate ::START");
    	  
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String ticketValue = (String) token.getPrincipal();

		logger.info("MockAuthenticationProvider :: authenticate ::  retrieved ticketValue ["+ticketValue +"]");

		if (validateTicket(ticketValue)) {
			logger.info("MockAuthenticationProvider :: authenticate ::  retrieved ticketValue  .. preparing MockAuthenticationToken");

			ExternalUserDetails externalUser = getUserFromRemoteService(ticketValue);
			Collection<String> externalUserAndGroups = externalUser.getAuthorities();

			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			if (externalUserAndGroups!=null){
				for (String authority : externalUserAndGroups) {
					authorities.add(new SimpleGrantedAuthority(authority));
				}
			}
			// CREATE A NEW USER WITH ROLES AND GROUPS ON AUTHORITIES
			UserDetails user = new User(externalUser.getUsername(), "", true, true, true, true, authorities);
			
			logger.info("MockAuthenticationProvider :: authenticate :: END");
			return new MockAuthenticationToken(externalUser.getUsername(), authorities, token.getCredentials(),
					token.getDetails(), user, true);

		} else {
			throw new MockAuthenticationException("Error: " + this.getClass().getName()
					+ "An error has ocurred while validating against openam");
		}
		  	
	}



	/**
	 * Validates a ticket against external service.
	 * 
	 * @param ticket to validate
	 * @return true if the ticket is valid, else false
	 */
	public static boolean validateTicket(String ticketValue) {
		return StringUtils.hasText(ticketValue); //TO IMPROVE ->CHECK THET EXISTS IN THE FILE
	}

	/**
	 * Returns a user details from the external service.
	 * 
	 * @param ticket to retrieve user details
	 * @return if validates returns a user
	 * @throws BadCredentialsException in case the token is not validated
	 */
	private ExternalUserDetails getUserFromRemoteService(String ticketValue) {
		logger.info("MockAuthenticationProvider ::  getUserFromRemoteService :: START :: FOR TICKET ["+ticketValue+"]");
		ExternalUserDetails externalUser = new ExternalUserDetails();

		//SEARCH IN THE FILE with the ticket as username
		String userValue = usersProp.getProperty(ticketValue);
		
		externalUser.setUsername(ticketValue);

		if (StringUtils.hasText(userValue)){
			externalUser.setAuthorities(Arrays.asList(userValue.toString().split(",")));
		}

		logger.info("MockAuthenticationProvider ::  getUserFromRemoteService :: END :: externl user ["+externalUser+"]");
		return externalUser;
	}

	@PostConstruct
    private void initialize() {
        try {
        	//this property file must be set in the webapp
        	usersProp = PropertyUtil.readProperties(PropertyUtil.MOCK_USERS_PROPERTIES_NAME);
        
        } catch (IOException e) {
            throw new MockAuthenticationException("An error has ocurred while validating token against mock");
        }
    }

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication == UsernamePasswordAuthenticationToken.class
				|| authentication == PreAuthenticatedAuthenticationToken.class;
	}

}
