package eu.ohim.sp.external.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ohim.sp.common.xml.XsdMap;
import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.ParamsMap;
import org.dozer.CustomConverter;
import org.dozer.DozerConverter;

public class MapToXsdMap extends DozerConverter<ParamsMap, Map> implements CustomConverter {

    public MapToXsdMap() {
        super(ParamsMap.class, Map.class);
    }


    @Override
    public Map<String, String> convertTo(ParamsMap source, Map destination) {
        return convertToMap(source);
    }

    @Override
    public ParamsMap convertFrom(Map source, ParamsMap destination) {
        return (ParamsMap) convertToXsdMap(source);
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue instanceof Map) {
            return convertToXsdMap((Map) sourceFieldValue);
        } else {
            return convertToMap((XsdMap) sourceFieldValue);
        }
    }

    public static XsdMap convertToXsdMap(Map map) {
        XsdMap toReturn = new ParamsMap();

        ParamsMap redirectionParamsMap = (ParamsMap) toReturn;
        Map<String, String> redirectionParams = map;
        List<Entry> entryList = new ArrayList<Entry>();
        for (Map.Entry<String, String> entry : redirectionParams.entrySet()) {
            entryList.add(new Entry(entry.getKey(), entry.getValue()));
        }
        redirectionParamsMap.setEntry(entryList);

        return toReturn;
    }

    public static Map convertToMap(XsdMap xsdMap) {
        Map toReturn = new HashMap();
        ParamsMap paramsMap = (ParamsMap) xsdMap;

        for (Entry entry : paramsMap.getEntry()) {
            toReturn.put(entry.getKey(), entry.getValue());
        }

        return toReturn;
    }
}
