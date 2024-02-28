/*******************************************************************************
 * * $Id:: SoundFileFormat.java 124681 2013-06-21 10:24:03Z karalch              $
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
package eu.ohim.sp.core.domain.resources;

/**
 * @author ionitdi
 * 
 */
@Deprecated
public enum SoundFileFormat {
	MP3("MP3");

	private SoundFileFormat(final String value) {
		this.value = value;
	}

	private final String value;

	public String value(){
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
