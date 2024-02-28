/*******************************************************************************
 * * $Id:: ListHelper.java 49264 2012-10-29 13:23:34Z karalch                    $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

/**
 * @author ionitdi
 */
public class ListHelper
{
    public static <T> Answer<List<T>> setupDummyListAnswer(T... values)
    {
        final List<T> someList = new ArrayList<T>();
        if(values != null)
        {
            someList.addAll(Arrays.asList(values));
        }

        Answer<List<T>> answer = new Answer<List<T>>() {
            public List<T> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

    public static <T> Answer<Set<T>> setupDummySetAnswer(T... values)
    {
        final Set<T> someList = new HashSet<T>();
        if(values != null)
        {
            someList.addAll(Arrays.asList(values));
        }

        Answer<Set<T>> answer = new Answer<Set<T>>() {
            public Set<T> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }
}
