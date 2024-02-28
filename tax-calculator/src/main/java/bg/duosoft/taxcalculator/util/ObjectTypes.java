package bg.duosoft.taxcalculator.util;

import java.util.*;

public class ObjectTypes {

    private static final List<String> objectTypes;
    static {
        List<String> aList = new ArrayList<>();
        aList.add("trademark");
        aList.add("design");
        aList.add("patent");
        aList.add("utility_model");
        aList.add("european_patent");
        aList.add("plants_and_breeds");
        aList.add("protection_certificate");
        objectTypes = Collections.unmodifiableList(aList);
    }

    public static List<String> getObjectTypes() {
        return objectTypes;
    }
}
