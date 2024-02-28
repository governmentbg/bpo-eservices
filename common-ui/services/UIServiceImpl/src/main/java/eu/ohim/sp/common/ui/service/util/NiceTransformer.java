package eu.ohim.sp.common.ui.service.util;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Raya
 * 08.12.2020
 */
public class NiceTransformer {

    public static Map<String, Set<String>> niceAlphaLists(String jsonsrc, String lang) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray entriesArray = (JSONArray) json;
        for(int i=0; i< entriesArray.size(); i++){
            JSONObject entry = entriesArray.getJSONObject(i);
            if(entry.getString("Language").equalsIgnoreCase(lang)){
                return buildAlphaListMap(entry.getJSONArray("AlphaLists"));
            }
        }
        return new HashMap<>();
    }

    public static Map<String, Set<String>> buildAlphaListMap(JSONArray sourceJsonArray){
        Map<String, Set<String>> resultMap = new HashMap<>();
        for(int i=0; i< sourceJsonArray.size(); i++){
            String classNbr = sourceJsonArray.getJSONObject(i).getString("ClassNbr");
            String[] termsText = sourceJsonArray.getJSONObject(i).getString("AlphaListTermText").split(";");
            Set<String> termsSet = Arrays.stream(termsText).filter(term -> term != null && !term.isEmpty()).map(term -> term.trim()).collect(Collectors.toSet());
            resultMap.put(classNbr, termsSet);
        }

        return resultMap;
    }
}
