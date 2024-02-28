package bg.duosoft.taxcalculator.util;

import java.util.*;

public class ObjectTypesOwedTaxes {

    private static final Map<String, String> objectTypes;
    static {
        Map<String, String> aMap = new LinkedHashMap<>();
        aMap.put("trademark", "N");
        aMap.put("design", "Д");
        aMap.put("patent", "P");
        aMap.put("utility_model", "U");
        aMap.put("european_patent", "T");

        aMap.put("divisional_design", "Р");
        aMap.put("divisional_mark", "D");
        aMap.put("plants_and_breeds", "С");
        aMap.put("spc", "S");
        aMap.put("geographical_indications", "Г");

        objectTypes = Collections.unmodifiableMap(aMap);
    }

    public static Map<String, String> getObjectTypes() {
        return objectTypes;
    }
}
