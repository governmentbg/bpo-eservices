package bg.duosoft.config;

import bg.duosoft.util.PropertyAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("bg.duosoft")
public class UrlConfig {

    @Autowired
    @Qualifier(value = "properties")
    private PropertyAccess propertyAccess;

    @Bean
    public Map<String, String> urlMap() {
        Map<String, String> map = new HashMap<>();
        map.put("trademarks", propertyAccess.getTrademarksUrl());
        map.put("designs", propertyAccess.getDesignsUrl());
        map.put("eservices", propertyAccess.getEservicesUrl());
        map.put("recentlySigned", propertyAccess.getRecentlySignedUrl());
        map.put("patents", propertyAccess.getPatentsUrl());
        map.put("spc", propertyAccess.getSpcUrl());
        map.put("sortBreeds", propertyAccess.getSortBreedsUrl());
        map.put("utilityModels", propertyAccess.getUtilityModelsUrl());
        map.put("euPatents", propertyAccess.getEuPatentsUrl());
        map.put("geoIndications", propertyAccess.getGeoIndicationsUrl());
        map.put("integralSchemas", propertyAccess.getIntegralSchemasUrl());
        return map;
    }


}
