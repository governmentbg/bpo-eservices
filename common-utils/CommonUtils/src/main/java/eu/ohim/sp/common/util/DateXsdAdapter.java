package eu.ohim.sp.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;


public class DateXsdAdapter {
    public static Date parseDate(String s) {
        if (s == null || s.trim().equals("")) {
            return null;
        } else {
            return DatatypeConverter.parseDate(s).getTime();
        }
    }

    public static String printDate(Date dt) {
        if (dt != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(dt);
            return DatatypeConverter.printDate(cal);
        } else {
            return null;
        }
    }
}
