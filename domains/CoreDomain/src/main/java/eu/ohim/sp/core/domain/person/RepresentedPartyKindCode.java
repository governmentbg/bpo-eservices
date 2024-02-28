/*
 *  CoreDomain:: RepresentedPartyKindCode 10/10/13 14:34 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.person;

public enum RepresentedPartyKindCode {

    APPLICANT("Applicant"),
    OWNER("Owner"),
    ASSIGNEE("Assignee"),
    OTHER("Other");

    private final String value;

    RepresentedPartyKindCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RepresentedPartyKindCode fromValue(String v) {
        for (RepresentedPartyKindCode c: RepresentedPartyKindCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
