package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.PatentType;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.inside.ws.client.BPOEBDPatentsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOEBDPatentsSearchService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Raya
 * 04.07.2019
 */
public class BPOEBDPatentSearchServiceTest {

    BPOEBDPatentsSearch adapter;
    Mapper mapper;
    String num;

    @Before
    public void init() {
        try {
            adapter =new BPOEBDPatentsSearchService(URI.create("http://172.16.1.55:8081/ebd-d-services/services/ebd-d-search?wsdl").toURL()).getBPOEBDPatentsSearchServiceDefaultPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapper = new DozerBeanMapper(Arrays.asList("ext-patentType-dozerMapping-global.xml", "ext-patentType-dozerMapping.xml", "ext-eu-patent-publication-dozerMapping.xml"));
        num = "3425720";
    }


    @Test
    public void testSearchByAppNum(){
        List<PatentType> result = adapter.getEBDPatentByAppNum(num);

        assertTrue(result != null && result.size()>0);
    }

    @Test
    public void testSearchByAppNumAndConvert(){
        List<PatentType> result = adapter.getEBDPatentByAppNum(num);
        Patent p = new Patent();
        mapper.map(result.get(0), p);

        assertTrue(p != null && p.getApplicationNumber() != null);
    }
}
