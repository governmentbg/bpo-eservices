package eu.ohim.sp.common.ui.service.util;

import eu.ohim.sp.core.domain.classification.ClassificationHeading;
import eu.ohim.sp.core.domain.classification.LocarnoClassHeading;
import eu.ohim.sp.core.domain.classification.LocarnoSubClassHeading;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by munozjf on 05/02/19.
 */
public class LocarnoTranformer {
	
	/** The Constants. */
	private static final String CLASS_CODE = "ClassCode";
	private static final String SUB_CLASS_CODE = "SubClassCode";
	private static final String SUB_CLASS_DESCRIPTION = "SubClassDescription";
	private static final String LOCARNO_SUB_CLASSES = "LocarnoSubClasses";
	private static final String CLASSIFICATION = "Classification";
	private static final String LANGUAGE = "Language";


	/**
	 * Locarno classes.
	 *
	 * @param jsonsrc the jsonsrc
	 * @return the classification heading
	 */
	public static ClassificationHeading locarnoClasses(String jsonsrc) {
		JSON json = JSONSerializer.toJSON(jsonsrc);
		JSONObject obj = ((JSONArray) json).getJSONObject(0);
        ClassificationHeading ch = locarnoClassesFromJsonObject(obj);
        return ch;
	}

	/**
	 * Locarno classes from json object.
	 *
	 * @param obj the obj
	 * @return the classification heading
	 */
	private static ClassificationHeading locarnoClassesFromJsonObject(JSONObject obj) {
		ClassificationHeading p = new ClassificationHeading();
        p.setLanguage((String)obj.get(LANGUAGE));
        p.setClassification(locarnoClassesFromJsonArray(
                (JSONArray) obj.get(CLASSIFICATION)));
        return p;
	}

	/**
	 * Locarno classes from json array.
	 *
	 * @param classification the classification
	 * @return the list
	 */
	private static List<LocarnoClassHeading> locarnoClassesFromJsonArray(JSONArray classification) {
		List<LocarnoClassHeading> classHeading = new ArrayList<>();
        for(int i=0;i<classification.size();i++)
        {
        	LocarnoClassHeading c = new LocarnoClassHeading();
            JSONObject obj = classification.getJSONObject(i);
            c.setClassCode(Integer.valueOf((String)obj.get(CLASS_CODE)));
            c.setClassDescription((String)obj.get(CLASS_CODE));
            c.setLocarnoSubClasses(locarnoSubClassesFromJsonArray((JSONArray)obj.get(LOCARNO_SUB_CLASSES)));
            classHeading.add(c);
        }
        return classHeading;
	}
	
	/**
	 * Locarno sub classes from json array.
	 *
	 * @param locarnoSubClasses the locarno sub classes
	 * @return the list
	 */
	private static List<LocarnoSubClassHeading> locarnoSubClassesFromJsonArray(JSONArray locarnoSubClasses) {
		List<LocarnoSubClassHeading> sch = new ArrayList<>();
		for(int i=0;i<locarnoSubClasses.size();i++)
        {
			LocarnoSubClassHeading c = new LocarnoSubClassHeading();
            JSONObject obj = locarnoSubClasses.getJSONObject(i);
            c.setSubClassCode(Integer.valueOf((String)obj.get(SUB_CLASS_CODE)));
            c.setDescription((String)obj.get(SUB_CLASS_DESCRIPTION));
            sch.add(c);
        }
        return sch;
	}

    /**
     * Gets the locarno sub class headings.
     *
     * @param locarnoClass the locarno class
     * @param classifications the classifications
     * @return the locarno sub class headings
     */
    public static List<LocarnoSubClassHeading> getLocarnoSubClassHeadings(Integer locarnoClass, ClassificationHeading classifications) {
        List<LocarnoSubClassHeading> ret = new ArrayList<>();
        if (classifications != null && classifications.getClassification() != null) {
            for (LocarnoClassHeading lch : classifications.getClassification()) {
                if (Objects.equals(lch.getClassCode(), locarnoClass)) {
                    if (lch.getLocarnoSubClasses() != null) {
                        ret.addAll(lch.getLocarnoSubClasses());
                    }
                    break;
                }
            }
        }
        return ret;
    }
	
}
