/*******************************************************************************
 * * $Id:: User.java 128442 2013-07-11 13:35:23Z jaraful                         $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.user;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;
import java.util.List;

/**
 * The User class of the core domain. 
 * 
 * @author karalch
 *
 */
public class User extends Id implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011965471242127751L;

	private String userName;
	private String email;
	private String fullName;
	private String status;
	private List<String> role;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
