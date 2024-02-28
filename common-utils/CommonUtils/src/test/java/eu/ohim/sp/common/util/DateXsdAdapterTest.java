package eu.ohim.sp.common.util;

import eu.ohim.sp.common.util.DateXsdAdapter;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 07/11/13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
public class DateXsdAdapterTest {
    @Test
    public void testParseString(){
        Date date = DateXsdAdapter.parseDate("2010-10-10");

        Assert.assertTrue(date instanceof Date);
    }


    @Test
    public void testParseDate(){
        String date = DateXsdAdapter.printDate(new Date());

        System.out.println(date);
        Assert.assertTrue(date!=null && !date.trim().equals(""));
    }


    @Test
    public void testParseStringNull(){
        Date date = DateXsdAdapter.parseDate(null);

        Assert.assertNull(date);
    }


    @Test
    public void testParseDateNull(){
        String date = DateXsdAdapter.printDate(null);

        Assert.assertNull(date);
    }
}
