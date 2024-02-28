package eu.ohim.sp.core.domain.design;
//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
/**
 * #DS Class Integration changes.
 * Enumeration with all EU official languages for the application.
 * @author Ramittal
 *
 */
public enum EUFirstLanguageCode {
    /**
     * Bulgarian
     */
    BG("bg"),

    /**
     * Bulgarian
     */
    CS("cs"),

    /**
     * Czech
     */
    DA("da"),

    /**
     * German
     */
    DE("de"),

    /**
     * Greek
     */
    EL("el"),

    /**
     * English
     */
    EN("en"),

    /**
     * Spanish
     */
    ES("es"),

    /**
     * Estonian
     */
    ET("et"),

    /**
     * Finnish
     */
    FI("fi"),

    /**
     * French
     */
    FR("fr"),

    /**
     * Hungarian
     */
    HU("hu"),

    /**
     * Croatian
     */
    HR("hr"),

    /**
     * Italian
     */
    IT("it"),

    /**
     * Lithuanian
     */
    LT("lt"),

    /**
     * Latvian
     */
    LV("lv"),

    /**
     * Maltese
     */
    MT("mt"),

    /**
     * Dutch
     */
    NL("nl"),

    /**
     * Polish
     */
    PL("pl"),

    /**
     * Portuguese
     */
    PT("pt"),

    /**
     * Romanian
     */
    RO("ro"),

    /**
     * Slovak
     */
    SK("sk"),

    /**
     * Slovenian
     */
    SL("sl"),

    /**
     * Swedish
     */
    SV("sv");
    
	/** value */
	private final String value;
    
	/**
	 * Enum constructors are private by default
	 * @param v
	 */
   EUFirstLanguageCode(String v) {
        value = v;
    }

    /**
     * value method
     * @return string
     */
    public String value() {
        return value;
    }

    /**
     * From value method
     * @param v value
     * @return enum
     */
    public static EUFirstLanguageCode fromValue(String v) {
        for (EUFirstLanguageCode c : EUFirstLanguageCode.values()) {
            if (c.value.equalsIgnoreCase(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    /**
     * enum value method
     * @return string
     */
    public String enumValue() {
        return this.value();
    }

}
