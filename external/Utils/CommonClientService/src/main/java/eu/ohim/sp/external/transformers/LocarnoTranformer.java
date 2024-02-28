package eu.ohim.sp.external.transformers;

import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoClassHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import eu.ohim.sp.core.domain.design.KeyTextUIClass;
import eu.ohim.sp.core.domain.design.TermLocarno;
import eu.ohim.sp.core.domain.design.TermsSearch;
import eu.ohim.sp.external.domain.design.ProductIndication;
import eu.ohim.sp.external.domain.design.ProductIndicationClass;
import eu.ohim.sp.external.domain.design.ProductIndicationTerm;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by marcoantonioalberoalbero on 11/10/16.
 */
public class LocarnoTranformer {

    //DS Class Integration changes start

    private static final Logger LOGGER = Logger.getLogger(LocarnoTranformer.class);
    private static final String CLASS_CODE = "ClassCode";

    private static Integer requestClass;
    private static String requestSearchInput;

    //DS Class Integration changes end

    /**
     * Lambda Convert JSON object from GET to ProductIndicationClass
     */
    private static Function<JSONObject, ProductIndicationClass> toProductIndicationClass = t -> {
        ProductIndicationClass pi = new ProductIndicationClass();
        pi.setDescription((String)t.get("description"));
        pi.setSubClass(String.valueOf(t.get("clasification")));
        return pi;
    };

    /**
     * Lambda Convert JSON object from SEARCH to Product indication class from
     */
    private static Function<JSONObject, ProductIndicationClass> toProductIndicationClassTerm = t -> {
        ProductIndicationClass pi = new ProductIndicationClass();
        pi.setMainClass((String)t.get("idclass"));
        pi.setSubClass((String)t.get("idsubclass"));
        pi.setTerms(toProductIndicationTerm(t));
        return pi;
    };

    /**
     * Convert JSON object to ProductIndicationTerm
     */
    private static List<ProductIndicationTerm> toProductIndicationTerm(JSONObject t) {
        ArrayList<ProductIndicationTerm> terms = new ArrayList();
        ProductIndicationTerm pi = new ProductIndicationTerm();
        pi.setIndprodID((String)t.get("idbasic"));
        pi.setText((String)t.get("indprod"));
        terms.add(pi);
        return terms;
    }

    /**
     * Convert a json array of product indication from GET to List
     * @param classes json array of product indication classes
     * @param parentClass parent class
     * @return List of product indication classes
     */
    private static  List<ProductIndicationClass> productIndicationClassesFromJsonArray(JSONArray classes, String parentClass) {
        List<ProductIndicationClass> pic = (List<ProductIndicationClass>)classes.stream()
                .map(e -> toProductIndicationClass.apply((JSONObject) e))
                .map(pi -> {
                    ((ProductIndicationClass)pi).setMainClass(parentClass);
                    return pi;
                })
                .collect(Collectors.toList());
        return pic;
    }

    /**
     * Convert a json array of product indication from SEARCH to List
     * @param classes
     * @return
     */
    private static List<ProductIndicationClass> classesWithTermsFromJsonArray(JSONArray classes) {
        List<ProductIndicationClass> pic = (List<ProductIndicationClass>)classes.stream()
                .map(e -> toProductIndicationClassTerm.apply((JSONObject) e))
                .collect(Collectors.toList());
        return pic;
    }

    /**
     * Convert a json object from GET to a product indication
     * @param obj json product indication
     * @return product indication java object
     */
    private static ProductIndication productIndicationFromJsonObject(JSONObject obj) {
        ProductIndication p = new ProductIndication();
        p.setDescription((String)obj.get("description"));
        p.setClasses(productIndicationClassesFromJsonArray(
                (JSONArray) obj.get("subclasses"),
                String.valueOf(obj.get("clasification"))));
        return p;
    }

    /**
     * Map product indication from json of GET to List
     * @param jsonsrc source
     * @return List of product indications.
     */
    public static List<ProductIndication> productIndications(String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        List<ProductIndication> pis = (List<ProductIndication>)((JSONArray) json).stream()
                .map(e -> {
                    ProductIndication p = productIndicationFromJsonObject((JSONObject)e);
                    return p;
                }).collect(Collectors.toList());
        return pis;
    }

    /**
     * Map product indication from json of SEARCH to List
     * @param jsonsrc
     * @return
     */
    public static List<ProductIndicationClass> productIndicationsClasses(String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        return classesWithTermsFromJsonArray((JSONArray)json);
    }

    //DS Class Integration changes start

    public static ClassificationHeading locarnoClasses(String jsonsrc) throws Exception {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONObject obj = ((JSONArray) json).getJSONObject(0);
        ClassificationHeading ch  = locarnoClassesFromJsonObject(obj);
        return ch;
    }

    private static ClassificationHeading locarnoClassesFromJsonObject(JSONObject obj) {
        ClassificationHeading p = new ClassificationHeading();
        p.setLanguage((String)obj.get("Language"));
        p.setClassification(locarnoClassesFromJsonArray(
            (JSONArray) obj.get("Classification")));
        return p;
    }



    private static List<LocarnoClassHeading> locarnoClassesFromJsonArray(JSONArray classification) {

        List<LocarnoClassHeading> classHeading = new ArrayList<>();
        for(int i=0;i<classification.size();i++)
        {
            LocarnoClassHeading c = new LocarnoClassHeading();
            JSONObject obj = classification.getJSONObject(i);
            c.setClassCode(Integer.valueOf((String)obj.get(CLASS_CODE)));
            c.setClassDescription((String)obj.get(CLASS_CODE));
            c.setLocarnoSubClasses(locarnoSubClassesFromJsonArray((JSONArray)obj.get("LocarnoSubClasses")));
            classHeading.add(c);
        }
        return classHeading;

    }

    private static List<LocarnoSubClassHeading> locarnoSubClassesFromJsonArray(JSONArray locarnoSubClasses) {
        List<LocarnoSubClassHeading> sch = new ArrayList<>();
        for(int i=0;i<locarnoSubClasses.size();i++)
        {
            LocarnoSubClassHeading c = new LocarnoSubClassHeading();
            JSONObject obj = locarnoSubClasses.getJSONObject(i);
            c.setSubClassCode(Integer.valueOf((String)obj.get("SubClassCode")));
            c.setDescription((String)obj.get("SubClassDescription"));
            sch.add(c);
        }
        return sch;
    }

    private static Function<JSONObject, LocarnoSubClassHeading> tolocarnoSubClass = t -> {
        LocarnoSubClassHeading pi = new LocarnoSubClassHeading();
        pi.setSubClassCode((Integer)t.get("SubClassCode"));
        pi.setDescription(String.valueOf(t.get("SubClassDescription")));
        return pi;
    };

    public static String translateDesignClassResponse(String designClassReponse) {
        StringBuilder textToReturn = new StringBuilder(designClassReponse);
        searchAndReplace(textToReturn, "\"Identifier\"", "\"identifier\"");
        searchAndReplace(textToReturn, "\"Text\"", "\"text\"");
        searchAndReplace(textToReturn, "\"ClassCode\"", "\"classCode\"");
        searchAndReplace(textToReturn, "\"SubClassCode\"", "\"subclassCode\"");
        return textToReturn.toString();
    }

    private static void searchAndReplace(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            // Move to the end of the replacement
            index += to.length();
            index = builder.indexOf(from, index);
        }
    }
    public static List<KeyTextUIClass> parsePIClasses(String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        if(((JSONArray)json).size()>0 && ((JSONArray)json).getJSONObject(0).has("Classification")){
            jsonArray=((JSONArray)json).getJSONObject(0).getJSONArray("Classification");
        }
        List<KeyTextUIClass> pic = (List<KeyTextUIClass>)jsonArray.stream()
            .map(e -> toCateFromJson.apply((JSONObject) e))
            .collect(Collectors.toList());
        return pic;
    }

    /**
     * Lambda Convert JSON object from SEARCH to Product indication class from
     */
    private static Function<JSONObject, KeyTextUIClass> toCateFromJson = t -> {
        KeyTextUIClass pi = new KeyTextUIClass();
        pi.setKey((Object)t.get(CLASS_CODE));
        pi.setText((String)t.get(CLASS_CODE));
        return pi;
    };
    /**
     * Lambda Convert JSON object from SEARCH to Product indication class from
     */
    private static Function<JSONObject, KeyTextUIClass> toSubCateFromJson = t -> {
        KeyTextUIClass pi = new KeyTextUIClass();
        pi.setKey((Object)t.get("SubClassCode"));
        pi.setText((String)t.get("SubClassCode"));
        return pi;
    };


    public static TermsSearch parseSearchTerms(Integer selectedClass, String jsonsrc, String searchInput) {
        requestClass = selectedClass;
        requestSearchInput = searchInput;
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        JSONObject objJson=(JSONObject)json;

        TermsSearch ret= new TermsSearch();

        if(json!=null && ((JSONObject)json).has("SearchTermResults") && ((JSONObject)json).getInt("TotalNumTerms")>0){
            jsonArray=((JSONObject)json).getJSONArray("SearchTermResults");
            ret.setTotalResults(((JSONObject)json).getInt("TotalNumTerms"));
        }


        if(CollectionUtils.isNotEmpty(jsonArray)){
            List<TermLocarno> terms = (List<TermLocarno>)jsonArray.stream()
                .map(e -> toTermsFromJson.apply((JSONObject) e))
                .collect(Collectors.toList());
            ret.setResults(terms);
        }else{
            ret.setResults(Collections.emptyList());
            ret.setTotalResults(0);
        }
        return ret;
    }

    /**
     * Lambda Convert JSON object for term search
     *
     */
    private static Function<JSONObject, TermLocarno> toTermsFromJson = t -> {
        TermLocarno tl = new TermLocarno();

        if(t.has("Classification")){
            String cls=(String)t.get("Classification");
            String[] array =cls.split("\\.");
            if(array.length>0){
                tl.setClassCode((array[0].length() == 1) ? "0"+array[0] : array[0]);
                tl.setSubclassCode((array[1].length() == 1) ? "0"+array[1] : array[1]);
                if(requestClass == -1 && StringUtils.isBlank(requestSearchInput)) {
                    tl.setSubclassCode(null);
                }
                if(t.has("TermDetails") && t.get("TermDetails")!=null){
                    boolean isGroup="Group Titles".equals(((JSONObject)((JSONArray)t.get("TermDetails")).get(0)).get("SourceName"));
                    tl.setGroup(isGroup);
                }
            }
        }

        tl.setIdentifier((String)t.get("Identifier"));
        tl.setText((String)t.get("TermText"));
        return tl;
    };
    /**
     * function for browse taxonomy
     *
     */
    public static TermsSearch parseBrowseTerms(String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        if(json!=null && ((JSONObject)json).has("Classes") && ((JSONObject)json).get("Classes")!=null){
            jsonArray=((JSONObject)json).getJSONArray("Classes");
        }
        TermsSearch ret= new TermsSearch();
        ret.setTotalResults(jsonArray.size());
        List<TermLocarno> terms = (List<TermLocarno>)jsonArray.stream()
            .map(e -> toBrowseTaxonomyFromJson.apply((JSONObject) e))
            .collect(Collectors.toList());
        ret.setResults(terms);
        return ret;
    }
    /**
     * Lambda Convert JSON object for browse taxonomy
     *
     */
    private static Function<JSONObject, TermLocarno> toBrowseTaxonomyFromJson = t -> {
        TermLocarno tl = new TermLocarno();

        if(t.has(CLASS_CODE) && t.has("ClassDescription")){
            String classCode=(String)t.get(CLASS_CODE);
            tl.setClassCode((classCode.length() == 1) ? "0"+classCode : classCode);
            tl.setText((String)t.get("ClassDescription"));

        }
        return tl;
    };
    /**
     * function for taxonomy tree
     *
     */
    public static TermsSearch parseTaxonomyTree(Integer selectedClass, String jsonsrc) {
        requestClass = selectedClass;
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        if(json!=null && ((JSONObject)json).has("Taxonomy") && ((JSONObject)json).get("Taxonomy")!=null){
            if(((JSONObject)json).getJSONArray("Taxonomy").size()>0){
                JSONObject jObj=((JSONObject)json).getJSONArray("Taxonomy").getJSONObject(0);
                if(jObj!=null && jObj.has("SubClasses") && jObj.get("SubClasses")!=null){
                    jsonArray=jObj.getJSONArray("SubClasses");
                }
            }
        }
        TermsSearch ret= new TermsSearch();
        ret.setTotalResults(jsonArray.size());
        List<TermLocarno> terms = (List<TermLocarno>)jsonArray.stream()
            .map(e -> toTaxonomyTreeFromJson.apply((JSONObject) e))
            .collect(Collectors.toList());
        ret.setResults(terms);
        return ret;
    }
    /**
     * Lambda Convert JSON object for taxonomy tree
     *
     */
    private static Function<JSONObject, TermLocarno> toTaxonomyTreeFromJson = t -> {
        TermLocarno tl = new TermLocarno();
        String classCode=requestClass.toString();
        if(t!=null && t.has("SubClassCode")){
            String subClassCode=(String)t.get("SubClassCode");
            tl.setSubclassCode(StringUtils.isBlank(subClassCode) ? "00" : (subClassCode.length() == 1) ? "0"+subClassCode : subClassCode);
            tl.setClassCode(StringUtils.isBlank(classCode) ? "00" : (classCode.length() == 1) ? "0"+classCode : classCode);
            tl.setText((String)t.get("SubClassDescription"));
        }
        return tl;
    };

    public static List<KeyTextUIClass> parseSubClasses(Integer selectedClass, String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        if(((JSONArray)json).size()>0 && ((JSONArray)json).getJSONObject(0).has("Classification")){
            jsonArray=((JSONArray)json).getJSONObject(0).getJSONArray("Classification");
        }
        JSONArray subCatArray= new JSONArray();
        for (Object item : jsonArray) {
            JSONObject obj=(JSONObject)item;
            Integer classCode=Integer.parseInt((String)obj.get(CLASS_CODE));
            if(classCode.equals(selectedClass)){
                subCatArray=(JSONArray)obj.getJSONArray("LocarnoSubClasses");
            }
        }
        List<KeyTextUIClass> pic = (List<KeyTextUIClass>)subCatArray.stream()
            .map(e -> toSubCateFromJson.apply((JSONObject) e))
            .collect(Collectors.toList());
        return pic;
    }

    public static List<KeyTextUIClass> parseTranslation(String jsonsrc) {
        JSON json = JSONSerializer.toJSON(jsonsrc);
        JSONArray jsonArray= new JSONArray();
        JSONArray subCatArray= new JSONArray();

        String termResults = ((JSONObject)json).getString("TranslateTermResults");

        if(json!=null && ((JSONObject)json).has("TranslateTermResults") && !termResults.equals("null")) {
            jsonArray=((JSONObject)json).getJSONArray("TranslateTermResults");
        }

        if(jsonArray.size()>0 && (jsonArray.getJSONObject(0).has("TermTranslations"))){
            subCatArray = jsonArray.getJSONObject(0).getJSONArray("TermTranslations");
        }
        List<KeyTextUIClass> terms = null;
        if(CollectionUtils.isNotEmpty(subCatArray)){
            terms = (List<KeyTextUIClass>)subCatArray.stream()
                .map(e -> toTranslationFromJson.apply((JSONObject) e))
                .collect(Collectors.toList());
        }else
            terms = new ArrayList<KeyTextUIClass>();
        return terms;

    }

    /**
     * Lambda Convert JSON object for translation
     *
     */
    private static Function<JSONObject, KeyTextUIClass> toTranslationFromJson = t -> {
        KeyTextUIClass tl = new KeyTextUIClass();
        tl.setKey((String)t.get("LanguageCode"));
        tl.setText((String)t.get("TermText"));
        return tl;
    };

    //DS Class Integration changes end
}
