/*
 *  FspDomain:: FSPConverter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

public abstract class FSPConverter<C,F> implements Converter {

    /**
     * Logger of {@see FSPConverter}
     */
    private static final Logger LOGGER = Logger.getLogger(FSPConverter.class);

    public <T> T internalConvert(Class<T> type, Object orig) {
		try {
			if (orig != null) {
				T dest = type.newInstance();
				BeanUtilsBean.getInstance().copyProperties(dest, orig);
				return dest;
			}
		} catch (IllegalAccessException e) {
            LOGGER.error(e);
		} catch (InvocationTargetException e) {
            LOGGER.error(e);
		} catch (InstantiationException e) {
            LOGGER.error(e);
		}
		return null;
	}
	
	public C convertFrom(Class<C> type, Object orig) {
		try {
			C dest = type.newInstance();
			BeanUtilsBean.getInstance().copyProperties(dest, orig);
			return dest;
		} catch (InstantiationException e) {
            LOGGER.error(e);
		} catch (IllegalAccessException e) {
            LOGGER.error(e);
		} catch (InvocationTargetException e) {
            LOGGER.error(e);
		}
		return null;
	}

	public F convertTo(Class<F> type, Object orig) {
		try {
			F dest = type.newInstance();
			BeanUtilsBean.getInstance().copyProperties(dest, orig);
			return dest;
		} catch (InstantiationException e) {
            LOGGER.error(e);
		} catch (IllegalAccessException e) {
            LOGGER.error(e);
		} catch (InvocationTargetException e) {
            LOGGER.error(e);
		}
		return null;
	}
	
	public <T> T beanConvert(Object value, Class<T> targetType) {
		return (T) BeanUtilsBean.getInstance().getConvertUtils().convert(value, targetType);
	}

}
