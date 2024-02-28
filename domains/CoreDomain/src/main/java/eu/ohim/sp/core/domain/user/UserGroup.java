/*
 *  CoreDomain:: UserGroup 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.user;

// TODO: Auto-generated Javadoc

import eu.ohim.sp.core.domain.id.Id;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * The Class UserGroup.
 */
public class UserGroup extends Id {
	
	/** The name. */
	private String name;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return "UserGroup [name=" + name + "]";
	}
}
