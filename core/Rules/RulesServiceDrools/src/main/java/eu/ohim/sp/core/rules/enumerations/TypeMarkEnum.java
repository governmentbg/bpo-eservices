/*******************************************************************************
 * * $Id:: TypeMarkEnum.java 50705 2012-11-14 10:40:17Z jaraful $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.rules.enumerations;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Enum TypeMarkEnum.
 */
public enum TypeMarkEnum implements Serializable {

	/** The word. */
	WORD("Word"),
	/** The figurative. */
	FIGURATIVE("Figurative"),
	/** The three d. */
	THREE_D("3-D"),
	/** The colour. */
	COLOUR("Colour"),
	/** The sound. */
	SOUND("Sound"),
	/** The other. */
	OTHER("Other");

	/** The Constant lookup. */
	private static final Map<String, TypeMarkEnum> lookup = new HashMap<String, TypeMarkEnum>();

	static {
		for (TypeMarkEnum tme : EnumSet.allOf(TypeMarkEnum.class)) {
			lookup.put(tme.toString(), tme);
		}
	}

	/**
	 * Instantiates a new type mark enum.
	 * 
	 * @param text
	 *            the text
	 */
	private TypeMarkEnum(final String text) {
		this.text = text;
	}

	/** The text. */
	private final String text;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

	/**
	 * Exists.
	 * 
	 * @param code
	 *            the code
	 * @return true, if successful
	 */
	public static boolean exists(String code) {
		if (lookup.get(code) != null)
			return true;
		return false;
	}
}
