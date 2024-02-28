package eu.ohim.sp.common.util;

import eu.ohim.sp.common.util.DateAdapter;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 07/11/13
 * Time: 21:34
 * To change this template use File | Settings | File Templates.
 */
public class DateAdapterTest {

    @Test
    public void testParseString(){
        DateAdapter dateAdapter = new DateAdapter();
        Date date = dateAdapter.unmarshal("2010-10-10-07:00");

        Assert.assertTrue(date instanceof Date);
    }


    @Test
    public void testParseDate(){
        DateAdapter dateAdapter = new DateAdapter();
        String date = dateAdapter.marshal(new Date());

        System.out.println(date);
        Assert.assertTrue(date!=null && !date.trim().equals(""));
    }


    @Test
    public void testParseStringNull(){
        DateAdapter dateAdapter = new DateAdapter();
        Date date = dateAdapter.unmarshal(null);

        Assert.assertNull(date);
    }


    @Test
    public void testParseDateNull(){
        DateAdapter dateAdapter = new DateAdapter();
        String date = dateAdapter.marshal(null);

        Assert.assertNull(date);
    }
}
