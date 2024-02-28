/*
 *  CoreDomain:: DesignStatusCode 28/11/13 16:20 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

public enum DesignStatusCode {
    FILED("Filed"),
    WITHDRAWN("Withdrawn"),
    REJECTED("Rejected"),
    REGISTERED("Registered"),
    CANCELLED("Cancelled"),
    EXPIRED("Expired"),
    UNDEFINED("Undefined"),
    ENDED("Ended"),
    REGISTERED_AND_FULLY_PUBLISHED("Registered and fully published"),
    REGISTERED_AND_SUBJECT_TO_DEFERMENT("Registered and subject to deferment"),
    LACK_OF_EFFECTS("Lack of effects"),
    DESIGN_SURRENDERED("Design surrendered"),
    INVALIDITY_PROCEDURE_PENDING("Invalidity procedure pending"),
    DESIGN_DECLARED_INVALID("Design declared invalid"),
    DESIGN_LAPSED("Design lapsed"),
    EXPIRING("Expiring"),
    APPLICATION_PUBLISHED("Application published");

	private DesignStatusCode(final String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
}