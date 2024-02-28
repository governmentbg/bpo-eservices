/*******************************************************************************
 * * $Id:: MarkFeature.java 121785 2013-06-06 18:45:12Z karalch                  $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
/**
 * 
 */
package eu.ohim.sp.core.domain.trademark;

/**
 * @author ionitdi
 * 
 */
public enum MarkFeature {
	WORD("Word"),
	STYLIZED_CHARACTERS("Stylized characters"),
	FIGURATIVE("Figurative"),
	COMBINED("Combined"),
	THREE_D("3-D"),
	COLOUR("Colour"),
	SOUND("Sound"),
	HOLOGRAM("Hologram"),
	OLFACTORY("Olfactory"),
	MOTION("Motion"),
	MUNICIPAL("Municipal"),
	CHIMNEY("Chimney"),
	KENNFADEN("Kennfaden"),
	NUMBER("Number"),
	MARK_IN_SERIES("Mark in series"),
	OTHER("Other"),
	THREE_D_SHAPE("3-D shape"),
	POSITION("Position"),
	PATTERN("Pattern"),
	MULTIMEDIA("Multimedia"),
	UNDEFINED("Undefined"),
	GEOGRAPHIC_INDICATION("Geographic indication"),
	ORIGIN_NAME("Origin name");

	private final String value;

	private MarkFeature(final String value) {
		this.value = value;
	}

	public String value(){
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
