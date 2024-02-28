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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

public class MarkCurrentStatusConverter implements CustomConverter, Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof String) {
			List<String> markCurrentStatusCode = new ArrayList<String>();
			markCurrentStatusCode.add((String) value);

			return markCurrentStatusCode;

		} else if (value instanceof List<?>) {
			String markCurrentStatusCode = "";
			List<String> markCurrentStatusCodeList = (List<String>) value;

			if (markCurrentStatusCodeList.size() > 0) {
				StringBuffer buf = new StringBuffer();
				for (String string : markCurrentStatusCodeList) {
					buf.append(string);
					buf.append(" ");
				}
				markCurrentStatusCode = buf.toString();
				markCurrentStatusCode = markCurrentStatusCode.trim();
				return markCurrentStatusCode;
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
