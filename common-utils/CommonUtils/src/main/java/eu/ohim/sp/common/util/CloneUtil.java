package eu.ohim.sp.common.util;

/**
 * Created by marcoantonioalberoalbero on 3/5/17.
 */

import com.thoughtworks.xstream.XStream;

/**
 * Used to clone java beans.
 */
public class CloneUtil {
    /**
     *  Returns a deeply cloned java bean as opposed to
     *  apache commons BeanUtils clone.
     *
     * @param fromBean java bean to be cloned.
     * @return a new java bean cloned from fromBean.
     */
    public static Object deepClone(Object fromBean) {
        XStream converter = new XStream();
        String xml = converter.toXML(fromBean);
        Object copy = converter.fromXML(xml);
        return copy;
    }
}