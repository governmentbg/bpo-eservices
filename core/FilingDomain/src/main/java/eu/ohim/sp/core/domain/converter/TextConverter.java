/*
 *  FspDomain:: TextConverter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;

import eu.ohim.sp.filing.domain.tm.Text;

public class TextConverter extends FSPConverter<Text, eu.ohim.sp.core.domain.common.Text> 
		implements CustomConverter {

    /**
     * Logger of {@see TextConverter}
     */
    private static final Logger LOGGER = Logger.getLogger(TextConverter.class);

    @Override
	public Object convert(Class type, Object orig) {
		Object dest = super.internalConvert(type, orig);
		if (orig instanceof String) {
			try {
				PropertyUtils.setProperty(dest, "value", orig);
			} catch (IllegalAccessException e) {
                LOGGER.error(e);
			} catch (InvocationTargetException e) {
                LOGGER.error(e);
            } catch (NoSuchMethodException e) {
                LOGGER.error(e);
            }
		}
		return dest;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

}