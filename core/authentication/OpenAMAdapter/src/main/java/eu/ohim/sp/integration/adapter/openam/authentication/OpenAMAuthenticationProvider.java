/**
 * 
 */
package eu.ohim.sp.integration.adapter.openam.authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;

import eu.ohim.sp.integration.adapter.openam.authentication.exception.OpenAMAuthenticationException;
import eu.ohim.sp.integration.adapter.openam.authentication.util.ExternalUserDetails;
import eu.ohim.sp.integration.adapter.openam.authentication.util.PropertyUtil;
import eu.ohim.sp.integration.adapter.openam.authorisation.token.OpenAmAuthenticationToken;

/**
 * @author simoean
 * 
 */
public class OpenAMAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = Logger.getLogger(OpenAMAuthenticationProvider.class);

	@Value("${openam.adapter.validate.ticket.endpoint}")
    String validateTicketEndpoint;
	@Value("${openam.adapter.user.details.endpoint}")
    String userDetailsEndpoint;
	@Value("${openam.adapter.validate.ticket.parameter.name}")
    String tokenParameterName;
	@Value("${openam.adapter.validate.ticket.subject.parameter.name}")
    String subjectParameterName;

    /**
     * Validates token against external service and get user details
     * Creates a new user with user/groups on authorities.
     * 
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Validate Token
        // http://openam.forgerock.org/openam-documentation/openam-doc-source/doc/dev-guide/#rest-api-tokens

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String ticketValue = (String) token.getPrincipal();

        if (validateTicket(ticketValue, validateTicketEndpoint, tokenParameterName)) {
            ExternalUserDetails externalUser = getUserFromRemoteService(ticketValue);
            Collection<String> externalUserAndGroups = externalUser.getAuthorities();

            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            if (externalUserAndGroups != null) {
                for (String authority : externalUserAndGroups) {
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            }

            // CREATE A NEW USER WITH ROLES AND GROUPS ON AUTHORITIES
            UserDetails user = new User(externalUser.getUsername(), "", true, true, true, true, authorities);
            return new OpenAmAuthenticationToken(externalUser.getUsername(), authorities, token.getCredentials(),
                    token.getDetails(), user, true);
        } else {
            throw new OpenAMAuthenticationException("Error: " + this.getClass().getName()
                    + "An error has ocurred while validating against openam");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class
                || authentication == PreAuthenticatedAuthenticationToken.class;
    }

    /**
     * Validates a ticket against external service.
     * 
     * @param artifact the artifact
     * @param validateTicketEndpoint the validate Ticket Endpoint
     * @param artifactName the artifact name
     * @return true if the ticket is valid, else false
     */
    public static boolean validateTicket(String artifact, String validateTicketEndpoint, String artifactName) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(validateTicketEndpoint + "?" + artifactName + "=" + artifact,
                    String.class);
            if (result.contains("true")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns a user details from the external service.
     * 
     * @param ticket to retrieve user details
     * @return if validates returns a user
     * @throws BadCredentialsException in case the token is not validated
     */
    private ExternalUserDetails getUserFromRemoteService(String ticket) {

        RestTemplate restTemplate = new RestTemplate();
        String result = "";
        try {
            result = restTemplate.getForObject(userDetailsEndpoint + "?" + subjectParameterName + "=" + ticket
                    + "&attributenames=uid", String.class);
        } catch (Exception e) {
            logger.warn(e);
        }

        ExternalUserDetails externalUser = new ExternalUserDetails();

        String usernamePattern = "userdetails.attribute.value=";
        String rolePattern = "userdetails.role=id=";

        if (result.indexOf(usernamePattern) > 0) {
            externalUser.setUsername(result.substring(result.indexOf(usernamePattern) + usernamePattern.length()).trim());
        }

        StringBuffer rolesAndGroups = new StringBuffer();
        String[] split = result.split(rolePattern);
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("@group_")) {
                rolesAndGroups.append(split[i].substring(0, split[i].indexOf(",") + 1));
            }
            if (split[i].contains("@role_")) {
                rolesAndGroups.append(split[i].substring(0, split[i].indexOf(",") + 1));
            }
        }

        if (!StringUtils.isEmpty(rolesAndGroups.toString())) {
            externalUser.setAuthorities(Arrays.asList(rolesAndGroups.toString().split(",")));
        }

        return externalUser;
    }

}
