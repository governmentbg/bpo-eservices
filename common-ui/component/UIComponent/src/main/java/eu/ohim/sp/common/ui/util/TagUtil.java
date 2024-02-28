/*******************************************************************************
 * * $Id:: TagUtil.java 49264 2012-10-29 13:23:34Z karalch                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

/**
 * @author ionitdi
 */
public class TagUtil
{
    public static String parseFieldName(String fieldName)
    {
        return fieldName.replaceAll("\\[.*\\]", "");
    }
}
