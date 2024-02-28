package eu.ohim.sp.common.ui.util;

import org.springframework.security.core.context.SecurityContextHolder;
import eu.ohim.sp.common.security.authorisation.domain.SPUser;

public class AuthenticationUtil {

	public static SPUser getAuthenticatedUser() {
		SPUser userDetails = (SecurityContextHolder.getContext().getAuthentication() != null && 
    			SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null &&
    			SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof SPUser ?
    			((SPUser) SecurityContextHolder
	            .getContext().getAuthentication().getPrincipal()) :
	            	null);

    	return userDetails;
	}
}
