/**
 * 
 */
package eu.ohim.sp.common.security.authorisation.token;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author simoean
 * 
 */
public class SPAuthenticationToken implements Authentication {

   

	private static final long serialVersionUID = 7346145207869701737L;
    private final String name;
    private final Collection<GrantedAuthority> authorities;
    private final Object credentials;
    private final Object details;
    private final Object principal;
    private boolean authenticated;

    @Override
    public String getName() {
        return this.name;
    }

    public SPAuthenticationToken(String name, Object credentials, Object details, Object principal,
            Collection<GrantedAuthority> authorities, boolean authenticated) {
        super();
        this.name = name;
        this.authorities = authorities;
        this.credentials = credentials;
        this.details = details;
        this.principal = principal;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;

    }
    
    @Override
   	public String toString() {
   		return String
   				.format("SPAuthenticationToken [name=%s, authorities=%s, credentials=%s, details=%s, principal=%s, authenticated=%s]",
   						name, authorities, credentials, details, principal,
   						authenticated);
   	}

}
