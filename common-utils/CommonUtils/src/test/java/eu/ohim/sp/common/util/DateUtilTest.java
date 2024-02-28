package eu.ohim.sp.common.util;

import eu.ohim.sp.common.util.DateUtil;
import org.junit.Test;

import static junit.framework.Assert.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 08/11/13
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class DateUtilTest {

    @Test
    public void testCloneDate() {
        Date date = new Date();
        assertEquals(date, DateUtil.cloneDate(date));
    }

    @Test
    public void testCloneDateNull() {
        Date date = null;
        assertNull(DateUtil.cloneDate(date));
    }

}
