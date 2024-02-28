/*
 *  FspDomain:: GroundCategoryKindTextConverter 13/11/13 10:21 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.tm;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

import eu.ohim.sp.core.domain.opposition.GroundCategoryKind;

public class GroundCategoryKindTextConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof GroundCategoryKind) {
			if (!((GroundCategoryKind)value).toString().equalsIgnoreCase(GroundCategoryKind.RELATIVE_GROUNDS.toString())) {
                return ((GroundCategoryKind)value).toString();
            }
		}
		else if (value instanceof String) {
			if (((String)value).equalsIgnoreCase(GroundCategoryKind.ABSOLUTE_GROUNDS.toString())) {
				return GroundCategoryKind.ABSOLUTE_GROUNDS;
            } else if (((String)value).equalsIgnoreCase(GroundCategoryKind.REVOCATION_GROUNDS.toString())) {
				return GroundCategoryKind.REVOCATION_GROUNDS;
            }
		}
		return null;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

}
