/*
 *  FspDomain:: DesignExhibitionPriorityConverter 09/10/13 15:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.ExhibitionPriority;

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
public class DesignExhibitionPriorityConverter implements ConfigurableCustomConverter, Converter, MapperAware {
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
                    "Error converting Exhibition Priority objects. Expected source of list type. Found: " + sourceFieldValue.getClass());
        }

        List sourceList = (List) sourceFieldValue;
        if (sourceList.size() == 0) {
            return null;
        }

        if (sourceList.get(0) instanceof ExhibitionPriority) {
            /// Converting to External domain
            List<ExhibitionPriority> core = (List<ExhibitionPriority>) sourceFieldValue;
            List<eu.ohim.sp.filing.domain.ds.ExhibitionPriority> ext = new ArrayList<eu.ohim.sp.filing.domain.ds.ExhibitionPriority>();

            for (ExhibitionPriority item : core) {
                ext.add(convertToExternal(item));
            }
            return ext;
        } else if (sourceList.get(0) instanceof eu.ohim.sp.filing.domain.ds.ExhibitionPriority) {
            /// Converting to Core domain

            List<eu.ohim.sp.filing.domain.ds.ExhibitionPriority> ext = (List<eu.ohim.sp.filing.domain.ds.ExhibitionPriority>) sourceFieldValue;
            List<ExhibitionPriority> core = new ArrayList<ExhibitionPriority>();

            for (eu.ohim.sp.filing.domain.ds.ExhibitionPriority item : ext) {
                core.add(convertToCore(item));
            }
            return core;
        }
        return null;
    }

    private eu.ohim.sp.filing.domain.ds.ExhibitionPriority convertToExternal(ExhibitionPriority core) {
        if (StringUtils.equals(parameter, "sequence")) {
            eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext = new eu.ohim.sp.filing.domain.ds.ExhibitionPriority();
            ext.setExhibitionPrioritySequenceNumber(new BigInteger(Integer.toString(core.getSequenceNumber())));
            return ext;
        }

        return mapper.map(core, eu.ohim.sp.filing.domain.ds.ExhibitionPriority.class, "fullExhibitionPriority");
    }

    private ExhibitionPriority convertToCore(eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext) {
        return mapper.map(ext, ExhibitionPriority.class, "fullExhibitionPriority");
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
