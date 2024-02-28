package eu.ohim.sp.core.domain.converter.ds;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ionitdi
 */
public class CommonSetup
{
    public static DozerBeanMapper getMapper()
    {
        DozerBeanMapper dozerBeanMapper;
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/ds/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/ds/dozerMapping.xml");

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setCustomConvertersWithId(designCustomConverterInstantiation());
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        return dozerBeanMapper;
    }
    private static Map<String, CustomConverter> designCustomConverterInstantiation()
    {
        Map<String,CustomConverter> map = new HashMap<String, CustomConverter>();
        map.put("partialPriority", new DesignPriorityOnlySequenceNumberConverter());
        return map;
    }

}
