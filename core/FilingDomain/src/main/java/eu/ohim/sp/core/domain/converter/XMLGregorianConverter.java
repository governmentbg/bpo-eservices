/*
 *  FspDomain:: XMLGregorianConverter 04/10/13 17:56 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

public class XMLGregorianConverter implements Converter {

	private final Logger LOGGER = Logger.getLogger(XMLGregorianConverter.class);

	@Override
	public Object convert(Class type, Object value) {
		if (type.equals(XMLGregorianCalendar.class)
				&& value instanceof Date) {
	        XMLGregorianCalendar gregCalendar = null;
            try {
		        GregorianCalendar calendar = new GregorianCalendar();
	            calendar.setTime((Date) value);
				gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		        return gregCalendar;
			} catch (DatatypeConfigurationException e) {
                LOGGER.warn(e);
			}
		} else if (type.equals(Date.class)
				&& value instanceof XMLGregorianCalendar) {
	        return ((XMLGregorianCalendar) value).toGregorianCalendar().getTime();
		} else if (type.equals(Date.class)
				&& value instanceof Date) {
			return value;
		}
		return null;
	}

}
