package eu.ohim.sp.common.security.authentication.filter;

import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.security.authorisation.token.SPAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Filter that delegates the Single Sign Out protocol to the client's
 * authentication provider.
 * <p>
 * 
 * @author OHIM
 * @author simonjo
 * @since 1.0.0
 * 
 */

public  class SPPermissionFilter extends GenericFilterBean {

	@Autowired
	@Value("${authentication.client.pattern.role}")
	private String rolePattern;

	@Autowired
	@Value("${authentication.client.pattern.group}")
	private String groupPattern;
	
	@Autowired
	@Qualifier("permissionsProperties")
	private Properties permissionsProperties;

	/**
	 * Splits a list of GrantedAuthority from a not fully initialized user into roles and
	 * groups.
	 * <p>
	 * Retrieves permissions for that user based on roles, creates a new fully Authenticated User and updates
	 * SecurityContext with it.
	 * 
	 */
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		
		logger.info("doFilter ::START ");

		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		// Check if needs to load permissions -> if it´s SP auth token don´t ask again
		if (user != null && !(user instanceof SPAuthenticationToken)) {

			try {
				
				Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
				ArrayList<String> roles = new ArrayList<String>();
				ArrayList<String> groups = new ArrayList<String>();
				for (GrantedAuthority authority : authorities) {
					if (authority.getAuthority().contains(rolePattern)) {
						String role = authority.getAuthority();
						roles.add(role);
					} else if (authority.getAuthority().contains(groupPattern)) {
						String group = authority.getAuthority();
						groups.add(group);
					}
				}

				Collection<GrantedAuthority> permissions = getPermissions(roles);
				Authentication authentication;

				if (user instanceof AnonymousAuthenticationToken) {
					String hash = String.valueOf(((AnonymousAuthenticationToken) user).getKeyHash());
					authentication = new AnonymousAuthenticationToken(hash, user.getPrincipal(), permissions);
					logger.info(" Created  AnonymousAuthenticationToken :: ["+authentication+"] ");
				} else {
					SPUser fullyInitializedUser =
					 new SPUser(((UserDetails) user.getPrincipal()).getUsername(), "", true, true, true, true, permissions,true);
					
					fullyInitializedUser.setGroups(groups);
					fullyInitializedUser.setRoles(roles);

					authentication = new SPAuthenticationToken(fullyInitializedUser.getUsername(), "", user.getDetails(),
							fullyInitializedUser, permissions, true);
				}

				SecurityContextHolder.getContext().setAuthentication(authentication);
				logger.info(" Created  SPAuthenticationToken :: ["+authentication+"] ");

			} catch (NullPointerException ex) {
				logger.info("No user session");
			}
		}

		logger.info("doFilter ::END ");
		filterChain.doFilter(servletRequest, servletResponse);

	}

	/**
	 * Returns permission list for a given role.
	 * 
	 * @param userRole as string
	 * @return returns a list of permissions for a given role
	 * @throws BadCredentialsException in case there is no such user in local repository
	 */
	private Collection<GrantedAuthority> getPermissions(Collection<String> authorities) {
		Collection<GrantedAuthority> permissions = new ArrayList<GrantedAuthority>();
		for (String role : authorities) {
			List<String> permissionsByRole = getPermissionsByRole(role);
			for (String permission : permissionsByRole) {
				GrantedAuthority authority = new SimpleGrantedAuthority(permission);
				permissions.add(authority);
			}
		}

		return permissions;

	}

	/**
	 * Gets a role and returns a list of GrantedAuthorities with permissions
	 * 
	 * @param role
	 * @return @see List of @see GrantedAuthority containing permissions
	 */
	private List<String> getPermissionsByRole(String role) {
		logger.info("getPermissionsByRole :: START :: in ["+role+"] ");
		
		List<String> out = Collections.emptyList();
		String permissions = permissionsProperties.getProperty(role);
	
		logger.info("getPermissionsByRole ::permissions read from property file  are ["+permissions+"] ");
		if (StringUtils.hasText(permissions)){
			out=Arrays.asList(permissions.split(","));
		}

		logger.info("getPermissionsByRole :: END ::  out ["+out+"] ");
		return out;

	}

}
