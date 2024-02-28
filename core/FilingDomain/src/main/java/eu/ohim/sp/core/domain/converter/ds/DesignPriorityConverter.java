/*
 *  FspDomain:: DesignPriorityConverter 09/10/13 15:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.Priority;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ionitdi
 */
public class DesignPriorityConverter implements ConfigurableCustomConverter, Converter, MapperAware {
    private String parameter;
    private Mapper mapper;

    @Override
    public Object convert(Class type, Object value) {
        return convert(null, value, type, null);
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            throw new MappingException("Source field is null");
        }

        if (!(sourceFieldValue instanceof List)) {
            throw new MappingException(
                    "Error converting Priority objects. Expected source of list type. Found: " + sourceFieldValue.getClass());
        }

        List sourceList = (List) sourceFieldValue;
        if (sourceList.size() == 0) {
            return null;
        }


        if (sourceList.get(0) instanceof Priority) {
            /// Converting to External domain
            List<Priority> core = (List<Priority>) sourceFieldValue;
            List<eu.ohim.sp.filing.domain.ds.Priority> ext = new ArrayList<eu.ohim.sp.filing.domain.ds.Priority>();

            for (Priority item : core) {
                ext.add(convertToExternal(item));
            }
            return ext;
        } else if (sourceList.get(0) instanceof eu.ohim.sp.filing.domain.ds.Priority) {
            /// Converting to Core domain

            List<eu.ohim.sp.filing.domain.ds.Priority> ext = (List<eu.ohim.sp.filing.domain.ds.Priority>) sourceFieldValue;
            List<Priority> core = new ArrayList<Priority>();

            for (eu.ohim.sp.filing.domain.ds.Priority item : ext) {
                core.add(convertToCore(item));
            }
            return core;
        }

        return null;
    }

    private eu.ohim.sp.filing.domain.ds.Priority convertToExternal(Priority core) {
        if (StringUtils.equals(parameter, "sequence")) {
            eu.ohim.sp.filing.domain.ds.Priority ext = new eu.ohim.sp.filing.domain.ds.Priority();
            ext.setPrioritySequenceNumber(new BigInteger(Integer.toString(core.getSequenceNumber())));
            return ext;
        }

        return mapper.map(core, eu.ohim.sp.filing.domain.ds.Priority.class, "fullPriority");
    }

    private Priority convertToCore(eu.ohim.sp.filing.domain.ds.Priority ext) {
        return mapper.map(ext, Priority.class, "fullPriority");
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void setParameter(String s) {
        this.parameter = s;
    }
}
