/*******************************************************************************
 * * $Id:: RepresentativeKind.java 124886 2013-06-24 19:18:44Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

public enum RepresentativeKind {
	PROFESSIONAL_REPRESENTATIVE("Professional Representative"),
	LAWYER("Lawyer"),
	LAWYER_COMPANY("Lawyer Company"),
	LAWYER_ASSOCIATION("Lawyer Association"),
	TEMPORARY_REPRESENTATIVE("Temporary Representative"),
	ASSOCIATION("Association"),
	EMPLOYEE("Employee"),
	EMPLOYEE_WITH_ECONOMIC_CONNECTIONS("Employee with Economic Connections"),
	OTHER("Other"),
	UNDEFINED("Undefined"),
	INTELLECTUAL_PROPERTY_REPRESENTATIVE("Intellectual Property Representative"),
	;

    private final String value;

    RepresentativeKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public String getEnumName(){
		return super.name();
	}
    
	@Override
	public String toString() {
		return value;
	}

}
