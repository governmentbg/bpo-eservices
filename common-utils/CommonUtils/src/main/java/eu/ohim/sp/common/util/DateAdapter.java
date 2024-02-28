package eu.ohim.sp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
    @Override
    public Date unmarshal(String value) {
        try {
            if (value == null || value.trim().equals("")) {
                return null;
            } else {
               return new SimpleDateFormat("yyyy-MM-ddXXX").parse(value);
            }
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(value);
            } catch (ParseException e1) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public String marshal(Date value) {
        if (value != null) {
            return new SimpleDateFormat("yyyy-MM-ddXXX").format(value);
        } else {
            return null;
        }
    }

}
