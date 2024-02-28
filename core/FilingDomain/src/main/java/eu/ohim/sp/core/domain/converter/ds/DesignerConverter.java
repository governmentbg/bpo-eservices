/*
 *  FspDomain:: DesignerConverter 09/10/13 15:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.Designer;

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
public class DesignerConverter implements ConfigurableCustomConverter, Converter, MapperAware {
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
                    "Error converting Designer objects. Expected source of list type. Found: " + sourceFieldValue.getClass());

        }

        List sourceList = (List) sourceFieldValue;
        if (sourceList.size() == 0) {
            return null;
        }

        if (sourceList.get(0) instanceof Designer) {
            /// Converting to External domain
            List<Designer> core = (List<Designer>) sourceFieldValue;
            List<eu.ohim.sp.filing.domain.ds.Designer> ext = new ArrayList<eu.ohim.sp.filing.domain.ds.Designer>();

            for (Designer item : core) {
                ext.add(convertToExternal(item));
            }
            return ext;
        } else if (sourceList.get(0) instanceof eu.ohim.sp.filing.domain.ds.Designer) {
            /// Converting to Core domain

            List<eu.ohim.sp.filing.domain.ds.Designer> ext = (List<eu.ohim.sp.filing.domain.ds.Designer>) sourceFieldValue;
            List<Designer> core = new ArrayList<Designer>();

            for (eu.ohim.sp.filing.domain.ds.Designer item : ext) {
                core.add(convertToCore(item));
            }
            return core;
        }
        return null;
    }

    private eu.ohim.sp.filing.domain.ds.Designer convertToExternal(Designer core) {
        if (StringUtils.equals(parameter, "sequence")) {
            eu.ohim.sp.filing.domain.ds.Designer ext = new eu.ohim.sp.filing.domain.ds.Designer();
            ext.setDesignerSequenceNumber(new BigInteger(Integer.toString(core.getSequenceNumber())));
            return ext;
        }

        return mapper.map(core, eu.ohim.sp.filing.domain.ds.Designer.class, "fullDesigner");
    }

    private Designer convertToCore(eu.ohim.sp.filing.domain.ds.Designer ext) {
        return mapper.map(ext, Designer.class, "fullDesigner");
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
