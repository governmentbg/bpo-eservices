/*******************************************************************************
 * * $Id:: Designer.java 122910 2013-06-12 19:30:31Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.domain.person;

public enum PersonRoleRelationKind {
    REPRESENTATION("Representation"),
    ASSOCIATION("Association");
    private final String value;

    PersonRoleRelationKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PersonRoleRelationKind fromValue(String v) {
        for (PersonRoleRelationKind c: PersonRoleRelationKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }


}
