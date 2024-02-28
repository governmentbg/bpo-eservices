/*******************************************************************************
 * * $Id:: StringBuilderUtil.java 49260 2012-10-29 13:02:02Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.util;

/**
 * A class for manipulating Strings with StringBuilder instances. 
 * 
 * @author monteca
 *
 */
public class StringBuilderUtil {

	/**
	 * Appends a series of String varargs to a StringBuilder representation of them, 
	 * each one appended in the same order as they are specified.
	 * 
	 * @param args : The Strings concatenate append
	 * @return : The StringBuilder representation of the Strings
	 */
	public static StringBuilder build(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String s : args) {
			sb.append(s);
		}
		return sb;
	}
	
	/**
	 * Concatenates a series of String varargs using a StringBuilder instance,
	 * each one appended in the same order as they are specified.
	 * @param args The strings to concatenate
	 * @return The concatenated String
	 */
	public static String buildToString(String... args) {
		return build(args).toString();
	}
	

}
