package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.ExhibitionPriority;

import org.dozer.DozerConverter;

import java.math.BigInteger;

/**
 * @author ionitdi
 */
public class DesignExhPriorityOnlySequenceNumberConverter extends DozerConverter<ExhibitionPriority, eu.ohim.sp.filing.domain.ds.ExhibitionPriority>
{

    public DesignExhPriorityOnlySequenceNumberConverter()
    {
        super(ExhibitionPriority.class, eu.ohim.sp.filing.domain.ds.ExhibitionPriority.class);
    }

    @Override
    public eu.ohim.sp.filing.domain.ds.ExhibitionPriority convertTo(ExhibitionPriority core, eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext)
    {
        eu.ohim.sp.filing.domain.ds.ExhibitionPriority exhibitionPriority = new eu.ohim.sp.filing.domain.ds.ExhibitionPriority();

        exhibitionPriority.setExhibitionPrioritySequenceNumber(new BigInteger(Integer.toString(core.getSequenceNumber())));
        return exhibitionPriority;
    }

    @Override
    public ExhibitionPriority convertFrom(eu.ohim.sp.filing.domain.ds.ExhibitionPriority ext, ExhibitionPriority core)
    {
    	ExhibitionPriority exhibitionPriority = new ExhibitionPriority();
        exhibitionPriority.setSequenceNumber(ext.getExhibitionPrioritySequenceNumber().intValue());
        return exhibitionPriority;
    }
}
