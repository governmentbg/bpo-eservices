/*
 *  CoreDomain:: Applicant 07/11/13 10:10 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.person;

public class Applicant extends PersonRole {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7687215915274029730L;

    private Boolean isOwner;

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }
}
