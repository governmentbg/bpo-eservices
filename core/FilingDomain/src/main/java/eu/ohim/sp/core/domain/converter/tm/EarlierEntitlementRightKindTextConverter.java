/*
 *  FspDomain:: EarlierEntitlementRightKindTextConverter 31/10/13 17:37 karalch $
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

import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;

public class EarlierEntitlementRightKindTextConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof EarlierEntitlementRightKind) {
			return ((EarlierEntitlementRightKind)value).toString();
		}
		else if (value instanceof String) {
			for (EarlierEntitlementRightKind earlierEntitlementRightKind : EarlierEntitlementRightKind.values()) {
				if (((String)value).equalsIgnoreCase(earlierEntitlementRightKind.toString())) {
					return earlierEntitlementRightKind;
				}
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
