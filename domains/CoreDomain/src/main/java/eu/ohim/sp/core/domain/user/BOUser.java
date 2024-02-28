/*
 *  CoreDomain:: BOUser 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.util.List;

public class BOUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3889021687004224663L;

    private List<String> groups;

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return groups != null ? groups.hashCode() : 0;
    }

    @Override
	public String toString() {
		return "BOUser [groups=" + groups + "]";
	}
}
