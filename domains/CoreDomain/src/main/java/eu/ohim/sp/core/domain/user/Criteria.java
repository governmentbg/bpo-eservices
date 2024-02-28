package eu.ohim.sp.core.domain.user;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

/**
 * The Class Criteria.
 */
public class Criteria extends Id implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The username. */
    private String username;
    
    /** The role. */
    private String role;
    
    /** The group. */
    private String group;
    
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

}
