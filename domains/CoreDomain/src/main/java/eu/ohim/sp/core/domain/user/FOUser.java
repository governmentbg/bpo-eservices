/*
 *  CoreDomain:: FOUser 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.user;

import org.apache.commons.lang.builder.EqualsBuilder;

public class FOUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8348708431903879283L;
	
	private UserPersonDetails userPersonDetails;

	public UserPersonDetails getUserPersonDetails() {
		return userPersonDetails;
	}

	public void setUserPersonDetails(UserPersonDetails userPersonDetails) {
		this.userPersonDetails = userPersonDetails;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return userPersonDetails != null ? userPersonDetails.hashCode() : 0;
    }

    @Override
	public String toString() {
		return "FOUser [userPersonDetails=" + userPersonDetails + "]";
	}
}
