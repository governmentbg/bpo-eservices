package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.Priority;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.math.BigInteger;

/**
 * @author ionitdi
 */
public class DesignPriorityOnlySequenceNumberConverter extends DozerConverter<Priority, eu.ohim.sp.filing.domain.ds.Priority> implements
        MapperAware
{
    private Mapper mapper;

    public DesignPriorityOnlySequenceNumberConverter()
    {
        super(Priority.class, eu.ohim.sp.filing.domain.ds.Priority.class);
    }

    @Override
    public eu.ohim.sp.filing.domain.ds.Priority convertTo(Priority core, eu.ohim.sp.filing.domain.ds.Priority ext)
    {
        String mapId = getParameter();
        if(mapId != null)
        {

            return mapper.map(core, eu.ohim.sp.filing.domain.ds.Priority.class, mapId);
        }
        eu.ohim.sp.filing.domain.ds.Priority priority = new eu.ohim.sp.filing.domain.ds.Priority();

        priority.setPrioritySequenceNumber(new BigInteger(Integer.toString(core.getSequenceNumber())));
        return priority;
    }

    @Override
    public Priority convertFrom(eu.ohim.sp.filing.domain.ds.Priority ext, Priority core)
    {
        Priority priority = new Priority();
        priority.setSequenceNumber(ext.getPrioritySequenceNumber().intValue());
        return priority;
    }

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }
}
