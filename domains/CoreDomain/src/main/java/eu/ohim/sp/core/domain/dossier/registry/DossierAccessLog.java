/*
 *  CoreDomain:: DossierAccessLog 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.dossier.registry;

import java.io.Serializable;
import java.util.Date;

import eu.ohim.sp.common.util.DateUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class represent a user access to a Dossier
 */
public class DossierAccessLog implements Serializable {

	/** serial id **/
	private static final long serialVersionUID = 1L;

	private int dossierId;
	private String username;
	private Date lastUpdate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getDossierId() {
		return dossierId;
	}

	public void setDossierId(int dossierId) {
		this.dossierId = dossierId;
	}

	public Date getLastUpdate() {
		return DateUtil.cloneDate(lastUpdate);
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = DateUtil.cloneDate(lastUpdate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != this.getClass()) {
			return super.equals(obj);
		}

		DossierAccessLog actual = (DossierAccessLog) obj;

		return EqualsBuilder.reflectionEquals(this, actual);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
