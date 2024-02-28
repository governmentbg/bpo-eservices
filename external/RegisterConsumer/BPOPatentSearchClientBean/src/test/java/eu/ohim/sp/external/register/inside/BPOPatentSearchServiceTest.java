package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.PatentType;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.inside.ws.client.BPOPatentsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOPatentsSearchService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Raya
 * 17.06.2019
 */
public class BPOPatentSearchServiceTest {

    BPOPatentsSearch adapter;
    Mapper mapper;
    String num;

    @Before
    public void init() {
        try {
            adapter =new BPOPatentsSearchService(URI.create("http://172.16.1.55:8081/bpo.register.ws.provider/services/BPOPatentsSearchServiceDefaultPort?wsdl").toURL()).getBPOPatentsSearchServiceDefaultPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapper = new DozerBeanMapper(Arrays.asList("ext-patentType-dozerMapping-global.xml", "ext-patentType-dozerMapping.xml"));
        num = "1952000011P";
    }


    @Test
    public void testSearchByAppNum(){
        List<PatentType> result = adapter.getPatentByAppNum(num);

        assertTrue(result != null && result.size()>0);
    }

    @Test
    public void testSearchByAppNumAndConvert(){
        List<PatentType> result = adapter.getPatentByAppNum(num);
        Patent p = new Patent();
        mapper.map(result.get(0), p);

        assertTrue(p != null && p.getApplicationNumber() != null);
    }


}
