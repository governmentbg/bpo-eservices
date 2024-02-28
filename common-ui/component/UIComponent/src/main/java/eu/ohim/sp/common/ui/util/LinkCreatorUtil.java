/*******************************************************************************
 * * $Id:: LinkCreatorUtil.java 49264 2012-10-29 13:23:34Z karalch               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import java.util.Map;

/**
 * @author ionitdi
 */
public class LinkCreatorUtil
{
    /**
     * <p>
     * This method creates a valid URL using the parametrized string provided and the parameter map.
     * Iterating over the parameter map, if the keys of the parameters are found, they are replaced with their values
     * </p>
     * Parameterized input string examples:
     * http://www.example.com/someAction?{someParameter}={someValue}&{param2}={value2}
     * http://{server}/{action}
     * {protocol}{server}
     *
     * @param parametrizedString
     * @param parameterMap
     * @return String with the created link
     */
    public String createLink(String parametrizedString, Map<String, String> parameterMap)
    {
        if (parametrizedString == null)
        {
            return null;
        }
        if (parameterMap == null || parameterMap.isEmpty())
        {
            return parametrizedString;
        }
        if (!parametrizedString.contains("{"))
        {
            return parametrizedString;
        }

        String link = parametrizedString;

        for (Map.Entry<String, String> entry : parameterMap.entrySet())
        {
            String pattern = "\\{" + entry.getKey() + "\\}";
            link = link.replaceFirst(pattern, entry.getValue());
        }
        return link;
    }
}
