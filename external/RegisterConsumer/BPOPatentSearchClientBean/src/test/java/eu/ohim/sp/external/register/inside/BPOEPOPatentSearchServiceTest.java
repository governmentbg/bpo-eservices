package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.PatentType;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.inside.ws.client.BPOEPOPatentsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOEPOPatentsSearchService;
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
public class BPOEPOPatentSearchServiceTest {

    BPOEPOPatentsSearch adapter;
    Mapper mapper;
    String num;

    @Before
    public void init() {
        try {
            adapter =new BPOEPOPatentsSearchService(URI.create("http://172.16.1.55:8081/bpo.register.ws.provider/services/BPOEPOPatentsSearchServiceDefaultPort?wsdl").toURL()).getBPOEPOPatentsSearchServiceDefaultPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapper = new DozerBeanMapper(Arrays.asList("ext-patentType-dozerMapping-global.xml", "ext-patentType-dozerMapping.xml", "ext-eu-patent-publication-dozerMapping.xml"));
        num = "EP16150499";
    }


    @Test
    public void testSearchByAppNum(){
        List<PatentType> result = adapter.getEpoPatentByAppNum(num);

        assertTrue(result != null && result.size()>0);
    }

    @Test
    public void testSearchByAppNumAndConvert(){
        List<PatentType> result = adapter.getEpoPatentByAppNum(num);
        Patent p = new Patent();
        mapper.map(result.get(0), p);

        assertTrue(p != null && p.getApplicationNumber() != null);
    }
}
