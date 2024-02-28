/*******************************************************************************
 * * $Id:: FileFormat.java 124681 2013-06-21 10:24:03Z karalch                   $
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
public enum FileFormat {
	JPEG("JPEG"),
	TIFF("TIFF"),
	PNG("PNG"),
	GIF("GIF"),
    BMP("BMP"),
	PDF("PDF"),
    DOC("DOC"),
	OTHER("Other")
	;
	
	private final String value;
	
	private FileFormat(final String value) {
		this.value = value;
	}

    public String value() {
        return value;
    }

}
