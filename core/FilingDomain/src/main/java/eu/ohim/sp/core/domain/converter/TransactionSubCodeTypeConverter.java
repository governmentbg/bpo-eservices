/*******************************************************************************
 * $Id:: TransactionSubCodeTypeConverter.java 2020/06/30 18:02 jmunoze
 *
 *        . * .
 *      * RRRR  *   Copyright (c) 2012-2020 EUIPO: European Intelectual
 *     .  RR  R  .  Property Organization (trademarks and designs).
 *     *  RRR    *
 *      . RR RR .   ALL RIGHTS RESERVED
 *       *. _ .*
 *
 *  The use and distribution of this software is under the restrictions exposed in 'license.txt'
 *
 ******************************************************************************/

package eu.ohim.sp.core.domain.converter;

import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;

/**
 * The type Transaction sub code type converter.
 */
public class TransactionSubCodeTypeConverter implements CustomConverter, Converter {

    @Override
    public Object convert(Class type, Object value) {
        if(value != null){
            if(value instanceof Boolean ) {
                return ((boolean) value) ? "Accelerated Registration" : "Normal";
            } else if(value instanceof String){
                return ((String) value).equalsIgnoreCase("Accelerated Registration") ? true : false;
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
