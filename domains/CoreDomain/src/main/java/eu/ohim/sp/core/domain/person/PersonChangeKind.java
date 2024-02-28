/*******************************************************************************
 * * $Id:: PersonChangeKind.java 124886 2013-06-24 19:18:44Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

public enum PersonChangeKind {

	ADD_NEW_REPRESENTATIVE("Add new representative"),
	REPLACE_REPRESENTATIVE("Replace representative"),
	REMOVE_REPRESENTATIVE("Remove representative"),
	CHANGE_REPRESENTATIVE_ADDRESS("Change of representative address"),
	CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS("Change of correspondence address"),
	ADD_NEW_CORRESPONDENT("Add new correspondent"),
	REPLACE_CORRESPONDENT("Replace correspondent"),
	REMOVE_CORRESPONDENT("Remove correspondent"),
	CHANGE_CORRESPONDENT_ADDRESS("Change of correspondent address")
	;

    private final String value;

    PersonChangeKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

}
