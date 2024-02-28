package eu.ohim.sp.external.epayment.inside.epayment_mock.util;

import eu.ohim.sp.external.domain.common.Entry;
import eu.ohim.sp.external.domain.common.ParamsMap;

import java.util.HashMap;
import java.util.Map;

public class MapToXsdMap {
	public static ParamsMap convertToXsdMap(Map<String, String> map) {

		ParamsMap paramsMap = new ParamsMap();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			paramsMap.getEntry().add(new Entry(entry.getKey(), entry.getValue()));
		}

		return paramsMap;
	}

	public static Map<String, String> convertToMap(ParamsMap xsdMap) {
		Map<String, String> toReturn = new HashMap<String, String>();

		for (Entry entry : xsdMap.getEntry()) {
			toReturn.put(entry.getKey(), entry.getValue());
		}

		return toReturn;
	}
}
