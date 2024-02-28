package eu.ohim.sp.core.domain.converter2;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 20/01/14
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class CommonSetup {
    public static DozerBeanMapper getMapper()
    {
        DozerBeanMapper dozerBeanMapper;
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozer/tm/globalConfigurationMapping.xml");
        myMappingFiles.add("dozer/tm/dozerMapping.xml");

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        return dozerBeanMapper;
    }

}
