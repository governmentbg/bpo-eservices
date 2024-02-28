/*******************************************************************************
 * * $Id:: LinkCreatorUtilTest.java 49264 2012-10-29 13:23:34Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author ionitdi
 */
public class LinkCreatorUtilTest
{
    private LinkCreatorUtil linkCreator;

    private Map<String, String> stubParameterMap;

    @Before
    public void setUp()
    {
        linkCreator = new LinkCreatorUtil();
        stubParameterMap = new HashMap<String, String>();
    }


    @Test
    public void createLink_nullParametrizedString_returnsNull()
    {
        // Arrange
        String inputString = null;

        // Act
        String result = linkCreator.createLink(inputString, stubParameterMap);

        // Assert
        assertNull(result);
    }

    @Test
    public void createLink_nullParameterMap_returnsInputString()
    {
        // Arrange
        String inputString = "some input String {parameter1} finish";

        // Act
        String result = linkCreator.createLink(inputString, null);

        // Assert
        assertEquals(inputString, result);
    }

    @Test
    public void createLink_singleParameter_returnsCorrectly()
    {
        // Arrange
        String inputString = "some input {parameter1} finish";
        stubParameterMap.put("parameter1", "test ok");
        String expected = "some input test ok finish";

        // Act
        String result = linkCreator.createLink(inputString, stubParameterMap);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void createLink_multipleParameter_returnsCorrectly()
    {
        // Arrange
        String inputString = "http://www.someUrl.com/action?{parameter1}={value1}&{parameter2}={value2}";
        stubParameterMap.put("parameter1", "id");
        stubParameterMap.put("value1", "1234");
        stubParameterMap.put("parameter2", "showFields");
        stubParameterMap.put("value2", "true");
        String expected = "http://www.someUrl.com/action?id=1234&showFields=true";

        // Act
        String result = linkCreator.createLink(inputString, stubParameterMap);

        // Assert
        assertEquals(expected, result);
    }
}
