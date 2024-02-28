package eu.ohim.sp.external.register.inside;

import bg.egov.regix.patentdepartment.GetUtilityModelByAppNumType;
import bg.egov.regix.patentdepartment.PatentDetailsType;
import bg.egov.regix.patentdepartment.PatentType;
import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.external.register.inside.ws.client.BPOUMsSearch;
import eu.ohim.sp.external.register.inside.ws.client.BPOUMsSearchService;
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
 * 18.06.2019
 */
public class BPOUMSearchServiceTest {
    BPOUMsSearch adapter;
    Mapper mapper;
    GetUtilityModelByAppNumType appNumType;

    @Before
    public void init() {
        try {
            adapter =new BPOUMsSearchService(URI.create("http://172.16.1.55:8081/bpo.register.ws.provider/services/BPOUMsSearchServiceDefaultPort?wsdl").toURL()).getBPOUMsSearchServiceDefaultPort();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        mapper = new DozerBeanMapper(Arrays.asList("ext-patentType-dozerMapping-global.xml", "ext-patentType-dozerMapping.xml"));
        appNumType = new GetUtilityModelByAppNumType("2012002251U");
    }


    @Test
    public void testSearchByAppNum(){
        PatentDetailsType resultDetails = adapter.getUMByAppNum(appNumType);
        List<PatentType> result = resultDetails.getPatent();

        assertTrue(result != null && result.size()>0);
    }

    @Test
    public void testSearchByAppNumAndConvert(){

        PatentDetailsType resultDetails = adapter.getUMByAppNum(appNumType);
        List<PatentType> result = resultDetails.getPatent();
        Patent p = new Patent();
        mapper.map(result.get(0), p);

        assertTrue(p != null && p.getApplicationNumber() != null);
    }
}
