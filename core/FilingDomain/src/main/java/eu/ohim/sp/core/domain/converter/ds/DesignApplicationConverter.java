/*
 *  FspDomain:: DesignApplicationConverter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.DesignApplication;
import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

/**
 * @author ionitdi
 */
public class DesignApplicationConverter implements CustomConverter, Converter, MapperAware
{
    private Mapper mapper;

    public eu.ohim.sp.filing.domain.ds.DesignApplication convertToExternal(DesignApplication core)
    {
        return mapper.map(core, eu.ohim.sp.filing.domain.ds.DesignApplication.class, "fullDesignApplication");
    }


    public DesignApplication convertToCore(eu.ohim.sp.filing.domain.ds.DesignApplication ext)
    {
        DesignApplication core = mapper.map(ext, DesignApplication.class, "fullDesignApplication");


        return core;
    }

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }

    @Override
    public Object convert(Class type, Object value)
    {
        return convert(null, value, null, type);
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
                          Class<?> sourceClass)
    {
        if(sourceFieldValue == null) {
            return null;
        }
        if(sourceFieldValue instanceof eu.ohim.sp.filing.domain.ds.DesignApplication)
        {
            /// Covert to CORE Domain
            eu.ohim.sp.filing.domain.ds.DesignApplication ext = (eu.ohim.sp.filing.domain.ds.DesignApplication)sourceFieldValue;
            return convertToCore(ext);

        }
        else if(sourceFieldValue instanceof DesignApplication)
        {
            /// Convert to External Domain
            DesignApplication core = (DesignApplication)sourceFieldValue;
            return convertToExternal(core);
        }
        return null;
    }
}
