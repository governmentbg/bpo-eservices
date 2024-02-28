/*******************************************************************************
 * * $Id:: DateUtil.java 137667 2013-09-03 09:59:55Z segurse                     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.util;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author ionitdi
 */
public class DateUtil
{
    public static Date cloneDate(Date input)
    {
        if(input != null)
        {
            return (Date)input.clone();
        }
        return null;
    }
    
    /*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }



    
}
